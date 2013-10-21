package no.ugland.utransprod.gui.model;

import org.apache.commons.lang.StringUtils;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.ApplicationUserDAO;
import no.ugland.utransprod.dao.BudgetDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

public enum BudgetType {
	PRODUCTION(BudgetManager.FILE_HEADER_IMPORT_BUDGET,4) {
		@Override
		public Budget validateAndGetBudget(ExcelUtil excelUtil, int row,Integer year, ProductArea productArea, BudgetDAO budgetDao, ApplicationUserDAO applicationUserDao) throws ProTransException {
			return validateProductionAndSale(excelUtil, row, year, productArea,
					budgetDao,BudgetType.PRODUCTION);
			
		}

		@Override
		public int getProductAreaColumn() {
			return BudgetManager.COLUMN_PRODUCT_AREA;
		}

		

		
	}, 
	SALE(BudgetManager.FILE_HEADER_IMPORT_BUDGET,4) {
		@Override
		public Budget validateAndGetBudget(ExcelUtil excelUtil, int row, Integer year,
				ProductArea productArea, BudgetDAO budgetDao, ApplicationUserDAO applicationUserDao) throws ProTransException {
			return validateProductionAndSale(excelUtil, row, year, productArea,
					budgetDao,BudgetType.SALE);
			
		}

		@Override
		public int getProductAreaColumn() {
			return BudgetManager.COLUMN_PRODUCT_AREA;
		}
	}, 
	SALESMAN(BudgetManager.FILE_HEADER_IMPORT_BUDGET_SALESMAN,3) {
		@Override
		public Budget validateAndGetBudget(ExcelUtil excelUtil, int row, Integer year,
				ProductArea productArea, BudgetDAO budgetDao, ApplicationUserDAO applicationUserDao) throws ProTransException {
			String salesman = excelUtil.readCell(row, BudgetManager.COLUMN_SALESMAN,null);
			
			Budget budget = budgetDao.findByYearAndSalesman(year, salesman, productArea,BudgetType.SALESMAN);

	        if (budget != Budget.UNKNOWN) {
	            throw new ProTransException("Det finnes allerede budsjett for " + year + " selger " + salesman
	                    + " produktområde " + productArea);
	        }
	        budget = new Budget();
	        
	        ApplicationUser applicationUser = getApplicationUser(
					applicationUserDao, salesman);

	        budget.setApplicationUser(applicationUser);
	        budget.setBudgetValue(excelUtil.readCellAsBigDecimal(row, BudgetManager.COLUMN_BUDGET));
	        budget.setBudgetValueOffer(excelUtil.readCellAsBigDecimal(row, BudgetManager.COLUMN_BUDGET_OFFER));
	        budget.setBudgetYear(year);
	        budget.setProductArea(productArea);
	        budget.setBudgetType(BudgetType.SALESMAN.ordinal());
	        return budget;
		}

		private ApplicationUser getApplicationUser(
				ApplicationUserDAO applicationUserDao, String salesman) {
			ApplicationUser applicationUser = applicationUserDao.findByFullName(salesman);
	        if(applicationUser==ApplicationUser.UNKNOWN){
	        	applicationUser=new ApplicationUser();
	        	int lastNameStart = StringUtils.lastIndexOf(salesman, " ");
	        	String lastName = StringUtils.substring(salesman, lastNameStart+1);
	        	String firstName=StringUtils.substring(salesman, 0, lastNameStart);
	        	applicationUser.setFirstName(firstName);
	        	applicationUser.setLastName(lastName);
	        	applicationUser.setPassword("passord");
	        	applicationUser.setUserName(Util.getCurrentDateAsDateTimeString());
	        	applicationUserDao.saveObject(applicationUser);
	        	
	        		
	        }
			return applicationUser;
		}

		@Override
		public int getProductAreaColumn() {
			return BudgetManager.COLUMN_PRODUCT_AREA_BUDGET_SALESMAN;
		}
	};
	private String headerFormat;
	private int yearColumn;

	private BudgetType(String aHeaderFormat,int yearColumn) {
		headerFormat = aHeaderFormat;
		this.yearColumn=yearColumn;
	}

	public String getHeaderFormat() {
		return headerFormat;
	}
	
	private static Budget validateProductionAndSale(ExcelUtil excelUtil, int row,
			Integer year, ProductArea productArea, BudgetDAO budgetDao, BudgetType budgetType)
			throws ProTransException {
		Integer week = excelUtil.readCellAsInteger(row, BudgetManager.COLUMN_WEEK);
        
		Budget budget = budgetDao.findByYearAndWeek(year, week, productArea,budgetType);

        if (budget != Budget.UNKNOWN) {
            throw new ProTransException("Det finnes allerede budsjett for " + year + " uke " + week
                    + " produktområde " + productArea);
        }
        
        budget = new Budget();

        budget.setBudgetWeek(week);
        budget.setBudgetValue(excelUtil.readCellAsBigDecimal(row, BudgetManager.COLUMN_BUDGET));
        budget.setBudgetHours(excelUtil.readCellAsBigDecimal(row, BudgetManager.COLUMN_HOURS));
        budget.setBudgetYear(year);
        budget.setProductArea(productArea);
        budget.setBudgetType(budgetType.ordinal());
        return budget;
	}

	public abstract Budget validateAndGetBudget(ExcelUtil excelUtil, int row,Integer year, ProductArea productArea, BudgetDAO budgetDao, ApplicationUserDAO applicationUserDao)throws ProTransException;

	public int getYearColumn() {
		return yearColumn;
	}

	public abstract int getProductAreaColumn();

	
}
