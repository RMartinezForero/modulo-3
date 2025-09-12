package com.plataforma.cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.plataforma.cursos.exception.CursoLlenoException;
import com.plataforma.cursos.exception.CursoNoEncontradoException;
import com.plataforma.cursos.exception.EstudianteNoEncontradoException;
import com.plataforma.cursos.model.Curso;
import com.plataforma.cursos.model.Estudiante;
import com.plataforma.cursos.model.Inscripcion;
import com.plataforma.cursos.service.CursoService;
import com.plataforma.cursos.service.InscripcionService;

public class InscripcionServiceTest {

    @BeforeEach
    void resetearCodigoCursos() throws Exception {
        Field field = Curso.class.getDeclaredField("asignadorCodigo");
        field.setAccessible(true);
        field.set(null, 1);
    }

    @Test
    void inscribirEstudianteAlSistema_estudianteYaExiste_retornaNull() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon suarez", "correo@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        assertEquals(null, inscripcionService.inscribirEstudianteAlSistema(estudiante));
    }

    @Test
    void inscribirEstudianteAlSistema_estudianteNuevo_retornaEstudiante() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon suarez", "correo@correo.com");
        assertEquals(estudiante, inscripcionService.inscribirEstudianteAlSistema(estudiante));
    }

    @Test
    void inscribirEstudianteAlSistema_estudianteNull_retornaNull() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = null;
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        assertEquals(null, inscripcionService.inscribirEstudianteAlSistema(estudiante));
    }

    @Test
    void inscribirEstudianteACurso_estudianteNoEnSistema_lanzaExcepcion() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        int codigoCurso = 1;
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");

        assertThrows(EstudianteNoEncontradoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(codigoCurso, estudiante);
        });
    }

    @Test
    void inscribirEstudianteACurso_cursoNoExiste_lanzaExcepcion() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);

        assertThrows(CursoNoEncontradoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(99, estudiante);
        });
    }

    @Test
    void inscribirEstudianteACurso_cursoLleno_lanzaExcepcion() {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        cursoService.agregarCurso("biologia", 0);
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);

        assertThrows(CursoLlenoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(1, estudiante);
        });
    }

    @Test
    void inscribirEstudianteACurso_estudianteNull_lanzaExcepcion() {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        cursoService.agregarCurso("biologia", 100);

        Estudiante estudiante = null;
        inscripcionService.inscribirEstudianteAlSistema(estudiante);

        assertThrows(EstudianteNoEncontradoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(1, estudiante);
        });
    }

    @Test
    void inscribirEstudianteACurso_estudianteYaInscrito_noDuplicaInscripcion() 
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        cursoService.agregarCurso("biologia", 100);
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);

        assertEquals(1, estudiante.getCursosIncritos().size());
    }

    @Test
    void inscribirEstudianteACurso_estudianteAgregadoAListaCurso() 
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        Curso curso = cursoService.agregarCurso("biologia", 100);
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);

        assertEquals(curso.getEstudiantes().get(0).getCorreo(), estudiante.getCorreo());
    }

    @Test
    void inscribirEstudianteACurso_cursoAgregadoAListaEstudiante() 
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        Curso curso = cursoService.agregarCurso("biologia", 100);
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);

        assertEquals(curso.getCodigo(), estudiante.getCursosIncritos().get(0).getCodigo());
    }

    @Test
    void inscribirEstudianteACurso_inscripcionRegistradaEnSistema() 
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        Curso curso = cursoService.agregarCurso("biologia", 100);
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);
        List<Inscripcion> list = inscripcionService.getInscripciones();

        assertEquals(list.get(0).getEstudiante().getCorreo(), estudiante.getCorreo());
        assertEquals(list.get(0).getCurso().getCodigo(), estudiante.getCursosIncritos().get(0).getCodigo());
        assertEquals(1, list.size());
    }

    @Test
    void listarInscripcionesPorEstudiante_estudianteNull_lanzaExcepcion() {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);

        assertThrows(EstudianteNoEncontradoException.class, () -> {
            inscripcionService.listarInscripcionesPorEstudiante(null);
        });
    }

    @Test
    void listarInscripcionesPorEstudiante_estudianteNoTieneCursos_lanzaExcepcion() {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);

        Estudiante estudiante = new Estudiante("ramon", "correo@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);

        assertThrows(EstudianteNoEncontradoException.class, () -> {
            inscripcionService.listarInscripcionesPorEstudiante(estudiante);
        });
    }

    @Test
    void listarInscripcionesPorEstudiante_estudianteConCursos_retornaListaCursos() 
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
        cursoService.agregarCurso("biologia", 100);
        Estudiante estudiante = new Estudiante("ramon", "correo@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        inscripcionService.inscribirEstudianteACurso(1, estudiante);
        List<Curso> cursosEstudiante = inscripcionService.listarInscripcionesPorEstudiante(estudiante);

        assertEquals("biologia", cursosEstudiante.get(0).getNombre());
        assertEquals(100, cursosEstudiante.get(0).getCapacidad());
        assertEquals(1, cursosEstudiante.get(0).getCodigo());
        assertEquals(1, cursosEstudiante.size());
    }
}
