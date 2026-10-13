alter table [dbo].[accident] add personal_injury_over_24 int;
alter table [dbo].[accident] add personal_injury_under_24 int;
alter table [dbo].[accident] add personal_injury_not_absent int;
alter table [dbo].[accident] add not_personal_injury int;
alter table [dbo].[accident] add number_of_own_employees int;


update [dbo].[accident] set personal_injury_over_24=1 where personal_injury=1 and absent_days is not null and absent_days >=1;
update [dbo].[accident] set personal_injury_under_24=1 where personal_injury=1 and absent_days is not null and absent_days >0 and absent_days <1;;
update [dbo].[accident] set personal_injury_not_absent=1 where personal_injury=1 and absent_days is not null and absent_days=0;
update [dbo].[accident] set not_personal_injury=1 where personal_injury=0;