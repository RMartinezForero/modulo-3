package com.plataforma.cursos.model;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private static int asignadorCodigo = 1;
    private int codigo;
    private String nombre;
    private int capacidad;
    private List<Estudiante> estudiantesInscritos = new ArrayList<>();
    
    public Curso(String nombre, int capacidad){
        this.codigo = asignadorCodigo++;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantesInscritos;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantesInscritos = estudiantes;
    }

    public boolean isCursoLLeno(){
        if(this.capacidad == estudiantesInscritos.size()){
            return true;
        }

        return false;
    }

    //TODO: posible problema, borrar?
    @Override
    public String toString() {
        return "Curso [codigo=" + codigo + ", nombre=" + nombre + ", capacidad=" + capacidad + "]";
    }
    
}


