USE kenastdb;

ALTER TABLE pago
ADD COLUMN idHabitante INTEGER;

ALTER TABLE pago
ADD CONSTRAINT fk_idHabitante
FOREIGN KEY (idHabitante) REFERENCES habitante(idHabitante);



DELIMITER //
CREATE PROCEDURE CREATEUSER 
	(IN userName VARCHAR(30), IN pass VARCHAR(30), IN nomb VARCHAR(30), IN lastname VARCHAR(30),
    IN email VARCHAR(50), IN phone VARCHAR(20))
	BEGIN
		INSERT INTO habitante(Nombre, Apellido, Correo, Telefono)
			VALUES(nomb, lastname, email, phone);
		INSERT INTO login(users, passwords, idHabitante)
			VALUES(userName, pass, 
								(SELECT idHabitante 
                                FROM habitante h
                                WHERE h.Nombre = nomb AND h.Apellido = lastname 
									AND h.Correo = email AND h.Telefono = phone));
		SELECT idHabitante 
		FROM habitante h
		WHERE h.Nombre = nomb AND h.Apellido = lastname 
			AND h.Correo = email AND h.Telefono = phone;
	END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE DELETEUSER
	(IN id INT)
    BEGIN
		DELETE FROM login
        WHERE idHabitante = id;
        
		DELETE FROM habitante
        WHERE idHabitante = id;
    END //
DELIMITER ;

INSERT INTO administrador(fechaInicio, fechaFin, idHabitante)
					VALUES('2018-01-01', '2018-12-31', 1);
ALTER TABLE apartamento
add constraint fk_dueno foreign key(idDueno)references habitante(idHabitante);

alter table apartamento 
drop foreign key apartamento_ibfk_1;

INSERT INTO apartamento(precio, descripcion, estado, idDueno, idHabitante, cantMascotas, cantPersonas)
			VALUES(2000, "Departamento comun", "ocupado", 1, 1, 2, 4),
					(1500, "Departamento simple", "ocupado", 2, 2, 0, 4),
                    (2500, "Departamento comun para 6 personas", "ocupado", 3, 3, 2, 6);
INSERT INTO apartamento(precio, descripcion, estado, idDueno, idHabitante, cantMascotas, cantPersonas)
			VALUES (3000, "Departamento elegante", "libre", 1, 0, 0, 5),
					(3000, "Departamento estilo clásico", "libre", 3, 0, 4, 7),
					(3500, "Departamento estilo clásico", "libre", 2, 0, 0, 7);



insert into factura(tipo)
	values("ALICUOTA"), ("ELECTRICIDAD"), ("AGUA"), ("TELEFONO"), ("MULTA");
    
DELIMITER //
CREATE PROCEDURE CREATEPAGO 
	(IN mont FLOAT,  IN datep DATE, IN idApart INT, 
    IN idAdmin INT, IN idFac INT, IN idHab INTEGER)
    BEGIN
		INSERT INTO pago (idFactura, idApartamento, idAdministrador, fecha, monto, idHabitante)
					VALUES(idFac, idApart, idAdmin, datep, mont, idHab);
    END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE READPAGO 
	(IN idHab INT, IN datep DATE)
    BEGIN
		SELECT idPago, monto, fecha, idFactura, idApartamento
        FROM pago
        WHERE idHabitante = idHab AND fecha = datep;
    END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE UPDATEPAGO
	(IN idPg INT, IN idFac INT, IN idApar INT, IN idAdmin INT, IN datep DATE, IN mont FLOAT, IN idHab INT)
    BEGIN
		UPDATE pago
        SET idFactura = idFac, idApartamento = idApar, idAdministrador = idAdmin, 
			fecha = datep, monto = mont, idHabitante = idHab
		WHERE idPago = idPg;
    END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE DELETEPAGO
	(IN idPg INT)
    BEGIN
		DELETE FROM pago
        WHERE idPago = idPg;
    END //
DELIMITER ;


CREATE VIEW LISTADOUSUARIOS
AS SELECT h.idHabitante, users, passwords, Nombre, Apellido, Correo, Telefono, TIPO
        FROM LOGIN l, HABITANTE h
        WHERE l.idHabitante = h.idHabitante;

DROP PROCEDURE LOGINAPP;
DROP PROCEDURE READUSER;

