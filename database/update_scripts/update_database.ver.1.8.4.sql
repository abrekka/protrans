create table cutting(
  cutting_id int identity(1,1) primary key,
  version nvarchar(50) not null,
  pro_sign nvarchar(59) not null,
  pro_id nvarchar(50) not null unique,
  order_id int references customer_order(order_id) not null unique
);
/**********************************************************************************************
create table cutting_line(
  cutting_line_id int identity(1,1) primary key,
  name nvarchar(50) not null,
  cut_id nvarchar(59) not null,
  cut_line nvarchar(4000) not null,
  line_nr int not null,
  area nvarchar(50),
  grade nvarchar(50),
  tickness nvarchar(50),
  depth nvarchar(50),
  total_length nvarchar(50),
  length_center nvarchar(50),
  description nvarchar(50),
  number_of nvarchar(50),
  timber_marking nvarchar(50),
  gross_length nvarchar(50),
  del_pc_belongs_to nvarchar(50),
  cutting_id int not null references cutting(cutting_id)
);
/**********************************************************************************************
insert into application_param(param_name,param_value) values('attachment_dir','vedlegg/');
/**********************************************************************************************
alter table attribute add attribute_data_type nvarchar(50);
/**********************************************************************************************
update attribute set attribute_data_type='TALL' where name='Makshøyde';

