update assembly_team set assembly_team_name='K.F. Montering DA' where assembly_team_name='Karsten & Finn'
/********************************************************************************************************
update assembly_team set assembly_team_name='Sørensen Garagebygg ANS' where assembly_team_name='Sørensens Garagebygg ANS'
/********************************************************************************************************
insert into supplier(supplier_name,supplier_type_id) values('Diverse montører',2);
/********************************************************************************************************
alter table assembly add supplier_id int references supplier(supplier_id);
/********************************************************************************************************
update assembly set supplier_id=(select supplier_id 
				from supplier,assembly_team
				where supplier.supplier_name=assembly_team.assembly_team_name and 
				assembly_team.assembly_team_id=assembly.assembly_team_id)
/********************************************************************************************************
ALTER TABLE [dbo].[Assembly] DROP CONSTRAINT [FK__Assembly__Assemb__0A338187]
/********************************************************************************************************
alter table assembly drop column assembly_team_id;
/********************************************************************************************************
drop table assembly_team;
/********************************************************************************************************
alter table supplier add inactive int;
/********************************************************************************************************
ALTER VIEW [dbo].[montering_v]
AS
SELECT     dbo.[Assembly].Assembly_year, 
			dbo.[Assembly].Assembly_week, 
			dbo.[Assembly].Supplier_id, 
            CAST(dbo.Customer.Customer_nr AS nvarchar(20)) 
            + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name + '-' + 
			dbo.Customer_order.Order_nr + ' ' + dbo.Customer_order.Postal_code + ' ' 
			+ dbo.Customer_order.Post_office + ',' + dbo.Construction_type.Name + 
			',' + dbo.Customer_order.Info AS order_details
FROM         dbo.[Assembly] INNER JOIN
                      dbo.Customer_order ON dbo.[Assembly].Order_id = dbo.Customer_order.Order_id INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN
                      dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id

/********************************************************************************************************
alter table product_area add own_production_cost_limit decimal(8,2);
/********************************************************************************************************
update product_area set own_production_cost_limit=20000 where product_area.product_area='Garasje villa';
/********************************************************************************************************
update product_area set own_production_cost_limit=20000 where product_area.product_area='Garasje rekke';
/********************************************************************************************************
update product_area set own_production_cost_limit=0 where product_area.product_area='Byggelement';
/********************************************************************************************************
update product_area set own_production_cost_limit=0 where product_area.product_area='Takstol';