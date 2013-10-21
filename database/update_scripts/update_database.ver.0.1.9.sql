alter table order_line add article_path nvarchar(500);
alter table order_line_attribute add order_line_attribute_name nvarchar(255);

KJØR OPPDATERINGSSNUTT

alter table order_line alter column article_path nvarchar(500) not null;
alter table order_line_attribute alter column order_line_attribute_name nvarchar(255) not null;