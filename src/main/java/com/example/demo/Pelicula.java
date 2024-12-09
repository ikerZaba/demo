package com.example.demo;

public class Pelicula {
    private String id;
    private String titulo;
    private int año;
    private String trama;

    public Pelicula(String id, String titulo, int año, String descripcion){
        this.id = id;
        this.titulo = titulo;
        this.año = año;
        this.trama = descripcion;
    }
    
    public Pelicula(String id, String titulo, int año) {
        this.id = id;
        this.titulo = titulo;
        this.año = año;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String descripcion) {
        this.trama = descripcion;
    }

    

    
}
