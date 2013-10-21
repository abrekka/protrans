--Oppdatering av artikler og attributter
--Oppdaterer artikkel Takstol til å ha en attributt egenordre med valg
--Setter gammel attributt til annet navn
update Attribute set name = 'Egenordre_org' where name='Egenordre';

--Legger inn ny attributt Egenordre
insert into attribute(name) values('Egenordre');

--Legger inn valg for egenordre
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Nei' from attribute where attribute.name='Egenordre');
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Kvist' from attribute where attribute.name='Egenordre');
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Raft' from attribute where attribute.name='Egenordre');
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Annen vinkel' from attribute where attribute.name='Egenordre');
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Sperretak' from attribute where attribute.name='Egenordre');
insert into attribute_choice(attribute_id,choice_value) (select attribute_id,'Annet' from attribute where attribute.name='Egenordre');

--Legger inn ny attributt på artikkel Takstol
insert into article_type_attribute(Article_type_id,attribute_id) (select article_type.article_type_id,attribute.attribute_id from article_type,attribute where article_type.article_type_name='Takstol' and attribute.name='Egenordre');

--Legger inn ny attributt på garasjetyper som har takstol med defaultverdi Nei
insert into construction_type_article_attribute(construction_type_article_id,article_type_attribute_id,construction_type_article_value,dialog_order)
(select construction_type_article_attribute.construction_type_article_id,
	new_article_type_attribute.article_type_attribute_id,
	'Nei',
	construction_type_article_attribute.dialog_order
from construction_type_article_attribute,
	article_type_attribute old_article_type_attribute,
	attribute old_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute
where 	old_attribute.name='Egenordre_org' and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_article_type_attribute.article_type_attribute_id = construction_type_article_attribute.article_type_attribute_id and
	new_attribute.name='Egenordre' and
	new_article_type_attribute.attribute_id=new_attribute.attribute_id);
	
--Legge inn ordrelinjeattributt for alle som har garasjetype med takstol og sett verdi nei hvor det er nei
insert into order_line_attribute(Order_line_id,
				construction_type_article_attribute_id,
				order_line_attribute_value,
				dialog_order,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_construction_type_article_attribute.construction_type_article_attribute_id,
	'Nei',
	order_line_attribute.dialog_order,
	'Egenordre'
from order_line_attribute,
	construction_type_article_attribute old_construction_type_article_attribute,
	article_type_attribute old_article_type_attribute,
	attribute old_attribute,
	construction_type_article_attribute new_construction_type_article_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute
where old_attribute.name='Egenordre_org' and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_article_type_attribute.article_type_attribute_id = old_construction_type_article_attribute.article_type_attribute_id and
	old_construction_type_article_attribute.construction_type_article_attribute_id=order_line_attribute.construction_type_article_attribute_id and
	new_attribute.name='Egenordre' and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	new_article_type_attribute.article_type_attribute_id = new_construction_type_article_attribute.article_type_attribute_id and
	order_line_attribute.order_line_attribute_value='Nei');
	
	
	
--Legge inn ordrelinjeattributt for alle som har garasjetype med takstol og setter verdi Annet hvor det er Ja
	insert into order_line_attribute(Order_line_id,
					construction_type_article_attribute_id,
					order_line_attribute_value,
					dialog_order,
					order_line_attribute_name)
	(select order_line_attribute.order_line_id,
		new_construction_type_article_attribute.construction_type_article_attribute_id,
		'Annet',
		order_line_attribute.dialog_order,
		'Egenordre'
	from order_line_attribute,
		construction_type_article_attribute old_construction_type_article_attribute,
		article_type_attribute old_article_type_attribute,
		attribute old_attribute,
		construction_type_article_attribute new_construction_type_article_attribute,
		article_type_attribute new_article_type_attribute,
		attribute new_attribute
	where old_attribute.name='Egenordre_org' and
		old_article_type_attribute.attribute_id = old_attribute.attribute_id and
		old_article_type_attribute.article_type_attribute_id = old_construction_type_article_attribute.article_type_attribute_id and
		old_construction_type_article_attribute.construction_type_article_attribute_id=order_line_attribute.construction_type_article_attribute_id and
		new_attribute.name='Egenordre' and
		new_article_type_attribute.attribute_id = new_attribute.attribute_id and
		new_article_type_attribute.article_type_attribute_id = new_construction_type_article_attribute.article_type_attribute_id and
	order_line_attribute.order_line_attribute_value='Ja');
	

