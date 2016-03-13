alter table [Protrans2].[dbo].[Customer_order] add sist_oppdatert datetime;
/***********************************************************
alter table [Protrans2].[dbo].[transport] add sist_oppdatert datetime;
/**************************************************************
alter table [Protrans2].[dbo].[assembly] add sist_oppdatert datetime;



/*******************************************************************

CREATE TRIGGER updateModified
ON [dbo].[Customer_order]
AFTER UPDATE 
AS
   UPDATE [dbo].[Customer_order]
   SET sist_oppdatert = SYSDATETIME()
   FROM Inserted i
   WHERE [dbo].[Customer_order].order_id = i.order_id
   
/***************************************

create TRIGGER updateTransport
ON [dbo].[transport]
AFTER UPDATE 
AS
   UPDATE [dbo].[transport]
   SET sist_oppdatert = SYSDATETIME()
   FROM Inserted i
   WHERE [dbo].[transport].transport_id = i.transport_id
   
   
/**********************
   
   create TRIGGER updateAssembly
   ON [dbo].[assembly]
   AFTER UPDATE 
   AS
      UPDATE [dbo].[assembly]
      SET sist_oppdatert = SYSDATETIME()
      FROM Inserted i
   WHERE [dbo].[assembly].assembly_id = i.assembly_id