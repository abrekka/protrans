ALTER VIEW [dbo].[fakturagrunnlag_v]
AS
SELECT        dbo.Customer_order.Order_id, dbo.Customer_order.Order_nr, F0100.dbo.Ord.OrdNo, F0100.dbo.OrdLn.Free2, F0100.dbo.OrdLn.LnNo, 
                         F0100.dbo.OrdLn.ProdNo, F0100.dbo.OrdLn.Descr, F0100.dbo.OrdLn.NoOrg AS noof, F0100.dbo.OrdLn.Free1 AS org_price_mont, 
                         F0100.dbo.OrdLn.Free1 * 0.85 AS price_mont, F0100.dbo.OrdLn.NoOrg * (F0100.dbo.OrdLn.Free1 * 0.85) AS sum_line, 
                         F0100.dbo.OrdLn.NoOrg * F0100.dbo.OrdLn.Free1 AS org_sum_line, F0100.dbo.OrdLn.CstPr, F0100.dbo.OrdLn.Price, F0100.dbo.OrdLn.PurcNo, 
                         F0100.dbo.OrdLn.LnPurcNo, F0100.dbo.OrdLn.Alloc
FROM            dbo.Customer_order INNER JOIN
                         F0100.dbo.Ord ON dbo.Customer_order.Order_nr = F0100.dbo.Ord.Inf6 INNER JOIN
                         F0100.dbo.OrdLn ON F0100.dbo.Ord.OrdNo = F0100.dbo.OrdLn.OrdNo
WHERE        (F0100.dbo.OrdLn.Free1 <> 0) AND (F0100.dbo.OrdLn.Free1 <> 0.01) AND (F0100.dbo.OrdLn.ProdNo <> 'GARAVR') OR
                         (F0100.dbo.OrdLn.ProdNo = 'MONTSPIKER') OR
                         (F0100.dbo.OrdLn.ProdNo = 'kranbil') OR
                         (F0100.dbo.OrdLn.ProdNo = 'overskrift')OR
                         (F0100.dbo.OrdLn.ProdNo = 'frakt')