CREATE view [dbo].[sale_status_v] as
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
from	superoffice.crm5.sale inner join
		superoffice.crm5.contact on superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id inner join
		superoffice.crm5.associate on superoffice.crm5.associate.associate_id = superoffice.crm5.contact.associate_id left join
		superoffice.crm5.udsalesmall on superoffice.crm5.sale.userdef_id = superoffice.crm5.udsalesmall.udsalesmall_id left join
		superoffice.crm5.udlist on superoffice.crm5.udlist.udlist_id = superoffice.crm5.udsalesmall.long06 left join
		Intelle_ordre.dbo.word on Intelle_ordre.dbo.word.sosaleno = superoffice.crm5.sale.number1

where	probability in (90,30)
/********************************************************************************************************
insert into window_access(window_name) values('Salgsstatus');
/********************************************************************************************************
alter table customer_order add takstol_height int;
/********************************************************************************************************
alter table post_shipment add takstol_height int;