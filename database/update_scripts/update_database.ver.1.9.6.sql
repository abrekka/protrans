ALTER VIEW [dbo].[takstol_production_v]
AS
select takstol_v.*,case article_name when 'Takstoler' then 1 else 2 end as default_article from takstol_v where is_default=0
/*************************************************************************************************************************************************
ALTER VIEW [dbo].[takstol_package_v]
AS
select takstol_v.* ,case article_name when 'Takstoler' then 1 else 2 end as default_article from takstol_v where is_default=1
/*************************************************************************************************************************************************
