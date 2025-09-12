package com.plataforma.cursos.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.*;
import com.plataforma.cursos.exception.CursoLlenoException;
import com.plataforma.cursos.exception.CursoNoEncontradoException;
import com.plataforma.cursos.exception.EstudianteNoEncontradoException;
import com.plataforma.cursos.model.Curso;
import com.plataforma.cursos.model.Estudiante;
import com.plataforma.cursos.model.Inscripcion;

public class InscripcionService {
    private static final Logger logger = LogManager.getLogger(InscripcionService.class);
    private List<Inscripcion> inscripciones;
    private List<Estudiante> estudiantes;
    private CursoService cursoService;

    public InscripcionService(CursoService cursoService) {
        this.cursoService = cursoService;
        this.inscripciones = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
    }

    private boolean estudianteExiste(Estudiante estudiante) {
        if (estudiante == null) {
            return false;
        }

        for (Estudiante e : estudiantes) {

            if (e.getCorreo().equals(estudiante.getCorreo())) {
                return true;
            }
        }
        return false;
    }

    public Estudiante inscribirEstudianteAlSistema(Estudiante estudiante) {

        if (estudiante == null) {
            logger.warn("El estudiante " + estudiante + " no tiene datos. No puede inscribirse al sistema.");
            System.out.println("El estudiante " + estudiante + " no tiene datos. No puede inscribirse al sistema.");
            return null;
        }

        if (estudianteExiste(estudiante)) {
            System.out.println("El estudiante ya se encuentra registrado en el sistema.");
            logger.warn("El estudiante " + estudiante.getNombre() + " ya se encuentra registrado en el sistema.");
            return null;
        }

        estudiantes.add(estudiante);
        System.out.println("Estudiante inscrito exitosamente.");
        logger.info("El estudiante " + estudiante.getNombre() + " fue inscrito.");
        return estudiante;
    }

    public void inscribirEstudianteACurso(int codigoCurso, Estudiante estudiante)
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        // 1. comprueba si existe el estudiante en el sistema
        if (!estudianteExiste(estudiante)) {
            logger.error("El estudiante no se encuentra registrado en el sistema.");
            throw new EstudianteNoEncontradoException("El estudiante no se encuentra registrado en el sistema.");
        }

        // 2. comprueba si el curso existe
        Curso cursoDestino = this.cursoService.buscarCursoPorCodigo(codigoCurso);

        // 3. comprueba si hay cupo en el curso
        if (cursoDestino.isCursoLLeno()) {
            logger.error("No hay capacidad para mas estudiantes en el curso " + cursoDestino.getNombre());
            throw new CursoLlenoException("No hay capacidad para más estudiantes en este curso.");
        }

        // 4. comprueba si el estudiante ya esta inscrito en el curso
        Estudiante estudianteInscrito;
        Curso cursoInscrito;

        for (Inscripcion inscripcion : this.inscripciones) {

            estudianteInscrito = inscripcion.getEstudiante();
            cursoInscrito = inscripcion.getCurso();

            if (estudianteInscrito != null && cursoInscrito != null) {

                if (estudianteInscrito.getCorreo().equals(estudiante.getCorreo())
                        && cursoInscrito.getCodigo() == codigoCurso) {

                    logger.warn("El estudiante " + estudiante.getNombre() + " ya se encuentra registrado en el curso "
                            + inscripcion.getCurso().getNombre());
                    System.out.println("El estudiante ya se encuentra registrado en este curso.");
                    return;
                }
            }

        }

        cursoDestino.getEstudiantes().add(estudiante);
        estudiante.agregarCurso(cursoDestino);
        inscripciones.add(new Inscripcion(estudiante, cursoDestino));
        logger.info("Estudiante " + estudiante.getNombre() + " inscrito al curso " + cursoDestino.getNombre()
                + " exitosamente");
    }

    public List<Curso> listarInscripcionesPorEstudiante(Estudiante estudiante)
            throws EstudianteNoEncontradoException {

        if (estudiante == null) {
            logger.error("El estudiante no tiene inscripciones.");
            throw new EstudianteNoEncontradoException("El estudiante no tiene inscripciones.");
        }

        List<Curso> cursos = new ArrayList<>();

        for (Inscripcion inscripcion : this.inscripciones) {
            Estudiante estudianteInscrito = inscripcion.getEstudiante();

            if (estudianteInscrito != null &&
                    estudiante.getCorreo().equals(estudianteInscrito.getCorreo()) &&
                    inscripcion.getCurso() != null) {

                cursos.add(inscripcion.getCurso());
            }
        }

        if (cursos.isEmpty()) {
            logger.error("El estudiante " + estudiante.getNombre() + " no tiene inscripciones.");
            throw new EstudianteNoEncontradoException(
                    "El estudiante " + estudiante.getNombre() + " no tiene inscripciones.");
        }

        return cursos;
    }

    public List<Inscripcion> getInscripciones() {
        return new ArrayList<>(this.inscripciones);
    }
}
