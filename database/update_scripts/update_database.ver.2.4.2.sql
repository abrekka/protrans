create view Takstoltegner_v as
select customer_nr, first_name + ' ' + last_name as name
   ,order_nr
   ,postal_code
   ,dbo.get_cost_for_type(order_id,'Egenproduksjon','Kunde') as cost_amount
   ,customer_order.packlist_ready
   ,salesman
   ,tross_ready
   ,tross_drawer
   ,max_tross_height
   ,probability
   ,Intelle_ordre.dbo.IPK_Ord.Tak_Prosjektering
   ,product_area_group.product_area_group_name
from Protrans.dbo.customer_order 
   inner join Protrans.dbo.customer on Protrans.dbo.customer.customer_id = Protrans.dbo.customer_order.customer_id 
   inner join product_area on product_area.product_area_id=customer_order.product_area_id
   inner join product_area_group on product_area_group.product_area_group_id=product_area.product_area_group_id
   left outer join Intelle_ordre.dbo.IPK_Ord on Intelle_ordre.dbo.IPK_Ord.ordno = 'SO-' + order_nr collate Danish_Norwegian_CI_AS 
where product_area_group.product_area_group_name = 'Takstol' 
   and tross_ready is not null
union 
select customer_nr
   ,first_name + ' ' + last_name as name
   ,order_nr
   ,postal_code
   ,dbo.get_cost_for_type(order_id,'Takstoler','Intern') as cost_amount
   ,customer_order.packlist_ready
   ,salesman
   ,tross_ready
   ,tross_drawer
   ,max_tross_height
   ,probability
   ,0
   ,product_area_group.product_area_group_name
from Protrans.dbo.customer_order 
   inner join Protrans.dbo.customer on Protrans.dbo.customer.customer_id = Protrans.dbo.customer_order.customer_id 
   inner join product_area on product_area.product_area_id=customer_order.product_area_id
   inner join product_area_group on product_area_group.product_area_group_id=product_area.product_area_group_id
where product_area_group.product_area_group_name <> 'Takstol' 
   and tross_ready is not null

