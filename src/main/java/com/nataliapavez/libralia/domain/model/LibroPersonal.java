package com.nataliapavez.libralia.domain.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class LibroPersonal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String titulo;

    @Column(columnDefinition = "text")
    private String autor;

    @Column(columnDefinition = "text")
    private String genero;

    @Column(columnDefinition = "text")
    private String descripcionGoogle;

    @Column(columnDefinition = "text")
    private String urlPortada;

    private int anioDePublicacion;

    @Column(columnDefinition = "text")
    private String reseniaPersonal;

    private Double calificacionGoogle;
    private Double calificacionPersonal;

    @Enumerated(EnumType.STRING)
    private EstadoLectura estadoLectura;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Libro_Libralia_id")
    private LibroLibraliaDB libroLibralia;

    //constructor con validaciones
    public LibroPersonal(String titulo, String autor, String genero, String descripcionGoogle,
                         String urlPortada, int anioDePublicacion, String reseniaPersonal,
                         Double calificacionGoogle, Double calificacionPersonal,
                         EstadoLectura estadoLectura, LibroLibraliaDB libroLibralia) {
        this.titulo = (titulo == null || titulo.isBlank()) ? "Titulo desconocido" : titulo;
        this.autor = (autor == null || autor.isBlank()) ? "Autor desconocido" : autor;
        this.genero = genero != null ? genero : "Género no especificado";
        this.descripcionGoogle = descripcionGoogle != null ? descripcionGoogle : "Sin descripción disponible";
        this.urlPortada = urlPortada != null ? urlPortada : "Sin imagen disponible";
        this.anioDePublicacion = (anioDePublicacion > 0) ? anioDePublicacion : 0;
        this.reseniaPersonal = (reseniaPersonal != null && !reseniaPersonal.isBlank()) ? reseniaPersonal : "Sin reseña personal por el momento";
        this.calificacionGoogle = (calificacionGoogle == null || calificacionGoogle == 0.0) ? null : calificacionGoogle;
        this.calificacionPersonal = calificacionPersonal;
        this.estadoLectura = estadoLectura != null ? estadoLectura : EstadoLectura.POR_LEER;
        this.libroLibralia = libroLibralia;
    }

    public LibroPersonal() {
        // Constructor vacío requerido por JPA
    }

    public LibroPersonal(LibroLibraliaDB libroBase, EstadoLectura estado) {
        this.titulo = libroBase.getTitulo();
        this.autor = libroBase.getAutor();
        this.genero = libroBase.getGenero();
        this.descripcionGoogle = libroBase.getDescripcion();
        this.urlPortada = libroBase.getUrlPortada();
        this.anioDePublicacion = libroBase.getAnioDePublicacion() != null ? libroBase.getAnioDePublicacion() : 0;
        this.calificacionGoogle = libroBase.getCalificacionGoogle();
        this.estadoLectura = estado;
        this.libroLibralia = libroBase;
        this.reseniaPersonal = "Sin reseña personal por el momento";
        this.calificacionPersonal = 0.0;
    }


    // metodo para mostrar titulo por autor
    public String getTituloConAutor() {
        return titulo + " (" + autor + ").";
    }

    // metodo para calificar el libro
    public void calificar(Double calificacionPersonal) {
        if (calificacionPersonal >= 0 && calificacionPersonal <= 5) {
            this.calificacionPersonal = calificacionPersonal;
        } else {
            System.out.println("Tu valoración debe estar entre 0 y 5 estrellas");
        }

    }

    // metodo para editar reseña
    public void editarResenia(String nuevaResenia) {
        this.reseniaPersonal = nuevaResenia;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcionGoogle() {
        return descripcionGoogle;
    }

    public void setDescripcionGoogle(String descripcionGoogle) {
        this.descripcionGoogle = descripcionGoogle;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    public long getId() {
        return id;
    }

    public void setCalificacionPersonal(Double calificacionPersonal) {
        this.calificacionPersonal = calificacionPersonal;
    }

    public EstadoLectura getEstadoLectura() {
        return estadoLectura;
    }

    public void setEstadoLectura(EstadoLectura estadoLectura) {
        this.estadoLectura = estadoLectura;
    }

    public Double getCalificacionGoogle() {
        return (calificacionGoogle == null || calificacionGoogle == 0.0) ? null : calificacionGoogle;
    }

    public void setCalificacionGoogle(Double calificacionGoogle) {
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

    public Double getCalificacionPersonal() {
        return calificacionPersonal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}


