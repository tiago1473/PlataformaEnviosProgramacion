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
        Direccion direccion1 = new Direccion("Casa", "Calle 10 #12-34", "Armenia");
        Direccion direccion2 = new Direccion("Trabajo", "Carrera 15 #8-22", "Pereira");
        Direccion direccion3 = new Direccion("Apartamento", "Avenida Bolívar #45-10", "Manizales");
        Direccion direccion4 = new Direccion("Bodega", "Calle 25 #19-55", "Cali");
        Direccion direccion5 = new Direccion("Oficina", "Carrera 7 #32-18", "Bogotá");
        Direccion direccion6 = new Direccion("Sucursal Norte", "Calle 80 #45-12", "Medellín");
        Direccion direccion7 = new Direccion("Casa de campo", "Vereda El Caimo, km 4 vía Montenegro", "Armenia");

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

        repartidor1.addEnvios(envio1);
        repartidor1.addEnvios(envio2);
        repartidor2.addEnvios(envio3);
        repartidor3.addEnvios(envio4);
        repartidor1.addEnvios(envio5);
        repartidor3.addEnvios(envio1);

        //Gestion Usuario y Envios
        Direccion direccion8 = new Direccion("Casa", "Carrera 35 # 19 - 79", "Calarcá");
        Direccion direccion9 = new Direccion("Empresa", "Carrera 15 #8-22", "Pereira");
        Direccion direccion10 = new Direccion("Hogar", "Carrera 41", "Manizales");
        Direccion direccion11 = new Direccion("Empresa", "Carrera 15 #8-22", "Pereira");

        Envio envio6 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0,
                EstadoEnvio.SOLICITADO).withFirma()
                .withSeguro()
                .build();

        Envio envio7 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0,
                EstadoEnvio.ENRUTA).withSeguro()
                .build();

        Usuario usuario1 = new Usuario("1004870794", "123", "Samuel", "SamuelLara@gmail.com", "3103970243");
        usuario1.addDireccion(direccion8);
        usuario1.addDireccion(direccion9);
        usuario1.addDireccion(direccion10);
        usuario1.addDireccion(direccion11);
        usuario1.addEnvio(envio6);
        usuario1.addEnvio(envio7);

        plataformaEnvios.addUsuario(usuario1);
    }
}
