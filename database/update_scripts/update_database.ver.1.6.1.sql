CREATE VIEW [dbo].[sales_v]
AS
select superoffice.crm5.sale.sale_id,
		superoffice.crm5.sale.number1 as order_nr,
		superoffice.crm5.sale.probability,
		superoffice.crm5.sale.group_idx,
		superoffice.crm5.sale.saledate,
		superoffice.crm5.sale.registered,
		superoffice.crm5.contact.number2 as customer_nr,
		superoffice.crm5.contact.name as customer_name,
		superoffice.crm5.address.address1 as delviery_address,
		superoffice.crm5.address.zipcode as postal_code,
		superoffice.crm5.address.city as post_office,
		superoffice.crm5.udsalesmall.double05 as own_production_cost,
		superoffice.crm5.udsalesmall.double06 as transport_cost,
		superoffice.crm5.udsalesmall.double07 as assembly_cost,
		superoffice.crm5.udsalesmall.double08 as yes_lines,
		superoffice.crm5.udsalesmall.double09 as contribution_margin,
		county.county_name,
		superoffice.crm5.person.firstname +' '+ superoffice.crm5.person.lastname as salesman
from superoffice.crm5.sale inner join
	superoffice.crm5.udsalesmall on superoffice.crm5.sale.userdef_id=
	superoffice.crm5.udsalesmall.udsalesmall_id left outer join 
	superoffice.crm5.address on superoffice.crm5.address.owner_id = superoffice.crm5.sale.contact_id left outer join
	superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id left outer join 
	superoffice.crm5.associate on superoffice.crm5.associate.associate_id=superoffice.crm5.contact.associate_id left outer join
	superoffice.crm5.person on superoffice.crm5.person.person_id = superoffice.crm5.associate.person_id left outer join 
	transport_cost on superoffice.crm5.address.zipcode = transport_cost.postal_code collate Danish_Norwegian_CI_AS left outer join 
	area on transport_cost.area_code = area.area_code left outer join 
	county on county.county_nr = area.county_nr 
where superoffice.crm5.address.atype_idx = 2
