package com.plataforma.cursos.model;

import java.time.LocalDate;

public class Inscripcion {
    private Estudiante estudiante;
    private Curso curso;
    private LocalDate fechaInscripcion;

    public Inscripcion(Estudiante estudiante, Curso curso){
        this.estudiante = estudiante;
        this.curso = curso;
        this.fechaInscripcion = LocalDate.now();
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFecha() {
        return fechaInscripcion;
    }

    public void setFecha(LocalDate fecha) {
        this.fechaInscripcion = fecha;
    }

    @Override
    public String toString() {
        return "Inscripcion [estudiante=" + estudiante + ", curso=" + curso + ", fecha=" + fechaInscripcion + "]";
    }

    
}
