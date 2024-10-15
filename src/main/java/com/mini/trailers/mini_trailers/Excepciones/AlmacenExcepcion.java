package com.mini.trailers.mini_trailers.Excepciones;

public class AlmacenExcepcion extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public AlmacenExcepcion(String mesaje){
        super(mesaje);
    }

    public AlmacenExcepcion(String mesaje, Throwable excepcion){
        super(mesaje, excepcion);
    }
    
}
