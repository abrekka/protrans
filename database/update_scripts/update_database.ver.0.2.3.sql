drop table assembly;

create table Assembly(
	Assembly_id int identity(1,1) primary key not null,
	Assembly_team_id int not null references Assembly_team(Assembly_team_id),
	Assembly_year int not null,
	Assembly_week int not null,
	Comment nvarchar(255)
);


alter table Customer_order add Assembly_id int null references Assembly(Assembly_id);
alter table Customer_order add Info nvarchar(100) null;

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Takstolproduksjon','no.ugland.utransprod.gui.TakstolProductionWindow',3);

INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('takstol','takstol','takstol','takstol');

INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Takstolproduksjon' and application_user.user_name='takstol');

insert into application_param values('takstol_artikkel','Takstol');