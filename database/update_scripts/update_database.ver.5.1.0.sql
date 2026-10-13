CREATE VIEW [Protrans].[dbo].[production_time_v]
AS
select [Production_time_id]
      ,[Order_nr]
      ,[Productionname]
      ,[Username]
      , u.first_name + ' ' + u.last_name as name
      ,[Created]
      ,[Updated]
      ,[Started]
      ,[Stopped]
      ,[Overtime]
  FROM [Protrans2].[dbo].[Production_time] p inner join
  [Protrans2].[dbo].[Application_user] u on u.user_name = p.username
  