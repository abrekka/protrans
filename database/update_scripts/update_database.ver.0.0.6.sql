CREATE TABLE Article_type_article_type(
  Article_type_article_type_id int identity(1,1) primary key not null,
  Article_type_id int references Article_type(Article_type_id) not null,
  Article_type_id_ref int references Article_type(Article_type_id) not null
);

alter table Construction_type_article alter column Construction_type_id int null;

ALTER TABLE Construction_type_article ADD Construction_type_article_ref int references Construction_type_article(Construction_type_article_id);

alter table Order_line alter column Order_id int null;

ALTER TABLE Order_line ADD Order_line_ref int references Order_line(Order_line_id);