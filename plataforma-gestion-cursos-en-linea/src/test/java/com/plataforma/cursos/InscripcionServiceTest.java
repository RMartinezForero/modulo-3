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
    void resetCodigo() throws Exception {
        Field field = Curso.class.getDeclaredField("asignadorCodigo");
        field.setAccessible(true);
        field.set(null, 1);
    }

    @Test
    void testSiEstudianteExistenteNoRegistraNuevo() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon suarez", "correo@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);
        assertEquals(null, inscripcionService.inscribirEstudianteAlSistema(estudiante));
    }

    @Test
    void testEscribirEstudianteAlSistemaExitosamente() {
        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon suarez", "correo@correo.com");
        assertEquals(estudiante, inscripcionService.inscribirEstudianteAlSistema(estudiante));
    }

    @Test
    void testExcepcionInscribirEstudianteACursoSinEstarEnSistema()
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        int codigoCurso = 1;
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");

        assertThrows(EstudianteNoEncontradoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(codigoCurso, estudiante);
        });
    }

    @Test
    void testExcepcionInscribirEstudianteACursoSiNoExisteCurso()
            throws CursoLlenoException, EstudianteNoEncontradoException, CursoNoEncontradoException {

        InscripcionService inscripcionService = new InscripcionService(new CursoService());
        Estudiante estudiante = new Estudiante("ramon", "correo1@correo.com");
        inscripcionService.inscribirEstudianteAlSistema(estudiante);

        assertThrows(CursoNoEncontradoException.class, () -> {
            inscripcionService.inscribirEstudianteACurso(99, estudiante);
        });
    }

    @Test
    void testIsCursoLLenoLanzaCursoLlenoExeption() {

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
    void testInscribirEstudianteACursoNoLoInscribeSiYaEstaInscrito()
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
    void testInscribirEstudianteACursoAgregaEstudianteAlCurso()
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
    void testInscribirEstudianteACursoAgregaCursoAEstudiante()
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
    void testInscribirEstudianteACursoAgregaInscripcion()
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

    

}
