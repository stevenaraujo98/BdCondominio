use kenastdb;

alter table actividades
add column fechaCreacion date;

alter table participaciones
add column tarea varchar(90);

DELIMITER //
CREATE PROCEDURE CREARACTIVIDAD(IN titu varchar(90), IN descrip varchar(90))
	BEGIN
        insert into actividades(titulo, descripcion, fechaCreacion)
			values(titu, descrip, curdate());
            
		select idActividades from actividades a
        where a.descripcion = descrip and titulo = titu and fechaCreacion = curdate();

	END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE CREARPARTICIPACION(IN idHab int, IN idActivi int, IN fech date, IN tare varchar(90))
	BEGIN
        insert into participaciones(idHabitante, idActividades, fecha, tarea)
			values(idHab, idActivi, fech, tare);
	END //
DELIMITER ;


create view lista_actividad_partiipaciones as
	select a.titulo, a.descripcion, p.tarea, p.fecha
    from actividades a, participaciones p
    where a.idActividades = p.idActividades;

#------------------------------------------------------------------------------
    
ALTER TABLE actividades
add column estado boolean default true;

ALTER TABLE participaciones
add column estado boolean default true;


drop view lista_actividad_partiipaciones;

create view lista_actividad_partiipaciones as
	select a.titulo, a.descripcion, a.fechaCreacion, p.tarea, p.fecha, p.idHabitante
    from actividades a, participaciones p
    where a.idActividades = p.idActividades and a.estado = true and p.estado = true;


DELIMITER //
CREATE PROCEDURE PARTICIPACIONUSUARIO(IN id int)
	BEGIN
		select * from lista_actividad_partiipaciones l
        where l.idHabitante = id;
	END //
DELIMITER ;
    

DELIMITER //
CREATE PROCEDURE LISTAACTIVIDADES(IN feI date, IN feF date)
	BEGIN
		select * from actividades a
        where a.fechaCreacion >= feI and a.fechaCreacion <= feF and a.estado = true;
	END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE READPATICIPACION(IN idActivi int)
	BEGIN 
		SELECT *
        FROM participaciones p
        WHERE p.idActividades = idActivi and p.estado = true;
    END //
DELIMITER ;

 
DELIMITER //
CREATE PROCEDURE DELTEPARTICIPACION(IN idPar int)
	BEGIN 
		update participaciones
        set estado = false
        where idParticipaciones = idPar;
    END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE EDITACTIVIDAD(IN idAct int, IN titu varchar(90), IN des varchar(90))
	BEGIN 
		update actividades
        set titulo = titu, descripcion = des
        where idActividades = idAct;
    END //
DELIMITER ;


DELIMITER //
CREATE PROCEDURE EDITPARTICIPACION(IN idPar int, IN idHab int, IN fech date, IN tar varchar(90))
	BEGIN 
		update participaciones
        set idHabitante = idHab, fecha = fech, tarea = tar
        where idParticipaciones = idPar;
    END //
DELIMITER ;


