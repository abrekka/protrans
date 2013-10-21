insert into window_access(window_name) values ('Parametre');
/********************************************************************************/
insert into application_param(param_name,param_value) values('temp_dir','temp');
/********************************************************************************/
insert into deviation_status(deviation_status_name,for_manager) values('Undersøkes videre',1);
insert into deviation_status(deviation_status_name,for_manager) values('Korrigerende tiltak',1);
insert into deviation_status(deviation_status_name,for_manager) values('Funksjonsmøte',1);
insert into deviation_status(deviation_status_name,for_manager) values('Lukket',1);
/********************************************************************************/
alter table deviation add checked int;