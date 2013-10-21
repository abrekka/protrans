alter table Customer_order drop column Assembly_id;
alter table Customer_order drop constraint FK__Customer___Assem__02FC7413;--Navnet kan være forskjellig
alter table Customer_order drop column Assembly_id;

drop table assembly;

create table Assembly(
	Assembly_id int identity(1,1) primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Comment nvarchar(255),
	Order_id int not null references Customer_order(Order_id),
	Planned nvarchar(255) null,
	First_planned nvarchar(255) null,
	Assemblied nvarchar(3) null,
	Invoiced nvarchar(3) null
);

alter table Transport add Sent datetime null;

insert into application_param values('stein_artikkel','Takstein');
insert into application_param values('gulvspon_attributt','Gulvspon');
insert into application_param values('gg_attributt','Sendes fra GG');
insert into application_param values('garasjetype_artikkel','Garasjetype');

