ALTER VIEW [dbo].[import_order_prob_100_v]
AS
SELECT     superoffice.crm7.sale.sale_id, superoffice.crm7.sale.number1, superoffice.crm7.sale.amount, superoffice.crm7.sale.saledate, 
                      superoffice.crm7.sale.userdef_id, superoffice.crm7.sale.registered, superoffice.crm7.sale.probability, 
                      superoffice.crm7.sale.contact_id, 
                      F0100.dbo.Ord.R1 AS product_area_nr, 
                      ISNULL(F0100.dbo.Ord.DelAd3, '') + ' ' + ISNULL(F0100.dbo.Ord.DelAd4, '') AS delivery_address, 
                      F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man, 
                      F0100.dbo.Ord.Inf3 AS telephonenr_site, Intelle_Ordre.dbo.IPK_Ord.Max_Hoyde AS maks_hoyde
FROM         superoffice.crm7.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm7.sale.number1 = F0100.dbo.Ord.Inf6 INNER JOIN
                      dbo.product_area ON dbo.product_area.product_area_nr = F0100.dbo.Ord.R1 AND 
                      superoffice.crm7.sale.amount > dbo.product_area.own_production_cost_limit LEFT OUTER JOIN
                      Intelle_Ordre.dbo.IPK_Ord ON 'SO-' + superoffice.crm7.sale.number1 = Intelle_Ordre.dbo.IPK_Ord.OrdNo
WHERE     (F0100.dbo.Ord.R1 IN (100, 200, 300, 400, 210)) AND (superoffice.crm7.sale.saledate > '2012-07-01') AND (superoffice.crm7.sale.probability = 100) AND 
                      (superoffice.crm7.sale.number1 <> '') 
                      AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm7.sale.number1)))

################################################################################################################################################################
ALTER VIEW [dbo].[drawer_v]
AS
SELECT     superoffice.crm7.contact.number2 AS customer_nr, superoffice.crm7.sale.number1 AS order_nr, ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, 
                      F0100.dbo.Ord.R1) AS product_area_nr, superoffice.crm7.contact.name AS customer_name, 
                      superoffice.crm7.doctmpl.name AS category_name, 
                      superoffice.crm7.appointment.registered, superoffice.crm7.udsalesmall.double05 AS own_production, 
                      superoffice.crm7.associate.name AS drawer_name, superoffice.crm7.[document].document_id
FROM         superoffice.crm7.appointment INNER JOIN
                      superoffice.crm7.doctmpl ON superoffice.crm7.appointment.task_idx = superoffice.crm7.doctmpl.DocTmpl_id INNER JOIN
                      superoffice.crm7.contact ON superoffice.crm7.appointment.contact_id = superoffice.crm7.contact.contact_id INNER JOIN
                      superoffice.crm7.sale ON superoffice.crm7.contact.contact_id = superoffice.crm7.sale.contact_id INNER JOIN
                      superoffice.crm7.[document] ON superoffice.crm7.[document].document_id = superoffice.crm7.appointment.document_id INNER JOIN
                      superoffice.crm7.relations ON superoffice.crm7.relations.source_record = superoffice.crm7.[document].document_id AND 
                      superoffice.crm7.relations.destination_record = superoffice.crm7.sale.sale_id INNER JOIN
                      superoffice.crm7.associate ON superoffice.crm7.associate.associate_id = superoffice.crm7.appointment.associate_id INNER JOIN
                      superoffice.crm7.udsalesmall ON superoffice.crm7.udsalesmall.udsalesmall_id = superoffice.crm7.sale.userdef_id LEFT OUTER JOIN
                      Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = superoffice.crm7.sale.number1 LEFT OUTER JOIN
                      F0100.dbo.Ord ON superoffice.crm7.sale.number1 = F0100.dbo.Ord.Inf6
WHERE     (superoffice.crm7.doctmpl.DocTmpl_id IN (102, 103, 153, 154, 166)) AND (superoffice.crm7.relations.source_table = 10) AND 
                      (superoffice.crm7.relations.destination_table = 13)

################################################################################################################################################################

