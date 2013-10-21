CREATE VIEW [dbo].[ordchgr_head_v]
AS
SELECT DISTINCT 
                      'H' AS RecType, '' AS CSOrdNo, F0100.dbo.ord.ordno AS OrdNo, '' AS Ordtp, '' AS t4182, '' AS SupNo, '' AS CustNo, '' AS Nm, '' AS Ad1, '' AS Ad2, 
                      '' AS Ad3, '' AS Ad4, '' AS PNo, '' AS PArea, '' AS BsNo, '' AS FrmNm, '' AS FrmAd1, '' AS FrmAd2, '' AS FrmAd3, '' AS FrmAd4, '' AS FrmPNo, 
                      '' AS FrmPArea, '' AS OrdDt, '' AS DelDt, '' AS CfDelDt, '' AS PmtTrm, '' AS PmtMt, '' AS DelTrm, '' AS DelMt, '' AS Inf, '' AS Inf2, '' AS Inf6, '' AS Rsp, 
                      '' AS CtrAm, '' AS TransGr, '' AS TransGr2, '' AS TransGr3, '' AS TransGr4, '' AS MainOrd, '' AS TrTp, '' AS InvoCust, '' AS OrdPref, '' AS R1, '' AS R2, 
                      '' AS R3, '' AS R4, '' AS R5, '' AS R6, '' AS DelActNo, '' AS DelNm, '' AS DelAd1, '' AS DelAd2, '' AS DelAd3, '' AS DelAd4, '' AS DelPNo, '' AS DelPArea, 
                      '' AS OurRef, '' AS YrRef, '' AS EmpNo, '' AS ReqNo, '' AS Label, '' AS SelBuy, '' AS FrStc, '' AS OrdPrGr, '' AS CustPrGr, '' AS CustPrGr2, '' AS Expr1, 
                      '' AS ArDt, '' AS ExVat, '' AS Gr, '' AS Gr2, '' AS Gr3, '' AS Gr4, '' AS Gr5, '' AS Gr6, '' AS Inf3, '' AS Inf4, '' AS Inf5, '' AS NoteNm, '' AS InvoPl, 
                      4 AS HeadStatus
FROM         dbo.Order_line INNER JOIN
                      F0100.dbo.ordln ON dbo.Order_line.ord_no = F0100.dbo.ordln.ordno AND dbo.Order_line.ln_no = F0100.dbo.ordln.lnno INNER JOIN
                      F0100.dbo.ord ON F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno
WHERE     (dbo.Order_line.ord_no IS NOT NULL) AND (dbo.Order_line.ln_no IS NOT NULL)

