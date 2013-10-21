alter table Customer_order add Invoiced int;
alter table Customer_order add Invoice_date datetime;
alter table Assembly drop column Invoiced;
alter table Attribute add Yes_no int;


CREATE TABLE Attribute_choice(
  Attribute_choice_id int identity(1,1) primary key not null,
  Attribute_id int not null references Attribute(Attribute_id),
  Choice_value nvarchar(255) not null,
  Comment nvarchar(255)
);