ALTER view [dbo].[import_order_prob_90_v]
as
SELECT		superoffice.crm7.sale.sale_id, 
			superoffice.crm7.sale.number1, 
			superoffice.crm7.sale.amount, 
			superoffice.crm7.sale.saledate, 
            superoffice.crm7.sale.userdef_id, 
			superoffice.crm7.sale.registered, 
			superoffice.crm7.sale.probability, 
			superoffice.crm7.sale.contact_id, 
            Intelle_ordre.dbo.word.DeptNo AS product_area_nr, --Varchar(50), null
			isnull(Intelle_ordre.dbo.word.DelAd1,'') + ' ' + isnull(Intelle_ordre.dbo.word.DelAd2,'') AS delivery_address, --Begge: Varchar(40), null
            Intelle_ordre.dbo.word.DelZip AS postal_code, --Varchar(10), null
			Intelle_ordre.dbo.word.DelCity AS postoffice, --Varchar(40), null
			Intelle_ordre.dbo.word.OurRef AS sales_man,	--Varchar(50), null
			Intelle_ordre.dbo.word.delPhone as telephonenr_site,
superoffice.crm7.contact.number2 as customer_nr,
superoffice.crm7.contact.name as customer_name,
superoffice.crm7.udsalesmall.double05 as own_production,
superoffice.crm7.udsalesmall.double06 as delivery_cost,
intelle_Ordre.dbo.IPK_ord.max_hoyde as maks_hoyde
FROM         superoffice.crm7.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm7.sale.number1 = Intelle_ordre.dbo.word.sosaleno left outer join
			Intelle_ordre.dbo.IPK_ord ON 'SO-'+superoffice.crm7.sale.number1 = Intelle_ordre.dbo.IPK_ord.ordno  inner join
			superoffice.crm7.contact on superoffice.crm7.contact.contact_id=superoffice.crm7.sale.contact_id inner join
superoffice.crm7.udsalesmall on superoffice.crm7.udsalesmall.udsalesmall_id=superoffice.crm7.sale.userdef_id
WHERE     (superoffice.crm7.sale.saledate > '2009-08-19') 
			AND (superoffice.crm7.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm7.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm7.sale.number1 collate Danish_Norwegian_CI_AS)))
			AND Intelle_ordre.dbo.word.deptno in (300,400) --Takstol og byggelement


################################################################################################################################################################

ALTER VIEW [dbo].[leveringsoversikt_element]
AS
SELECT     dbo.Customer_order.Order_nr, dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS name, dbo.Transport.Transport_Week, 
                      dbo.Transport.Transport_Year, DATEPART(ww, dbo.Customer_order.production_date) - 1 AS ProdUke, DATEPART(WW, 
                      dbo.Customer_order.packlist_ready) - 1 AS PakklisteKlar,
                          (SELECT     SUM(F0100.dbo.OrdLn.NoInvoAb) AS Expr1
                            FROM          F0100.dbo.OrdLn INNER JOIN
                                                   F0100.dbo.Ord ON F0100.dbo.Ord.OrdNo = F0100.dbo.OrdLn.OrdNo
                            WHERE      (F0100.dbo.Ord.Inf6 = dbo.Customer_order.Order_nr) AND (F0100.dbo.OrdLn.ProdNo = 'PRODUKSJONSKOSTELEMENT')) AS timeforbruk, 
                      750 AS tilgjTimerPrUKe,
                          (SELECT     superoffice.crm7.text.text
                            FROM          superoffice.crm7.sale INNER JOIN
                                                   superoffice.crm7.project ON superoffice.crm7.project.project_id = superoffice.crm7.sale.project_id INNER JOIN
                                                   superoffice.crm7.text ON superoffice.crm7.text.text_id = superoffice.crm7.project.text_id
                            WHERE      (superoffice.crm7.sale.number1 = dbo.Customer_order.Order_nr)) AS Kommentar,
                          (SELECT     budget_value
                            FROM          dbo.budget
                            WHERE      (dbo.Transport.Transport_Week = budget_week) AND (dbo.Transport.Transport_Year = budget_year) AND (product_area_id = 2) AND 
                                                   (budget_type = 0)) AS budsjettprod, dbo.Order_cost.Cost_Amount, dbo.oed_Timeforbruk_produksjonsuke.TimeforbrukPrUke, 
                      dbo.Customer_order.production_basis
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer.Customer_id = dbo.Customer_order.Customer_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Transport.Transport_id = dbo.Customer_order.Transport_id INNER JOIN
                      dbo.Order_cost ON dbo.Order_cost.Order_id = dbo.Customer_order.Order_id LEFT OUTER JOIN
                      dbo.oed_Timeforbruk_produksjonsuke ON dbo.oed_Timeforbruk_produksjonsuke.ProdUke = DATEPART(ww, dbo.Customer_order.production_date)
