create table deviation_comment(
  deviation_comment_id int identity(1,1) primary key,
  user_name nvarchar(100) not null,
  comment_date datetime not null,
  comment nvarchar(1000) not null,
  deviation_id int not null references deviation(deviation_id)
);

insert into deviation_comment(user_name,comment_date,comment,deviation_id) (select 'org',getdate(),free_text,deviation_id from deviation where free_text is not null);