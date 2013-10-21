alter table application_param add user_accessible integer;
/************************************************************
insert into application_param(param_name,param_value,user_accessible) values('arbeidskost','256',1);
/************************************************************
update application_param set user_accessible=1 where param_name='ugland_adresse';
update application_param set user_accessible=1 where param_name='ugland_fax';
update application_param set user_accessible=1 where param_name='mail_deviation_attachment_description';
update application_param set user_accessible=1 where param_name='mail_from_mail';
update application_param set user_accessible=1 where param_name='mail_deviation_subject';
update application_param set user_accessible=1 where param_name='mail_deviation_message';
update application_param set user_accessible=1 where param_name='nav_blankett';
update application_param set user_accessible=1 where param_name='visma_out_dir';
/************************************************************
ALTER VIEW [dbo].[takstol_basis_v] 
AS 
SELECT dbo.Order_line.Order_line_id, 
	dbo.Customer.Customer_nr, 
dbo.Customer.First_name + ' ' + dbo.Customer.Last_name as customer_name,
	CAST(dbo.Customer.Customer_nr AS nvarchar(10)) + ' ' + dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS customer_details, 
	dbo.Customer_order.Order_id, 
	dbo.Customer_order.Order_nr, 
	dbo.Customer_order.Postal_code + ' ' + dbo.Customer_order.Post_office AS address, 
	dbo.Customer_order.Info, 
customer_order.probability,
customer_order.packlist_ready,
	dbo.Construction_type.Name AS construction_type_name, 
	dbo.order_line_article_name(dbo.Order_line.Order_line_id, dbo.Order_line.Construction_type_article_id, dbo.Order_line.Article_type_id) AS article_name, 
	dbo.Order_line.attribute_info + dbo.get_attribute_info(dbo.Order_line.Order_id, N'Gavl') AS Attribute_info, 
	dbo.Order_line.Number_of_items, 
	dbo.Transport.Loading_date, 
	dbo.Order_line.Colli_id, 
	dbo.Order_line.produced, 
	CAST(dbo.Transport.Transport_Week AS nvarchar(2)) + '- ' + dbo.Transport.Transport_name AS transport_details, 
	dbo.get_order_comment(dbo.Customer_order.Order_id) AS Comment, 
	ISNULL(dbo.Transport.Transport_Year, 9999) AS transport_year, 
	dbo.Transport.Transport_Week, 
	dbo.Transport.load_time, 
	dbo.Order_line.post_shipment_id, 
	dbo.product_area_group.product_area_group_name, 
	dbo.Order_line.action_started, 
	dbo.get_attribute_value(dbo.Order_line.Order_line_id, N'Egenordre') AS egenordre, 
	dbo.is_order_line_default(dbo.Order_line.Order_line_id, dbo.Order_line.is_default) AS is_default,
	customer_order.production_date,production_unit.production_unit_name,
	dbo.is_order_line_sent(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) as sent,
	dbo.Order_line.ord_no,
	dbo.Order_line.ln_no
FROM dbo.Order_line INNER JOIN 
	dbo.Customer_order ON dbo.Order_line.Order_id = dbo.Customer_order.Order_id INNER JOIN 
	dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id INNER JOIN 
	dbo.Construction_type ON dbo.Customer_order.Construction_type_id = dbo.Construction_type.Construction_type_id INNER JOIN 
	dbo.product_area ON dbo.Customer_order.product_area_id = dbo.product_area.product_area_id INNER JOIN 
	dbo.product_area_group ON dbo.product_area.product_area_group_id = dbo.product_area_group.product_area_group_id LEFT OUTER JOIN 
    dbo.production_unit ON dbo.Order_line.production_unit_id = dbo.production_unit.production_unit_id LEFT OUTER JOIN
	dbo.Transport ON dbo.get_transport_id(dbo.Order_line.Order_id, dbo.Order_line.post_shipment_id) = dbo.Transport.Transport_id  --CROSS JOIN
                     --dbo.application_param
WHERE (dbo.Transport.Transport_Week is null or dbo.Transport.Transport_Week <> 0) and
exists(select 1 from application_param where application_param.param_name like 'takstol_artikkel%' AND application_param.param_value=order_line.article_path )
/************************************************************
ALTER VIEW [dbo].[takstol_all_v] 
AS 
SELECT takstol_basis_v.*,
case article_name when 'Takstoler' then 1 else 2 end as default_article,
	dbo.get_customer_cost_for_type(order_id,'Egenproduksjon') as own_production,
dbo.get_cost_for_type(order_id,'Takstoler','Intern') as own_internal_production,
dbo.get_cost_for_type(order_id,'Frakt','Kunde') as delivery_cost,
F0100.dbo.ordln.price,
F0100.dbo.ordln.Dc1p
FROM takstol_basis_v LEFT OUTER JOIN 
F0100.dbo.ordln on takstol_basis_v.ord_no=F0100.dbo.ordln.ordno and takstol_basis_v.ln_no=F0100.dbo.ordln.lnno
/************************************************************
ALTER VIEW [dbo].[takstol_v] 
AS 
select takstol_basis_v.*,case article_name when 'Takstoler' then 1 else 2 end as default_article from takstol_basis_v where sent=0
/************************************************************
ALTER VIEW [dbo].[takstol_production_v]
AS
select * from takstol_v where is_default=0
/************************************************************
ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1
/************************************************************
ALTER VIEW [dbo].[takstol_info_v]
AS
SELECT     F0100.dbo.Ord.Inf6 AS ordernr, dbo.Customer_order.Delivery_address AS leveringsadresse, dbo.Customer_order.Postal_code AS postnr, 
                      dbo.Customer_order.Post_office AS poststed, dbo.Customer.Customer_nr AS kundenr, 
                      dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS navn, Intelle_Ordre.dbo.IPK_Ord.Tak_Hoyde_Over_Havet AS hoyde_over_havet, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Beregnet_For AS beregnet_for, Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_sno AS snolast, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_egenvekt AS egenvekt, Intelle_Ordre.dbo.IPK_Ord.Tak_UtstikkType AS utstikk_type, F0100.dbo.OrdLn.LnNo, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Kode, Intelle_Ordre.dbo.IPK_TakOrdLn.Antall, F0100.dbo.OrdLn.ProdNo, F0100.dbo.OrdLn.Descr AS beskrivelse, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.TakstolType, F0100.dbo.OrdLn.WdtU AS virkesbredde, F0100.dbo.OrdLn.LgtU AS spennvidde, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Utstikk AS utstikkslengde, Intelle_Ordre.dbo.IPK_TakOrdLn.Sville_Klaring AS svilleklaring, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Rombr_A_stol AS rombredde_a_stol, Intelle_Ordre.dbo.IPK_TakOrdLn.Romh_A_stol AS romhoyde_a_stol, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Baering AS baering_gulv, Intelle_Ordre.dbo.IPK_TakOrdLn.Hoyde_isolasjon AS isolasjonshoyde, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Loddh_kutt_utstk AS loddkutt, Intelle_Ordre.dbo.IPK_TakOrdLn.Nedstikk, 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.Antall * Intelle_Ordre.dbo.IPK_TakOrdLn.Arbeidssats / cast(application_param.param_value as decimal) AS beregnet_tid, F0100.dbo.OrdLn.NoteNm AS memofilnavn,
                       dbo.Customer_order.telephone_nr AS tlf_kunde, dbo.Customer_order.telephonenr_site AS tlf_byggeplass, 
                      dbo.Customer_order.delivery_week AS oensket_uke, F0100.dbo.Ord.YrRef AS kunde_ref
FROM         F0100.dbo.OrdLn INNER JOIN
                      F0100.dbo.Ord ON F0100.dbo.Ord.OrdNo = F0100.dbo.OrdLn.OrdNo INNER JOIN
                      Intelle_Ordre.dbo.IPK_Ord ON 'SO-' + F0100.dbo.Ord.Inf6 = Intelle_Ordre.dbo.IPK_Ord.OrdNo INNER JOIN
                      Intelle_Ordre.dbo.wOrdLn_History ON 'SO-' + F0100.dbo.Ord.Inf6 = Intelle_Ordre.dbo.wOrdLn_History.OrdNo AND 
                      F0100.dbo.OrdLn.LnNo = Intelle_Ordre.dbo.wOrdLn_History.LnNo LEFT OUTER JOIN
                      Intelle_Ordre.dbo.IPK_TakOrdLn ON Intelle_Ordre.dbo.IPK_TakOrdLn.Kalk_ID = Intelle_Ordre.dbo.wOrdLn_History.KalkID AND 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.LnNo = Intelle_Ordre.dbo.wOrdLn_History.LnNo INNER JOIN
                      F0100.dbo.Prod ON F0100.dbo.Prod.ProdNo = F0100.dbo.OrdLn.ProdNo INNER JOIN
                      dbo.Customer_order ON F0100.dbo.Ord.Inf6 = dbo.Customer_order.Order_nr collate Danish_Norwegian_CI_AS INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id CROSS JOIN
                     dbo.application_param
WHERE     (F0100.dbo.OrdLn.ProdNo NOT IN ('PROSJEKTERINGTAKSTOL', 'FRAKT', 'PBMJUST', 'HEBKAPPOGTILR')) AND (F0100.dbo.Prod.PrCatNo4 = 300) and application_param.param_name='arbeidskost'
/************************************************************
ALTER view [dbo].[import_order_prob_90_v]
as
SELECT		superoffice.crm5.sale.sale_id, 
			superoffice.crm5.sale.number1, 
			superoffice.crm5.sale.amount, 
			superoffice.crm5.sale.saledate, 
            superoffice.crm5.sale.userdef_id, 
			superoffice.crm5.sale.registered, 
			superoffice.crm5.sale.probability, 
			superoffice.crm5.sale.contact_id, 
            Intelle_ordre.dbo.word.DeptNo AS product_area_nr, --Varchar(50), null
			Intelle_ordre.dbo.word.DelAd1 + ' ' + Intelle_ordre.dbo.word.DelAd2 AS delivery_address, --Begge: Varchar(40), null
            Intelle_ordre.dbo.word.DelZip AS postal_code, --Varchar(10), null
			Intelle_ordre.dbo.word.DelCity AS postoffice, --Varchar(40), null
			Intelle_ordre.dbo.word.OurRef AS sales_man,	--Varchar(50), null
			Intelle_ordre.dbo.word.delPhone as telephonenr_site,
superoffice.crm5.contact.number2 as customer_nr,
superoffice.crm5.contact.name as customer_name,
superoffice.crm5.udsalesmall.double05 as own_production,
superoffice.crm5.udsalesmall.double06 as delivery_cost
FROM         superoffice.crm5.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm5.sale.number1 = Intelle_ordre.dbo.word.sosaleno collate Danish_Norwegian_CI_AS inner join
			superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id inner join
superoffice.crm5.udsalesmall on superoffice.crm5.udsalesmall.udsalesmall_id=superoffice.crm5.sale.userdef_id
WHERE     (superoffice.crm5.sale.saledate > 1250640000) 
			AND (superoffice.crm5.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm5.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS)))
			AND Intelle_ordre.dbo.word.deptno in (300,400) --Takstol og byggelement
			
/************************************************************
ALTER view [dbo].[import_order_v]
as
select sale_id,number1,amount,saledate,userdef_id,registered,probability,contact_id,product_area_nr,delivery_address,postal_code,postoffice,sales_man,telephonenr_site from import_order_prob_90_v
union
select * from import_order_prob_100_v
/************************************************************
ALTER VIEW [dbo].[takstol_probability_90_v]
AS
SELECT		superoffice.crm5.sale.sale_id, 
			superoffice.crm5.sale.number1, 
			superoffice.crm5.sale.amount, 
			superoffice.crm5.sale.saledate, 
            superoffice.crm5.sale.userdef_id, 
			superoffice.crm5.sale.registered, 
			superoffice.crm5.sale.probability, 
			superoffice.crm5.sale.contact_id, 
            Intelle_ordre.dbo.word.DeptNo AS product_area_nr, --Varchar(50), null
			Intelle_ordre.dbo.word.DelAd1 + ' ' + Intelle_ordre.dbo.word.DelAd2 AS delivery_address, --Begge: Varchar(40), null
            Intelle_ordre.dbo.word.DelZip AS postal_code, --Varchar(10), null
			Intelle_ordre.dbo.word.DelCity AS postoffice, --Varchar(40), null
			Intelle_ordre.dbo.word.OurRef AS sales_man,	--Varchar(50), null
			Intelle_ordre.dbo.word.delPhone as telephonenr_site,
superoffice.crm5.contact.number2 as customer_nr,
superoffice.crm5.contact.name as customer_name,
superoffice.crm5.udsalesmall.double05 as own_production,
superoffice.crm5.udsalesmall.double06 as delivery_cost
FROM         superoffice.crm5.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm5.sale.number1 = Intelle_ordre.dbo.word.sosaleno collate Danish_Norwegian_CI_AS inner join
			superoffice.crm5.contact on superoffice.crm5.contact.contact_id=superoffice.crm5.sale.contact_id inner join
superoffice.crm5.udsalesmall on superoffice.crm5.udsalesmall.udsalesmall_id=superoffice.crm5.sale.userdef_id
WHERE     (superoffice.crm5.sale.saledate > 1250640000) 
			AND (superoffice.crm5.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm5.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS and probability=100)))
			AND Intelle_ordre.dbo.word.deptno in (300) --Takstol og byggelement
/************************************************************
insert into window_access(window_name) values('Ordrereserve takstol');