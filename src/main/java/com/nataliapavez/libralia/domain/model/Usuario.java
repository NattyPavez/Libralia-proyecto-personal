package com.nataliapavez.libralia.domain.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String correo;
    private String nombreUsuario;
    private int edad;


    private String descripcion;

    private String enlaces;

    private String avatarUrl = "https://cdn-icons-png.flaticon.com/512/149/149071.png";


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LibroPersonal> libros = new ArrayList<>();


    // Constructor con lógica de negocio
    public Usuario(String correo, String nombreUsuario, int edad, String descripcion,
                   String enlaces) {
        this.correo = (correo != null && correo.contains("@")) ? correo : "correo@desconocido.com";
        this.nombreUsuario = (nombreUsuario != null && !nombreUsuario.isBlank()) ? nombreUsuario : "Usuario Anónimo";
        this.edad = (edad > 0 && edad < 100) ? edad : 0;
        this.descripcion = Optional.ofNullable(descripcion).filter(s -> !s.isBlank()).orElse(null);
        this.enlaces = Optional.ofNullable(enlaces).filter(s -> !s.isBlank()).orElse(null);

    }

    // Constructor vacío
    public Usuario() {
    }

    // Metodo para agregar libros
    public void agregarLibro(LibroPersonal libro) {
        boolean existe = this.libros.stream()
                .anyMatch(l ->
                        l.getTitulo().equalsIgnoreCase(libro.getTitulo()) &&
                                l.getAutor().equalsIgnoreCase(libro.getAutor()) &&
                                l.getEstadoLectura() == libro.getEstadoLectura()
                );
        if (!existe) {
            libro.setUsuario(this);
            this.libros.add(libro);
        }else {
            System.out.println(" Este libro ya está en tu biblioteca. No se volverá a agregar.");
        }
    }

    // Mostrar información del usuario
    public String mostrarInfoUsuario() {
        System.out.println("---------------------");
        System.out.println("PERFIL DE USUARIO");
        System.out.println("---------------------");
        System.out.println("Avatar: " +  this.avatarUrl);
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Edad: " + edad + " años");
        System.out.println("Sobre mí: " + (descripcion != null ? descripcion : "Sin descripción por el momento"));
        System.out.println("\n***Biblioteca personal***");

        if (libros == null || libros.isEmpty()) {
            System.out.println("Aún no tienes libros registrados.");
            return "";
        }
        Map<EstadoLectura, List<LibroPersonal>> librosPorEstado = libros.stream()
                .collect(Collectors.groupingBy(LibroPersonal::getEstadoLectura));

        for (EstadoLectura estado : EstadoLectura.values()) {
            List<LibroPersonal> lista = librosPorEstado.getOrDefault(estado, new ArrayList<>());

            switch (estado) {
                case LEYENDO -> System.out.println("\n Lectura actual (" + lista.size() + "):");
                case LEIDO -> System.out.println("\n Libros leídos (" + lista.size() + "):");
                case POR_LEER -> System.out.println("\n Por leer (" + lista.size() + "):");
            }
            if (lista.isEmpty()) {
                System.out.println(" (sin libros en esta categoría)");
            } else {
                lista.forEach(libro -> {
                    System.out.println("\n- " + libro.getTitulo() + " | " + libro.getAutor());
                    // Mostrar reseña personal si existe
                    if (estado == EstadoLectura.LEIDO) {
                        if (libro.getReseniaPersonal() != null && !libro.getReseniaPersonal().isBlank()) {
                            System.out.println("  Reseña personal: " + libro.getReseniaPersonal());
                        } else {
                            System.out.println("  Reseña personal: No has escrito ninguna reseña");
                        }
                        // Mostrar calificación personal si existe
                        Double calificacion = libro.getCalificacionPersonal();
                        if (calificacion != null) {
                            System.out.printf("  Calificación personal: %.1f estrellas\n", calificacion);
                        } else {
                            System.out.println("  Calificación personal: Sin evaluar");
                        }
                        System.out.println("  Portada: " + libro.getUrlPortada());
                    } else {
                        // Para los demás estados sigue como antes
                        System.out.println("  Descripción: " + libro.getDescripcionGoogle());
                        System.out.println("  Portada: " + libro.getUrlPortada());
                        System.out.println("--------------------------------------------------------------");
                    }
                });

            }
        }
        System.out.println("\nREDES");
        System.out.println("------");
        System.out.println("Correo: " + correo);
        System.out.println("Redes: " + (enlaces != null ? enlaces : "Sin enlaces"));
        return "";
    }

    public List<LibroPersonal> getLibrosPorEstado(EstadoLectura estado) {
        return libros.stream()
                .filter(libro -> libro.getEstadoLectura() == estado)
                .collect(Collectors.toList());
    }


    @Override
    public String toString() {
        return "Perfil \n" + this.getNombreUsuario() + "\n" + this.getDescripcion();
    }

    // Getters and Setters

    public void setLibros(List<LibroPersonal> libros) {
        this.libros = libros;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Optional<String> getDescripcion() {
        return Optional.ofNullable(descripcion);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion != null && !descripcion.isBlank()) ? descripcion : null;
    }

    public Optional<String> getEnlaces() {
        return Optional.ofNullable(enlaces);
    }

    public void setEnlaces(String enlaces) {
        this.enlaces = (enlaces != null && !enlaces.isBlank()) ? enlaces : null;
    }

    public List<LibroPersonal> getLibros() {
        return libros;
    }

    public String getAvatarUrl() {
        return (avatarUrl != null && !avatarUrl.isBlank())
                ? avatarUrl
                : "https://cdn-icons-png.flaticon.com/512/149/149071.png";
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = (avatarUrl != null && !avatarUrl.isBlank())
                ? avatarUrl : "https://cdn-icons-png.flaticon.com/512/149/149071.png";
    }

}
