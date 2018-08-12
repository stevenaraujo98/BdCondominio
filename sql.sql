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




