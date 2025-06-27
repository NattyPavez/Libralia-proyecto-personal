package com.nataliapavez.libralia.principal;

import com.nataliapavez.libralia.api.ConsumoAPI;
import com.nataliapavez.libralia.dto.ImageLinks;
import com.nataliapavez.libralia.dto.ResultadoGoogle;
import com.nataliapavez.libralia.dto.VolumeInfo;
import com.nataliapavez.libralia.model.EstadoLectura;
import com.nataliapavez.libralia.model.LibroLibraliaDB;
import com.nataliapavez.libralia.model.LibroPersonal;
import com.nataliapavez.libralia.model.Usuario;
import com.nataliapavez.libralia.repository.LibroLibraliaDBRepository;
import com.nataliapavez.libralia.repository.LibroPersonalRepository;
import com.nataliapavez.libralia.repository.UsuarioRepository;
import com.nataliapavez.libralia.service.ConversorLibroService;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Principal {
    Scanner teclado = new Scanner(System.in);

    private final UsuarioRepository usuarioRepositorio;
    private final LibroPersonalRepository libroPersonalRepositorio;
    private final LibroLibraliaDBRepository libroLibraliaDBRepositorio;

    public Principal(UsuarioRepository usuarioRepository,
                     LibroPersonalRepository libroPersonalRepository,
                     LibroLibraliaDBRepository libroLibraliaDBRepository) {
        this.usuarioRepositorio = usuarioRepository;
        this.libroPersonalRepositorio = libroPersonalRepository;
        this.libroLibraliaDBRepositorio = libroLibraliaDBRepository;
    }

    @Transactional
    public void ejecutar() throws IOException, InterruptedException {

        Usuario usuario = crearUsuarioDesdeConsola(teclado); //el usuario construye su perfil

        usuario = usuarioRepositorio.findByIdConLibros(usuario.getId()).orElseThrow();

        System.out.println("_______________________________________________________________________________");
        System.out.println("Bienvenido/a " + usuario.getNombreUsuario() + " a tu biblioteca social Libralia ");
        System.out.println("--------------------------------------------------------------------------------");
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n ¿Qué deseas hacer ahora?");
            System.out.println("""
                    
                    1 - Ver tu perfil.
                    2 - Buscar un libro.
                    3 - Busqueda de libros por genero
                    4 - Agregar calificación y reseña personal a libros leídos
                    5 - Ver TOP 5 mejores evaluados Libralia
                    
                    0 - salir
                    """);
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> {
                    usuario = usuarioRepositorio.findByIdConLibros(usuario.getId()).orElseThrow();
                    usuario.mostrarInfoUsuario();
                }
                case "2" -> agregarLibrosAPerfil(usuario, true);
                case "3" -> buscarLibrosPorGenero();
                case "4" -> agregarPuntuacionYReseniaALeidos(usuario);
                case "5" -> top5MejoresEvaluadosLibralia();
                case "0" -> {
                    System.out.println("Fin de capítulo. Nos vemos en la próxima página.");
                    continuar = false;
                }
                default -> System.out.println("Opción Invalida.");
            }
        }
    }

    // Metodo para construir el perfil del usuario
    private Usuario crearUsuarioDesdeConsola(Scanner teclado) throws IOException, InterruptedException {

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
            System.out.println("¡Wow! Un lector joven, bienvenido a Libralia.\n");
        }

        System.out.println("Cuentanos brevemente algo de tí: ");
        user.setDescripcion(teclado.nextLine());

        System.out.println("¿Qué estás leyendo actualmente?");
        System.out.println("***Puedes buscar un libro para añadirlo como lectura actual.***");

        List<LibroPersonal> lecturaActual = buscarLibro(user, EstadoLectura.LEYENDO);

        for (LibroPersonal libro : lecturaActual) {
            user.agregarLibro(libro);  // asegura la relación bidireccional

        }

        if (!lecturaActual.isEmpty()) {
            System.out.println("\n*Libro(s) agregados a tu escritorio abierto*");
        } else {
            System.out.println("\nNo añadiste ningun libro como lectura actual");
        }
        System.out.println("Libros en la biblioteca del usuario: " + user.getLibros().size());


        //Permitir al usuario buscar libros leidos y por leer./ BUCLE
        boolean seguirAgregando = true;
        while (seguirAgregando) {
            System.out.println("\n¿Quieres seguir agregando libros a tu biblioteca ahora? (si/no)");
            String respuesta = teclado.nextLine().trim().toLowerCase();

            if (respuesta.equals("si")) {
                agregarLibrosAPerfil(user, false);
            } else if (respuesta.equals("no")) {
                seguirAgregando = false;
            } else {
                System.out.println("Respuesta no válida. Intenta nuevamente.");
            }
        }

        System.out.println("¿Quieres compartir algún enlace o red social?");
        user.setEnlaces(teclado.nextLine());

        Usuario userGuardado = usuarioRepositorio.save(user);

        System.out.println("\n*¡Felicidades! Tu perfil ha sido creado*"); // persiste el usuario y sus libros

        return userGuardado;
    }

    // Metodo para agregar libros al perfil evitando duplicar logica
    private void agregarLibrosAPerfil(Usuario usuario, boolean recargarDesdeDB) throws IOException, InterruptedException {
        if (recargarDesdeDB) {
            usuario = usuarioRepositorio.findByIdConLibros(usuario.getId()).orElseThrow();
        }

        Scanner teclado = new Scanner(System.in);

        System.out.println("\n***¿Dónde deseas agregarlos?***");
        System.out.println("1 - Leídos");
        System.out.println("2 - Por leer");
        System.out.println("3 - Lectura Actual (libro abierto)");
        System.out.println("0 - No agregarlos");

        String opcionAgregar = teclado.nextLine().trim();
        //       if (!opcionAgregar.equals("1") && !opcionAgregar.equals("2") && !opcionAgregar.equals("3")) {
//            System.out.println("Ok, puedes volver al menú cuando quieras.");
//            return;
//        }
        // Codigo refactorizado
        if (!opcionAgregar.matches("[1-3]")) {
            System.out.println("Opción inválida. Puedes volver al menú cuando quieras.");
            return;
        }

        EstadoLectura estadoSeleccionado;

        switch (opcionAgregar) {
            case "1" -> estadoSeleccionado = EstadoLectura.LEIDO;
            case "2" -> estadoSeleccionado = EstadoLectura.POR_LEER;
            case "3" -> estadoSeleccionado = EstadoLectura.LEYENDO;
            case "0" -> {
                System.out.println("Ok, puedes volver al menú cuando quieras.");
                return;
            }
            default -> {
                System.out.println("Opción inválida. No se agregaron libros.");
                return;
            }
        }

        // Llamar al metodo buscarLibro con el estado elegido
        List<LibroPersonal> libros = buscarLibro(usuario, estadoSeleccionado);

        if (!libros.isEmpty()) {
            for (LibroPersonal libro : libros) {
                usuario.agregarLibro(libro);
            }
        }

        switch (estadoSeleccionado) {
            case LEIDO -> System.out.println("Libros agregados a tu biblioteca de leídos.");
            case POR_LEER -> System.out.println("Libros agregados a tu lista de deseos (por leer).");
            case LEYENDO -> System.out.println("Libro(s) agregados a tu escritorio abierto.");
        }

        // Guardar el usuario actualizado si ya existe en base de datos
        if (usuario.getId() != null) {
            usuarioRepositorio.save(usuario);
        }

    }


    //buscar libro con Api
    public List<LibroPersonal> buscarLibro(Usuario usuario, EstadoLectura estadoLectura) throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("\nEscribe el título o autor que quieres buscar:");
        String busqueda = teclado.nextLine();

        if (busqueda.trim().isEmpty()) {
            System.out.println("Escribe un nombre válido");
            return new ArrayList<>();
        }

        var consumoApi = new ConsumoAPI();
        var conversor = new ConversorLibroService();
        final String URL_BASE = "https://www.googleapis.com/books/v1/volumes?q=";
        String busquedaFormateada = URLEncoder.encode(busqueda.trim(), StandardCharsets.UTF_8);

        String json = consumoApi.obtenerDatos(URL_BASE + busquedaFormateada);
        var resultado = conversor.convertir(json, ResultadoGoogle.class);

        if (resultado.items() == null || resultado.items().isEmpty()) {
            System.out.println("No se encontraron resultados para: " + busqueda);
            return new ArrayList<>();
        }

        List<VolumeInfo> listaOriginal = resultado.items().stream()
                .map(item -> item.volumeInfo())
                .filter(Objects::nonNull)
                .toList();

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

        List<VolumeInfo> listaFinalSeleccionable = Stream.concat(concidenciasAltas.stream(), otrasSugerencias.stream())
                .limit(10)
                .collect(Collectors.toList());

        System.out.println("\nRESULTADOS ENCONTRADOS:\n");
        AtomicInteger contador = new AtomicInteger(1);
        listaFinalSeleccionable.forEach(info -> {
            String titulo = Optional.ofNullable(info.getTitle()).orElse("Título desconocido");
            String autor = Optional.ofNullable(info.getAuthors())
                    .filter(list -> !list.isEmpty())
                    .map(list -> String.join(", ", list))
                    .orElse("Autor desconocido");
            String descripcion = Optional.ofNullable(info.getDescription())
                    .orElse("Sin descripción disponible");
            String urlPortada = Optional.ofNullable(info.getImageLinks())
                    .map(links -> links.getSmallThumbnail())
                    .orElse("Sin imagen disponible");

            System.out.printf("%d. %s - %s\n", contador.getAndIncrement(), titulo, autor);
            System.out.println("Descripción: " + descripcion);
            System.out.println("Portada: " + urlPortada);
            System.out.println("-------------------------------------------------------------------------");
        });

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

                            String titulo = seleccionado.getTitle();

                            String autor = Optional.ofNullable(seleccionado.getAuthors())
                                    .filter(list -> !list.isEmpty())
                                    .map(list -> list.get(0))
                                    .orElse("Autor desconocido");

                            String genero = Optional.ofNullable(seleccionado.getCategories())
                                    .filter(list -> !list.isEmpty())
                                    .map(list -> list.get(0))
                                    .orElse("Genero no especificado");

                            String descripcionGoogle = Optional.ofNullable(seleccionado.getDescription())
                                    .filter(desc -> !desc.isBlank())
                                    .orElse("Sin descripción disponible");

                            String urlPortada = Optional.ofNullable(seleccionado.getImageLinks())
                                    .map(ImageLinks::getSmallThumbnail)
                                    .orElse("Sin imagen disponible");

                            int aniopublicacion = Optional.ofNullable(seleccionado.getPublishedDate())
                                    .filter(fecha -> fecha.length() >= 4)
                                    .map(fecha -> {
                                        try {
                                            return Integer.parseInt(fecha.substring(0, 4));
                                        } catch (NumberFormatException e) {
                                            return 0;
                                        }
                                    })
                                    .orElse(0);

                            Double calificacionGoogle = (seleccionado.getAverageRating() != null)
                                    ? seleccionado.getAverageRating()
                                    : 0.0;
                            // Buscar si ya existe en la base de datos el libro general
                            LibroLibraliaDB libroExistenteEnDB = libroLibraliaDBRepositorio
                                    .findByTituloIgnoreCaseAndAutorIgnoreCase(titulo, autor)
                                    .orElseGet(() -> {
                                        //si no existe crearlo y guardarlo
                                        LibroLibraliaDB nuevoLibro = new LibroLibraliaDB();
                                        nuevoLibro.setTitulo(titulo);
                                        nuevoLibro.setAutor(autor);
                                        nuevoLibro.setGenero(genero);
                                        nuevoLibro.setDescripcion(descripcionGoogle);
                                        nuevoLibro.setUrlPortada(urlPortada);
                                        nuevoLibro.setAnioDePublicacion(aniopublicacion);
                                        nuevoLibro.setCalificacionGoogle(calificacionGoogle);
                                        return libroLibraliaDBRepositorio.save(nuevoLibro);
                                    });
                            // Crear libro personal y vincularlo al libro general en DB
                            return new LibroPersonal(
                                    titulo,
                                    autor,
                                    genero,
                                    descripcionGoogle,
                                    urlPortada,
                                    aniopublicacion,
                                    "Sin reseña por el momento",
                                    calificacionGoogle,
                                    null,
                                    estadoLectura,
                                    libroExistenteEnDB
                            );
                        } else {
                            System.out.println("Número fuera de rango: " + idx);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida: " + idx);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return librosSeleccionados;
    }

    public void buscarLibrosPorGenero() {
        System.out.println("Ingresa el género que deseas buscar: ");
        var generoBuscado = teclado.nextLine();

        List<LibroPersonal> resultadoBuscarPorGenero = libroPersonalRepositorio.findByGeneroContainingIgnoreCase(generoBuscado);


        if (resultadoBuscarPorGenero.isEmpty()) {
            System.out.println("No se encontraron libros para el genero: " + generoBuscado);
        } else {
            resultadoBuscarPorGenero.forEach(l -> {
                System.out.println("- " + l.getTituloConAutor() + " | " + l.getGenero());
            });
        }

    }

    //metodo para editar puntuacion y reseñas personales
    private void agregarPuntuacionYReseniaALeidos(Usuario usuario) {
        //filtrar solo libros leídos
        List<LibroPersonal> leidos = usuario.getLibros().stream()
                .filter(l -> l.getEstadoLectura() == EstadoLectura.LEIDO)
                .toList();
        // si no hay libros en la biblioteca 'leídos'
        if (leidos.isEmpty()) {
            System.out.println("Agrega líbros a tu lista de 'Leidos' para calificar y reseñar");
            return;
        }
        // mostrar lista enumerada
        System.out.println("Libros Leídos: \n");
        for (int i = 0; i < leidos.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, leidos.get(i).getTituloConAutor());
        }

        System.out.println("\nIngresa el número del libro que deseas calificar");
        int seleccion;
        try {
            seleccion = Integer.parseInt(teclado.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida");
            return;
        }

        if (seleccion < 0 || seleccion >= leidos.size()) {
            System.out.println("Selección fuera de rango.");
            return;
        }

        LibroPersonal libroSeleccionadoParaCalificar = leidos.get(seleccion);
        //calificacion
        System.out.println("Califica este libro desde 0.0 a 5.0 estrellas: ");
        try {
            String inputCalificacion = teclado.nextLine().trim();
            if (inputCalificacion.isEmpty()) {
                //usuario no ingresa nada
                libroSeleccionadoParaCalificar.setCalificacionPersonal(null);
                System.out.println("No se asignó calificación para este libro");
            } else {
                double calificacionUsuario = Double.parseDouble(inputCalificacion);
                if (calificacionUsuario >= 0.0 && calificacionUsuario <= 5.0) {
                    libroSeleccionadoParaCalificar.setCalificacionPersonal(calificacionUsuario);
                    System.out.println("Calificaste con " + calificacionUsuario + " estrellas, el libro: " + libroSeleccionadoParaCalificar.getTituloConAutor());
                } else {
                    System.out.println("Valor fuera de rango, no se realizó la calificación de este libro");
                    libroSeleccionadoParaCalificar.setCalificacionPersonal(null);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Valor Invalido, no calificaste este libro");
            libroSeleccionadoParaCalificar.setCalificacionPersonal(null);
        }
        //reseña
        System.out.println("¿Deseas agregar también una reseña personal? (si/no)");
        String respuesta = teclado.nextLine().trim().toLowerCase();
        if (respuesta.equals("si")) {
            System.out.println("Escribe una reseña: ");
            String resenia = teclado.nextLine();
            libroSeleccionadoParaCalificar.editarResenia(resenia);
            System.out.println("Se agrego tu reseña personal para el lilbro: " + libroSeleccionadoParaCalificar.getTituloConAutor());

        }
        //guardar cambios
        usuarioRepositorio.save(usuario);
    }

    //metodo para mostrar libros mejor evaluados por los usuarios de Libralia
    private void top5MejoresEvaluadosLibralia() {
        System.out.println("TOP 5 de Usuarios Libralia");

        List<LibroPersonal> top5Libros = libroPersonalRepositorio.findTop5ByCalificacionPersonalIsNotNullOrderByCalificacionPersonalDesc();

        top5Libros.forEach(l ->
                System.out.println("\nLibro : " + l.getTitulo() + " | Calificación: " + l.getCalificacionPersonal() + " estrellas"));
    }
}