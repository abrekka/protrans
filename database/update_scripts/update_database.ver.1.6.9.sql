CREATE UNIQUE NONCLUSTERED INDEX [production_budget_uk] ON [dbo].[production_budget] 
(
	[budget_year] ASC,
	[budget_week] ASC,
	[product_area_id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
/******************************************************************************************************
insert into window_access(window_name) values('Importer budsjettall');