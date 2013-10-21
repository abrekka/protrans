alter table product_area_group add use_prepayment int;
update product_area_group set use_prepayment=1 where product_area_group_name='Garasje';
update product_area_group set use_prepayment=1 where product_area_group_name='Byggelement';