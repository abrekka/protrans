ALTER VIEW [dbo].[import_order_v]
AS
SELECT  superoffice.crm5.sale.sale_id, 
	superoffice.crm5.sale.number1, 
	superoffice.crm5.sale.amount, 
	superoffice.crm5.sale.saledate, 
        superoffice.crm5.sale.userdef_id, 
        superoffice.crm5.sale.registered, 
        superoffice.crm5.sale.probability, 
        superoffice.crm5.sale.contact_id, 
        F0100.dbo.Ord.R1 AS product_area_nr,
        F0100.dbo.Ord.delad1 + ' ' + F0100.dbo.Ord.delad2 as delivery_address,
        F0100.dbo.Ord.delpno as postal_code,
        F0100.dbo.Ord.delparea as postoffice
FROM         superoffice.crm5.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6
WHERE     (superoffice.crm5.sale.saledate > 1230768000) AND (superoffice.crm5.sale.probability = 100) AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS)))