WHERE     (dbo.Customer_order.product_area_id = 2) AND (dbo.Transport.Sent IS NULL) AND (dbo.Order_cost.Cost_type_id = 2) AND 
                      (dbo.Order_cost.Cost_unit_id = 1) AND (dbo.Customer_order.order_complete IS NULL)


################################################################################################################################################################

ALTER view [dbo].[sale_status_v] as
select    superoffice.crm7.contact.number2 as customer_nr, 
                superoffice.crm7.contact.name as customer_name, 
                superoffice.crm7.sale.number1 as order_nr, 
                superoffice.crm7.sale.amount, 
                superoffice.crm7.sale.probability,
                superoffice.crm7.sale.saledate,
                superoffice.crm7.udsalesmall.long08 as build_date,
                isnull(superoffice.crm7.udlist.name,'') as sale_status, 
                superoffice.crm7.associate.name as salesman, 
                Intelle_ordre.dbo.word.deptno
   ,superoffice.crm7.udsalesmall.double05 as own_production
from      superoffice.crm7.sale inner join
                               superoffice.crm7.contact on superoffice.crm7.contact.contact_id = superoffice.crm7.sale.contact_id inner join
                               superoffice.crm7.associate on superoffice.crm7.associate.associate_id = superoffice.crm7.contact.associate_id left join
                               superoffice.crm7.udsalesmall on superoffice.crm7.sale.userdef_id = superoffice.crm7.udsalesmall.udsalesmall_id left join
                               superoffice.crm7.udlist on superoffice.crm7.udlist.udlist_id = superoffice.crm7.udsalesmall.long06 left join
                               Intelle_ordre.dbo.word on Intelle_ordre.dbo.word.sosaleno = superoffice.crm7.sale.number1
where  probability in (90,30)

################################################################################################################################################################

ALTER VIEW [dbo].[sales_v]
AS
SELECT DISTINCT 
                      superoffice.crm7.sale.sale_id, superoffice.crm7.sale.number1 AS order_nr, 
                      superoffice.crm7.sale.probability, ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1) AS product_area_nr, 
                      superoffice.crm7.sale.saledate, superoffice.crm7.sale.registered, 
                      superoffice.crm7.contact.number2 AS customer_nr, superoffice.crm7.contact.name AS customer_name, 
                      superoffice.crm7.address.address1 AS delivery_address, 
                      Intelle_Ordre.dbo.wOrd.DelZip AS postal_code, superoffice.crm7.address.city AS post_office, 
                      superoffice.crm7.udsalesmall.double05 AS own_production_cost, superoffice.crm7.udsalesmall.double06 AS transport_cost, 
                      superoffice.crm7.udsalesmall.double07 AS assembly_cost, superoffice.crm7.udsalesmall.double08 AS yes_lines, 
                      superoffice.crm7.udsalesmall.double09 AS contribution_margin, superoffice.crm7.udsalesmall.long03 AS order_date, 
                      dbo.county.county_name, 
                      ISNULL(Intelle_Ordre.dbo.wOrd.OurRef, F0100.dbo.Ord.OurRef) AS salesman
FROM         superoffice.crm7.sale INNER JOIN
                      superoffice.crm7.udsalesmall ON superoffice.crm7.sale.userdef_id = superoffice.crm7.udsalesmall.udsalesmall_id LEFT OUTER JOIN
                      Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = superoffice.crm7.sale.number1 LEFT OUTER JOIN
                      F0100.dbo.Ord ON superoffice.crm7.sale.number1 = F0100.dbo.Ord.Inf6 LEFT OUTER JOIN
                      superoffice.crm7.address ON superoffice.crm7.address.owner_id = superoffice.crm7.sale.contact_id LEFT OUTER JOIN
                      superoffice.crm7.contact ON superoffice.crm7.contact.contact_id = superoffice.crm7.sale.contact_id LEFT OUTER JOIN
                      dbo.transport_cost ON Intelle_Ordre.dbo.wOrd.DelZip = dbo.transport_cost.postal_code LEFT OUTER JOIN
                      dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
                      dbo.county ON dbo.county.county_nr = dbo.area.county_nr
WHERE     (superoffice.crm7.address.atype_idx = 2) AND (superoffice.crm7.sale.probability > 0) AND EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.product_area
                            WHERE      (product_area_nr = ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1)) 
                            AND (ISNULL(superoffice.crm7.udsalesmall.double05, 0) 
                                                   > own_production_cost_limit)) AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          superoffice.crm7.sale AS S LEFT OUTER JOIN
                                                   superoffice.crm7.udsalesmall AS udsalesmall_1 ON superoffice.crm7.sale.userdef_id = udsalesmall_1.udsalesmall_id
                            WHERE      (udsalesmall_1.long06 IN (175, 176)) AND (S.probability = 30) AND (S.number1 = superoffice.crm7.sale.number1))) AND 
                      (superoffice.crm7.contact.number2 <> '743796')


