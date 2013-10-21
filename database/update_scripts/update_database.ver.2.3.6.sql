ALTER VIEW [dbo].[import_order_prob_100_v]
AS
SELECT     superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1, superoffice.crm5.sale.amount, superoffice.crm5.sale.saledate, 
                      superoffice.crm5.sale.userdef_id, superoffice.crm5.sale.registered, superoffice.crm5.sale.probability, superoffice.crm5.sale.contact_id, 
                      F0100.dbo.Ord.R1 AS product_area_nr, isnull(F0100.dbo.Ord.DelAd3,'') + ' ' + isnull(F0100.dbo.Ord.DelAd4,'') AS delivery_address, 
                      F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man, 
                      F0100.dbo.Ord.Inf3 AS telephonenr_site,intelle_Ordre.dbo.IPK_ord.max_hoyde as maks_hoyde
FROM         superoffice.crm5.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6 left outer join
Intelle_ordre.dbo.IPK_ord ON 'SO-'+superoffice.crm5.sale.number1 = Intelle_ordre.dbo.IPK_ord.ordno collate Danish_Norwegian_CI_AS
WHERE     (F0100.dbo.Ord.R1 IN (100, 200, 300, 400,210)) AND (superoffice.crm5.sale.saledate > 1260230400) AND (superoffice.crm5.sale.probability = 100) AND 
                      (superoffice.crm5.sale.number1 <> '') AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr  collate Danish_Norwegian_CI_AS  = superoffice.crm5.sale.number1)))

/***************************************************************************************************************************************************************************
ALTER view [dbo].[import_order_prob_90_v]
as
SELECT		superoffice.crm5.sale.sale_id, 
			superoffice.crm5.sale.number1, 
			superoffice.crm5.sale.amount, 
			superoffice.crm5.sale.saledate, 
            superoffice.crm5.sale.userdef_id, 
			superoffice.crm5.sale.registered, 
			superoffice.crm5.sale.probability, 
			superoffice.crm5.sale.contact_id, 
            Intelle_ordre.dbo.word.DeptNo AS product_area_nr, --Varchar(50), null
			isnull(Intelle_ordre.dbo.word.DelAd1,'') + ' ' + isnull(Intelle_ordre.dbo.word.DelAd2,'') AS delivery_address, --Begge: Varchar(40), null
            Intelle_ordre.dbo.word.DelZip AS postal_code, --Varchar(10), null
			Intelle_ordre.dbo.word.DelCity AS postoffice, --Varchar(40), null
			Intelle_ordre.dbo.word.OurRef AS sales_man,	--Varchar(50), null
			Intelle_ordre.dbo.word.delPhone as telephonenr_site,
superoffice.crm5.contact.number2 as customer_nr,
superoffice.crm5.contact.name as customer_name,
superoffice.crm5.udsalesmall.double05 as own_production,
superoffice.crm5.udsalesmall.double06 as delivery_cost,
intelle_Ordre.dbo.IPK_ord.max_hoyde as maks_hoyde
FROM         superoffice.crm5.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm5.sale.number1 = Intelle_ordre.dbo.word.sosaleno collate Danish_Norwegian_CI_AS left outer join
			Intelle_ordre.dbo.IPK_ord ON 'SO-'+superoffice.crm5.sale.number1 = Intelle_ordre.dbo.IPK_ord.ordno collate Danish_Norwegian_CI_AS inner join
			superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id inner join
superoffice.crm5.udsalesmall on superoffice.crm5.udsalesmall.udsalesmall_id=superoffice.crm5.sale.userdef_id
WHERE     (superoffice.crm5.sale.saledate > 1250640000) 
			AND (superoffice.crm5.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm5.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS)))
			AND Intelle_ordre.dbo.word.deptno in (300,400) --Takstol og byggelement
			
/***************************************************************************************************************************************************************************
ALTER view [dbo].[import_order_v]
as
select sale_id,number1,amount,saledate,userdef_id,registered,probability,contact_id,product_area_nr,delivery_address,postal_code,postoffice,sales_man,telephonenr_site,maks_hoyde
from import_order_prob_90_v
union
select * from import_order_prob_100_v
/***************************************************************************************************************************************************************************
