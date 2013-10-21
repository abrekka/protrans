insert into user_type(description,startup_window) values('Avviksansvarlig','no.ugland.utransprod.gui.ProTransMain');
insert into user_type(description,startup_window) values('Lese','no.ugland.utransprod.gui.ProTransMain');

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Avvik','no.ugland.utransprod.gui.DeviationWindow',5);

INSERT INTO User_type(Description,Startup_window,User_level) VALUES('Fakturaansvarlig','no.ugland.utransprod.gui.InvoiceWindow',5);


alter table order_line add is_default int;