################################################################################################################################################################


ALTER VIEW [dbo].[takstol_probability_90_v]
AS
SELECT		superoffice.crm7.sale.sale_id, 
			superoffice.crm7.sale.number1, 
			superoffice.crm7.sale.amount, 
			superoffice.crm7.sale.saledate, 
            superoffice.crm7.sale.userdef_id, 
			superoffice.crm7.sale.registered, 
			superoffice.crm7.sale.probability, 
			superoffice.crm7.sale.contact_id, 
            Intelle_ordre.dbo.word.DeptNo AS product_area_nr, --Varchar(50), null
			Intelle_ordre.dbo.word.DelAd1 + ' ' + Intelle_ordre.dbo.word.DelAd2 AS delivery_address, --Begge: Varchar(40), null
            Intelle_ordre.dbo.word.DelZip AS postal_code, --Varchar(10), null
			Intelle_ordre.dbo.word.DelCity AS postoffice, --Varchar(40), null
			Intelle_ordre.dbo.word.OurRef AS sales_man,	--Varchar(50), null
			Intelle_ordre.dbo.word.delPhone as telephonenr_site,
superoffice.crm7.contact.number2 as customer_nr,
superoffice.crm7.contact.name as customer_name,
isnull(superoffice.crm7.udsalesmall.double05,0) as own_production,
superoffice.crm7.udsalesmall.double06 as delivery_cost
FROM         superoffice.crm7.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm7.sale.number1 = Intelle_ordre.dbo.word.sosaleno  inner join
			superoffice.crm7.contact on superoffice.crm7.contact.contact_id=superoffice.crm7.sale.contact_id inner join
superoffice.crm7.udsalesmall on superoffice.crm7.udsalesmall.udsalesmall_id=superoffice.crm7.sale.userdef_id
WHERE     (superoffice.crm7.sale.saledate > '2009-08-19') 
			AND (superoffice.crm7.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm7.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm7.sale.number1 collate Danish_Norwegian_CI_AS and probability=100)))
			AND Intelle_ordre.dbo.word.deptno in (300) --Takstol og byggelement


################################################################################################################################################################
ALTER view [dbo].[import_order_v]
as
select sale_id,number1,amount,saledate,userdef_id,registered,probability,contact_id,product_area_nr,delivery_address,postal_code,postoffice,sales_man,telephonenr_site,maks_hoyde
from import_order_prob_90_v
union
select * from import_order_prob_100_v

################################################################################################################################################################


alter table sales_data_snapshot add registered_date datetime;

alter table sales_data_snapshot add saledate_date datetime;


update sales_data_snapshot set registered_date=dateadd(s, registered, '19700101'),saledate_date=dateadd(s, saledate, '19700101');

alter table sales_data_snapshot drop column saledate

alter table sales_data_snapshot drop column registered;


EXEC sp_rename
    @objname = 'sales_data_snapshot.registered_date',
    @newname = 'registered',
    @objtype = 'COLUMN';
    
    
    
EXEC sp_rename
    @objname = 'sales_data_snapshot.saledate_date',
    @newname = 'saledate',
    @objtype = 'COLUMN';    
    
    
    
################################################################################################################################################################    
    
ALTER PROCEDURE [dbo].[create_sales_v_snapshot] 
    AS
    BEGIN
      DECLARE @StartDateInt datetime
      DECLARE @EndDateInt datetime
    
      select @StartDateInt = DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),0)
      select @EndDateInt = dateadd(hh,23,DATEADD(wk,DATEDIFF(wk,0,dateadd(dd,-1,GETDATE())),6))
    	SET NOCOUNT ON;
    
    	insert into application_log(log_date,log_type,log_msg) values(getdate(),'DEBUG','create_sales_v_snapshot startdate:'+cast(@StartDateInt as nvarchar)+' enddate:'+cast(@EndDateInt as nvarchar));
    
    	insert into sales_data_snapshot(sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date) 
    	(select sale_id,order_nr,probability,product_area_nr,saledate,registered,customer_nr,customer_name,delivery_address,postal_code,post_office,own_production_cost,transport_cost,assembly_cost,yes_lines,contribution_margin,county_name,salesman,order_date
    	  from sales_v
	  where saledate between @StartDateInt and @EndDateInt)