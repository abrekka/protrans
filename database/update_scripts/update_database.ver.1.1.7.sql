/******************************* Mulig navn på constraint er noe annet i databasen på Ugland  ****/
ALTER TABLE [dbo].[deviation] DROP CONSTRAINT [FK__deviation__inter__1E3A7A34]
/*************************************************************************************************/
alter table deviation drop column internal_cost_id;
/******************************* Mulig navn på constraint er noe annet i databasen på Ugland  ****/
ALTER TABLE [dbo].[deviation] DROP CONSTRAINT [FK__deviation__exter__1F2E9E6D]
/*************************************************************************************************/
alter table deviation drop column external_cost_id;
/*************************************************************************************************/
drop table deviation_comment;
/*************************************************************************************************/
create table preventive_action(
  preventive_action_id int identity(1,1) primary key,
  preventive_action_nr nvarchar(50) not null unique,
  manager nvarchar(50),
  preventive_action_name nvarchar(50) not null,
  job_function_id int references job_function(job_function_id),
  function_category_id int references function_category(function_category_id),
  description nvarchar(1000),
  expected_outcome nvarchar(1000),
  project_closed int
);
/*************************************************************************************************/
create table preventive_action_comment(
  preventive_action_comment_id int identity(1,1) primary key,
  user_name nvarchar(100) not null,
  comment_date datetime not null,
  comment nvarchar(1000) not null,
  comment_type int not null,
  preventive_action_id int references preventive_action(preventive_action_id) not null
);

/**********************************************************************************************************************/
insert into window_access(window_name) values('Prevantive tiltak');
/**********************************************************************************************************************/
alter table deviation add preventive_action_id int references preventive_action(preventive_action_id);