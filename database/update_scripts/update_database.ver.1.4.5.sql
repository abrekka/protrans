update customer_order set garage_colli_height =(select height 
						from colli
						where colli_name='Garasjepakke' and 
						      colli.order_id=customer_order.order_id and height is not null)
/*****************************************************************************************/
