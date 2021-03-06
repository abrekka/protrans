ALTER VIEW [dbo].[monteringsanvisning_v]
AS
SELECT DISTINCT dbo.Customer_order.Order_nr, F0100.dbo.FreeInf1.PictFNm AS Filsti, '' AS Overskrift,F0100.dbo.FreeInf1.WebPg AS Filsti_se,F0100.dbo.Ord.Lang,F0100.dbo.FreeInf1.Txt1
FROM            F0100.dbo.Ord INNER JOIN
                         dbo.Customer_order ON dbo.Customer_order.Order_nr = F0100.dbo.Ord.Inf6 INNER JOIN
                         F0100.dbo.OrdLn ON F0100.dbo.OrdLn.OrdNo = F0100.dbo.Ord.OrdNo INNER JOIN
                         F0100.dbo.FreeInf1 ON F0100.dbo.FreeInf1.ProdNo = F0100.dbo.OrdLn.ProdNo
WHERE        (F0100.dbo.FreeInf1.R1 = 0) OR
                         (F0100.dbo.Ord.R1 = F0100.dbo.FreeInf1.R1) OR
                         (F0100.dbo.Ord.R1 - 20 = F0100.dbo.FreeInf1.R1)





/******************************************************************************************************************

ALTER VIEW [dbo].[gavl_production_v]
AS
SELECT     dbo.Order_line.Order_line_id, dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, dbo.Customer_order.Order_nr, 
                      dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, dbo.Customer_order.Info, 
                      dbo.Construction_type.Name AS construction_type_name, dbo.order_line_article_name(dbo.Order_line.Order_line_id, 
                      dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, dbo.Order_line.attribute_info, 
                      dbo.Order_line.Number_of_items, dbo.Transport.Loading_date, dbo.Order_line.produced, CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
                      ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, dbo.Transport.Transport_Week, dbo.Transport.load_time, 
                      dbo.Customer_order.status AS order_status, dbo.Order_line.post_shipment_id, dbo.product_area_group.product_area_group_name,order_line.action_started,
						customer_order.production_date,production_unit.production_unit_name,
						dbo.order_line.real_production_hours,dbo.customer_order.production_week,
						dbo.order_line.done_by
FROM         dbo.Order_line INNER JOIN
                      dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
					  dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id
WHERE     ((dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') OR
                      (dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = 0) AND 
                      (dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) = 'Gavl') AND 
                      (dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) IS NULL))
