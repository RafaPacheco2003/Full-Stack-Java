package com.mini.trailers.mini_trailers.Servicio.Impl;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Repositorio.GeneroRepositorio;
import com.mini.trailers.mini_trailers.Repositorio.PeliculaRepositorio;
import com.mini.trailers.mini_trailers.Servicio.GeneroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GeneroServicieImp implements GeneroService {

    @Autowired
    private GeneroRepositorio generoRepositorio;

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Override
    public List<Genero> listGenero() {
        return generoRepositorio.findAll();
    }

    @Override
    public Genero guardarGenero(Genero genero) {
        return generoRepositorio.save(genero);
    }

    @Override
    public Genero actualizarGenero(Genero genero) {
        return generoRepositorio.save(genero);
    }

    @Override
    public void eliminarGenero(Integer id) {
        Genero genero = obtenerGeneroPorId(id);
        if (genero != null) {
            // Paso 1: Desasociar el género de las películas
            eliminarGeneroDePeliculas(id);

            // Paso 2: Eliminar el género de la tabla `genero`
            generoRepositorio.delete(genero);
        }
    }

    @Override
    public Genero obtenerGeneroPorId(Integer id) {
        return generoRepositorio.findById(id).orElse(null);
    }

    @Override
    public List<Genero> buscarPorNombre(String nombre) {
        return generoRepositorio.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorGeneroId(Integer idGenero) {
        // Este método ya está bien para obtener las películas
        return peliculaRepositorio.findByGeneros_Id(idGenero);
    }

    // Método para eliminar el género de todas las películas
    private void eliminarGeneroDePeliculas(Integer idGenero) {
        // Paso 1: Obtener las películas que tienen este género
        List<Pelicula> peliculas = obtenerPeliculasPorGeneroId(idGenero);
        
        if (!peliculas.isEmpty()) {
            Genero genero = obtenerGeneroPorId(idGenero);  // Buscar el género por ID
            if (genero != null) {
                // Paso 2: Eliminar el género de la lista de géneros de cada película
                for (Pelicula pelicula : peliculas) {
                    pelicula.getGeneros().remove(genero);
                    // Guardar la película sin el género
                    peliculaRepositorio.save(pelicula);
                }
            }
        }
    }
}
