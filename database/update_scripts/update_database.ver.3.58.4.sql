select 1 as Customer_nr, 
'1 Igland Garasjen AS' as customer_details, 
F0100.dbo.ord.ordno as Order_nr, 
'Lagerproduksjon' as Address, 
'' as info, 
'' as construction_type_name,
'Vegg' as article_name,
null as loading_date,
F0100.dbo.ord.FinDt as produced,
F0100.dbo.ord.empno, F0100.dbo.ord.nm, (select prodtp from F0100.dbo.ordln where F0100.dbo.ordln.ordno = F0100.dbo.ord.ordno and F0100.dbo.ordln.lnno = 1) as Avdeling,

(select descr from F0100.dbo.ordln where F0100.dbo.ordln.ordno = F0100.dbo.ord.ordno and F0100.dbo.ordln.lnno = 1) as Produktbeskrivelse

from F0100.dbo.ord

where F0100.dbo.ord.TrTp = 7 and ordprst & 256 = 256 and ordprst & 4194304 = 4194304 and orddt > 20160705 and ordbasno = ''