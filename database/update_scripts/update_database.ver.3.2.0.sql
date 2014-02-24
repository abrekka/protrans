ALTER VIEW [dbo].[import_order_prob_100_v]
AS
SELECT        SuperofficeCRM7.crm7.SALE.sale_id, SuperofficeCRM7.crm7.SALE.number1, SuperofficeCRM7.crm7.SALE.amount, SuperofficeCRM7.crm7.SALE.saledate, 
                         SuperofficeCRM7.crm7.SALE.userdef_id, SuperofficeCRM7.crm7.SALE.registered, SuperofficeCRM7.crm7.SALE.probability, 
                         SuperofficeCRM7.crm7.SALE.contact_id, F0100.dbo.Ord.R1 AS product_area_nr, ISNULL(F0100.dbo.Ord.DelAd3, '') + ' ' + ISNULL(F0100.dbo.Ord.DelAd4, '') 
                         AS delivery_address, F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man, 
                         F0100.dbo.Ord.Inf3 AS telephonenr_site, Intelle_Ordre.dbo.IPK_Ord.Max_Hoyde AS maks_hoyde
FROM            SuperofficeCRM7.crm7.SALE INNER JOIN
                         F0100.dbo.Ord ON SuperofficeCRM7.crm7.SALE.number1 = F0100.dbo.Ord.Inf6 INNER JOIN
                         dbo.product_area ON dbo.product_area.product_area_nr = F0100.dbo.Ord.R1 AND 
                         SuperofficeCRM7.crm7.SALE.amount > dbo.product_area.own_production_cost_limit LEFT OUTER JOIN
                         Intelle_Ordre.dbo.IPK_Ord ON 'SO-' + SuperofficeCRM7.crm7.SALE.number1 = Intelle_Ordre.dbo.IPK_Ord.OrdNo
WHERE        (F0100.dbo.Ord.R1 IN (100, 200, 210,220,230,300)) AND (SuperofficeCRM7.crm7.SALE.saledate > '2012-07-01') AND (SuperofficeCRM7.crm7.SALE.probability = 100) AND 
                         (SuperofficeCRM7.crm7.SALE.number1 <> '') AND (NOT EXISTS
                             (SELECT        1 AS Expr1
                               FROM            dbo.Customer_order
                               WHERE        (Order_nr = SuperofficeCRM7.crm7.SALE.number1)))


/************************************************************************************************************************************************************************

update [Protrans2].[dbo].[product_area] set product_area='Rekke' where product_area_nr=100;

update [Protrans2].[dbo].[product_area] set product_area='Villa Element' where product_area_nr=200;

update [Protrans2].[dbo].[product_area] set product_area='Villa Precut' where product_area_nr=210;

update [Protrans2].[dbo].[product_area] set product_area='Service' where product_area_nr=300;

insert into  [Protrans2].[dbo].[product_area] (product_area,sort_nr,product_area_group_id,own_production_cost_limit,product_area_nr,packlist_ready) values('Villa XL',7,1,15000,220,0);

insert into  [Protrans2].[dbo].[product_area] (product_area,sort_nr,product_area_group_id,own_production_cost_limit,product_area_nr,packlist_ready) values('Villa spesial',8,1,15000,230,0);