create table supplier_product_area_group(
  id integer not null identity(1,1) primary key,
  supplier_id integer not null references supplier(supplier_id),
  product_area_group_id integer not null references product_area_group(product_area_group_id)
);

/*********************************************************************************************************
insert into supplier_product_area_group(supplier_id,product_area_group_id) (select supplier.supplier_id,product_area_group.product_area_group_id
from supplier,product_area_group
where product_area_group.product_area_group_name='Garasje'
   and exists(select 1 
             from supplier_type 
             where supplier_type.supplier_type_id=supplier.supplier_type_id 
                and supplier_type.supplier_type_name='Montering'))