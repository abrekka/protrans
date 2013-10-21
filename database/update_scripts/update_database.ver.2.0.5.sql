ALTER VIEW [dbo].[takstol_info_v]
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
            F0100.dbo.ordln.notenm AS memofilnavn,
			customer_order.telephone_nr as tlf_kunde,
			customer_order.telephonenr_site as tlf_byggeplass,
			customer_order.delivery_week as oensket_uke,
			F0100.dbo.ord.YrRef as kunde_ref
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
/***********************************************************************************************************************************************