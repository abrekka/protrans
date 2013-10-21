alter table colli add number_of_collies int;
/***************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_info_v]
AS
SELECT     dbo.Customer_order.Order_nr AS ordernr, dbo.Customer_order.Delivery_address AS leveringsadresse, dbo.Customer_order.Postal_code AS postnr, 
                      dbo.Customer_order.Post_office AS poststed, dbo.Customer.Customer_nr AS kundenr, 
                      dbo.Customer.First_name + ' ' + dbo.Customer.Last_name AS navn, Intelle_Ordre.dbo.IPK_Ord.Tak_Hoyde_Over_Havet AS hoyde_over_havet, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Beregnet_For AS beregnet_for, Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_sno AS snolast, 
                      Intelle_Ordre.dbo.IPK_Ord.Tak_Bel_egenvekt AS egenvekt, Intelle_Ordre.dbo.IPK_Ord.Tak_UtstikkType AS utstikk_type, 
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
/***************************************************************************************************************************************
