package com.nataliapavez.libralia.model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String correo;
    private String nombreUsuario;
    private int edad;
    private String descripcion;
    private String listaLecturaActual;
    private String enlaces;

    private List<LibroPersonal> listaLibrosLeidos = new ArrayList<>();
    private List<LibroPersonal> listaLibrosPorLeer = new ArrayList<>();

    // Constructor con lógica de negocio
    public Usuario(String correo, String nombreUsuario, int edad, String descripcion,
                   String listaLecturaActual, String enlaces) {
        this.correo = (correo != null && correo.contains("@")) ? correo : "correo@desconocido.com";
        this.nombreUsuario = (nombreUsuario != null && !nombreUsuario.isBlank()) ? nombreUsuario : "Usuario Anónimo";
        this.edad = (edad > 0 && edad < 100) ? edad : 0;
        this.descripcion = (descripcion != null && !descripcion.isBlank()) ? descripcion : "Sin descripción por el momento";
        this.listaLecturaActual = (listaLecturaActual != null) ? listaLecturaActual : "Sin lectura actual";
        this.enlaces = (enlaces != null) ? enlaces : "Sin enlaces";
    }

    // Constructor vacío
    public Usuario() {}

    // Métodos para agregar libros
    public void agregarLibroLeido(LibroPersonal libro) {
        if (libro != null) {
            listaLibrosLeidos.add(libro);
        } else {
            System.out.println("No se puede agregar un libro nulo");
        }
    }

    public void agregarLibroPorLeer(LibroPersonal libro) {
        if (libro != null) {
            listaLibrosPorLeer.add(libro);
        } else {
            System.out.println("No se puede agregar un libro nulo");
        }
    }

    // Mostrar información del usuario
    public String mostrarInfoUsuario() {
        System.out.println("PERFIL DE USUARIO");
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Edad: " +  edad + " años");
        System.out.println("Sobre mí: " + descripcion);


        System.out.println("Lectura actual: " + listaLecturaActual);

        System.out.println("\nLibros leídos: " + listaLibrosLeidos.size());
        listaLibrosLeidos.forEach((libroPersonal ->
                System.out.println("- " + libroPersonal.getTitulo() + " | " + libroPersonal.getAutor())));

        System.out.println("\nPor leer: " + listaLibrosPorLeer.size());
        listaLibrosPorLeer.forEach(listaLibrosPorLeer ->
                System.out.println("- " + listaLibrosPorLeer.getTitulo() + " | " + listaLibrosPorLeer.getAutor()));

        System.out.println("\nCorreo: " + correo);
        System.out.println("Redes: " + enlaces);
        return "";
    }

    @Override
    public String toString() {
        return "Perfil \n" + this.getNombreUsuario() + "\n" + this.getDescripcion();
    }

    // Getters
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getEnlaces() { return enlaces; }
    public void setEnlaces(String enlaces) { this.enlaces = enlaces; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getListaLecturaActual() { return listaLecturaActual; }
    public void setListaLecturaActual(String listaLecturaActual) { this.listaLecturaActual = listaLecturaActual; }

    public List<LibroPersonal> getListaLibrosLeidos() { return listaLibrosLeidos; }
    public List<LibroPersonal> getListaLibrosPorLeer() { return listaLibrosPorLeer; }
}
