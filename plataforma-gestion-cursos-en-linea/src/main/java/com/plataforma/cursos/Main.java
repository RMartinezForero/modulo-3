package com.plataforma.cursos;

import com.plataforma.cursos.exception.CursoLlenoException;
import com.plataforma.cursos.exception.CursoNoEncontradoException;
import com.plataforma.cursos.exception.EstudianteNoEncontradoException;
import com.plataforma.cursos.model.Estudiante;
import com.plataforma.cursos.service.CursoService;
import com.plataforma.cursos.service.InscripcionService;

public class Main {
    public static void main(String[] args) {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);

        cursoService.agregarCurso("fundamentos de programacion", 100);
        cursoService.agregarCurso("javascript", 99);
        System.out.println();

        Estudiante estudiante1 = new Estudiante("Ramon Suarez", "correo1@correo.com");
        Estudiante estudiante2 = new Estudiante("Sandra Perez", "correo2@correo.com");

        inscripcionService.inscribirEstudianteAlSistema(estudiante1);
        inscripcionService.inscribirEstudianteAlSistema(estudiante2);

        try {
            inscripcionService.inscribirEstudianteACurso(1, estudiante1);
        } catch (CursoLlenoException | EstudianteNoEncontradoException | CursoNoEncontradoException e) {
            e.printStackTrace();
        }

        try {
            inscripcionService.inscribirEstudianteACurso(2, estudiante1);
        } catch (CursoLlenoException | EstudianteNoEncontradoException | CursoNoEncontradoException e) {
            e.printStackTrace();
        }

        try {
            inscripcionService.inscribirEstudianteACurso(2, estudiante2);
        } catch (CursoLlenoException | EstudianteNoEncontradoException | CursoNoEncontradoException e) {
            e.printStackTrace();
        }

        System.out.println();

        // imprime cursos a los que esta inscrito cada estudiante
        System.out.println(estudiante1.getCursosIncritos());
        System.out.println(estudiante2.getCursosIncritos());

        // imprime estudiantes inscritos a un curso
        System.out.println();
        try {
            for (Estudiante estudiante : cursoService.buscarCursoPorCodigo(1).getEstudiantes()) {
                System.out.println(estudiante.getNombre());
            }
        } catch (CursoNoEncontradoException e) {
            e.printStackTrace();
        }

        System.out.println();

        try {
            for (Estudiante estudiante : cursoService.buscarCursoPorCodigo(2).getEstudiantes()) {
                System.out.println(estudiante.getNombre());
            }
        } catch (CursoNoEncontradoException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("listando inscripciones por estudiante:");
        System.out.println();
        try {
            inscripcionService.listarInscripcionesPorEstudiante(estudiante1);
            inscripcionService.listarInscripcionesPorEstudiante(estudiante2);
        } catch (EstudianteNoEncontradoException e) {
            e.printStackTrace();
        }

    }
}