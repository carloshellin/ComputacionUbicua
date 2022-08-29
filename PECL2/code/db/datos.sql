insert ignore into Usuario values (1,'0','Diego'),(2,'2','Maria'),(3,'3','Laura'),(4,'4','Marcos'),(5,'5','Pepe'),(6,'6','Guille'),(7,'7','Sara'),(8,'8','Adrian');

insert ignore into Tipo values (1,'secano','Planta desertica con puas',800,500,200,100,300,150),(2,'regadio','Planta de interior clasica',600,300,700,400,500,250),(3,'tropical','Planta que trepa por la pared',700,300,650,400,600,250);

insert ignore into Maceta values (1,"Maceta de la cocina",1,1),(1,"Maceta del baño",5,2),(2,"Maceta de la cocina",1,1),(3,"Maceta del baño",1,3),(4,"Maceta de la cocina",6,2),(5,"Maceta del baño",2,3),(6,"Maceta de la cocina",3,3),(7,"Maceta del baño",4,3),(8,"Maceta de la cocina",7,2),(9,"Maceta del baño",8,2);

insert ignore into Sensor values (1,'agua',0,10,'cm',1),(2,'humedad en la tierra',0,100,'porcentaje',1),(3,'humedad en el ambiente',0,100,'porcentaje',1),(4,'temperatura',0,50,'grados',1);

insert ignore into Medida values (1,'20/12/24',2,1),(2,'20/12/25',4.5,1),(3,'20/12/26',1,1),(4,'20/12/24',54,2),(5,'20/12/25',23,2),(6,'20/12/26',88,2),(7,'20/12/24',48,3),(8,'20/12/25',73,3),(9,'20/12/24',24,3),(10,'20/12/25',20,4),(11,'20/12/26',22,4),(12,'20/12/26',24,4);