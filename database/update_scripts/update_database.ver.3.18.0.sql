alter table[Protrans2].[dbo].[Assembly] add assembly_comment nvarchar(400);
/********************************************************************************  

CREATE VIEW [dbo].[fakturagrunnlag_sum_v]
AS
SELECT order_id,sum(sum_line) as montering_sum
  FROM [Protrans2].[dbo].[fakturagrunnlag_v] 
  where prodno <> 'Montering Villa' and sum_line>0.01 and prodno<>'FRAKT'
  group by order_id
  
  
  
/********************************************************************************  
ALTER VIEW [dbo].[assembly_v]
AS
SELECT distinct dbo.[Assembly].Assembly_id,
    dbo.customer_order.order_id,
dbo.Customer_order.sent , 
dbo.[Assembly].assemblied_date, 
dbo.[Assembly].Assembly_week,
dbo.[Assembly].Assembly_year,
dbo.supplier.supplier_name,
dbo.Assembly.Planned,
dbo.Customer_order.packlist_ready,
dbo.Assembly.sent_base,
dbo.Customer_order.Order_nr,
dbo.Customer.First_name,
dbo.Customer.last_name,
dbo.Customer_order.Postal_code,
dbo.Customer_order.Post_office,
dbo.Customer_order.telephone_nr,
dbo.product_area.product_area,
dbo.Customer_order.production_week,
dbo.Transport.Transport_Year,
dbo.Transport.Transport_Week,
dbo.Transport.Transport_name,
dbo.Order_line.attribute_info as takstein_info,
dbo.own_assembly_cost_v.sum_cost_amount as assembly_cost,
dbo.Assembly.First_planned,
dbo.kraning_cost_v.sum_cost_amount as kraning_cost,
dbo.Customer_order.has_missing_collies,
dbo.Construction_type.Name as construction_type_name,
dbo.Customer_order.Info,
dbo.Customer_order.special_concern,
dbo.fakturagrunnlag_sum_v.montering_sum,
dbo.Assembly.assembly_comment,
dbo.fakturagrunnlag_v.price as assembler_craning,
(select distinct SuperofficeCRM7.crm7.sale.number1
                                               from SuperofficeCRM7.crm7.appointment inner join
                                               SuperofficeCRM7.crm7.document on SuperofficeCRM7.crm7.DOCUMENT.document_id = SuperofficeCRM7.crm7.appointment.document_id inner join
                                               SuperofficeCRM7.crm7.sale on SuperofficeCRM7.crm7.sale.contact_id = SuperofficeCRM7.crm7.appointment.contact_id
                                               where SuperofficeCRM7.crm7.appointment.registered_associate_id = 193 and task_idx = 145 and header like 'Montering%' and 
                                                            dbo.Customer_order.Order_nr = SuperofficeCRM7.crm7.sale.number1) as sent_mail_customer

FROM         dbo.Customer_order INNER JOIN
dbo.Construction_type on dbo.Customer_order.Construction_type_id=dbo.Construction_type.Construction_type_id inner join
dbo.product_area on dbo.Customer_order.product_area_id=dbo.product_area.product_area_id inner join
dbo.Customer on dbo.Customer_order.Customer_id=dbo.Customer.Customer_id inner join
dbo.own_assembly_cost_v on dbo.Customer_order.order_id=dbo.own_assembly_cost_v.order_id INNER JOIN
dbo.kraning_cost_v on dbo.Customer_order.order_id=dbo.kraning_cost_v.order_id INNER JOIN
                      dbo.[Assembly] ON dbo.Customer_order.Order_id = dbo.[Assembly].Order_id left outer JOIN
					  dbo.fakturagrunnlag_sum_v ON dbo.Customer_order.Order_id = dbo.fakturagrunnlag_sum_v.Order_id left outer JOIN
					  dbo.fakturagrunnlag_v ON dbo.Customer_order.Order_id = dbo.fakturagrunnlag_v.Order_id and dbo.fakturagrunnlag_v.prodno='KRANBIL' left outer JOIN
                      dbo.supplier on dbo.Assembly.supplier_id=dbo.supplier.supplier_id left outer join
					  dbo.Transport on dbo.Customer_order.Transport_id=dbo.Transport.Transport_id left outer join
					  dbo.Order_line on dbo.Customer_order.Order_id=dbo.Order_line.Order_id and dbo.order_line.article_path='Takstein' and dbo.order_line.attribute_info is not null





