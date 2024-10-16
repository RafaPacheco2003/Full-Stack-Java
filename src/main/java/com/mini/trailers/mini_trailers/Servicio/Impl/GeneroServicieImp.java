package com.mini.trailers.mini_trailers.Servicio.Impl;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Excepciones.RecursoNoEncontradoException;
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
        if (!generoRepositorio.existsById(genero.getId())) {
            throw new RecursoNoEncontradoException("Género no encontrado con ID: " + genero.getId());
        }
        return generoRepositorio.save(genero);
    }

    @Override
    public void eliminarGenero(Integer id) {
        Genero genero = obtenerGeneroPorId(id);
        // Si no existe, se lanza la excepción
        if (genero == null) {
            throw new RecursoNoEncontradoException("Género no encontrado con ID: " + id);
        }
        eliminarGeneroDePeliculas(id);
        generoRepositorio.delete(genero);
    }

    @Override
    public Genero obtenerGeneroPorId(Integer id) {
        return generoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Género no encontrado con ID: " + id));
    }

    @Override
    public List<Genero> buscarPorNombre(String nombre) {
        return generoRepositorio.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Pelicula> obtenerPeliculasPorGeneroId(Integer idGenero) {
        return peliculaRepositorio.findByGeneros_Id(idGenero);
    }

    // Método para eliminar el género de todas las películas
    private void eliminarGeneroDePeliculas(Integer idGenero) {
        List<Pelicula> peliculas = obtenerPeliculasPorGeneroId(idGenero);
        Genero genero = obtenerGeneroPorId(idGenero);

        for (Pelicula pelicula : peliculas) {
            pelicula.getGeneros().remove(genero);
            peliculaRepositorio.save(pelicula);
        }
    }
}
