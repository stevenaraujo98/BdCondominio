use kenastdb;

create table administrador(
	idAdministrador int auto_increment primary key,
    fechaInicio dateTime,
    fechaFin dateTime,
    idHabitante int,
    foreign key (idHabitante) references habitante(idHabitante)
);

create table factura(
	idFactura int auto_increment primary key,
    tipo varchar(25)
);

create table apartamento(
	idApartamento int auto_increment primary key,
    precio float,
    descripcion varchar(90),
    estado varchar(30),
    idDueno int,
    idHabitante int,
    cantMascotas int,
    cantPersonas int,
    foreign key (idHabitante) references habitante(idHabitante)
);

create table pago(
idPago int primary key auto_increment,
idFactura int,
idApartamento int,
idAdministrador int,
fecha date,
monto float,
foreign key (idFactura) references factura(idFactura),
foreign key (idApartamento) references apartamento(idApartamento),
foreign key (idAdministrador) references administrador(idAdministrador)
);

create table actividades(
idActividades int primary key auto_increment,
descripcion varchar(90),
titulo varchar(90)
);

create table participaciones(
idParticipaciones int primary key auto_increment,
idHabitante int,
idActividades int,
fecha date,
foreign key (idHabitante) references habitante(idHabitante),
foreign key (idActividades) references actividades(idActividades)
);

create table votacion(
idVotacion int primary key auto_increment,
idParticipaciones int,
cantvotos int,
fechaVotacion date,
foreign key (idParticipaciones) references participaciones(idParticipaciones)
);


