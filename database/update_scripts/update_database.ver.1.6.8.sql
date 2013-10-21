CREATE view [dbo].[drawer_v] 
as
Select superoffice.crm5.contact.number2 as customer_nr, 
	superoffice.crm5.sale.number1 as order_nr, 
	superoffice.crm5.sale.group_idx, 
	superoffice.crm5.contact.name as customer_name, 
	superoffice.crm5.doctmpl.name as category_name, 
	superoffice.crm5.appointment.registered, 
	superoffice.crm5.udsalesmall.double05 as own_production, 
	superoffice.crm5.associate.name as drawer_name
from superoffice.crm5.appointment inner join 
	superoffice.crm5.doctmpl on superoffice.crm5.appointment.task_idx = superoffice.crm5.doctmpl.doctmpl_id inner join 
	superoffice.crm5.contact on superoffice.crm5.appointment.contact_id = superoffice.crm5.contact.contact_id inner join 
	superoffice.crm5.sale on superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id inner join 
	superoffice.crm5.document on superoffice.crm5.document.document_id = superoffice.crm5.appointment.document_id inner join 
	superoffice.crm5.relations on (superoffice.crm5.relations.source_record = superoffice.crm5.document.document_id and 
	superoffice.crm5.relations.destination_record = superoffice.crm5.sale.sale_id) inner join 
	superoffice.crm5.associate on superoffice.crm5.associate.associate_id = superoffice.crm5.appointment.associate_id inner join 
	superoffice.crm5.udsalesmall on superoffice.crm5.udsalesmall.udsalesmall_id = superoffice.crm5.sale.userdef_id 
where superoffice.crm5.doctmpl.doctmpl_id in (102,103,153,154) and 
	superoffice.crm5.relations.source_table = 10 and 
	superoffice.crm5.relations.destination_table = 13 

/************************************************************************************
insert into window_access(window_name) values('Tegningsrapport');
/************************************************************************************