/***************************************************************************************************************************************************************
ALTER VIEW [dbo].[sales_v]
AS
SELECT     superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1 AS order_nr, superoffice.crm5.sale.probability, ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, 
                      F0100.dbo.Ord.R1) AS product_area_nr, superoffice.crm5.sale.saledate, superoffice.crm5.sale.registered, 
                      superoffice.crm5.contact.number2 AS customer_nr, superoffice.crm5.contact.name AS customer_name, 
                      superoffice.crm5.address.address1 AS delivery_address, superoffice.crm5.address.zipcode AS postal_code, 
                      superoffice.crm5.address.city AS post_office, superoffice.crm5.udsalesmall.double05 AS own_production_cost, 
                      superoffice.crm5.udsalesmall.double06 AS transport_cost, superoffice.crm5.udsalesmall.double07 AS assembly_cost, 
                      superoffice.crm5.udsalesmall.double08 AS yes_lines, superoffice.crm5.udsalesmall.double09 AS contribution_margin, 
                      superoffice.crm5.udsalesmall.long03 AS order_date, dbo.county.county_name, ISNULL(Intelle_Ordre.dbo.wOrd.OurRef, F0100.dbo.Ord.OurRef) 
                      AS salesman
FROM         superoffice.crm5.sale INNER JOIN
                      superoffice.crm5.udsalesmall ON superoffice.crm5.sale.userdef_id = superoffice.crm5.udsalesmall.udsalesmall_id LEFT OUTER JOIN
                      Intelle_Ordre.dbo.wOrd ON Intelle_Ordre.dbo.wOrd.SOSaleNo = superoffice.crm5.sale.number1 LEFT OUTER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6 LEFT OUTER JOIN
                      superoffice.crm5.address ON superoffice.crm5.address.owner_id = superoffice.crm5.sale.contact_id LEFT OUTER JOIN
                      superoffice.crm5.contact ON superoffice.crm5.contact.contact_id = superoffice.crm5.sale.contact_id LEFT OUTER JOIN
                      dbo.transport_cost ON superoffice.crm5.address.zipcode = dbo.transport_cost.postal_code collate Danish_Norwegian_CI_AS LEFT OUTER JOIN
                      dbo.area ON dbo.transport_cost.area_code = dbo.area.area_code LEFT OUTER JOIN
                      dbo.county ON dbo.county.county_nr = dbo.area.county_nr
WHERE     (superoffice.crm5.address.atype_idx = 2) AND (superoffice.crm5.sale.probability > 0) AND (superoffice.crm5.udsalesmall.long06 NOT IN (176)) AND 
                      EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.product_area
                            WHERE      (product_area_nr = ISNULL(Intelle_Ordre.dbo.wOrd.DeptNo, F0100.dbo.Ord.R1)) AND (ISNULL(superoffice.crm5.udsalesmall.double05, 0) 
                                                   > own_production_cost_limit)) AND (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          superoffice.crm5.sale AS S LEFT OUTER JOIN
                                                   superoffice.crm5.udsalesmall AS udsalesmall_1 ON superoffice.crm5.sale.userdef_id = udsalesmall_1.udsalesmall_id
                            WHERE      (udsalesmall_1.long06 IN (175, 176)) AND (S.probability = 30) AND (S.number1 = superoffice.crm5.sale.number1)))

				 
/*******************************************************************************************************************************************************************
insert into window_access(window_name) values('Takstoltegning');
/*******************************************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_info_v]
AS
SELECT     dbo.Customer_order.Order_nr AS ordernr, dbo.Customer_order.Delivery_address AS leveringsadresse, dbo.Customer_order.Postal_code AS postnr, 
                      dbo.Customer_order.Post_office AS poststed, dbo.Customer.Customer_nr AS kundenr, 
                      dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS navn, Intelle_Ordre.dbo.IPK_Ord.Tak_Hoyde_Over_Havet AS hoyde_over_havet, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Beregnet_For AS beregnet_for, Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_sno AS snolast, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_egenvekt AS egenvekt, Intelle_Ordre.dbo.IPK_Ord.Tak_UtstikkType AS utstikk_type, 
						Intelle_Ordre.dbo.IPK_Ord.max_hoyde as maks_hoyde,
                      Intelle_Ordre.dbo.IPK_TakOrdLn.LnNo, Intelle_Ordre.dbo.IPK_TakOrdLn.Kode, Intelle_Ordre.dbo.IPK_TakOrdLn.Antall, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.ProdNo, Intelle_Ordre.dbo.IPK_TakOrdLn.Beskrivelse AS beskrivelse, Intelle_Ordre.dbo.IPK_TakOrdLn.TakstolType, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.VirkesBr AS virkesbredde, Intelle_Ordre.dbo.IPK_TakOrdLn.Spennvidde AS spennvidde, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Utstikk AS utstikkslengde, Intelle_Ordre.dbo.IPK_TakOrdLn.Sville_Klaring AS svilleklaring, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Rombr_A_stol AS rombredde_a_stol, Intelle_Ordre.dbo.IPK_TakOrdLn.Romh_A_stol AS romhoyde_a_stol, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Baering AS baering_gulv, Intelle_Ordre.dbo.IPK_TakOrdLn.Hoyde_isolasjon AS isolasjonshoyde, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Loddh_kutt_utstk AS loddkutt, Intelle_Ordre.dbo.IPK_TakOrdLn.Nedstikk, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Antall * Intelle_Ordre.dbo.IPK_TakOrdLn.Arbeidssats / 256 AS beregnet_tid, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Memo AS memofilnavn, dbo.Customer_order.telephone_nr AS tlf_kunde, 
						Intelle_Ordre.dbo.IPK_TakOrdLn.Vinkel,
                      dbo.Customer_order.telephonenr_site AS tlf_byggeplass, dbo.Customer_order.delivery_week AS oensket_uke,dbo.customer_order.tross_drawer,
                          (SELECT     YrRef
                            FROM          Intelle_Ordre.dbo.wOrd_History
                            WHERE      (OrdNo = 'SO-' + dbo.Customer_order.Order_nr collate Danish_Norwegian_CI_AS)) AS kunde_ref
FROM         dbo.Customer_order INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id LEFT OUTER JOIN
                      Intelle_Ordre.dbo.IPK_Ord ON 'SO-' + dbo.Customer_order.Order_nr collate Danish_Norwegian_CI_AS = Intelle_Ordre.dbo.IPK_Ord.OrdNo LEFT OUTER JOIN
                      Intelle_Ordre.dbo.IPK_TakOrdLn ON Intelle_Ordre.dbo.IPK_TakOrdLn.Kalk_ID = Intelle_Ordre.dbo.IPK_Ord.Kalk_ID
WHERE     (Intelle_Ordre.dbo.IPK_TakOrdLn.ProdNo NOT IN ('PROSJEKTERINGTAKSTOL', 'FRAKT', 'HEBKAPPOGTILR', 'AVRUNDING'))
/*******************************************************************************************************************************************************************
alter table accident add absent_days decimal(4,2);
/*******************************************************************************************************************************************************************
alter table deviation_status add for_deviation int;
/*******************************************************************************************************************************************************************
update deviation_status set for_deviation=1;
/*******************************************************************************************************************************************************************
alter table deviation_status add for_accident int;
/*******************************************************************************************************************************************************************
update deviation_status set for_accident=1;
/*******************************************************************************************************************************************************************
alter table accident add deviation_status_id int references deviation_status(deviation_status_id);
/*******************************************************************************************************************************************************************
	