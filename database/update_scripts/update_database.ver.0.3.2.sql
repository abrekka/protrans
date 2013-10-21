create table job_function(
  job_function_id int identity(1,1) not null primary key,
  job_function_name nvarchar(100) not null,
  job_function_description nvarchar(255) null,
  user_id int references application_user(user_id) null
  
);

create table deviation_status(
  deviation_status_id int identity(1,1) not null primary key,
  deviation_status_name nvarchar(100) not null,
  deviation_status_description nvarchar(255) null
);

create table function_category(
  function_category_id int identity(1,1) not null primary key,
  function_category_name nvarchar(100) not null,
  function_category_description nvarchar(255) null,
  job_function_id int references job_function(job_function_id) not null
);


create table deviation(
  deviation_id int identity(1,1) not null primary key,
  registration_date datetime not null,
  user_name nvarchar(100) not null,
  description nvarchar(1000) null,
  customer_nr int null,
  order_nr int null,
  date_closed datetime null,
  free_text nvarchar(1000) null,
  product_name nvarchar(255) null,
  preventive_action nvarchar(3) null,
  own_function_id int references job_function(job_function_id) null,
  deviation_function_id int references job_function(job_function_id) null,
  deviation_status_id int references deviation_status(deviation_status_id) null,
  function_category_id int references function_category(function_category_id) null,
  internal_cost_id int references order_cost(order_cost_id) null,
  external_cost_id int references order_cost(order_cost_id) null
);

alter table order_cost alter column order_id int null;

alter table customer_order add order_ready datetime;
alter table customer_order add order_complete datetime;

alter table customer_order add package_started datetime;