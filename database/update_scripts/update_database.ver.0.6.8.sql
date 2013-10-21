Lag en backup av database

/***************************************************************************************************/			
insert into article_type_article_type(article_type_id,article_type_id_ref) 
	(select gavl.article_type_id ,kledning.article_type_id 
		from article_type gavl,article_type kledning
		where 	gavl.article_type_name='Gavl' and
			kledning.article_type_name='Kledning');
			
/***************************************************************************************************/			
			
insert into construction_type_article(construction_type_id,article_type_id,dialog_order)
	(select construction_type.construction_type_id,article_type.article_type_id,gavlkledning.dialog_order
		from construction_type,article_type,construction_type_article gavlkledning,article_type art_gavlkledning
		where is_master=1 and
			lower(article_type.article_type_name)='Gavl' and
			gavlkledning.construction_type_id=construction_type.construction_type_id and
			gavlkledning.article_type_id=art_gavlkledning.article_type_id and
			lower(art_gavlkledning.article_type_name)='gavlkledning')
			
/***************************************************************************************************/			

Kjør SetGavlKledning.bat

/***************************************************************************************************/			

delete from order_line where article_path='Gavlkledning';

/***************************************************************************************************/			

delete 
  from construction_type_article 
  where exists(select 1 
                 from article_type 
		 where article_type_name='Gavlkledning' and 
			article_type.article_type_id=construction_type_article.article_type_id)
			
/***************************************************************************************************/
alter table assembly drop column assemblied;