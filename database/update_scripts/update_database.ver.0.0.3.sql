alter table Customer_order alter column Construction_type_id int not null;
alter table Customer_order add Do_assembly int null;

create table Assembly_team(
	Assembly_team_id int identity(1,1) not null primary key,
	Assembly_team_name nvarchar(155) not null unique
);

	
ALTER TABLE Customer_order WITH NOCHECK ADD 
	 PRIMARY KEY  CLUSTERED 
	(
		Order_id
	);	

create table Assembly(
	Assembly_id int identity(1,1) primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Order_id int not null references Customer_order(Order_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Invoiced int null,
	Comment nvarchar(255)
);

INSERT INTO Construction_type(Name) VALUES('A5');