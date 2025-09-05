package com.plataforma.cursos.service;

import java.util.ArrayList;
import java.util.List;

import com.plataforma.cursos.model.Curso;

public class CursoService {
    private List<Curso> cursosDisponibles = new ArrayList<>();

    public void agregarCurso(String nombre, int capacidad) {
        this.cursosDisponibles.add(new Curso(nombre, capacidad));
    }

    public void listarCursos() {
        System.out.println(cursosDisponibles);
    }

    public Curso buscarCursoPorCodigo(int codigo) {
        for (Curso curso : cursosDisponibles) {

            if (curso.getCodigo() == codigo) {
                return curso;
            }
        }
        System.out.println("El curso no se encuentra registrado.");
        return null;
    }

}
