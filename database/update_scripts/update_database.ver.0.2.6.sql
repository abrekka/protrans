CREATE TABLE Colli(
  Colli_id int identity(1,1) primary key not null,
  Order_id int references Customer_order(Order_id) not null,
  Colli_name nvarchar(255) not null,
  Transport_id int references Transport(Transport_id) null,
  Sent datetime null
);

alter table Order_line add Colli_id int references Colli(Colli_id) null;

alter table Application_user alter column User_name nvarchar(50) not null;

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Takstolpakking','no.ugland.utransprod.gui.TakstolPackageWindow',4);
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('takstol_pakking','takstol_pakking','takstol_pakking','takstol_pakking');
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Takstolpakking' and application_user.user_name='takstol_pakking');