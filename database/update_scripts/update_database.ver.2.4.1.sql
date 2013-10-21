create view intelle_v
as
select customer_order.order_nr
   ,ipk_ord.max_hoyde
from customer_order
   join intelle_ordre.dbo.ipk_ord ipk_ord on ipk_ord.ordno='SO-'+customer_order.order_nr  collate Danish_Norwegian_CI_AS 