--Legge inn ordrelinjeattributt for alle som har artikkel takstol og sett verdi nei hvor det er nei
insert into order_line_attribute(Order_line_id,
				article_type_attribute_id,
				order_line_attribute_value,
				dialog_order,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_article_type_attribute.article_type_attribute_id,
	'Nei',
	order_line_attribute.dialog_order,
	'Egenordre'
from order_line_attribute,
	article_type_attribute old_article_type_attribute,
	attribute old_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute
where old_attribute.name='Egenordre_org' and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_article_type_attribute.article_type_attribute_id=order_line_attribute.article_type_attribute_id and
	new_attribute.name='Egenordre' and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	order_line_attribute.order_line_attribute_value='Nei');	
	
--Legge inn ordrelinjeattributt for alle som har artikkel takstol og sett verdi Annet hvor det er Ja
insert into order_line_attribute(Order_line_id,
				article_type_attribute_id,
				order_line_attribute_value,
				dialog_order,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_article_type_attribute.article_type_attribute_id,
	'Annet',
	order_line_attribute.dialog_order,
	'Egenordre'
from order_line_attribute,
	article_type_attribute old_article_type_attribute,
	attribute old_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute
where old_attribute.name='Egenordre_org' and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_article_type_attribute.article_type_attribute_id=order_line_attribute.article_type_attribute_id and
	new_attribute.name='Egenordre' and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	order_line_attribute.order_line_attribute_value='Ja');		
	
	
	
--Sletter alle ordrelineattributter med Egenordre_org for garasjetype
delete from order_line_attribute 
where exists(select 1 
	from construction_type_article_attribute,
		article_type_attribute,
		attribute
	where construction_type_article_attribute.construction_type_article_attribute_id = order_line_attribute.construction_type_article_attribute_id and
		construction_type_article_attribute.article_type_attribute_id = article_type_attribute.article_type_attribute_id and
		article_type_attribute.attribute_id = attribute.attribute_id and
		attribute.name='Egenordre_org');
						
						
--Sletter alle ordrelineattributter med Egenordre_org for artikkel
	delete from order_line_attribute 
	where exists(select 1 
		from article_type_attribute,
			attribute
		where article_type_attribute.article_type_attribute_id = order_line_attribute.article_type_attribute_id and
			article_type_attribute.attribute_id = attribute.attribute_id and
			attribute.name='Egenordre_org');
						
						
--Sletter alle attributter for garasjetyper som er av typen egenordre_org
delete from construction_type_article_attribute where exists(
select 1 
	from article_type_attribute,
		attribute
	where construction_type_article_attribute.article_type_attribute_id = article_type_attribute.article_type_attribute_id and
		article_type_attribute.attribute_id = attribute.attribute_id and
		attribute.name='Egenordre_org')
		
--Sletter attributt egenordre_org for takstol
delete from article_type_attribute where exists(select 1
						from attribute
						where attribute.attribute_id = article_type_attribute.attribute_id and
							attribute.name='Egenordre_org');
							
--Sletter attributt Egenordre_org
delete from attribute where name ='Egenordre_org';

--Legge til attributt stående under takstol (ja/nei)
insert into attribute(name,yes_no) values('Stående',1);
insert into article_type_attribute(Article_type_id,attribute_id) (select article_type.article_type_id,attribute.attribute_id from article_type,attribute where article_type.article_type_name='Takstol' and attribute.name='Stående');

--Legge til attributt stående under takstol på garasjetype
insert into construction_type_article_attribute(construction_type_article_id,
						article_type_attribute_id,
						construction_type_article_value)
(select construction_type_article_attribute.construction_type_article_id,
	new_article_type_attribute.article_type_attribute_id,
	'Nei'
from construction_type_article_attribute,
	article_type_attribute old_article_type_attribute,
	article_type_attribute new_article_type_attribute,
	attribute new_attribute,
	attribute old_attribute,
	article_type
where construction_type_article_attribute.article_type_attribute_id = old_article_type_attribute.article_type_attribute_id and
	old_article_type_attribute.attribute_id = old_attribute.attribute_id and
	old_attribute.name='Egenordre' and
	old_article_type_attribute.article_type_id = article_type.article_type_id and
	article_type.article_type_name='Takstol' and
	new_article_type_attribute.attribute_id =  new_attribute.attribute_id and
	new_attribute.name='Stående');

