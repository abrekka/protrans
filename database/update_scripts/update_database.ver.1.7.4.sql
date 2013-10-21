create table comment_type(
  comment_type_id int identity(1,1) primary key,
  comment_type_name nvarchar(100) not null,
  comment_type_comment nvarchar(100)
);
/********************************************************************************
insert into comment_type(comment_type_name,comment_type_comment) values('Ordre','Brukes av ordre');
insert into comment_type(comment_type_name,comment_type_comment) values('Transport','Brukes av ordre');
insert into comment_type(comment_type_name,comment_type_comment) values('Pakking','Brukes av ordre');
insert into comment_type(comment_type_name,comment_type_comment) values('Montering','Brukes av ordre');
insert into comment_type(comment_type_name,comment_type_comment) values('Opprettet','Brukes av prevantive tiltak');
insert into comment_type(comment_type_name,comment_type_comment) values('Bruker','Brukes av prevantive tiltak');
insert into comment_type(comment_type_name,comment_type_comment) values('Lukking','Brukes av prevantive tiltak');
insert into comment_type(comment_type_name,comment_type_comment) values('Åpning','Brukes av prevantive tiltak');
/********************************************************************************
create table preventive_action_comment_comment_type(
  preventive_action_comment_comment_type_id int identity(1,1) primary key,
  preventive_action_comment_id int references preventive_action_comment(preventive_action_comment_id) not null,
  comment_type_id int references comment_type(comment_type_id) not null
);
/********************************************************************************
insert into preventive_action_comment_comment_type(preventive_action_comment_id,comment_type_id) 
(select preventive_action_comment.preventive_action_comment_id,comment_type.comment_type_id 
   from preventive_action_comment,comment_type 
   where preventive_action_comment.comment_type=1 and comment_type.comment_type_name='Opprettet');
/********************************************************************************
insert into preventive_action_comment_comment_type(preventive_action_comment_id,comment_type_id) 
(select preventive_action_comment.preventive_action_comment_id,comment_type.comment_type_id 
   from preventive_action_comment,comment_type 
   where preventive_action_comment.comment_type=0 and comment_type.comment_type_name='Bruker');   
/********************************************************************************
insert into preventive_action_comment_comment_type(preventive_action_comment_id,comment_type_id) 
(select preventive_action_comment.preventive_action_comment_id,comment_type.comment_type_id 
   from preventive_action_comment,comment_type 
   where preventive_action_comment.comment_type=2 and comment_type.comment_type_name='Lukking');      
/********************************************************************************
insert into preventive_action_comment_comment_type(preventive_action_comment_id,comment_type_id) 
(select preventive_action_comment.preventive_action_comment_id,comment_type.comment_type_id 
   from preventive_action_comment,comment_type 
   where preventive_action_comment.comment_type=3 and comment_type.comment_type_name='Åpning');
/********************************************************************************
alter table preventive_action_comment drop column comment_type;
/********************************************************************************
create table order_comment_comment_type(
  order_comment_comment_type_id int identity(1,1) primary key,
  order_comment_id int references order_comment(order_comment_id) not null,
  comment_type_id int references comment_type(comment_type_id) not null
);
/********************************************************************************
insert into order_comment_comment_type(order_comment_id,comment_type_id) 
(select order_comment.order_comment_id,comment_type.comment_type_id 
   from order_comment,comment_type 
   where order_comment.for_transport is null and order_comment.for_package is null and comment_type.comment_type_name='Ordre');
/********************************************************************************   
insert into order_comment_comment_type(order_comment_id,comment_type_id) 
(select order_comment.order_comment_id,comment_type.comment_type_id 
   from order_comment,comment_type 
   where order_comment.for_transport is not null and order_comment.for_package is null and comment_type.comment_type_name='Transport');   
/********************************************************************************   
insert into order_comment_comment_type(order_comment_id,comment_type_id) 
(select order_comment.order_comment_id,comment_type.comment_type_id 
   from order_comment,comment_type 
   where order_comment.for_transport is null and order_comment.for_package is not null and comment_type.comment_type_name='Pakking');
/********************************************************************************   
alter table order_comment drop column for_transport;
/********************************************************************************   
alter table order_comment drop column for_package;
/********************************************************************************
ALTER view [dbo].[drawer_v] 
as
Select 
superoffice.crm5.contact.number2 as customer_nr, 
	superoffice.crm5.sale.number1 as order_nr, 
	superoffice.crm5.sale.group_idx, 
	superoffice.crm5.contact.name as customer_name, 
	superoffice.crm5.doctmpl.name as category_name, 
	superoffice.crm5.appointment.registered, 
	superoffice.crm5.udsalesmall.double05 as own_production, 
	superoffice.crm5.associate.name as drawer_name,
	superoffice.crm5.document.document_id
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
/********************************************************************************
insert into order_comment(user_name,comment_date,comment,order_id) (select 'admin',getdate(),comment,order_id from assembly where comment is not null);
/********************************************************************************
insert into order_comment_comment_type(order_comment_id,comment_type_id) 
(select order_comment.order_comment_id,comment_type.comment_type_id from order_comment,comment_type where order_comment.user_name='mont' and comment_type.comment_type_name='Montering');
/********************************************************************************
alter table assembly drop column comment;