package com.mini.trailers.mini_trailers.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mini.trailers.mini_trailers.Entidades.Genero;
//import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;

public interface PeliculaRepositorio  extends JpaRepository<Pelicula, Integer>{
    
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);
    
    // Método para buscar películas por ID de género
    List<Pelicula> findByGeneros_Id(Integer idGenero);

     // Método para buscar películas por género
    @Query("SELECT p FROM Pelicula p JOIN p.generos g WHERE g.nombre = :nombreGenero")
    List<Pelicula> findByGenero(@Param("nombreGenero") String nombreGenero);


    @Query("SELECT COUNT(p) FROM Pelicula p JOIN p.generos g WHERE g = :genero")
    int contarPeliculasPorGenero(@Param("genero") Genero genero);
    
}
