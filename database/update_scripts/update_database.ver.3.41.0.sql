CREATE VIEW [dbo].[monteringsanvisning_v]
AS
select distinct Protrans2.dbo.Customer_order.Order_nr, F0100.dbo.FreeInf1.PictFNm as Filsti, '' as Overskrift
from F0100.dbo.ord inner join
        Protrans2.dbo.Customer_order on Protrans2.dbo.Customer_order.Order_nr = F0100.dbo.ord.inf6 inner join
       F0100.dbo.ordln on F0100.dbo.ordln.ordno = F0100.dbo.ord.ordno inner join
       F0100.dbo.FreeInf1 on F0100.dbo.FreeInf1.prodno = F0100.dbo.ordln.prodno