DELIMITER //
CREATE PROCEDURE LOGINAPP (IN userName VARCHAR(30), IN pass VARCHAR(30))
	BEGIN
		SELECT idHabitante, users, passwords, Nombre, Apellido, Correo, Telefono, TIPO
        FROM LISTADOUSUARIOS 
        WHERE users = userName AND passwords = pass;
	END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE READUSER (IN id INT)
	BEGIN
		SELECT *
        FROM listadousuarios
        WHERE idHabitante = id;
	END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE READAPARTAMENTO(IN id INT)
	BEGIN
		SELECT * 
        FROM APARTAMENTO
        WHERE id = idApartamento;
	END //
DELIMITER;

DELIMITER //
CREATE PROCEDURE CREATEAPARTAMENTOC(IN precion FLOAT, IN descripcionn VARCHAR(90), IN estadon VARCHAR(30), IN idDuenon INT, IN idHabitanten INT, IN cantMascotasn INT, IN cantPersonasn INT)
	BEGIN 
		INSERT INTO apartamento(precio, descripcion, estado, idDueno, idHabitante, cantMascotas, cantPersonas) VALUES(precion, descripcionn, estadon, idDuenon, idHabitanten, cantMascotasn, cantPersonasn);
        
        SELECT idApartamento
        FROM APARTAMENTO
        WHERE precio = precion AND estado = estadon AND descripcion = descripcionn AND idDueno = idDuenon
        AND idHabitante = idHabitanten AND cantMascotas = cantMascotasn AND cantPersonas = cantPersonasn;
	END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CREATEAPARTAMENTOI(IN precion FLOAT, IN descripcionn VARCHAR(90), IN estadon VARCHAR(30), IN idDuenon INT)
	BEGIN 
		INSERT INTO apartamento(precio, descripcion, estado, idDueno, idHabitante, cantMascotas, cantPersonas) VALUES(precion, descripcionn, estadon, idDuenon, 0, 0, 0);
        
        SELECT idApartamento
        FROM APARTAMENTO
        WHERE precio = precion AND estado = estadon AND descripcion = descripcionn AND idDuenon = idDueno;
	END //
DELIMITER ;

CREATE VIEW APARTAMENTOSDISPONIBLES
AS 	SELECT *
	FROM apartamento
	WHERE estado <> "Inhabilitado";
    
DELIMITER //
CREATE PROCEDURE USERAPARTMENTS(IN id INT)
	BEGIN
		SELECT *
        FROM apartamentodisponibles
        where idDueno = id OR idHabitante = id;
    END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE APARTMENTBYID(IN id INT)
	BEGIN
		SELECT *
        FROM apartamentodisponibles
        where idApartamento = id;
    END//
DELIMITER ;UPDATEPAGO

DELIMITER //
CREATE PROCEDURE DELETEAPARTAMENTO(IN id INT)
	BEGIN
		UPDATE apartamento SET estado = 'Inhabilitado'
        WHERE idApartamento = id;
    END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE UPDATEDEPARTAMENTO(IN id INT, IN precion FLOAT, IN descripcionn VARCHAR(90), IN estadon VARCHAR(30), IN idDuenon INT, IN idHabitanten INT, IN cantMascotasn INT, IN cantPersonasn INT)
	BEGIN
		UPDATE apartamento SET precio = precion, estado = estadon, descripcion = descripcionn, idDueno = idDuenon, idHabitante = idHabitanten, cantMascotas = cantMascotasn, cantPersonas = cantPersonasn
        WHERE idApartamento = id;
    END //
DELIMITER ;listadousuarios

DELIMITER //
CREATE PROCEDURE UPDATEUSER 
	(IN id INT, IN userName VARCHAR(30), IN pass VARCHAR(30), IN nomb VARCHAR(30), IN lastname VARCHAR(30),
    IN email VARCHAR(50), IN phone VARCHAR(20), IN tip VARCHAR(30))
	BEGIN
		UPDATE habitante
        SET Nombre = nomb, Apellido = lastname, Correo = email, Telefono = phone, TIPO = tip
        WHERE idHabitante = id;
        
        UPDATE login
        SET users = userName, passwords = pass
		WHERE idHabitante = id;
	END //
DELIMITER ;