--Flytte Kvisttakstoler fra ordrelinjer som har takstol til takstol med attributt egenordre kvist og stående
-- Legg inn attributt stående for ordrelinjer som har garasjetype med takstol
insert into order_line_attribute(order_line_id,
				construction_type_article_attribute_id,
				order_line_attribute_value,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_construction_type_article_attribute.construction_type_article_attribute_id,
	'Nei',
	'Stående'
from	order_line_attribute,
	construction_type_article_attribute old_construction_type_article_attribute,
	construction_type_article_attribute new_construction_type_article_attribute,
	article_type_attribute old_article_type_attribute,
	article_type_attribute new_article_type_attribute,
	article_type,
	attribute old_attribute,
	attribute new_attribute
where order_line_attribute.construction_type_article_attribute_id = old_construction_type_article_attribute.construction_type_article_attribute_id and
	old_construction_type_article_attribute.article_type_attribute_id = old_article_type_attribute.article_type_attribute_id and
	old_article_type_attribute.article_type_id = article_type.article_type_id and
	old_attribute.attribute_id = old_article_type_attribute.attribute_id and
	old_attribute.name='Egenordre' and
	article_type.article_type_name='Takstol' and
	new_article_type_attribute.article_type_id = article_type.article_type_id and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	new_attribute.name='Stående' and
	new_construction_type_article_attribute.article_type_attribute_id=new_article_type_attribute.article_type_attribute_id);
	
-- Legg inn attributt stående for ordrelinjer som har artikkel takstol
insert into order_line_attribute(order_line_id,
				article_type_attribute_id,
				order_line_attribute_value,
				order_line_attribute_name)
(select order_line_attribute.order_line_id,
	new_article_type_attribute.article_type_attribute_id,
	'Nei',
	'Stående'
from	order_line_attribute,
	article_type_attribute old_article_type_attribute,
	article_type_attribute new_article_type_attribute,
	article_type,
	attribute old_attribute,
	attribute new_attribute
where order_line_attribute.article_type_attribute_id = old_article_type_attribute.article_type_attribute_id and
	old_article_type_attribute.article_type_id = article_type.article_type_id and
	old_attribute.attribute_id = old_article_type_attribute.attribute_id and
	old_attribute.name='Egenordre' and
	article_type.article_type_name='Takstol' and
	new_article_type_attribute.article_type_id = article_type.article_type_id and
	new_article_type_attribute.attribute_id = new_attribute.attribute_id and
	new_attribute.name='Stående');	
	
--Oppdatere ordrelinjer takstol til stående ja dersom kvisttakstol er ja
update order_line_attribute set order_line_attribute_value='Ja' 
where order_line_attribute.order_line_attribute_name='Stående' and
	exists(select 1 
		from order_line_attribute other_attribute,
			order_line other_order_line,
			order_line this_order_line,
			customer_order
		where other_attribute.order_line_id = other_order_line.order_line_id and
			other_order_line.order_id = customer_order.order_id and
			other_attribute.order_line_attribute_name='Kvisttakstoler' and
			other_attribute.order_line_attribute_value='Ja' and
			this_order_line.order_id = customer_order.order_id and
			this_order_line.order_line_id = order_line_attribute.order_line_id);
			
			
--Oppdatere ordrelinjer takstol til egnordre kvist dersom kvisttakstol er ja
update order_line_attribute set order_line_attribute_value='Kvist' 
where order_line_attribute.order_line_attribute_name='Egenordre' and
	exists(select 1 
		from order_line_attribute other_attribute,
			order_line other_order_line,
			order_line this_order_line,
			customer_order
		where other_attribute.order_line_id = other_order_line.order_line_id and
			other_order_line.order_id = customer_order.order_id and
			other_attribute.order_line_attribute_name='Kvisttakstoler' and
			other_attribute.order_line_attribute_value='Ja' and
			this_order_line.order_id = customer_order.order_id and
			this_order_line.order_line_id = order_line_attribute.order_line_id);
			
--Fjerner ordrelinjeattributter kvisttakstoler for garasjetype
delete from order_line_attribute where construction_type_attribute_id is not null and order_line_attribute_name='Kvisttakstoler';

