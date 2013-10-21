CREATE VIEW [dbo].[montering_v]
AS
SELECT     dbo.[Assembly].Assembly_year, dbo.[Assembly].Assembly_week, dbo.[Assembly].Assembly_team_id, 
                      CAST(dbo.Customer.Customer_nr AS nvarchar(20)) 
                      + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name + '-' + dbo.Customer_order.Order_nr + ' ' + dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office
                       + ',' + dbo.Construction_type.Name + ',' + dbo.Customer_order.Info AS order_details
FROM         dbo.[Assembly] INNER JOIN
                      dbo.Customer_order ON dbo.[Assembly].Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id

/*************************************************************************************

alter table supplier add phone nvarchar(255);