alter table [dbo].[Application_user] add package_type int;

/****************************************************************************************************/
update [dbo].[application_param] set param_value=null where param_name='not_package';


/****************************************************************************************************/

ALTER VIEW [dbo].[takstol_package_v]
AS
SELECT     Order_line_id, Customer_nr, customer_name, customer_details, Order_id, Order_nr, address, Info, probability, packlist_ready, 
                      construction_type_name, article_name, Attribute_info, Number_of_items, Loading_date, Colli_id, produced, transport_details, Comment, 
                      transport_year, Transport_Week, load_time, post_shipment_id, product_area_group_name, action_started, egenordre, is_default, production_date, 
                      production_unit_name, sent, ord_no, ln_no, tross_drawer, cutting_started, cutting_done, default_article,
					  ordlnkunde.purcno,ordlnkjop.NoInvoAb as rest
FROM         dbo.takstol_v left outer join
F0100.dbo.ordln as ordlnkunde on dbo.takstol_v.ord_no = ordlnkunde.ordno and dbo.takstol_v.ln_no=ordlnkunde.lnno left outer join
F0100.dbo.ordln as ordlnkjop on ordlnkunde.purcno=ordlnkjop.ordno and ordlnkunde.lnpurcno=ordlnkjop.lnno

