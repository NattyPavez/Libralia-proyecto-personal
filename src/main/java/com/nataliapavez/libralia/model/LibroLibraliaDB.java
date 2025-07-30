package com.nataliapavez.libralia.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro_libralia")
public class LibroLibraliaDB {
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
    private String descripcion;

    @Column(columnDefinition = "text")
    private String urlPortada;

    private Integer anioDePublicacion;
    private Double calificacionGoogle;

    @OneToMany(mappedBy = "libroLibralia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LibroPersonal> librosUsuarios;

    // Constructores
    public LibroLibraliaDB() {}

    public LibroLibraliaDB(String titulo, String autor, String genero, String descripcion,
                         String urlPortada, Integer anioDePublicacion, Double calificacionGoogle) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.descripcion = descripcion;
        this.urlPortada = urlPortada;
        this.anioDePublicacion = anioDePublicacion;
        this.calificacionGoogle = calificacionGoogle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlPortada() {
        return urlPortada;
    }

    public void setUrlPortada(String urlPortada) {
        this.urlPortada = urlPortada;
    }

    public Integer getAnioDePublicacion() {
        return anioDePublicacion;
    }

    public void setAnioDePublicacion(Integer anioDePublicacion) {
        this.anioDePublicacion = anioDePublicacion;
    }

    public Double getCalificacionGoogle() {
        return calificacionGoogle;
    }

    public void setCalificacionGoogle(Double calificacionGoogle) {
        this.calificacionGoogle = calificacionGoogle;
    }

    public List<LibroPersonal> getLibrosUsuarios() {
        return librosUsuarios;
    }

    public void setLibrosUsuarios(List<LibroPersonal> librosUsuarios) {
        this.librosUsuarios = librosUsuarios;
    }
}


