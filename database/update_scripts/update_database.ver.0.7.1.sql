create table external_order(
  external_order_id int identity(1,1) primary key,
  external_order_nr nvarchar(255),
  delivery_date datetime,
  att nvarchar(255),
  our_ref nvarchar(255),
  marked_with nvarchar(255),
  phone_nr nvarchar(50),
  fax_nr nvarchar(50),
  supplier_id int not null references supplier(supplier_id),
  order_id int not null references customer_order(order_id)
);

/*************************************************************************************/

create table external_order_line(
  external_order_line_id int identity(1,1) primary key,
  article_name nvarchar(255) not null,
  attribute_info nvarchar(255),
  number_of_items int,
  order_line_id int not null references order_line(order_line_id),
  external_order_id int not null references external_order(external_order_id)
);

/*************************************************************************************/

create table External_order_line_attribute(
  external_order_line_attribute_id int identity(1,1) primary key,
  external_order_line_attribute_name nvarchar(255) not null,
  external_order_line_attribute_value nvarchar(255) not null,
  external_order_line_id int not null references external_order_line(external_order_line_id)
);

/*************************************************************************************/

insert into application_param(param_name,param_value) values('ugland_adresse','Reddalsveien 47,  4886  GRIMSTAD,  NORGE, Tlf.: 37 25 75 00, Faks.: 37 25 75 01 Foretaksnummer: NO 929 685 164 MVA');
insert into application_param(param_name,param_value) values('ugland_fax','37 04 45 51');

/*************************************************************************************/
