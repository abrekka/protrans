ALTER TABLE dbo.order_line add opprettet datetime default getdate();
ALTER TABLE dbo.order_line add oppdatert datetime default getdate();


/**************************************************************************************************************************************************
CREATE TRIGGER trg_order_line_oppdatert
  ON order_line
 AFTER UPDATE
AS

UPDATE order_line set oppdatert = GETDATE()
FROM order_line
where order_line_id in(select order_line_id from inserted)
