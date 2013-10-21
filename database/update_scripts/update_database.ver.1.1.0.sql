alter table customer_order add paid_date datetime;

/***********************************************************************************************/
CREATE VIEW [dbo].[paid_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.paid_date, dbo.Customer.Customer_nr, dbo.Customer.First_name, 
                      dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, dbo.Construction_type.Name AS Construction_name, 
                      dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) AS customer_cost, dbo.Customer_order.Comment, 
                      dbo.Customer_order.Do_Assembly
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id
WHERE     dbo.Customer_order.Sent IS NULL

/***********************************************************************************************/
ALTER VIEW [dbo].[fakturering_v]
AS
SELECT     dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, dbo.Customer_order.Invoice_date, dbo.Customer.Customer_nr, 
                      dbo.Customer.First_name, dbo.Customer.Last_name, dbo.Customer_order.Postal_code, dbo.Customer_order.Post_office, 
                      dbo.Construction_type.Name AS Construction_name, dbo.Customer_order.Sent, dbo.Get_customer_cost(dbo.Customer_order.Order_id) 
                      AS customer_cost, dbo.Customer_order.Comment, dbo.get_assemblied_date(dbo.Customer_order.Order_id, dbo.Customer_order.Do_Assembly) 
                      AS assemblied_date
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id LEFT OUTER JOIN
                      dbo.Transport ON dbo.Customer_order.Transport_id = dbo.Transport.Transport_id
WHERE     (dbo.Customer_order.Sent IS NOT NULL) AND (COALESCE (dbo.Customer_order.Do_Assembly, 0) = 0) AND 
                      (dbo.Transport.Transport_name <> 'Historisk') OR
                      (dbo.Customer_order.Sent IS NOT NULL) AND (dbo.Customer_order.Do_Assembly = 1) AND (dbo.Transport.Transport_name <> 'Historisk') AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.[Assembly]
                            WHERE      (Order_id = dbo.Customer_order.Order_id) AND (assemblied_date IS NOT NULL))

/***********************************************************************************************/
insert into user_type(description,startup_window) values('Betalingsansvarlig','no.ugland.utransprod.gui.PaidWindow');