alter table area add snowload_basic_value decimal(5,2);
alter table area add height_limit int;
alter table area add delta_snowload decimal(5,2);
alter table area add snowload_max decimal(5,2);
alter table area add snowload_comment nvarchar(150);

/*******************************************************************************************************************************************************************************

ALTER VIEW [dbo].[import_order_prob_100_v]
AS
SELECT     superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1, superoffice.crm5.sale.amount, superoffice.crm5.sale.saledate, 
                      superoffice.crm5.sale.userdef_id, superoffice.crm5.sale.registered, superoffice.crm5.sale.probability, superoffice.crm5.sale.contact_id, 
                      F0100.dbo.Ord.R1 AS product_area_nr, F0100.dbo.Ord.DelAd3 + ' ' + F0100.dbo.Ord.DelAd4 AS delivery_address, 
                      F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man, 
                      F0100.dbo.Ord.Inf3 AS telephonenr_site
FROM         superoffice.crm5.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6
WHERE     (F0100.dbo.Ord.R1 IN (100, 200, 300, 400,210)) AND (superoffice.crm5.sale.saledate > 1260230400) AND (superoffice.crm5.sale.probability = 100) AND 
                      (superoffice.crm5.sale.number1 <> '') AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr  collate Danish_Norwegian_CI_AS  = superoffice.crm5.sale.number1)))

/*******************************************************************************************************************************************************************************
/* Før denne kjøres må det defneres en produktenhet (product_area) som heter 'Garasje Precut'
/* Dersom denne ikke er lagt inn kan du bruke denne sql'en: insert into product_area(product_area,sort_nr,product_area_group_id,product_area_nr) values('Garasje Precut',1,210);
/*******************************************************************************************************************************************************************************
insert into construction_type(name,product_area_id) (
select name+'PRECUT',product_area.product_area_id 
from construction_type,product_area
where construction_type.product_area_id=1 
	and len(name)=2
	and lower(product_area.product_area)='garasje precut')
/*******************************************************************************************************************************************************************************
insert into construction_type_attribute(construction_type_id,attribute_id,attribute_value,dialog_order)
(select new_cons.construction_type_id,att.attribute_id,att.attribute_value,att.dialog_order
	from construction_type  new_cons,construction_type_attribute att 
	where new_cons.name like '%PRECUT'  and exists(select 1 from construction_type cons where cons.name = substring(new_cons.name,1,2) and cons.construction_type_id=att.construction_type_id)
	and exists(select 1 from attribute where attribute.attribute_id=att.attribute_id and attribute.name in ('Bredde','Lengde')))	
/*******************************************************************************************************************************************************************************
