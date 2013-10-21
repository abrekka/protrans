alter table customer_order add cached_comment nvarchar(2000);
/********************************************************************/
alter table customer_order add garage_colli_height int;
/********************************************************************/
alter table customer_order add has_missing_collies int;
/********************************************************************/
alter table post_shipment add garage_colli_height int;