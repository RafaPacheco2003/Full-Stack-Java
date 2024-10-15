package com.mini.trailers.mini_trailers.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.trailers.mini_trailers.Entidades.Genero;

public interface GeneroRepositorio  extends JpaRepository<Genero, Integer>{
    List<Genero> findByNombreContainingIgnoreCase(String nombre);

    
    
}
