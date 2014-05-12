ALTER VIEW [dbo].[fakturagrunnlag_v]
AS
SELECT dbo.Customer_order.Order_id, 
dbo.Customer_order.Order_nr, F0100.dbo.Ord.OrdNo, F0100.dbo.OrdLn.Free2, F0100.dbo.OrdLn.LnNo, F0100.dbo.OrdLn.ProdNo, 
                  F0100.dbo.OrdLn.Descr, F0100.dbo.OrdLn.NoOrg AS noof, 
				  F0100.dbo.OrdLn.Free1 AS org_price_mont, 
				  F0100.dbo.OrdLn.Free1*0.85 AS price_mont, 
				  F0100.dbo.OrdLn.NoOrg * (F0100.dbo.OrdLn.Free1*0.85) AS sum_line, 
				  F0100.dbo.OrdLn.NoOrg * (F0100.dbo.OrdLn.Free1) AS org_sum_line, 
                  F0100.dbo.OrdLn.CstPr, F0100.dbo.OrdLn.Price, F0100.dbo.OrdLn.PurcNo,F0100.dbo.OrdLn.lnPurcNo,F0100.dbo.OrdLn.alloc
FROM     dbo.Customer_order INNER JOIN
                  F0100.dbo.Ord ON dbo.Customer_order.Order_nr = F0100.dbo.Ord.Inf6 INNER JOIN
                  F0100.dbo.OrdLn ON F0100.dbo.Ord.OrdNo = F0100.dbo.OrdLn.OrdNo LEFT OUTER JOIN
                  Intelle_Ordre.dbo.wOrdLn_History ON 'SO-' + dbo.Customer_order.Order_nr = Intelle_Ordre.dbo.wOrdLn_History.OrdNo AND 
                  Intelle_Ordre.dbo.wOrdLn_History.LnNo = F0100.dbo.OrdLn.LnNo AND Intelle_Ordre.dbo.wOrdLn_History.ProdNo = F0100.dbo.OrdLn.ProdNo
WHERE  (Intelle_Ordre.dbo.wOrdLn_History.MainProd LIKE '') OR
                  (F0100.dbo.OrdLn.Free1 <> 0) OR
                  (F0100.dbo.OrdLn.ProdNo = 'MONTSPIKER')
