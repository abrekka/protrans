alter table [dbo].[accident] add reported_arbeidstilsynet int;
update [dbo].[accident] set reported_arbeidstilsynet=reported_police;
update [dbo].[accident] set reported_police=null;