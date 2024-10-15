package com.mini.trailers.mini_trailers.Servicio;

import java.util.List;
import java.util.Map;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;

public interface GeneroService {


    /*
     * CRUD
     */
    List <Genero> listGenero();
    Genero guardarGenero(Genero genero);
    Genero obtenerGeneroPorId(Integer id);
    Genero actualizarGenero(Genero genero);
    void eliminarGenero(Integer id);


    

    // Nuevo método para obtener las películas por ID de género
    List<Pelicula> obtenerPeliculasPorGeneroId(Integer idGenero);

    


    
    
    
    List<Genero> buscarPorNombre(String nombre);


}
