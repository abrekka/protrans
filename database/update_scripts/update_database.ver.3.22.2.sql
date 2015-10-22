ALTER VIEW [dbo].[sales_v]
AS
SELECT DISTINCT 
SuperofficeCRM7.crm7.SALE.sale_id, SuperofficeCRM7.crm7.SALE.number1 AS order_nr, SuperofficeCRM7.crm7.SALE.probability, ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1) AS product_area_nr, 
SuperofficeCRM7.crm7.SALE.saledate, SuperofficeCRM7.crm7.SALE.registered, SuperofficeCRM7.crm7.CONTACT.number2 AS customer_nr, SuperofficeCRM7.crm7.CONTACT.name AS customer_name, 
SuperofficeCRM7.crm7.ADDRESS.address1 AS delivery_address, Intelle_Ordre.dbo.wOrd.DelZip AS postal_code, 
SuperofficeCRM7.crm7.ADDRESS.city AS post_office, 
SuperofficeCRM7.crm7.UDSALESMALL.double05 AS own_production_cost, SuperofficeCRM7.crm7.UDSALESMALL.double06 AS transport_cost, 
SuperofficeCRM7.crm7.UDSALESMALL.double07 AS assembly_cost, 
SuperofficeCRM7.crm7.UDSALESMALL.double08 AS yes_lines, 
SuperofficeCRM7.crm7.UDSALESMALL.double09 AS contribution_margin, SuperofficeCRM7.crm7.UDSALESMALL.long03 AS order_date, 
case when SuperofficeCRM7.crm7.COUNTRY.name = 'Norge' then dbo.county.county_name else SuperofficeCRM7.crm7.COUNTRY.name end as county_name,
ISNULL(Intelle_Ordre.dbo.wOrd.OurRef, F0100.dbo.Ord.OurRef) AS salesman, Intelle_Ordre.dbo.wOrd.SegmentNo
FROM            SuperofficeCRM7.crm7.SALE INNER JOIN
                         SuperofficeCRM7.crm7.UDSALESMALL ON SuperofficeCRM7.crm7.SALE.userdef_id = SuperofficeCRM7.crm7.UDSALESMALL.udsalesmall_id LEFT OUTER JOIN
                         Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = SuperofficeCRM7.crm7.SALE.number1 LEFT OUTER JOIN
                         F0100.dbo.Ord ON SuperofficeCRM7.crm7.SALE.number1 = F0100.dbo.Ord.Inf6 LEFT OUTER JOIN
                         SuperofficeCRM7.crm7.ADDRESS ON SuperofficeCRM7.crm7.ADDRESS.owner_id = SuperofficeCRM7.crm7.SALE.contact_id LEFT OUTER JOIN
                         SuperofficeCRM7.crm7.CONTACT ON SuperofficeCRM7.crm7.CONTACT.contact_id = SuperofficeCRM7.crm7.SALE.contact_id LEFT OUTER JOIN
                         dbo.transport_cost ON Intelle_Ordre.dbo.wOrd.DelZip = dbo.transport_cost.postal_code LEFT OUTER JOIN
                         dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
                         dbo.county ON dbo.county.county_nr = dbo.area.county_nr left outer join
						 SuperofficeCRM7.crm7.COUNTRY on SuperofficeCRM7.crm7.CONTACT.country_id = SuperofficeCRM7.crm7.COUNTRY.country_id

WHERE        (SuperofficeCRM7.crm7.ADDRESS.atype_idx = 2) AND (SuperofficeCRM7.crm7.SALE.probability > 0) AND (SuperofficeCRM7.crm7.CONTACT.number2 <> '743796') AND EXISTS
                             (SELECT        1 AS Expr1
                               FROM            dbo.product_area
                               WHERE        (product_area_nr = ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1)) AND (ISNULL(SuperofficeCRM7.crm7.UDSALESMALL.double05, 0) > own_production_cost_limit)) AND 
                         (NOT EXISTS
                             (SELECT        1 AS Expr1
                               FROM            SuperofficeCRM7.crm7.SALE AS S LEFT OUTER JOIN
                                                         SuperofficeCRM7.crm7.UDSALESMALL AS udsalesmall_1 ON SuperofficeCRM7.crm7.sale.userdef_id = udsalesmall_1.udsalesmall_id
                               WHERE        (udsalesmall_1.long06 IN (175, 176)) AND (S.probability < 91) AND (S.number1 = SuperofficeCRM7.crm7.sale.number1)))
