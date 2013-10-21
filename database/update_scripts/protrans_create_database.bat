osql -U sa -P -S ar006 -i protrans_database.sql
osql -U sa -P -S ar006 -d protrans -i protrans_user.sql
osql -U admin -P admin -S ar006 -d protrans -i protrans_tables.sql