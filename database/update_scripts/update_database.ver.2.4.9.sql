/************************* Setter kolli for takstol til kolli for ordre *******************
update order_line set colli_id=(select distinct line2.colli_id
               from order_line line2
               where line2.order_id = order_line.order_id
                  and line2.order_line_id<>order_line.order_line_id
                  and line2.article_path='Takstoler'
                  and line2.colli_id is not null)
where colli_id is not null 
   and order_line.article_path='Takstoler'
   and exists (select 1 
               from colli 
               where colli.colli_id=order_line.colli_id
                  and colli.order_id <> order_line.order_id)
   and exists (select 1
               from order_line line2
               where line2.order_id = order_line.order_id
                  and line2.order_line_id<>order_line.order_line_id
                  and line2.article_path='Takstoler'
                  and line2.colli_id is not null)
                  
                  
/************************* Dersom det fortsatt er takstolordrelinjer som ligger i kolli til annen ordre settes disse til null *******************                  
update order_line set colli_id=null
where colli_id is not null 
   and order_line.article_path='Takstoler'
   and exists (select 1 
               from colli 
               where colli.colli_id=order_line.colli_id
                  and colli.order_id <> order_line.order_id)
   and exists (select 1
               from order_line line2
               where line2.order_id = order_line.order_id
                  and line2.order_line_id<>order_line.order_line_id
                  and line2.article_path='Takstoler'
                  and line2.colli_id is not null)                  
                  

/***********************************************************************************************************************************                  
CREATE TRIGGER [dbo].[tr_check_colli] 
   ON  [dbo].[Order_line] 
   AFTER INSERT,UPDATE
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for trigger here
DECLARE @numberOf numeric

select @numberOf = count(order_line_id) 
from order_line 
where colli_id is not null 
   and exists (select 1 
               from colli 
               where colli.colli_id=order_line.colli_id
                  and colli.order_id <> order_line.order_id);

if(@numberOf >0)
BEGIN
RAISERROR 50009 'Kan ikke ha ordrelinje i kolli som ikke hører til ordre'
ROLLBACK TRANSACTION
RETURN
END

END
                  