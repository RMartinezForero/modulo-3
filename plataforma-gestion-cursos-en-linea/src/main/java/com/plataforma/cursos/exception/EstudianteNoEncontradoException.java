package com.plataforma.cursos.exception;

public class EstudianteNoEncontradoException extends Exception {
    public EstudianteNoEncontradoException(String mensaje){
        super(mensaje);
    }

    public EstudianteNoEncontradoException(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
