package com.plataforma.cursos;

import com.plataforma.cursos.service.CursoService;
import com.plataforma.cursos.service.InscripcionService;

public class Main {
    public static void main(String[] args) {
        CursoService cursoService = new CursoService();
        InscripcionService inscripcionService = new InscripcionService(cursoService);
    }
}