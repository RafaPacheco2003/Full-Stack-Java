package com.mini.trailers.mini_trailers.Excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileNotFoundException(String mesaje){
        super(mesaje);
    }

    public FileNotFoundException(String mesaje, Throwable excepcion){
        super(mesaje, excepcion);
    }
    
}
