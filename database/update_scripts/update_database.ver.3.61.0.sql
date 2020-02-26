ALTER VIEW [dbo].[monteringsanvisning_v]
AS
SELECT DISTINCT dbo.Customer_order.Order_nr, F0100.dbo.FreeInf1.PictFNm AS Filsti, '' AS Overskrift,F0100.dbo.FreeInf1.WebPg AS Filsti_se,F0100.dbo.Ord.Lang
FROM            F0100.dbo.Ord INNER JOIN
                         dbo.Customer_order ON dbo.Customer_order.Order_nr = F0100.dbo.Ord.Inf6 INNER JOIN
                         F0100.dbo.OrdLn ON F0100.dbo.OrdLn.OrdNo = F0100.dbo.Ord.OrdNo INNER JOIN
                         F0100.dbo.FreeInf1 ON F0100.dbo.FreeInf1.ProdNo = F0100.dbo.OrdLn.ProdNo
WHERE        (F0100.dbo.FreeInf1.R1 = 0) OR
                         (F0100.dbo.Ord.R1 = F0100.dbo.FreeInf1.R1) OR
                         (F0100.dbo.Ord.R1 - 20 = F0100.dbo.FreeInf1.R1)
