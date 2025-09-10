package com.plataforma.cursos.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;

import com.plataforma.cursos.exception.CursoNoEncontradoException;
import com.plataforma.cursos.model.Curso;

public class CursoService {
    private static final Logger logger = LogManager.getLogger(CursoService.class);
    private List<Curso> cursosDisponibles = new ArrayList<>();

    public Curso agregarCurso(String nombre, int capacidad) {
        Curso curso = new Curso(nombre, capacidad);
        this.cursosDisponibles.add(curso);
        logger.info("Curso " + nombre + " creado.");
        return curso;
    }

    public void listarCursos() {
        System.out.println(cursosDisponibles);
    }

    public Curso buscarCursoPorCodigo(int codigo) throws CursoNoEncontradoException {
        for (Curso curso : cursosDisponibles) {

            if (curso.getCodigo() == codigo) {
                return curso;
            }
        }
        logger.error("El curso con id: " + codigo + ", no se encuentra registrado.");
        throw new CursoNoEncontradoException("El curso no se encuentra registrado.");
    }

}
