select  'L' as OrderHead,	--1	Linjehode
		lnno as lnNo,		--2 Linjenr
		'' as prodno,		--3	Produktnr
		'' as descr,		--4	Beskrivelse
		'' as NoInvoAb,		--5	
		'' as Price,		--6
		'' as CCstpr,		--7
		'' as CstPr,		--8
		'' as cur,			--9
		'' as ExRt,			--10
		'' as CstCur,		--11
		'' as CstExRt,		--12
		'' as Dc1P,			--13
		'' as DelDt,		--14
		'' as CfDelDt,		--15
		'' as TrDt,			--16
		'' as OrdTp,		--17
		'' as EmpNo,		--18
		'' as CustNo,		--19	
		'' as SupNo,		--20
		'' as InvoCust,		--21
		'' as EdFmt,		--22
		'' as InvoRef,		--23
		'' as RefNo,		--24
		'' as SelBuy,		--25
		'' as FrStc,		--26
		'' as TranspTm,		--27
		'' as DelTm,		--28
		'' as Un,			--29
		'' as StUnRt,		--30
		'' as LgtU,			--31
		'' as WdtU,			--32
		'' as AreaU,		--33
		'' as HgtU,			--34
		'' as VolU,			--35
		'' as DensU,		--36
		'' as NWgtU,		--37
		'' as TareU,		--38
		'' as NoUn,			--39
		'' as NWgtL,		--40
		'' as TareL,		--41
		'' as LgtL,			--42
		'' as AreaL,		--43
		'' as VolL,			--44
		'' as Transgr,		--45
		'' as Transgr2,		--46
		'' as Transgr3,		--47
		'' as Transgr4,		--48
		'' as R1,			--49
		'' as R2,			--50
		'' as R3,			--51
		'' as R4,			--52
		'' as R5,			--53
		'' as R6,			--54
		'' as DurDt,		--55
		'' as Trinf1,		--56
		'' as Trinf2,		--57
		'' as DelGr,		--58
		'' as Frstc,		--59
		'' as SCd,			--60
		'' as ProdPrGr,		--61
		'' as ProdPrGr2,	--62
		'' as ProdPrGr3,	--63
		'' as CustPrGr,		--64
		'' as CustPrGr2,	--65
		'' as delDt,		--66
		'' as NoteNm,		--67
		'' as InvoPlLn,		--68
		'' as ProcMt,		--69
		'' as ExcPrint,		--70
		'' as EditPref,		--71
		'' as SpecFunc,		--72
		1  as NetQty,		--73 Sjekk
		1  as KeepPrice,	--74 Behold pris fra ordre
		1  as FM4729,		--75 Ferdigmeld NÅ angi antall som er pakket.
		3  as LineStatus	--76 Status 3 -> endring av linje

from	protrans.dbo.order_line inner join
		F0100.dbo.ordln on	protrans.dbo.order_line.ord_no = F0100.dbo.ordln.ordno and
							protrans.dbo.order_line.ln_no = F0100.dbo.ordln.lnno inner join
		F0100.dbo.ord on F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno

where	ord_no is not null and
		ln_no is not null and
		F0100.dbo.ord.inf6 = 49771 --ordrenr
		