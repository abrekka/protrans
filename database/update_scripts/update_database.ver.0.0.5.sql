CREATE TABLE Order_line(
  Order_line_id int identity(1,1) primary key not null,
  Order_id int references Customer_order(Order_id) not null,
  Construction_type_article_id int references Construction_type_article(Construction_type_article_id),
  Article_type_id int references Article_type(Article_type_id)
);


CREATE TABLE Order_line_attribute(
  Order_line_attribute_id int identity(1,1) primary key not null,
  Order_line_id int references Order_line(Order_line_id) not null,
  Construction_type_attribute_id int references Construction_type_attribute(Construction_type_attribute_id),
  Construction_type_article_attribute_id int references Construction_type_article_attribute(Construction_type_article_attribute_id),
  Article_type_attribute_id int references Article_type_attribute(Article_type_attribute_id),
  Order_line_Attribute_value nvarchar(255) not null
);

INSERT INTO Order_line(Order_id) (select order_id
	from customer_order);
			
INSERT INTO Order_line(Order_id,Construction_type_article_id) (select order_id,construction_type_article.construction_type_article_id
	from customer_order,construction_type,construction_type_article
	where customer_order.construction_type_id = construction_type.construction_type_id and
			construction_type_article.construction_type_id = construction_type.construction_type_id);
			
INSERT INTO Order_line_attribute(Order_line_id,Construction_type_attribute_id,Order_line_attribute_value) (select order_line_id,construction_type_attribute.construction_type_attribute_id,construction_type_attribute.attribute_value
	from customer_order,construction_type_attribute,order_line
	where 	order_line.order_id = customer_order.order_id and
			customer_order.construction_type_id = construction_type_attribute.construction_type_id and
			construction_type_attribute.construction_type_id = construction_type_attribute.construction_type_id and
			order_line.construction_type_article_id is null and
			order_line.article_type_id is null);
			
INSERT INTO Order_line_attribute(Order_line_id,Construction_type_article_attribute_id,Order_line_attribute_value) (select 	order_line_id,
		construction_type_article_attribute.construction_type_article_attribute_id,
		construction_type_article_attribute.construction_type_article_value
	from 	order_line,
			construction_type_article_attribute,
			construction_type_article
	where 	order_line.construction_type_article_id = construction_type_article.construction_type_article_id and
			construction_type_article_attribute.construction_type_article_id = construction_type_article.construction_type_article_id);			