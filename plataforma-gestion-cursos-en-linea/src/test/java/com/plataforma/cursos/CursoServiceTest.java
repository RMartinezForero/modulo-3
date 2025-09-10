package com.plataforma.cursos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.plataforma.cursos.exception.CursoNoEncontradoException;
import com.plataforma.cursos.model.Curso;
import com.plataforma.cursos.service.CursoService;

public class CursoServiceTest {
    @BeforeEach
    void resetCodigo() throws Exception {
        Field field = Curso.class.getDeclaredField("asignadorCodigo");
        field.setAccessible(true);
        field.set(null, 1);
    }

    @Test
    void testCrearCurso() throws CursoNoEncontradoException {
        CursoService cursoService = new CursoService();
        Curso curso = cursoService.agregarCurso("biologia", 100);

        assertEquals(curso.getNombre(), "biologia");
        assertEquals(curso.getCapacidad(), 100);
        assertEquals(curso.getCodigo(), 1);
    }

    @Test
    void testBuscarCursoPorCodigo() throws CursoNoEncontradoException{
        CursoService cursoService = new CursoService();
        Curso curso = cursoService.agregarCurso("natacion", 88);
        assertEquals(curso, cursoService.buscarCursoPorCodigo(1));
    }

    @Test
    void testLanzaExcepcionSiCursoNoExiste() throws CursoNoEncontradoException {
        CursoService cursoService = new CursoService();
        cursoService.agregarCurso("arte", 99);

        assertThrows(CursoNoEncontradoException.class, () -> {
            cursoService.buscarCursoPorCodigo(0);
        });
        assertThrows(CursoNoEncontradoException.class, () -> {
            cursoService.buscarCursoPorCodigo(2);
        });
    }

}
