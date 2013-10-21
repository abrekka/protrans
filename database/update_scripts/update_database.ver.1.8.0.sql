alter table attribute add prod_cat_no int;
alter table attribute add prod_cat_no_2 int;
/**************************************************************************
update user_type set description='Pakking' where description='Garasjepakking';
/**************************************************************************
insert into application_param(param_name,param_value) values('user_type_package','Pakking');
/**************************************************************************
alter table attribute_choice add prod_cat_no int;
alter table attribute_choice add prod_cat_no_2 int;
/**************************************************************************
update window_access set table_names='TableOrders,TablePostShipments,TableTransportOrders,TableTransportOrdersList' where window_name='Transport';