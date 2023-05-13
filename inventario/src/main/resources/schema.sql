-- Create Table Products
CREATE TABLE product 
	(id VARCHAR(10) not null, 
	 nombre varchar(20), 
	 descripcion varchar(200),
	 precio numeric(19,2),
	 modelo varchar(10),  
	 primary key (id));