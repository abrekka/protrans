package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.Model;

public class TrossReadyViewHandler implements Closeable {
	private boolean canceled;
	private List<String> userNameList;
	private PresentationModel presentationModel;
	private Login login;

	@Inject
	public TrossReadyViewHandler(ManagerRepository aManagerRepository,
			Order order, CostType costTypeTross, CostUnit costUnitTross,
			final Login aLogin) {
		login = aLogin;
		userNameList = aManagerRepository.getApplicationUserManager()
				.findAllNamesNotGroup();
		presentationModel = new PresentationModel(new TrossReady(order,
				costTypeTross, costUnitTross));
	}

	public JButton getButtonOk(WindowInterface window) {
		JButton button = new CancelButton(window, this, false, "Ok",
				IconEnum.ICON_OK, null, true);
		button.setName("ButtonOk");
		return button;
	}

	public JButton getButtonCancel(WindowInterface window) {
		JButton button = new CancelButton(window, this, true);
		button.setName("ButtonCancel");
		return button;
	}

	public boolean canClose(String actionString, WindowInterface window) {
		if (!actionString.equalsIgnoreCase("Ok")) {
			canceled = true;
		}
		return true;
	}

	public JComboBox getComboBoxUsers() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(userNameList,
				presentationModel.getModel(TrossReady.PROPERTY_DRAWER)));
		comboBox.setName("ComboBoxUsers");
		if (presentationModel.getValue(TrossReady.PROPERTY_DRAWER) == null) {
			presentationModel.setValue(TrossReady.PROPERTY_DRAWER, login
					.getApplicationUser().getFullName());
		}
		return comboBox;
	}

	public class TrossReady extends Model {
		private static final long serialVersionUID = 1L;
		public static final String PROPERTY_DRAWER = "drawer";
		public static final String PROPERTY_FINSIH = "finish";
		public static final String PROPERTY_TROSS_COST = "trossCost";
		public static final String PROPERTY_ORDER = "order";

		private Order currentOrder;
		private OrderCost trossOrderCost;
		private CostType costTypeTross;
		private CostUnit costUnitTross;

		public TrossReady(Order aOrder, CostType aCostTypeTross,
				CostUnit aCostUnitTross) {
			costTypeTross = aCostTypeTross;
			costUnitTross = aCostUnitTross;
			currentOrder = aOrder;
			trossOrderCost = currentOrder.getOrderCost("Takstoler", "Intern");
		}

		public String getDrawer() {
			return currentOrder.getTrossDrawer();
		}

		public void setDrawer(String drawer) {
			String oldDrawer = getDrawer();
			currentOrder.setTrossDrawer(drawer);
			firePropertyChange(PROPERTY_DRAWER, oldDrawer, drawer);
		}

		public Integer getFinish() {
			return currentOrder.getTrossReady() != null ? 1 : 0;
		}

		public void setFinish(Integer finish) {
			Integer oldInt = getFinish();
			Date trossReadyDate = finish == 1 ? Util.getCurrentDate() : null;
			currentOrder.setTrossReady(trossReadyDate);
			if (currentOrder.getProductAreaGroup().getProductAreaGroupName()
					.equalsIgnoreCase("Takstol")) {
				currentOrder.setPacklistReady(trossReadyDate);
			}
			Date trossStartDate = currentOrder.getTrossStart();
			if (trossStartDate == null) {
				trossStartDate = Util.getCurrentDate();
			}
			currentOrder.setTrossStart(trossStartDate);
			firePropertyChange(PROPERTY_FINSIH, oldInt, finish);
		}

		public String getTrossCost() {
			return trossOrderCost != null ? trossOrderCost.getCostAmount()
					.toString() : null;

		}

		public void setTrossCost(String trossCostString) {
			String oldCost = getTrossCost();
			if (trossOrderCost == null) {
				trossOrderCost = new OrderCost();

				trossOrderCost.setCostType(costTypeTross);
				trossOrderCost.setCostUnit(costUnitTross);

			}
			trossOrderCost.setCostAmount(trossCostString != null
					&& trossCostString.length() != 0 ? BigDecimal
					.valueOf(Double.valueOf(trossCostString)) : null);
			firePropertyChange(PROPERTY_TROSS_COST, oldCost, trossCostString);
		}

		public Order getOrder() {
			if (trossOrderCost != null
					&& trossOrderCost.getOrderCostId() == null) {
				currentOrder.addOrderCost(trossOrderCost);
			}
			return currentOrder;
		}
	}

	public JRadioButton getRadioButtonStart() {
		JRadioButton radioButton = BasicComponentFactory.createRadioButton(
				presentationModel.getModel(TrossReady.PROPERTY_FINSIH), 0,
				"Påbegynt");
		radioButton.setName("RadioButtonStart");
		return radioButton;
	}

	public JRadioButton getRadioButtonFinsih() {
		JRadioButton radioButton = BasicComponentFactory.createRadioButton(
				presentationModel.getModel(TrossReady.PROPERTY_FINSIH), 1,
				"Ferdig");
		radioButton.setName("RadioButtonFinish");
		return radioButton;
	}

	public JTextField getTextFieldCost() {
		return BasicComponentFactory.createTextField(presentationModel
				.getModel(TrossReady.PROPERTY_TROSS_COST));
	}

	public boolean getCanceled() {
		return canceled;
	}

	public Order getOrder() {
		return (Order) presentationModel.getValue(TrossReady.PROPERTY_ORDER);
	}
}
