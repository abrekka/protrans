ALTER view [dbo].[sale_status_v] as
select	superoffice.crm5.contact.number2 as customer_nr, 
	superoffice.crm5.contact.name as customer_name, 
	superoffice.crm5.sale.number1 as order_nr, 
	superoffice.crm5.sale.amount, 
	superoffice.crm5.sale.probability,
	superoffice.crm5.sale.saledate,
	superoffice.crm5.udsalesmall.long08 as build_date,
	isnull(superoffice.crm5.udlist.name,'') as sale_status, 
	superoffice.crm5.associate.name as salesman, 
	Intelle_ordre.dbo.word.deptno
   ,superoffice.crm5.udsalesmall.double05 as own_production
from	superoffice.crm5.sale inner join
		superoffice.crm5.contact on superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id inner join
		superoffice.crm5.associate on superoffice.crm5.associate.associate_id = superoffice.crm5.contact.associate_id left join
		superoffice.crm5.udsalesmall on superoffice.crm5.sale.userdef_id = superoffice.crm5.udsalesmall.udsalesmall_id left join
		superoffice.crm5.udlist on superoffice.crm5.udlist.udlist_id = superoffice.crm5.udsalesmall.long06 left join
		Intelle_ordre.dbo.word on Intelle_ordre.dbo.word.sosaleno = superoffice.crm5.sale.number1

where	probability in (90,30)
/*************************************************************************************************************************************************
create view sale_status_order_reserve_v as
select deptno
   ,count(order_nr) as order_count
   ,sum(own_production) as order_reserve
from sale_status_v
where probability=90
   and not exists(select 1 
                 from customer_order
                 where customer_order.order_nr collate Danish_Norwegian_CI_AS =sale_status_v.order_nr)
group by deptno
/*************************************************************************************************************************************************