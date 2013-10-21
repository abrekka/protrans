alter table article_type_attribute add attribute_formula nvarchar(1000);


/*****************************************************************************************************/
Denne må kjøres etter at attributt stående er fjernet og lagt til atrikkel Gavl
/*****************************************************************************************************/
update article_type_attribute set attribute_formula='if((0.5*Bredde+45)*tan(Vinkel)>257,1,0)' 
where exists(select 1 
					from article_type 
					where article_type.article_type_id=article_type_attribute.article_type_id and 
							article_type.article_type_name='Gavl') and
			exists(select 1
					from attribute
					where attribute.attribute_id=article_type_attribute.attribute_id and
							lower(attribute.name) like '%stående%');