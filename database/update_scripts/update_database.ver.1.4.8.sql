/***********************************************************************************************/
alter table order_cost add post_shipment_id int references post_shipment(post_shipment_id);
/***********************************************************************************************/
alter table order_cost add comment nvarchar(1000);