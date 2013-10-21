insert into application_param values('kolli_1','Garasjepakke;Garasjetype');
insert into application_param values('kolli_2','Takstol;Takstol');
insert into application_param values('kolli_3','Takstein;Takstein');
insert into application_param values('kolli_4','Gulvspon;Gulvspon');

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Garasjepakking','no.ugland.utransprod.gui.MainPackageWindow',5);

INSERT INTO Application_user(User_name,First_name,Last_name,Password) VALUES('garasjepakking','garasjepakking','garasjepakking','garasjepakking');

INSERT INTO User_role(User_type_id,User_id) (select User_type.User_type_id,Application_user.User_id from user_type,application_user where user_type.description='Garasjepakking' and application_user.user_name='garasjepakking');

insert into application_param values('gulvspon_artikkel','Gulvspon');