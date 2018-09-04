ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr, 
dbo.Customer_order.Invoice_date, 
dbo.Customer.Customer_nr, 
dbo.Customer.First_name, 
dbo.Customer.Last_name, 
dbo.Customer_order.Postal_code, 
dbo.Customer_order.Post_office, 
dbo.Construction_type.Name AS Construction_name, 
dbo.Customer_order.Sent, 
dbo.order_customer_cost_v.customer_cost_amount as customer_cost, 
customer_order_comment_v.comment,
dbo.order_assembly_date_v.assemblied_date, 
dbo.product_area_group.product_area_group_name,
SuperofficeCRM7.crm7.person.firstname+SuperofficeCRM7.crm7.person.lastname as ordrekoordinator
FROM         dbo.Customer_order inner JOIN
customer_order_comment_v on dbo.Customer_order.Order_id=customer_order_comment_v.order_id inner join
dbo.order_customer_cost_v on dbo.Customer_order.Order_id=dbo.order_customer_cost_v.Order_id INNER JOIN
dbo.order_assembly_date_v on dbo.Customer_order.Order_id=dbo.order_assembly_date_v.order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN
                      dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN
                      dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id left outer join
					  SuperofficeCRM7.crm7.sale ON SuperofficeCRM7.crm7.sale.number1 = dbo.Customer_order.Order_nr left outer join
					  SuperofficeCRM7.crm7.associate ON SuperofficeCRM7.crm7.associate.associate_id=SuperofficeCRM7.crm7.sale.associate_id left outer join
					  SuperofficeCRM7.crm7.person ON  SuperofficeCRM7.crm7.person.person_id=SuperofficeCRM7.crm7.associate.person_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) AND 
(dbo.Transport.Transport_name <> 'Historisk') OR
(dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Do_Assembly = 1) 
AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (assemblied_date IS NOT NULL))


/************************************************************************************************************

alter table customer_order add sent_mail datetime;