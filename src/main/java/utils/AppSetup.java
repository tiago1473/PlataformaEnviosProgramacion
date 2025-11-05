package utils;

import models.*;

import java.util.ArrayList;

public class AppSetup {
    private PlataformaEnvios plataformaEnvios;

    public AppSetup() {
        incializarDatos();
    }

    private void incializarDatos() {
        PlataformaEnvios plataformaEnvios=PlataformaEnvios.getInstancia();

        UsuarioBase a1= new UsuarioBase("adm01","pas123",Rol.ADM);
        Usuario u1 = new Usuario("1004870794", "123", Rol.USER,"Samuel", "SamuelLara@gmail.com", "3103970243");
        Usuario u2 = new Usuario("24567890", "pas123", Rol.USER, "María López", "maria.lopez@example.com", "555-5678");
        Usuario u3 = new Usuario("1010121631", "pas456", Rol.USER, "Juan García", "juan.garcia@example.com", "555-9012");
        Usuario u4 = new Usuario("1097404946", "pas789", Rol.USER, "Ana Torres", "ana.torres@example.com", "555-3456");
        Usuario u5 = new Usuario("18605979", "con123", Rol.USER,"Luis Ramírez", "luis.ramirez@example.com", "555-7890");
        Usuario u6 = new Usuario("18603312", "con456", Rol.USER,"Laura Fernández", "laura.fernandez@example.com", "555-2345");
        Usuario u7 = new Usuario("1094942977", "con789", Rol.USER,"Diego Martínez", "diego.martinez@example.com", "555-6789");
        Usuario u8 = new Usuario("1012492055", "user123", Rol.USER, "Sofía Gómez", "sofia.gomez@example.com", "555-4321");
        Usuario u9 = new Usuario("24575977", "user456", Rol.USER,"Andrés Herrera", "andres.herrera@example.com", "555-8765");
        Usuario u10 = new Usuario("1334567", "user789", Rol.USER,"Valentina Cruz", "valentina.cruz@example.com", "555-1111");


        Direccion direccion1 = new Direccion("Casa", "Calle 10 #12-34", "Armenia");
        Direccion direccion2 = new Direccion("Trabajo", "Carrera 15 #8-22", "Pereira");
        Direccion direccion3 = new Direccion("Apartamento", "Avenida Bolívar #45-10", "Manizales");
        Direccion direccion4 = new Direccion("Bodega", "Calle 25 #19-55", "Cali");
        Direccion direccion5 = new Direccion("Oficina", "Carrera 7 #32-18", "Bogotá");
        Direccion direccion6 = new Direccion("Sucursal Norte", "Calle 80 #45-12", "Medellín");
        Direccion direccion7 = new Direccion("Casa de campo", "Vereda El Caimo, km 4 vía Montenegro", "Armenia");
        Direccion direccion8 = new Direccion("Casa", "Carrera 35 # 19 - 79", "Calarcá");
        Direccion direccion9 = new Direccion("Empresa", "Carrera 15 #8-22", "Pereira");
        Direccion direccion10 = new Direccion("Hogar", "Carrera 41", "Manizales");
        Direccion direccion11 = new Direccion("Empresa", "Carrera 15 #8-22", "Pereira");

        Repartidor repartidor1 = new Repartidor("1009876543", "Juan Pérez", "3107896543", EstadoRepartidor.ACTIVO);
        Repartidor repartidor2 = new Repartidor("1012233445", "Ana Morales", "3124567890", EstadoRepartidor.INACTIVO);
        Repartidor repartidor3 = new Repartidor("1098765432", "Luis Herrera", "3198765432", EstadoRepartidor.ACTIVO);
        Repartidor repartidor4 = new Repartidor("1089345621", "Camila Rojas", "3176543210", EstadoRepartidor.ENRUTA);
        Repartidor repartidor5 = new Repartidor("1023456789", "David Gómez", "3141122334", EstadoRepartidor.ACTIVO);

        Envio envio1 = new EnvioBuilder(direccion1, direccion2, 2.5, 30.0, 20.0, 15.0,
                EstadoEnvio.SOLICITADO
        ).withFirma()
                .withSeguro()
                .build();

        Envio envio2 = new EnvioBuilder(direccion3, direccion4, 5.0, 50.0, 40.0, 35.0,
                EstadoEnvio.ENRUTA)
                .withFragil()
                .build();

        Envio envio3 = new EnvioBuilder(direccion5, direccion6, 1.2, 25.0, 15.0, 10.0,
                EstadoEnvio.ENTREGADO)
                .withPrioridad()
                .build();

        Envio envio4 = new EnvioBuilder(direccion2, direccion7, 8.0, 60.0, 45.0, 40.0,
                EstadoEnvio.SOLICITADO)
                .withSeguro()
                .withFragil()
                .build();

        Envio envio5 = new EnvioBuilder(direccion4, direccion1, 3.3, 40.0, 30.0, 25.0,
                EstadoEnvio.ENRUTA)
                .withFirma()
                .withPrioridad()
                .build();
        Envio envio6 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0,
                EstadoEnvio.SOLICITADO).withFirma()
                .withSeguro()
                .build();

        Envio envio7 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0,
                EstadoEnvio.ENRUTA).withSeguro()
                .build();

        plataformaEnvios.addUsuario(a1);
        plataformaEnvios.addUsuario(u1);
        plataformaEnvios.addUsuario(u2);
        plataformaEnvios.addUsuario(u3);
        plataformaEnvios.addUsuario(u4);
        plataformaEnvios.addUsuario(u5);
        plataformaEnvios.addUsuario(u6);
        plataformaEnvios.addUsuario(u7);
        plataformaEnvios.addUsuario(u8);
        plataformaEnvios.addUsuario(u9);
        plataformaEnvios.addUsuario(u10);

        plataformaEnvios.addRepartidor(repartidor1);
        plataformaEnvios.addRepartidor(repartidor2);
        plataformaEnvios.addRepartidor(repartidor3);
        plataformaEnvios.addRepartidor(repartidor4);
        plataformaEnvios.addRepartidor(repartidor5);

        plataformaEnvios.addEnvio(envio1);
        plataformaEnvios.addEnvio(envio2);
        plataformaEnvios.addEnvio(envio3);
        plataformaEnvios.addEnvio(envio4);
        plataformaEnvios.addEnvio(envio5);
        plataformaEnvios.addEnvio(envio6);
        plataformaEnvios.addEnvio(envio7);

        repartidor1.addEnvios(envio1);
        repartidor1.addEnvios(envio2);
        repartidor2.addEnvios(envio3);
        repartidor3.addEnvios(envio4);
        repartidor1.addEnvios(envio5);
        repartidor3.addEnvios(envio1);
        repartidor3.addEnvios(envio2);
        repartidor4.addEnvios(envio6);
        repartidor4.addEnvios(envio7);

        u1.addDireccion(direccion8);
        u1.addDireccion(direccion9);
        u1.addDireccion(direccion10);
        u1.addDireccion(direccion11);
        u1.addEnvio(envio6);
        u1.addEnvio(envio7);



    }
}
