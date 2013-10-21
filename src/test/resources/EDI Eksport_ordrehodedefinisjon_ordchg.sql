select  'H' as RecType,	--1 Ordrehode
		'' as CSOrdNo,		--2	Kunde / lev ordno
		F0100.dbo.ord.ordNo as OrdNo, --3	Ordrenr
		'' as Ordtp,		--4	
		'' as t4182,		--5 Ordresum brutto
		'' as SupNo,		--6
		'' as CustNo,		--7
		'' as Nm,			--8
		'' as Ad1,			--9
		'' as Ad2,			--10
		'' as Ad3,			--11
		'' as Ad4,			--12
		'' as PNo,			--13
		'' as PArea,		--14
		'' as BsNo,			--15 Firmaoppl
		'' as FrmNm,		--16 Firmaoppl
		'' as FrmAd1,		--17 Firmaoppl
		'' as FrmAd2,		--18 Firmaoppl	
		'' as FrmAd3,		--19 Firmaoppl
		'' as FrmAd4,		--20 Firmaoppl
		'' as FrmPNo,		--21 Firmaoppl
		'' as FrmPArea,		--22 Firmaoppl
		'' as OrdDt,		--23
		'' as DelDt,		--24 Leveringsdato
		'' as CfDelDt,		--25
		'' as PmtTrm,		--26
		'' as PmtMt,		--27
		'' as DelTrm,		--28
		'' as DelMt,		--29
		'' as Inf,			--30
		'' as Inf2,			--31
		'' as Inf6,			--32
		'' as Rsp,			--33
		'' as CtrAm,		--34
		'' as TransGr,		--35
		'' as TransGr2,		--36
		'' as TransGr3,		--37
		'' as TransGr4,		--38
		'' as MainOrd,		--39
		'' as TrTp,			--40
		'' as InvoCust,		--41
		'' as OrdPref,		--42
		'' as R1,			--43
		'' as R2,			--44
		'' as R3,			--45
		'' as R4,			--46
		'' as R5,			--47
		'' as R6,			--48
		'' as DelActNo,		--49
		'' as DelNm,		--50
		'' as DelAd1,		--51
		'' as DelAd2,		--52
		'' as DelAd3,		--53
		'' as DelAd4,		--54
		'' as DelPNo,		--55
		'' as DelPArea,		--56
		'' as OurRef,		--57
		'' as YrRef,		--58
		'' as EmpNo,		--59
		'' as ReqNo,		--60
		'' as Label,		--61
		'' as SelBuy,		--62
		'' as FrStc,		--63
		'' as OrdPrGr,		--64
		'' as CustPrGr,		--65
		'' as CustPrGr2,	--66
		'' as DelDt,		--67
		'' as ArDt,			--68
		'' as ExVat,		--69
		'' as Gr,			--70
		'' as Gr2,			--71
		'' as Gr3,			--72
		'' as Gr4,			--73
		'' as Gr5,			--74
		'' as Gr6,			--75
		'' as Inf3,			--76
		'' as Inf4,			--77
		'' as Inf5,			--78
		'' as NoteNm,		--79
		F0100.dbo.ord.InvoPl as InvoPl,		--80
		4  as HeadStatus	--81

from	protrans.dbo.order_line inner join
		F0100.dbo.ordln on	protrans.dbo.order_line.ord_no = F0100.dbo.ordln.ordno and
							protrans.dbo.order_line.ln_no = F0100.dbo.ordln.lnno inner join
		F0100.dbo.ord on F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno

where	ord_no is not null and
		ln_no is not null and
		F0100.dbo.ord.inf6 = 49771 --ordrenr
		