package com.mini.trailers.mini_trailers.Servicio.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Repositorio.PeliculaRepositorio;
import com.mini.trailers.mini_trailers.Servicio.AlmacenServicio;
import com.mini.trailers.mini_trailers.Servicio.PeliculaService;

@Service
public class PeliculaServiceImp implements PeliculaService {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private AlmacenServicio almacenServicio;


    @Override
    public Page<Pelicula> listarPeliculas(Pageable pageable) {

        //Funtion to get the list for Order Asc(Titulo)
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Order.asc("titulo")));



        return peliculaRepositorio.findAll(sortedPageable);
    }

    @Override
    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepositorio.save(pelicula);
    }

    @Override
    public Pelicula obtenerPeliculaPorId(Integer id) {
        return peliculaRepositorio.getOne(id); 
    }

  

    public Pelicula actualizarPelicula(Pelicula pelicula) {
        return peliculaRepositorio.save(pelicula);
    }

    public void eliminarPelicula(Integer id){
        Pelicula pelicula = obtenerPeliculaPorId(id);
        if (pelicula != null) {
            peliculaRepositorio.delete(pelicula);
            almacenServicio.eiminarArchivo(pelicula.getRutaPortada());
        }
    }


    


    /*
     * User
     */
    @Override
    public List<Pelicula> listarUltimPeliculas() {
        return peliculaRepositorio.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").descending())).toList();
    }

    @Override
    public List<Pelicula> ListarPeliculasAntiguas() {
        return peliculaRepositorio.findAll(PageRequest.of(0, 4, Sort.by("fechaEstreno").ascending())).toList();
    }

    @Override
    public List<Pelicula> listarPeliculasPorGenero(Genero genero) {
        return peliculaRepositorio.findByGenero(genero.getNombre()); // Usa el nombre del genero
    }

    @Override
    public List<Pelicula> listarTodasLasPelicuas() {
        // TODO Auto-generated method stub
       return peliculaRepositorio.findAll();
    }


    



}
