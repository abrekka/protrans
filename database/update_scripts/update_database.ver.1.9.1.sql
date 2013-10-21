ALTER VIEW [dbo].[takstol_v] 
AS 
SELECT dbo.Order_line.Order_line_id, 
	dbo.Customer.Customer_nr, 
	CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
	dbo.Customer_order.Order_nr, 
	dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
	dbo.Customer_order.Info, 
	dbo.Construction_type.Name AS construction_type_name, 
	dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
	dbo.Order_line.attribute_info + dbo.get_attribute_info(dbo.Order_line.Order_id, N'Gavl') AS Attribute_info, 
	dbo.Order_line.Number_of_items, 
	dbo.Transport.Loading_date, 
	dbo.Order_line.Colli_id, 
	dbo.Order_line.produced, 
	CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
	dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
	ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
	dbo.Transport.Transport_Week, 
	dbo.Transport.load_time, 
	dbo.Order_line.post_shipment_id, 
	dbo.product_area_group.product_area_group_name, 
	dbo.Order_line.action_started, 
	dbo.get_attribute_value(dbo.Order_line.Order_line_id, N'Egenordre') AS egenordre, 
	dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) AS is_default,
	customer_order.production_date,production_unit.production_unit_name
FROM dbo.Order_line INNER JOIN 
	dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN 
	dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN 
	dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN 
	dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN 
	dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN 
    dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
	dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id  CROSS JOIN
                      dbo.application_param
WHERE (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
	(dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = application_param.param_value 
	and application_param.param_name like 'takstol_artikkel%')
	AND (dbo.order_line_has_article(dbo.Order_line.Order_line_id, dbo.Order_line.has_article, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
	dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id)) = 1) 
/***************************************************************************************************/
ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1
/***************************************************************************************************/
ALTER VIEW [dbo].[takstol_production_v]
AS
select * from takstol_v where is_default=0
/***************************************************************************************************/
ALTER VIEW [dbo].[ordchgr_line_v]
AS
SELECT     F0100.dbo.ord.ordno AS OrdNo,'L' AS OrderHead, F0100.dbo.ordln.lnno AS lnNo, '' AS prodno, '' AS descr, '' AS NoInvoAb, '' AS Price, '' AS CCstpr, '' AS CstPr, '' AS cur, '' AS ExRt, 
                      '' AS CstCur, '' AS CstExRt, '' AS Dc1P, '' AS DelDt, '' AS CfDelDt, '' AS TrDt, '' AS OrdTp, '' AS EmpNo, '' AS CustNo, '' AS SupNo, '' AS InvoCust, 
                      '' AS EdFmt, '' AS InvoRef, '' AS RefNo, '' AS SelBuy, '' AS FrStc, '' AS TranspTm, '' AS DelTm, '' AS Un, '' AS StUnRt, '' AS LgtU, '' AS WdtU, '' AS AreaU, 
                      '' AS HgtU, '' AS VolU, '' AS DensU, '' AS NWgtU, '' AS TareU, '' AS NoUn, '' AS NWgtL, '' AS TareL, '' AS LgtL, '' AS AreaL, '' AS VolL, '' AS Transgr, 
                      '' AS Transgr2, '' AS Transgr3, '' AS Transgr4, '' AS R1, '' AS R2, '' AS R3, '' AS R4, '' AS R5, '' AS R6, '' AS DurDt, '' AS Trinf1, '' AS Trinf2, '' AS DelGr, 
                      '' AS Expr1, '' AS SCd, '' AS ProdPrGr, '' AS ProdPrGr2, '' AS ProdPrGr3, '' AS CustPrGr, '' AS CustPrGr2, '' AS Expr2, '' AS NoteNm, '' AS InvoPlLn, 
                      '' AS ProcMt, '' AS ExcPrint, '' AS EditPref, '' AS SpecFunc, 1 AS NetQty, 1 AS KeepPrice, 1 AS FM4729, isnull(order_line.number_of_items,0) AS LineStatus
FROM         dbo.Order_line INNER JOIN
                      F0100.dbo.ordln ON dbo.Order_line.ord_no = F0100.dbo.ordln.ordno AND dbo.Order_line.ln_no = F0100.dbo.ordln.lnno INNER JOIN
                      F0100.dbo.ord ON F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno
WHERE     (dbo.Order_line.ord_no IS NOT NULL) AND (dbo.Order_line.ln_no IS NOT NULL)
/***************************************************************************************************/