package utils;

import models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        Envio envio1 = new EnvioBuilder(direccion1, direccion2, 2.5, 30.0, 20.0, 15.0)
                .withFirma()
                .withSeguro()
                .build();

        Envio envio2 = new EnvioBuilder(direccion3, direccion4, 5.0, 50.0, 40.0, 35.0)
                .withFragil()
                .build();

        Envio envio3 = new EnvioBuilder(direccion5, direccion6, 1.2, 25.0, 15.0, 10.0)
                .withPrioridad()
                .build();

        Envio envio4 = new EnvioBuilder(direccion5, direccion7, 8.0, 60.0, 45.0, 40.0)
                .withSeguro()
                .withFragil()
                .build();

        Envio envio5 = new EnvioBuilder(direccion4, direccion1, 3.3, 40.0, 30.0, 25.0)
                .withFirma()
                .withPrioridad()
                .build();

        Envio envio6 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0)
                .withFirma()
                .withSeguro()
                .build();

        Envio envio7 = new EnvioBuilder(direccion8, direccion9, 2.8, 30.0, 20.0, 15.0)
                .withSeguro()
                .build();

        envio1.setCosto(45000);
        envio2.setCosto(25000);
        envio3.setCosto(105000);
        envio4.setCosto(55000);
        envio5.setCosto(105000);
        envio6.setCosto(95000);
        envio7.setCosto(15000);

        envio2.porAsignar();
        envio3.porAsignar();
        envio5.porAsignar();
        envio7.porAsignar();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        envio1.setFechaCreacion(LocalDateTime.parse("13/09/2025 00:00", formatter));
        envio1.setFechaEstimadaEntrega(LocalDateTime.parse("28/09/2025 00:00", formatter));
        envio2.setFechaCreacion(LocalDateTime.parse("17/09/2025 00:00", formatter));
        envio2.setFechaEstimadaEntrega(LocalDateTime.parse("02/10/2025 00:00", formatter));
        envio3.setFechaCreacion(LocalDateTime.parse("21/10/2025 00:00", formatter));
        envio3.setFechaEstimadaEntrega(LocalDateTime.parse("05/11/2025 00:00", formatter));
        envio3.setFechaEntrega(LocalDateTime.parse("04/11/2025 00:00", formatter));
        envio4.setFechaCreacion(LocalDateTime.parse("25/09/2025 00:00", formatter));
        envio4.setFechaEstimadaEntrega(LocalDateTime.parse("09/10/2025 00:00", formatter));
        envio5.setFechaCreacion(LocalDateTime.parse("28/09/2025 00:00", formatter));
        envio5.setFechaEstimadaEntrega(LocalDateTime.parse("12/10/2025 00:00", formatter));
        envio6.setFechaCreacion(LocalDateTime.parse("02/11/2025 00:00", formatter));
        envio6.setFechaEstimadaEntrega(LocalDateTime.parse("17/11/2025 00:00", formatter));
        envio7.setFechaCreacion(LocalDateTime.parse("06/10/2025 00:00", formatter));
        envio7.setFechaEstimadaEntrega(LocalDateTime.parse("21/10/2025 00:00", formatter));
        envio7.setFechaEntrega(LocalDateTime.parse("01/11/2025 00:00", formatter));

        DateTimeFormatter formatt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        Incidencia incidencia1 = new Incidencia("Envio en Camino");
        Incidencia incidencia2 = new Incidencia("El repartidor se accidentó, lamentamos los inconvenientes");
        Incidencia incidencia3 = new Incidencia("El envio retorna su camino nuevamente");
        incidencia1.setFecha(LocalDateTime.parse("03/11/2025 00:00", formatt));
        incidencia2.setFecha(LocalDateTime.parse("05/11/2025 00:00", formatt));
        incidencia3.setFecha(LocalDateTime.parse("10/11/2025 00:00", formatt));
        envio6.addIncidencia(incidencia1);
        envio6.addIncidencia(incidencia2);
        envio6.addIncidencia(incidencia3);

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

        u1.addDireccion(direccion8);
        u1.addDireccion(direccion9);
        u1.addDireccion(direccion10);
        u1.addDireccion(direccion11);
        u1.addEnvio(envio6);
        u1.addEnvio(envio7);
        envio6.setNombreUsuario(u1.getNombre());
        envio7.setNombreUsuario(u1.getNombre());

        u2.addDireccion(direccion1);
        u2.addDireccion(direccion2);
        u2.addDireccion(direccion3);
        u2.addDireccion(direccion4);
        u2.addEnvio(envio1);
        u2.addEnvio(envio2);
        u2.addEnvio(envio5);
        envio1.setNombreUsuario(u2.getNombre());
        envio2.setNombreUsuario(u2.getNombre());
        envio5.setNombreUsuario(u2.getNombre());

        u3.addDireccion(direccion5);
        u3.addDireccion(direccion6);
        u3.addDireccion(direccion7);
        u3.addEnvio(envio3);
        u3.addEnvio(envio4);
        envio3.setNombreUsuario(u3.getNombre());
        envio4.setNombreUsuario(u3.getNombre());

        //ENVIOS ENTREGADOS PARA PANEL DE METRICAS

        Random rand = new Random();
        Direccion[] dirs = {direccion1, direccion2, direccion3, direccion4, direccion5,
                direccion6, direccion7, direccion8, direccion9, direccion10, direccion11};
        Repartidor[] reps = {repartidor1, repartidor3, repartidor5};
        Usuario[] users = {u1, u2, u3, u4, u5, u6, u7, u8, u9, u10};

        List<Envio> nuevos20 = new ArrayList<>();
        LocalDate baseDate = LocalDate.of(2025, 8, 21);

        for (int i = 1; i <= 30; i++) {

            Direccion origen = dirs[rand.nextInt(dirs.length)];
            Direccion destino = dirs[rand.nextInt(dirs.length)];

            double peso = 1 + rand.nextDouble(8); // 1kg - 9kg
            double distancia = 20 + rand.nextInt(50); // 20-70km
            double costoBase = 15000 + rand.nextInt(90000); // 15k - 105k
            double extra1 = 10 + rand.nextInt(40);
            double extra2 = 5 + rand.nextInt(30);

            boolean seguro = rand.nextBoolean();
            boolean fragil = rand.nextBoolean();
            boolean firma = rand.nextBoolean();
            boolean prioridad = rand.nextBoolean();

              EnvioBuilder builder = new EnvioBuilder(origen, destino, peso, distancia, extra1, extra2);

            if (seguro) builder.withSeguro();
            if (fragil) builder.withFragil();
            if (firma) builder.withFirma();
            if (prioridad) builder.withPrioridad();

            Envio e = builder.build();
            e.setCosto(costoBase);

            e.porAsignar();
            Repartidor rep = reps[i % reps.length];
            rep.addEnvios(e);
            e.setNombreRepartidor(rep.getNombre());
            e.asignar();
            e.enRuta();
            e.entregar();

            LocalDateTime creacion = baseDate.plusDays(rand.nextInt(60))
                    .atTime(rand.nextInt(24), rand.nextInt(60));
            LocalDateTime estimada = creacion.plusDays(5 + rand.nextInt(10));
            LocalDateTime entrega = estimada.minusDays(1).plusHours(rand.nextInt(6));

            e.setFechaCreacion(creacion);
            e.setFechaEstimadaEntrega(estimada);
            e.setFechaEntrega(entrega);

            Usuario u = users[i % users.length];
            u.addEnvio(e);
            e.setNombreUsuario(u.getNombre());

            nuevos20.add(e);
        }

        for (Envio e : nuevos20) {
            plataformaEnvios.addEnvio(e);
        }
    }

}
