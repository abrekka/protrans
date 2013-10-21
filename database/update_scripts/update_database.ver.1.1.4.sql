alter table article_type add metric nvarchar(50);
/*********************************************************************/
alter table application_param add param_type nvarchar(50);
/*********************************************************************/
insert into application_param(param_name,param_value,param_type) values('Meter','meter','Betegnelse');
insert into application_param(param_name,param_value,param_type) values('Stk','stk','Betegnelse');
insert into application_param(param_name,param_value,param_type) values('Løpemeter','løpemeter','Betegnelse');