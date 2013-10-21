alter table budget add user_id int references application_user(user_id);
/******************************************************************************************************
alter table budget add budget_value_offer int ;
/******************************************************************************************************
alter table application_user alter column user_name nvarchar(50) null;