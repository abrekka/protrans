ALTER VIEW [dbo].[takstol_package_v]
AS
select * from takstol_v where is_default=1 and LOWER(construction_type_name) like '%PRECUT%' OR Number_of_items <> 2;
