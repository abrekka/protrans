alter table deviation_status add for_manager int;
/******************************************************************************************/
insert into deviation_status(deviation_status_name,for_manager) values('Ikke godkjent',1);