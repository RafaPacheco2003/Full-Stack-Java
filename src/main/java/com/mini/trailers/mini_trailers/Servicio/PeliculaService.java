package com.mini.trailers.mini_trailers.Servicio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;

public interface PeliculaService {


    //Lis movies
    Page<Pelicula> listarPeliculas(Pageable pageable);//Listamos pelicula
    List<Pelicula> listarUltimPeliculas();//Lista de ultimas peliculas
    List<Pelicula> ListarPeliculasAntiguas();
    List<Pelicula> listarPeliculasPorGenero(Genero genero);
    List<Pelicula> listarTodasLasPelicuas();




    //Crud
    Pelicula guardarPelicula(Pelicula pelicula);
    Pelicula obtenerPeliculaPorId(Integer id);
    public Pelicula actualizarPelicula(Pelicula pelicula);
    void eliminarPelicula(Integer id);



   
}
