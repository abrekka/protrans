create table window_access(
  window_access_id int identity(1,1) primary key,
  window_name nvarchar(255) not null
);

create table user_type_access(
  user_type_access_id int identity(1,1) primary key,
  write_access int,
  user_type_id int not null references user_type(user_type_id),
  window_access_id int not null references window_access(window_access_id)
);

alter table user_type add is_admin int;

insert into window_access(window_name) values('Kunde');
insert into window_access(window_name) values('Ordre');
insert into window_access(window_name) values('Transport');
insert into window_access(window_name) values('Montering');
insert into window_access(window_name) values('Fakturering');
insert into window_access(window_name) values('Ordre til avrop');
insert into window_access(window_name) values('Pakkliste');
insert into window_access(window_name) values('Bestillinger');
insert into window_access(window_name) values('Master garasjetype');
insert into window_access(window_name) values('Garasjetype');
insert into window_access(window_name) values('Artikkel');
insert into window_access(window_name) values('Kostnadstyper');
insert into window_access(window_name) values('Kostnadsenheter');
insert into window_access(window_name) values('Monteringsteam');
insert into window_access(window_name) values('Funksjon');
insert into window_access(window_name) values('Kategori');
insert into window_access(window_name) values('Avikstatus');
insert into window_access(window_name) values('Attributter');
insert into window_access(window_name) values('Leverandører');
insert into window_access(window_name) values('Leverandørtyper');
insert into window_access(window_name) values('Gavl');
insert into window_access(window_name) values('Takstol');
insert into window_access(window_name) values('Vegg');
insert into window_access(window_name) values('Front');
insert into window_access(window_name) values('Standard takstol');
insert into window_access(window_name) values('Garasjepakke');
insert into window_access(window_name) values('Gulvspon');
insert into window_access(window_name) values('Transportstatistikk');
insert into window_access(window_name) values('Nøkkeltall');
insert into window_access(window_name) values('Avvik');

update user_type set is_admin=1 where lower(description)='administrator';

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='takstolproduksjon' and lower(window_access.window_name)='takstol');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='kunde');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='ordre');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='transport');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='montering');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='fakturering');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='ordre til avrop');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='pakkliste');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='bestillinger');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='master garasjetype');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='garasjetype');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='artikkel');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='kostnadstyper');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='kostnadsenheter');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='monteringsteam');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='funksjon');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='kategori');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='avikstatus');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='attributter');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='leverandører');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='leverandørtyper');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='gavl');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='takstol');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='vegg');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='front');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='standard takstol');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='garasjepakke');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='gulvspon');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='transportstatistikk');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='nøkkeltall');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 0,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='lese' and lower(window_access.window_name)='avvik');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='takstolpakking' and lower(window_access.window_name)='standard takstol');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='avviksansvarlig' and lower(window_access.window_name)='avvik');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='frontproduksjon' and lower(window_access.window_name)='front');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='pakklister' and lower(window_access.window_name)='pakkliste');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='pakklister' and lower(window_access.window_name)='bestillinger');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='fakturaansvarlig' and lower(window_access.window_name)='fakturering');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='avvik' and lower(window_access.window_name)='avvik');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='garasjepakking' and lower(window_access.window_name)='garasjepakke');
insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='garasjepakking' and lower(window_access.window_name)='gulvspon');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='veggproduksjon' and lower(window_access.window_name)='vegg');

insert into user_type_access(write_access,user_type_id,window_access_id) 
(select 1,user_type.user_type_id,window_access.window_access_id from user_type,window_access where lower(user_type.description)='gavlproduksjon' and lower(window_access.window_name)='gavl');