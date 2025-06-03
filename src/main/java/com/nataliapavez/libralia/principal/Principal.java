package com.nataliapavez.libralia.principal;

import com.nataliapavez.libralia.api.ConsumoAPI;
import com.nataliapavez.libralia.dto.ResultadoGoogle;
import com.nataliapavez.libralia.dto.VolumeInfo;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.model.Usuario;
import com.nataliapavez.libralia.service.ConversorLibroService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner teclado = new Scanner(System.in);
        Usuario usuario = crearUsuarioDesdeConsola(); //el usuario construye su perfil
        System.out.println("Bienvenido/a " + usuario.getNombreUsuario() + " a tu biblioteca social Libralia ");

        while (true) {
            System.out.println("\n ¿Qué deseas hacer ahora?");
            System.out.println("""
                    1 - Ver tu perfil.
                    2 - Buscar un libro.
                    0 - salir
                    """);
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> usuario.mostrarInfoUsuario();
                case "2" -> agregarLibrosAPerfil(usuario);
                case "0" -> {
                    System.out.println("Fin de capítulo. Nos vemos en la próxima página.");
                    teclado.close();
                    return;
                }
                default -> System.out.println("Opción Invalida.");
            }
        }
    }

    // Metodo para construir el perfil del usuario
    public static Usuario crearUsuarioDesdeConsola() throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);
        Usuario user = new Usuario();

        System.out.println("//////////////////////////////////////////////");
        System.out.println("Bienvenido/a a tu biblioteca social Libralia ");
        System.out.println("---------------------------------------------");
        System.out.println("Indica tu correo: ");
        user.setCorreo(teclado.nextLine());
        System.out.println("¿Cuál será tu nombre en el Multiverso Libralia?");
        user.setNombreUsuario(teclado.nextLine());

        System.out.println("¿Cuál es tu edad?");

        try {
            user.setEdad((Integer.parseInt(teclado.nextLine())));
        } catch (NumberFormatException e) {
            user.setEdad(0);
            System.out.println("Edad inválida o no registrada");
        }
        if (user.getEdad() > 0 && user.getEdad() < 14) {
            System.out.println("¡Wow! Un lector joven, bienvenido a Libralia.");
        }

        System.out.println("Cuentanos brevemente algo de tí: ");
        user.setDescripcion(teclado.nextLine());

        System.out.println("¿Qué estás leyendo actualmente?");
        user.setListaLecturaActual(teclado.nextLine());

        //Permitir al usuario buscar libros leidos y por leer./ BUCLE
        boolean seguirAgregando = true;
        while (seguirAgregando) {
            System.out.println("¿Quieres agregar libros a tu biblioteca ahora? (si/no)");
            String respuesta = teclado.nextLine().trim().toLowerCase();

            if (respuesta.equals("si")) {
                agregarLibrosAPerfil(user);
            } else if (respuesta.equals("no")) {
                seguirAgregando = false;
            } else {
                System.out.println("Respuesta no válida. Intenta nuevamente.");
            }
        }

        System.out.println("¿Quieres compartir algún enlace o red social?");
        user.setEnlaces(teclado.nextLine());

        System.out.println("¡Felicidades! Tu perfil ha sido creado");
        return user;
    }
    // Metodo para agregar libros al perfilbnevitando duplicar logica
    private static void agregarLibrosAPerfil(Usuario usuario) throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("¿Dónde deseas agregarlos?");
        System.out.println("1 - Leídos");
        System.out.println("2 - Por leer");
        System.out.println("3 - No agregarlos");

        String opcionAgregar = teclado.nextLine().trim();

        if (!opcionAgregar.equals("1") && !opcionAgregar.equals("2")) {
            System.out.println("Ok, puedes volver al menú cuando quieras.");
            return;
        }

        List<LibroPersonal> libros = buscarLibro(usuario);

        if (libros.isEmpty()) {
            System.out.println("No se seleccionaron libros.");
            return;
        }
        switch (opcionAgregar) {
            case "1" -> {
                libros.forEach(usuario::agregarLibroLeido);
                System.out.println("Libros agregados a tu biblioteca de leídos.");
            }
            case "2" -> {
                libros.forEach(usuario::agregarLibroPorLeer);
                System.out.println("Libros agregados a tu lista de deseos (por leer).");
            }
            case "3" -> System.out.println("Libros no agregados.");
            default -> System.out.println("Opción inválida, no se agregaron libros.");
        }
    }

    //buscar libro con Api
    public static List<LibroPersonal> buscarLibro(Usuario usuario) throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Escribe el título o autor que quieres buscar:");
        String busqueda = teclado.nextLine();
        // armar Url busqueda libros
        // verificar que el usuario no deje en blanco
        if (busqueda.trim().isEmpty()) {
            System.out.println("Escribe un nombre válido");
        }
        var consumoApi = new ConsumoAPI();
        var conversor = new ConversorLibroService();
        final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
        String busquedaFormateada = URLEncoder.encode(busqueda.trim(), StandardCharsets.UTF_8);
        //remplaza los espacios intermedios por "+",.trim : elimina espacio al comienzo y final.

        //deserializamos
        String json = consumoApi.obtenerDatos(URL_BASE + busquedaFormateada);
        var resultado = conversor.convertir(json, ResultadoGoogle.class);

        // info de libros buscados y encontrados comprobando que existe la informacion o es nula
        if (resultado.items() == null || resultado.items().isEmpty()) {
            System.out.println("No se encontraron resultados para: " + busqueda);
            return new ArrayList<>();
        }
        //Obtener resultados
        List<VolumeInfo> listaOriginal = resultado.items().stream()
                .map(item -> item.volumeInfo())
                .filter(Objects::nonNull)
                .toList();

        //Ordenar segun coincidencia con la busqueda- refinada para coincidenciasAltas
        String texto = busqueda.toLowerCase();

        List<VolumeInfo> concidenciasAltas = listaOriginal.stream()
                .filter(v -> {
                    String autor = v.getAuthors() != null ? String.join(" ", v.getAuthors()).toLowerCase() : "";
                    String titulo = v.getTitle() != null ? v.getTitle().toLowerCase() : "";
                    return titulo.contains(texto) || autor.contains(texto);
                })
                .collect(Collectors.toList());

        List<VolumeInfo> otrasSugerencias = listaOriginal.stream()
                .filter(v -> !concidenciasAltas.contains(v))
                .collect(Collectors.toList());

        // combinar priorizando coincidencias
        List<VolumeInfo> listaFinalSeleccionable = Stream.concat(concidenciasAltas.stream(), otrasSugerencias.stream())
                .limit(10)
                .collect(Collectors.toList());


        // Mostrar libros con índice al usuario
        System.out.println("RESULTADOS ENCONTRADOS:");
        AtomicInteger contador = new AtomicInteger(1);
        listaFinalSeleccionable.forEach(info -> System.out.printf("%d. %s - %s\n",
                contador.getAndIncrement(),
                info.getTitle(),
                (info.getAuthors() != null && !info.getAuthors().isEmpty())
                        ? String.join(", ", info.getAuthors())
                        : "Autor desconocido"));

        // Preguntar si desea agregar uno o varios
        System.out.println("\n Ingresa el/los números del libro que quieres agregar (separados por coma):");
        String entrada = teclado.nextLine();
        String[] indices = entrada.split(",");

        List<LibroPersonal> librosSeleccionados = Arrays.stream(indices)
                .map(String::trim)
                .map(idx -> {

                    try {
                        int indice = Integer.parseInt(idx.trim());
                        if (indice >= 1 && indice <= listaFinalSeleccionable.size()) {
                            VolumeInfo seleccionado = listaFinalSeleccionable.get(indice - 1);
                            // Crear un LibroPersonal
                            String titulo = seleccionado.getTitle();
                            //practicando optional
                            String autor = Optional.ofNullable(seleccionado.getAuthors())
                                    .filter(list -> !list.isEmpty())
                                    .map(list -> list.get(0))
                                    .orElse("Autor desconocido");

                            String genero = Optional.ofNullable(seleccionado.getCategories())
                                    .filter(list -> !list.isEmpty())
                                    .map(list -> list.get(0))
                                    .orElse("Genero no especificado");

                            int aniopublicacion = Optional.ofNullable(seleccionado.getPublishedDate())
                                    .filter(fecha-> fecha.length() >= 4)
                                    .map(fecha -> {
                                        try {
                                            return Integer.parseInt(fecha.substring(0, 4));
                                        } catch (NumberFormatException e) {
                                            return 0;
                                        }
                                    })
                                    .orElse(0);

                            double calificacionGoogle = (seleccionado.getAverageRating() != null)
                                    ? seleccionado.getAverageRating() : 0.0;

                            return new LibroPersonal(
                                    titulo,
                                    autor,
                                    genero,
                                    aniopublicacion,
                                    "Sin reseña por el momento",
                                    calificacionGoogle);

                        } else {
                            System.out.println("Número fuera de rango: " + idx);
                        }
                    } catch (
                            NumberFormatException e) {
                        System.out.println("Entrada inválida: " + idx);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return librosSeleccionados;
    }
}

