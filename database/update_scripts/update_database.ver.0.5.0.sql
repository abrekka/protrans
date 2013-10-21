alter table attribute add special_concern int;

alter table customer_order add special_concern nvarchar(500);

update colli set post_shipment_id=null;

alter table post_shipment drop column order_line_id;

--Kopier navnet på constraint som kommer opp i neste statement

alter table post_shipment drop constraint FK__Post_ship__order__3BCADD1B;

alter table post_shipment drop column order_line_id;

alter table post_shipment add order_id int references customer_order(order_id) not null;

alter table order_line add post_shipment_id int references post_shipment(post_shipment_id);

alter table post_shipment add post_shipment_ref int references post_shipment(post_shipment_id);

alter table post_shipment add status nvarchar(500);


/****************************************************************************************
ALTER VIEW [dbo].[main_package_v]
AS
SELECT     dbo.Customer_order.Order_id,dbo.Customer.Customer_nr, CAST(dbo.Customer.Customer_nr AS nvarchar(10)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_details,customer_order.order_nr,customer_order.postal_code+' '+customer_order.post_office as address,Construction_type.Name as construction_type_name,customer_order.info
                      , CAST(dbo.Transport.Transport_Week AS nvarchar(2)) 
                      + '- ' + dbo.Transport.Transport_name AS transport_details, dbo.is_order_ready_for_package(dbo.Customer_order.Order_id) AS package_ready, 
                      dbo.is_order_done_package(dbo.Customer_order.Order_id, dbo.Customer_order.Collies_done) AS done_package, dbo.Customer_order.Comment,transport.transport_year,transport.transport_week
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Transport_id IS NULL) AND (dbo.Customer_order.Sent IS NULL) OR
                      (dbo.Transport.Transport_name <> 'Historisk') AND (dbo.Customer_order.Sent IS NULL)

