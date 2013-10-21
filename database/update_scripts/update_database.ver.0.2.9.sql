update application_param set param_value='Takstol;Takstoler' where param_value='Takstol;Takstol';
-------------------------------------------------------------------------------------------
alter table Customer_order add Collies_done int;
-------------------------------------------------------------------------------------------
alter table Order_line add has_article int;
-------------------------------------------------------------------------------------------

insert into application_param values('vegg_artikkel','Vegg');
-------------------------------------------------------------------------------------------
insert into application_param values('front_artikkel','Front');
-------------------------------------------------------------------------------------------
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Veggproduksjon','no.ugland.utransprod.gui.VeggProductionWindow',6);
-------------------------------------------------------------------------------------------
INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Frontproduksjon','no.ugland.utransprod.gui.FrontProductionWindow',7);
-------------------------------------------------------------------------------------------
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('vegg','vegg','vegg','vegg');
-------------------------------------------------------------------------------------------
INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('front','front','front','front');
-------------------------------------------------------------------------------------------
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Veggproduksjon' and application_user.user_name='vegg');
-------------------------------------------------------------------------------------------
INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Frontproduksjon' and application_user.user_name='front');
-------------------------------------------------------------------------------------------
--Oppdatere artikkel Kledning vegg til Vegg
update Article_type set article_type_name='Vegg' where article_type_name='Kledning vegg';
-------------------------------------------------------------------------------------------
update order_line set article_path='Vegg' where article_path='Kledning vegg';
-------------------------------------------------------------------------------------------
update order_line set article_path='Vegg$Kledning' where article_path='Kledning vegg$Kledning';
-------------------------------------------------------------------------------------------
--Legg til attributt Lager på artikkel Vegg
insert into attribute(name,yes_no) values('Lager',1);
-------------------------------------------------------------------------------------------
insert into article_type_attribute(Article_type_id,attribute_id) (select article_type.article_type_id,attribute.attribute_id from article_type,attribute where article_type.article_type_name='Vegg' and attribute.name='Lager');
-------------------------------------------------------------------------------------------
update article_type set top_level=0 where article_type_name='Kledning';
-------------------------------------------------------------------------------------------
--Legger inn attributt Lager på artikkel vegg for garasjetype
insert into construction_type_article_attribute(construction_type_article_id,
						article_type_attribute_id,
						construction_type_article_value)
(select construction_type_article_attribute.construction_type_article_id,
	new_article_type_attribute.article_type_attribute_id,
	'Ja'
from construction_type_article_attribute,
	article_type_attribute old_article_type_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute,
	attribute old_attribute,
	article_type
where construction_type_article_attribute.article_type_attribute_id = old_article_type_attribute.article_type_attribute_id and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_attribute.name='Tilpasning mur' and
	old_article_type_attribute.article_type_id = article_type.article_type_id and
	article_type.article_type_name='Vegg' and
	new_article_type_attribute.attribute_id =  new_attribute.attribute_id and
	new_attribute.name='Lager');
################################################################################
--Legge inn attributt Lager på ordrelinjer med Vegg
insert into order_line_attribute(order_line_id,
				construction_type_article_attribute_id,
				order_line_attribute_value,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_construction_type_article_attribute.construction_type_article_attribute_id,
	'Ja',
	'Lager'
from	order_line_attribute,
	construction_type_article_attribute,
	article_type_attribute,
	attribute,
	
	construction_type_article_attribute new_construction_type_article_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute
where order_line_attribute.construction_type_article_attribute_id= construction_type_article_attribute.construction_type_article_attribute_id and
	construction_type_article_attribute.article_type_attribute_id = article_type_attribute.article_type_attribute_id and
	article_type_attribute.attribute_id = attribute.attribute_id and
	attribute.name='Tilpasning mur' and
	
	new_construction_type_article_attribute.article_type_attribute_id=new_article_type_attribute.article_type_attribute_id and
	new_article_type_attribute.article_type_id = article_type_attribute.article_type_id and
	new_construction_type_article_attribute.construction_type_article_id = construction_type_article_attribute.construction_type_article_id and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	new_attribute.name='Lager')
################################################################################	
--Legger inn artikkel Front
insert into article_type(article_type_name) values('Front');
################################################################################
insert into article_type_attribute(Article_type_id,attribute_id) (select article_type.article_type_id,attribute.attribute_id from article_type,attribute where article_type.article_type_name='front' and attribute.name='Lager');
################################################################################
--Legger inn artikkel Front på alle garasjetyper
insert into construction_type_article(construction_type_id,article_type_id)
(select construction_type.construction_type_id,
	article_type.article_type_id
from construction_type,article_type
where article_type.article_type_name='Front');
################################################################################
insert into construction_type_article_attribute(construction_type_article_id,
						article_type_attribute_id,
						construction_type_article_value)
(select construction_type_article.construction_type_article_id,
	article_type_attribute.article_type_attribute_id,
	'Ja'
from construction_type_article,
	article_type_attribute,
	attribute,
	article_type
where 	attribute.name='Lager' and
	article_type_attribute.attribute_id=attribute.attribute_id and
	article_type_attribute.article_type_id=article_type.article_type_id and
	article_type.article_type_name='Front' and
	construction_type_article.article_type_id = article_type.article_type_id);
################################################################################
--Legger in front på alle ordrelinjer
insert into order_line(order_id,
			construction_type_article_id,
			article_path)
(select customer_order.order_id,
	construction_type_article.construction_type_article_id,
	'Front'
from customer_order,
	construction_type_article,
	article_type
where construction_type_article.article_type_id = article_type.article_type_id and
	article_type.article_type_name='Front' and
	customer_order.construction_type_id = construction_type_article.construction_type_id);
################################################################################	
insert into order_line_attribute(order_line_id,
				construction_type_article_attribute_id,
				order_line_attribute_value,
				order_line_attribute_name)
(select order_line.order_line_id,
	construction_type_article_attribute.construction_type_article_attribute_id,
	'Ja',
	'Lager'
from order_line,
	construction_type_article,
	article_type,
	construction_type_article_attribute
where order_line.construction_type_article_id = construction_type_article.construction_type_article_id and
	construction_type_article.article_type_id = article_type.article_type_id and
	article_type.article_type_name='Front' and
	construction_type_article_attribute.construction_type_article_id = construction_type_article.construction_type_article_id);	