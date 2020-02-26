ALTER VIEW [dbo].[ordrelinjer_pakket_i_bx_v]
AS
select pol.order_id,Order_line_id,article_path, ord_no, ln_no,name,externalid,colli.Colli_name
from Protrans2.dbo.Order_line pol,BxEngine.dbo.ShipmentProduct bol,BxEngine.dbo.ShipmentLoadCarrier slc 
left outer join Colli on slc.externalid=Colli_id
where pol.ord_no=bol.Orderno and pol.ln_no=bol.Orderline and bol.ShipmentLoadCarrier=slc.Id and pol.colli_id is null


/*************************************************************************************************************************

ALTER PROCEDURE [dbo].[oppdater_pakking_fra_bx] 
AS
BEGIN
declare @ordreid int
declare @ordrelinjeid int
declare @artikkel varchar(100)
declare @ordno int
declare @lnno int
declare @pakkenavn varchar(100)
declare @kolliid int
declare @antallKollier int
declare @lagtInnKolliId int
declare @kollinavn varchar(100)

declare @listeAvInnlagteKollier table (ordreid int,pakkenavn varchar(100),kolliid int);

      DECLARE ordrelinje_cursor CURSOR FOR 
SELECT order_id,Order_line_id,article_path, ord_no, ln_no,name,externalid,colli_name  FROM [dbo].[ordrelinjer_pakket_i_bx_v]

OPEN ordrelinje_cursor  
FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid,@kollinavn

WHILE @@FETCH_STATUS = 0  
BEGIN 
  if(@pakkenavn is null)
    set @pakkenavn=@artikkel
  if(@kolliid is null)
  begin
    select  @antallKollier=count(*) from @listeAvInnlagteKollier where ordreid=@ordreid and pakkenavn=@pakkenavn;

	if(@antallKollier>0)
	begin
	  select @lagtInnKolliId=kolliid from @listeAvInnlagteKollier where ordreid=@ordreid and pakkenavn=@pakkenavn;
	end
	else
	begin
      insert into Colli(Order_id,Colli_name,created_from) values(@ordreid,@pakkenavn,'oppdater_pakking_fra_bx');
	  set @lagtInnKolliId=SCOPE_IDENTITY()
	  insert into @listeAvInnlagteKollier(ordreid,pakkenavn,kolliid) values(@ordreid,@pakkenavn,@lagtInnKolliId);
	  
	end
	update order_line set Colli_id=@lagtInnKolliId where order_line_id=@ordrelinjeid;
	if(@pakkenavn<>@kollinavn)
	begin
	  update colli set colli_name=@pakkenavn where colli_id=@kolliid;
	end
  end
  else
  begin
    update order_line set Colli_id=@kolliid where order_line_id=@ordrelinjeid;
  end

      FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid,@kollinavn
END 

CLOSE ordrelinje_cursor  
DEALLOCATE ordrelinje_cursor 
END
GO

