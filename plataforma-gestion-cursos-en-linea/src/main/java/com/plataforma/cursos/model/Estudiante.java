package com.plataforma.cursos.model;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private static int generadorId = 1;
    private int id;
    private String nombre;
    private String email;
    private List<Curso> cursosIncritos = new ArrayList<>();

    public Estudiante(String nombre, String correo) {
        this.id = generadorId++;
        this.nombre = nombre;
        this.email = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }

    public List<Curso> getCursosIncritos() {
        return cursosIncritos;
    }

    public void setCursosIncritos(List<Curso> cursosIncritos) {
        this.cursosIncritos = cursosIncritos;
    }

    public void agregarCurso(Curso curso){
        this.cursosIncritos.add(curso);
    }

    @Override
    public String toString() {
        return "Estudiante [id=" + id + ", nombre=" + nombre + ", email=" + email + "\n\n";
    }

    
}
