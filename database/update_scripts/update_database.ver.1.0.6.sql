create table product_area(
  product_area_id int identity(1,1) primary key,
  product_area nvarchar(100) not null,
  sort_nr int null,
);


insert into product_area(product_area,sort_nr) values('Garasje',1);
insert into product_area(product_area,sort_nr) values('Byggelement',2);
insert into product_area(product_area,sort_nr) values('Takstol',3);

alter table deviation add product_area_id int references product_area(product_area_id);

update deviation set product_area_id = 1 where deviation.product_name is not null;