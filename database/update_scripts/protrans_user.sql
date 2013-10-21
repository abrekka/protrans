exec sp_addlogin 'admin','admin','protrans'
GO
EXEC sp_grantdbaccess N'admin', N'admin'
GO
exec sp_addrolemember N'db_owner', 'admin'
GO
