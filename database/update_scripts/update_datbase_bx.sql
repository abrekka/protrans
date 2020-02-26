CREATE VIEW [dbo].[ordrelinjer_pakket_i_bx_v]
AS
select order_id,Order_line_id,article_path, ord_no, ln_no,name,externalid
from Protrans2.dbo.Order_line pol,BxEngine.dbo.ShipmentProduct bol,BxEngine.dbo.ShipmentLoadCarrier slc
where pol.ord_no=bol.Orderno and pol.ln_no=bol.Orderline and bol.ShipmentLoadCarrier=slc.Id and colli_id is null

/************************************************************************************************************

CREATE PROCEDURE [dbo].[oppdater_pakking_fra_bx] 
AS
BEGIN
declare @ordreid int
declare @ordrelinjeid int
declare @artikkel varchar(100)
declare @ordno int
declare @lnno int
declare @pakkenavn varchar(100)
declare @kolliid int

      DECLARE ordrelinje_cursor CURSOR FOR 
SELECT order_id,Order_line_id,article_path, ord_no, ln_no,name,externalid
FROM [dbo].[ordrelinjer_pakket_i_bx_v]

OPEN ordrelinje_cursor  
FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid

WHILE @@FETCH_STATUS = 0  
BEGIN 
  if(@pakkenavn is null)
    set @pakkenavn=@artikkel
  if(@kolliid is null)
  begin
    insert into Colli(Order_id,Colli_name,created_from) values(@ordreid,@pakkenavn,'oppdater_pakking_fra_bx');
	update order_line set Colli_id=SCOPE_IDENTITY() where order_line_id=@ordrelinjeid;
  end
  else
  begin
    update order_line set Colli_id=@kolliid where order_line_id=@ordrelinjeid;
  end

      FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid
END 

CLOSE db_cursor  
DEALLOCATE db_cursor 
END
GO