/***********************************************************************************************************
CREATE VIEW [dbo].[ordchgr_line_v]
AS
SELECT     F0100.dbo.ord.ordno AS OrdNo,'L' AS OrderHead, F0100.dbo.ordln.lnno AS lnNo, '' AS prodno, '' AS descr, '' AS NoInvoAb, '' AS Price, '' AS CCstpr, '' AS CstPr, '' AS cur, '' AS ExRt, 
                      '' AS CstCur, '' AS CstExRt, '' AS Dc1P, '' AS DelDt, '' AS CfDelDt, '' AS TrDt, '' AS OrdTp, '' AS EmpNo, '' AS CustNo, '' AS SupNo, '' AS InvoCust, 
                      '' AS EdFmt, '' AS InvoRef, '' AS RefNo, '' AS SelBuy, '' AS FrStc, '' AS TranspTm, '' AS DelTm, '' AS Un, '' AS StUnRt, '' AS LgtU, '' AS WdtU, '' AS AreaU, 
                      '' AS HgtU, '' AS VolU, '' AS DensU, '' AS NWgtU, '' AS TareU, '' AS NoUn, '' AS NWgtL, '' AS TareL, '' AS LgtL, '' AS AreaL, '' AS VolL, '' AS Transgr, 
                      '' AS Transgr2, '' AS Transgr3, '' AS Transgr4, '' AS R1, '' AS R2, '' AS R3, '' AS R4, '' AS R5, '' AS R6, '' AS DurDt, '' AS Trinf1, '' AS Trinf2, '' AS DelGr, 
                      '' AS Expr1, '' AS SCd, '' AS ProdPrGr, '' AS ProdPrGr2, '' AS ProdPrGr3, '' AS CustPrGr, '' AS CustPrGr2, '' AS Expr2, '' AS NoteNm, '' AS InvoPlLn, 
                      '' AS ProcMt, '' AS ExcPrint, '' AS EditPref, '' AS SpecFunc, 1 AS NetQty, 1 AS KeepPrice, 1 AS FM4729, 3 AS LineStatus
FROM         dbo.Order_line INNER JOIN
                      F0100.dbo.ordln ON dbo.Order_line.ord_no = F0100.dbo.ordln.ordno AND dbo.Order_line.ln_no = F0100.dbo.ordln.lnno INNER JOIN
                      F0100.dbo.ord ON F0100.dbo.ord.ordno = F0100.dbo.ordln.ordno
WHERE     (dbo.Order_line.ord_no IS NOT NULL) AND (dbo.Order_line.ln_no IS NOT NULL)
/***********************************************************************************************************
insert into application_param (param_name,param_value) values('visma_out_dir','visma');
insert into application_param (param_name,param_value) values('visma_file_name','ordchgr.txt');
/***********************************************************************************************************
CREATE FUNCTION [dbo].[is_deviation_done] 
(
	-- Add the parameters for the function here
	@deviationStatusId int
)
RETURNS int
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result int

	-- Add the T-SQL statements to compute the return value here
	SELECT @Result = isnull(deviation_done,0) from deviation_status where deviation_status.deviation_status_id=@deviationStatusId

	-- Return the result of the function
	RETURN isnull(@Result,0)

END
/***********************************************************************************************************
ALTER VIEW [dbo].[sum_avvik_v]
AS
SELECT     COUNT(DISTINCT dbo.deviation.deviation_id) AS deviation_count, ISNULL(dbo.job_function.job_function_name, 'Uspesifisert') AS job_function_name, 
                      DATEPART(yyyy, dbo.deviation.registration_date) AS registration_year, DATEPART(ww, dbo.deviation.registration_date) AS registration_week, 
                      SUM(ISNULL(dbo.Order_cost.Cost_Amount, 0)) AS internal_cost, DATEPART(month, dbo.deviation.registration_date) AS registration_month, 
                      --CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END AS closed, 
dbo.is_deviation_done(deviation.deviation_status_id) as closed,
						dbo.product_area.product_area, 
                      dbo.product_area_group.product_area_group_name
FROM         dbo.Customer_order INNER JOIN
                      dbo.Order_cost ON dbo.Customer_order.Order_id = dbo.Order_cost.Order_id RIGHT OUTER JOIN
                      dbo.deviation LEFT OUTER JOIN
                      dbo.product_area_group INNER JOIN
                      dbo.product_area ON dbo.product_area_group.product_area_group_id = dbo.product_area.product_area_group_id ON 
                      dbo.deviation.product_area_id = dbo.product_area.product_area_id ON dbo.Order_cost.deviation_id = dbo.deviation.deviation_id AND 
                      dbo.is_internal_cost(dbo.Order_cost.Order_cost_id) = 1 LEFT OUTER JOIN
                      dbo.job_function ON dbo.deviation.deviation_function_id = dbo.job_function.job_function_id
GROUP BY dbo.job_function.job_function_name, DATEPART(yyyy, dbo.deviation.registration_date), DATEPART(ww, dbo.deviation.registration_date), 
                      DATEPART(month, dbo.deviation.registration_date), 
--CASE isnull(deviation.date_closed, 0) WHEN 0 THEN 0 ELSE 1 END, 
dbo.is_deviation_done(deviation.deviation_status_id),
                      dbo.product_area.product_area, dbo.product_area_group.product_area_group_name