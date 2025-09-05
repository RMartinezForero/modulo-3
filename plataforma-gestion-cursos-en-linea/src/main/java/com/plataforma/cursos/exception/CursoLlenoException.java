package com.plataforma.cursos.exception;

public class CursoLlenoException extends Exception {
    
    public CursoLlenoException(String mensaje){
        super(mensaje);
    }

    public CursoLlenoException(String mensaje, Throwable causa){
        super(mensaje, causa);
    }
}
