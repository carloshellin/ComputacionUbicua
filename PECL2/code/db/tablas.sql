
CREATE TABLE `Maceta` ( 
   `id` INT NOT NULL AUTO_INCREMENT,
   `Descripcion` TEXT NOT NULL,
   `Usuario_id` INT NOT NULL,
   `Tipo_id` INT NOT NULL,
    PRIMARY KEY `Primary key`(
   `id`
    )
);
CREATE TABLE `Sensor` ( 
   `id` INT NOT NULL AUTO_INCREMENT,
   `Descripcion` TEXT NOT NULL,
   `MinValue` INT NOT NULL,
   `MaxValue` INT NOT NULL,
   `Unidad` TEXT NOT NULL,
   `Maceta_id` INT NOT NULL,
    PRIMARY KEY `Primary key`(
   `id`
    )
);
CREATE TABLE `Medida` ( 
   `id` INT NOT NULL AUTO_INCREMENT,
   `Fecha` DATE NOT NULL,
   `Valor` DOUBLE NOT NULL,
   `Sensor_id` INT NOT NULL,
    PRIMARY KEY `Primary key`(
   `id`
    )
);
CREATE TABLE `Tipo` ( 
   `id` INT NOT NULL AUTO_INCREMENT,
   `Nombre` TEXT NOT NULL,
   `Descripcion` TEXT NOT NULL,
   `LuzMax` INT NOT NULL,
   `LuzMin` INT NOT NULL,
   `AguaMax` INT NOT NULL,
   `AguaMin` INT NOT NULL,
   `HumedadMax` INT NOT NULL,
   `HumedadMin` INT NOT NULL,
    PRIMARY KEY `Primary key`(
   `id`
    )
);
CREATE TABLE `Usuario` ( 
   `id` INT NOT NULL AUTO_INCREMENT,
   `Contrasena` TEXT NOT NULL,
   `Nombre` TEXT NOT NULL,
    PRIMARY KEY `Primary key`(
   `id`
    )
);
ALTER TABLE `Maceta` 
  ADD CONSTRAINT `Usuario-Maceta`
  FOREIGN KEY ( 
   `Usuario_id`
)   REFERENCES `Usuario`( 
   `id`
) ;


ALTER TABLE `Sensor` 
  ADD CONSTRAINT `Maceta-Sensor`
  FOREIGN KEY ( 
   `Maceta_id`
)   REFERENCES `Maceta`( 
   `id`
) ON DELETE CASCADE ;


ALTER TABLE `Medida` 
  ADD CONSTRAINT `Sensor-Medida`
  FOREIGN KEY ( 
   `Sensor_id`
)   REFERENCES `Sensor`( 
   `id`
) ON DELETE CASCADE ;


ALTER TABLE `Maceta` 
  ADD CONSTRAINT `Tipo-Maceta`
  FOREIGN KEY ( 
   `Tipo_id`
)   REFERENCES `Tipo`( 
   `id`
) ;

