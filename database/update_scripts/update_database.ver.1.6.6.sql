insert into cost_type(cost_type_name) values('Jalinjer');
/******************************************************************************************

CREATE view [dbo].[import_order_v] 
as
select * 
	from superoffice.crm5.sale 
	where superoffice.crm5.sale.saledate > 1230768000 and 
	      superoffice.crm5.sale.probability=100 and
	      not exists(select 1 
				from customer_order 
				where customer_order.order_nr=superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS) and 
	      exists(select 1 
			from F0100.dbo.ord 
			where f0100.dbo.ord.inf6=superoffice.crm5.sale.number1)