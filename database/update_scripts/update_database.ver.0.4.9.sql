
alter table post_shipment add post_shipment_complete datetime;
alter table post_shipment add post_shipment_ready datetime;
alter table post_shipment alter column order_line_id int not null;

alter table colli add post_shipment_id int references post_shipment(post_shipment_id);
alter table colli alter column order_id int null;
