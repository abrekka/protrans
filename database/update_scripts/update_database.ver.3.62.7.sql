USE [Protrans2]
GO

/****** Object:  StoredProcedure [dbo].[oppdater_pakking_fra_bx]    Script Date: 06.10.2019 15:05:40 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO




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
declare @lengde int
declare @hoyde int
declare @bredde int


declare @listeAvInnlagteKollier table (ordreid int,pakkenavn varchar(100),kolliid int);

DECLARE ordrelinje_cursor CURSOR FOR
SELECT order_id,Order_line_id,article_path, ord_no, ln_no,name,externalid,colli_name,Height,Length,Width  FROM [dbo].[ordrelinjer_pakket_i_bx_v]

OPEN ordrelinje_cursor 

FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid,@kollinavn,@hoyde,@lengde,@bredde;


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
        insert into Colli(Order_id,Colli_name,created_from,height,lenght,widht) values(@ordreid,@pakkenavn,'oppdater_pakking_fra_bx',@hoyde,@lengde,@bredde);
        set @lagtInnKolliId=SCOPE_IDENTITY()
        insert into @listeAvInnlagteKollier(ordreid,pakkenavn,kolliid) values(@ordreid,@pakkenavn,@lagtInnKolliId);
      end
      
	  update order_line set Colli_id=@lagtInnKolliId where order_line_id=@ordrelinjeid;
      
	  
  end
  else
  begin
    update order_line set Colli_id=@kolliid where order_line_id=@ordrelinjeid;

	if(@hoyde is not null and @lengde is not null and @bredde is not null and @hoyde <>0 and @lengde <>0 and @bredde <>0)
	begin
	  update colli set height=@hoyde,lenght=@lengde,widht=@bredde where colli_id=@kolliid;
	end

	if(@pakkenavn is not null and @pakkenavn<>@kollinavn)
	  begin
	    update colli set colli_name=@pakkenavn where colli_id=@kolliid;
	  end
  end


      FETCH NEXT FROM ordrelinje_cursor INTO @ordreid,@ordrelinjeid,@artikkel,@ordno,@lnno,@pakkenavn,@kolliid,@kollinavn,@hoyde,@lengde,@bredde

END

 

CLOSE ordrelinje_cursor 

DEALLOCATE ordrelinje_cursor

END
GO


