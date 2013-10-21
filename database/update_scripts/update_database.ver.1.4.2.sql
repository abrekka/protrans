create table user_product_area_group(
  group_id int identity(1,1) not null,
  product_area_group_id int not null references product_area_group(product_area_group_id),
  user_id int not null references application_user(user_id)
);