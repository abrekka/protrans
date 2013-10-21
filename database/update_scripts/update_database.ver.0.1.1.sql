alter table order_line add produced int;
alter table user_type add Startup_window nvarchar(50);
alter table user_type add User_level int;

alter table Construction_type_article add dialog_order int;
alter table Order_line add dialog_order int;
alter table Construction_type_article_attribute add dialog_order int;
alter table Construction_type_attribute add dialog_order int;
alter table Order_line_attribute add dialog_order int;

create table application_param(
  param_id int identity(1,1) not null primary key,
  param_name nvarchar(50) not null,
  param_value nvarchar(100) not null
);

insert into application_param values('gavl_artikkel','Gavl');

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Gavlproduksjon','no.ugland.utransprod.gui.GavlProductionWindow',2);
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('gavl','gavl','gavl','gavl');
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Gavlproduksjon' and application_user.user_name='gavl');

update user_type set startup_window = 'no.ugland.utransprod.gui.ProTransMain' where description = 'Administrator';
update user_type set user_level = 1 where description = 'Administrator';



