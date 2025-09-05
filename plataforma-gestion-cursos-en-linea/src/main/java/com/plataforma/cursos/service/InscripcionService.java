package com.plataforma.cursos.service;

import java.util.ArrayList;
import java.util.List;

import com.plataforma.cursos.model.Curso;
import com.plataforma.cursos.model.Estudiante;

public class InscripcionService {
    private List<Estudiante> estudiantes = new ArrayList<>();
    private CursoService cursoService;

    public InscripcionService(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    public void crearEstudiante(int id, String nombre, String email) {
        this.estudiantes.add(new Estudiante(id, nombre, email));
    }

    public void inscribirEstudianteACurso(int codigoCurso, int IdEstudiante) {
        Curso curso = this.cursoService.buscarCursoPorCodigo(codigoCurso);

        if (curso == null)
            return;

        boolean estudianteExiste = false;
        for (Estudiante estudiante : this.estudiantes) {

            if (estudiante.getId() == IdEstudiante) {
                if(curso.getEstudiantes().contains(estudiante)){
                    System.out.println("El estudiante ya se encuentra registrado en este curso.");
                    estudianteExiste = true;
                    break;
                }

                curso.getEstudiantes().add(estudiante);
                estudiante.agregarCurso(curso);
                System.out.println("Estudiante inscrito al curso exitosamente");
                estudianteExiste = true;
                break;
            }
        }

        if (estudianteExiste == false) {
            System.out.println("El estudiante no se encuentra registrado en el sistema.");
        }
    }

}
