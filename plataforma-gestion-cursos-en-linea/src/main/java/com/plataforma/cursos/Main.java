package com.plataforma.cursos;

//TODO: borrar esta importacion
import java.util.List;

import com.plataforma.cursos.model.Curso;
import com.plataforma.cursos.model.Estudiante;
import com.plataforma.cursos.service.CursoService;
import com.plataforma.cursos.service.InscripcionService;

public class Main {
    public static void main(String[] args) {
        CursoService cursos = new CursoService();
        cursos.agregarCurso(123, "fundamentos de programacion",100);
        cursos.agregarCurso(128, "javascript",55);
        cursos.listarCursos();
        cursos.buscarCursoPorCodigo(123);

        InscripcionService inscripcionService = new InscripcionService();
        
        inscripcionService.crearEstudiante(55, "Ramón Suárez", "correo1@correo.com");
        inscripcionService.crearEstudiante(55, "Sandra Pérez", "correo2@correo.com");

        inscripcionService.inscribirEstudianteACurso(123, "Sandra Pérez");
        inscripcionService.inscribirEstudianteACurso(123, "Ramón Suárez");
        inscripcionService.inscribirEstudianteACurso(128, "Sandra Pérez");

        
        //imprime cursos a los que esta inscrito cada estudiante
        System.out.println();
        for(Estudiante estudiante: new InscripcionService().estudiantes){
            System.out.println("nombre estudiante: " + estudiante.getNombre());
            for(Curso curso: estudiante.getCursosIncritos()){
                System.out.println(curso.getNombre());
            }
            System.out.println();
        }

        //imprime estudiantes inscritos a un curso
        for(Estudiante estudiante: cursos.buscarCursoPorCodigo(123).getEstudiantes()){
            System.out.println(estudiante.getNombre());
        }
    }
}