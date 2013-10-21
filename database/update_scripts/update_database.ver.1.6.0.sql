create table craning_cost(
  craning_cost_id int identity(1,1) primary key,
  rule_id nvarchar(100) not null,
  description nvarchar(100) not null,
  cost_value decimal(18,2) not null
);

/*******************************************************************************************/
insert into craning_cost(rule_id,description,cost_value) values('STANDARD','Standard kraning',2800);
insert into craning_cost(rule_id,description,cost_value) values('STANDARD_PLUSS','Standard kraning, lang garasje',3500);
insert into craning_cost(rule_id,description,cost_value) values('LONG_HIGH','Kraning lange vegger/høye vegger/høy mur',2000);
insert into craning_cost(rule_id,description,cost_value) values('PORT_SUPPORT','Kraning bæring over port m/ brei portbredde',1700);
insert into craning_cost(rule_id,description,cost_value) values('STANDARD_PORT_SUPPORT','Standard kraning + bæring over port',3500);
insert into craning_cost(rule_id,description,cost_value) values('STANDARD_LONG_HIGH','Standard kraning + lange/høye vegger/høy mur/kvist',3800);
insert into craning_cost(rule_id,description,cost_value) values('ATTIC','Kvist',0);