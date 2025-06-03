package com.nataliapavez.libralia.model;

public class LibroPersonal {
    private String titulo;
    private String autor;
    private String genero;
    private int anioDePublicacion;
    private String reseniaPersonal;
    private double calificacionGoogle;
    private double calificacionPersonal;

    //constructor con validaciones
    public LibroPersonal(String titulo, String autor, String genero, int anioDePublicacion, String reseniaPersonal, double calificacion) {
        this.titulo = (titulo == null || titulo.isBlank()) ? "Titulo desconocido" : titulo;
        this.autor = (autor == null || autor.isBlank()) ? "Autor desconocido" : autor;
        this.genero = genero != null ? genero : "Género no especificado";
        this.anioDePublicacion = (anioDePublicacion > 0) ? anioDePublicacion : 0;
        this.reseniaPersonal = (reseniaPersonal != null || !reseniaPersonal.isBlank()) ? reseniaPersonal : "Sin reseña personal por el momento";
        this.calificacionGoogle = (getCalificacionGoogle() >= 0 && calificacionGoogle <= 5) ? calificacionGoogle : 0.0;
        this.calificacionPersonal = 0.0;

    }

    // metodo para mostrar titulo por autor
    public String getTituloConAutor() {
        return titulo + " (" + autor + ").";
    }

    // metodo para calificar el libro
    public void calificar(double calificacionPersonal) {
        if (calificacionPersonal >= 0 && calificacionPersonal <= 5) {
            this.calificacionPersonal = calificacionPersonal;
        } else {
            System.out.println("Tu valoración debe estar entre 0 y 5 estrellas");
        }

    }

    // metodo para editar reseña
    public String editarResenia(String nuevaResenia) {
        this.reseniaPersonal = nuevaResenia;
        return nuevaResenia;
    }
    // mostrar todos los datos del libro

    public String mostrarInfoGeneral() {
        System.out.println("Título: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Género: " + genero);
        System.out.println("Año: " + anioDePublicacion);
        System.out.println("Reseña Personal: " + reseniaPersonal);

        return "";
    }

// getters y setters

    public double getCalificacionGoogle() {
        return calificacionGoogle;
    }

    public void setCalificacionGoogle(double calificacionGoogle) {
        this.calificacionGoogle = calificacionGoogle;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAnioDePublicacion() {
        return anioDePublicacion;
    }

    public void setAnioDePublicacion(int anioDePublicacion) {
        this.anioDePublicacion = anioDePublicacion;
    }

    public String getReseniaPersonal() {
        return reseniaPersonal;
    }

    public void setReseniaPersonal(String reseniaPersonal) {
        this.reseniaPersonal = reseniaPersonal;
    }

    public double getCalificacionPersonal() {
        return calificacionPersonal;
    }

    public void setCalificacion(double calificacion) {
        this.calificacionPersonal = calificacion;
    }

}


