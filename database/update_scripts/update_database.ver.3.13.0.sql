update [Protrans2].[dbo].[Assembly] set sent_base='0' where sent_base is null

/*********************************************************************

ALTER TABLE [Protrans2].[dbo].[Assembly]
ADD CONSTRAINT default_sent_base DEFAULT '0' FOR sent_base;