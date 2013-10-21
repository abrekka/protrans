CREATE VIEW [dbo].[takstol_info_v]
AS
SELECT     F0100.dbo.ord.inf6 AS ordernr, 
			dbo.Customer_order.Delivery_address AS leveringsadresse, 
			dbo.Customer_order.postal_code as postnr,
			dbo.Customer_order.post_office as poststed,
			dbo.Customer.Customer_nr AS kundenr, 
            dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS navn, 
			Intelle_Ordre.dbo.IPK_Ord.Tak_Hoyde_Over_Havet AS hoyde_over_havet, 
            Intelle_Ordre.dbo.IPK_Ord.Tak_Beregnet_For AS beregnet_for, Intelle_Ordre.dbo.IPK_Ord.Tak_bel_sno AS snolast, 
            Intelle_Ordre.dbo.IPK_Ord.Tak_bel_egenvekt AS egenvekt, 
			Intelle_Ordre.dbo.IPK_Ord.Tak_UtstikkType as utstikk_type, 
			F0100.dbo.ordln.lnno, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.kode, 
            Intelle_Ordre.dbo.IPK_TakOrdLn.antall, 
			F0100.dbo.ordln.prodno, 
			F0100.dbo.ordln.descr AS beskrivelse, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.takstoltype, 
            F0100.dbo.ordln.wdtu AS virkesbredde, 
			F0100.dbo.ordln.lgtu AS spennvidde, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.utstikk AS utstikkslengde, 
            Intelle_Ordre.dbo.IPK_TakOrdLn.sville_klaring AS svilleklaring, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.rombr_a_stol AS rombredde_a_stol, 
            Intelle_Ordre.dbo.IPK_TakOrdLn.romh_a_stol AS romhoyde_a_stol, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.baering AS baering_gulv, 
            Intelle_Ordre.dbo.IPK_TakOrdLn.hoyde_isolasjon AS isolasjonshoyde, Intelle_Ordre.dbo.IPK_TakOrdLn.Loddh_kutt_utstk AS loddkutt, 
            Intelle_Ordre.dbo.IPK_TakOrdLn.Nedstikk AS nedstikk, 
			Intelle_Ordre.dbo.IPK_TakOrdLn.antall * Intelle_Ordre.dbo.IPK_TakOrdLn.arbeidssats / 256 AS beregnet_tid, 
            F0100.dbo.ordln.notenm AS memofilnavn
FROM         F0100.dbo.ordln INNER JOIN
                      F0100.dbo.ord ON F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno INNER JOIN
                      Intelle_Ordre.dbo.IPK_Ord ON 'SO-' + F0100.dbo.ord.inf6 = Intelle_Ordre.dbo.IPK_Ord.ordno INNER JOIN
                      Intelle_Ordre.dbo.wordln_history ON 'SO-' + F0100.dbo.ord.inf6 = Intelle_Ordre.dbo.wordln_history.ordno AND 
                      F0100.dbo.ordln.lnno = Intelle_Ordre.dbo.wordln_history.lnno LEFT OUTER JOIN
                      Intelle_Ordre.dbo.IPK_TakOrdLn ON Intelle_Ordre.dbo.IPK_TakOrdLn.kalk_Id = Intelle_Ordre.dbo.wordln_history.kalkid AND 
                      Intelle_Ordre.dbo.IPK_TakOrdLn.lnno = Intelle_Ordre.dbo.wordln_history.lnno INNER JOIN
                      F0100.dbo.prod ON F0100.dbo.prod.prodno = F0100.dbo.ordln.prodno INNER JOIN
                      dbo.Customer_order ON F0100.dbo.ord.inf6 COLLATE Danish_Norwegian_CI_AS = dbo.Customer_order.Order_nr INNER JOIN
                      dbo.Customer ON dbo.Customer_order.Customer_id = dbo.Customer.Customer_id
WHERE     (F0100.dbo.ordln.prodno NOT IN ('PROSJEKTERINGTAKSTOL', 'FRAKT', 'PBMJUST')) AND (F0100.dbo.prod.prcatno3 = 300)
/******************************************************************************************************************************************************
CREATE VIEW [dbo].[import_order_prob_100_v]
AS
SELECT     superoffice.crm5.sale.sale_id, superoffice.crm5.sale.number1, superoffice.crm5.sale.amount, superoffice.crm5.sale.saledate, 
                      superoffice.crm5.sale.userdef_id, superoffice.crm5.sale.registered, superoffice.crm5.sale.probability, superoffice.crm5.sale.contact_id, 
                      F0100.dbo.Ord.R1 AS product_area_nr, F0100.dbo.Ord.DelAd3 + ' ' + F0100.dbo.Ord.DelAd4 AS delivery_address, 
                      F0100.dbo.Ord.DelPNo AS postal_code, F0100.dbo.Ord.DelPArea AS postoffice, F0100.dbo.Ord.OurRef AS sales_man
FROM         superoffice.crm5.sale INNER JOIN
                      F0100.dbo.Ord ON superoffice.crm5.sale.number1 = F0100.dbo.Ord.Inf6
WHERE     (superoffice.crm5.sale.saledate > 1250640000) AND (superoffice.crm5.sale.probability = 100) AND (superoffice.crm5.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS)))
/******************************************************************************************************************************************************
create view [dbo].[import_order_prob_90_v]
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
			Intelle_ordre.dbo.word.OurRef AS sales_man	--Varchar(50), null
FROM         superoffice.crm5.sale INNER JOIN
             Intelle_ordre.dbo.word ON superoffice.crm5.sale.number1 = Intelle_ordre.dbo.word.sosaleno collate Danish_Norwegian_CI_AS
WHERE     (superoffice.crm5.sale.saledate > 1250640000) 
			AND (superoffice.crm5.sale.probability = 90) -- 90 betyr at denne er en ordre og ikke overført Visma.
			AND (superoffice.crm5.sale.number1 <> '') AND 
                      (NOT EXISTS
                          (SELECT     1 AS Expr1
                            FROM          dbo.Customer_order
                            WHERE      (Order_nr = superoffice.crm5.sale.number1 collate Danish_Norwegian_CI_AS)))
			AND Intelle_ordre.dbo.word.deptno in (300,400) --Takstol og byggelement
/******************************************************************************************************************************************************
ALTER view [dbo].[import_order_v]
as
select * from import_order_prob_90_v
union
select * from import_order_prob_100_v
/******************************************************************************************************************************************************
