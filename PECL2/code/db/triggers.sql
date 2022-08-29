DELIMITER $$

CREATE TRIGGER LuzMax
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.LuzMax <0 ) or (new.LuzMax > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER LuzMin
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.LuzMin <0 ) or (new.LuzMin > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER AguaMax
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.AguaMax <0 ) or (new.AguaMax > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER AguaMin
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.AguaMin <0 ) or (new.AguaMin > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER HumedadMax
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.HumedadMax <0 ) or (new.HumedadMax > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER HumedadMin
BEFORE INSERT
ON Tipo FOR EACH ROW

BEGIN

IF ( (new.HumedadMin <0 ) or (new.HumedadMin > 1024) ) 	THEN

	DELETE FROM Tipo
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER ValueMax
BEFORE INSERT
ON Sensor FOR EACH ROW

BEGIN

IF ( (new.MaxValue <0 ) or (new.MaxValue > 1024) ) 	THEN

	DELETE FROM Sensor
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER MinValue
BEFORE INSERT
ON Sensor FOR EACH ROW

BEGIN

IF ( (new.MinValue <0 ) or (new.MinValue > 1024) ) 	THEN

	DELETE FROM Sensor
	where id=new.id;
END IF;

END $$

DELIMITER ;

DELIMITER $$

CREATE TRIGGER Valor
BEFORE INSERT
ON Medida FOR EACH ROW

BEGIN

IF ( (new.Valor <0 ) or (new.Valor > 1024) ) 	THEN

	DELETE FROM Medida
	where id=new.id;
END IF;

END $$

DELIMITER ;