--Fjerne attributt Kvisttakstoler fra garasjetype
delete from construction_type_attribute 
where exists(select 1
		from attribute
		where construction_type_attribute.attribute_id = attribute.attribute_id and
			attribute.name='Kvisttakstoler');
			
--Sletter attributt kvisttakstoler
delete from attribute where name='Kvisttakstoler';

--Endre attributt gulvspon til en artikkel med attributt Ja/Nei
--Legger inn artikkel gulvspon
insert into article_type(article_type_name,top_level) values('Gulvspon',1);
--Legger inn attributt Har gulvspon
insert into attribute(name,yes_no) values('Har gulvspon',1);
--Legger inn attributt ja/nei for gulvspon
insert into article_type_attribute(article_type_id,attribute_id)
(select article_type.article_type_id,
	attribute.attribute_id
from article_type,
	attribute
where article_type.article_type_name='Gulvspon' and
	attribute.name='Har gulvspon');
	
--Legger inn artikkel gulvspon for garasjetyper som har attributt gulvspon
insert into construction_type_article(construction_type_id,article_type_id)
(select construction_type.construction_type_id,
	article_type.article_type_id
from construction_type,article_type
where exists(select 1 
		from construction_type_attribute,
			attribute
		where construction_type_attribute.construction_type_id = construction_type.construction_type_id and
			construction_type_attribute.attribute_id = attribute.attribute_id and
			attribute.name='Gulvspon') and
		article_type.article_type_name='Gulvspon');
		
--Legger inn attributt Har gulvspon med defualt nei for artikkel gulvspon for garasjetyper
insert into construction_type_article_attribute(construction_type_article_id,
						article_type_attribute_id,
						construction_type_article_value)
(select construction_type_article.construction_type_article_id,
	article_type_attribute.article_type_attribute_id,
	'Nei'
from construction_type_article,
	article_type_attribute,
	attribute,
	article_type
where 	attribute.name='Har gulvspon' and
	article_type_attribute.attribute_id=attribute.attribute_id and
	article_type.article_type_name='Gulvspon' and
	construction_type_article.article_type_id = article_type.article_type_id);
	
	
--legger inn ordrelinje for gulvspon for de som har attributt gulvspon
insert into order_line(order_id,
			construction_type_article_id,
			article_path)
(select old_order_line.order_id,
	construction_type_article.construction_type_article_id,
	'Gulvspon'
from order_line old_order_line,
	order_line_attribute,
	construction_type_article,
	article_type,
	customer_order
where order_line_attribute.order_line_attribute_name='Gulvspon' and
	order_line_attribute.order_line_id=old_order_line.order_line_id and
	old_order_line.construction_type_article_id is null and
	old_order_line.article_type_id is null and
	old_order_line.order_id = customer_order.order_id and
	customer_order.construction_type_id = construction_type_article.construction_type_id and
	construction_type_article.article_type_id=article_type.article_type_id and
	article_type.article_type_name='Gulvspon');
	
	
--legger inn ordrelinjeattributt for Har gulvspon for de som har nei
insert into order_line_attribute(order_line_id,
				construction_type_article_attribute_id,
				order_line_attribute_value,
				order_line_attribute_name)
(select order_line.order_line_id,
	construction_type_article_attribute.construction_type_article_attribute_id,
	'Ja',
	'Har gulvspon'
from order_line,
	construction_type_article,
	article_type,
	construction_type_article_attribute
where order_line.construction_type_article_id = construction_type_article.construction_type_article_id and
	construction_type_article.article_type_id = article_type.article_type_id and
	article_type.article_type_name='Gulvspon' and
	construction_type_article_attribute.construction_type_article_id = construction_type_article.construction_type_article_id and
exists (select 1 
		from order_line_attribute att,
			order_line att_order_line
		where att.order_line_attribute_name='Gulvspon' and
			att.order_line_attribute_value='Ja' and
			att.order_line_id = att_order_line.order_line_id and
			att_order_line.order_id = order_line.order_id));
			
--fjerner attributt gulvspon fra ordrelineattributter
delete from order_line_attribute where order_line_attribute_name='Gulvspon';

--fjerner attributt gulvspon fra garasjetyper
delete from construction_type_attribute 
where exists(select 1 
		from attribute
		where attribute.name='Gulvspon' and
			construction_type_attribute.attribute_id = attribute.attribute_id);