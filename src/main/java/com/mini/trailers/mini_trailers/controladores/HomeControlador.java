package com.mini.trailers.mini_trailers.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Servicio.GeneroService;
import com.mini.trailers.mini_trailers.Servicio.PeliculaService;

@Controller
@RequestMapping("/")
public class HomeControlador {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private GeneroService generoService;

    /**
     * Muestra la página de inicio con las últimas y antiguas películas y una lista de géneros.
     */
    @GetMapping
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        List<Pelicula> ultimasPeliculas = peliculaService.listarUltimPeliculas();
        List<Pelicula> peliculasAntiguas = peliculaService.ListarPeliculasAntiguas();
        List<Genero> generos = generoService.listGenero();

        return new ModelAndView("index")
                .addObject("ultimasPeliculas", ultimasPeliculas)
                .addObject("peliculasAntiguas", peliculasAntiguas)
                .addObject("generos", generos);
    }

    /**
     * Muestra los detalles de una película específica.
     */
    @GetMapping("peliculas/{id}")
    public ModelAndView mostrarDetallesDePelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);
        if (pelicula == null) {
            return new ModelAndView("error/404"); // Redirige a una página de error si no se encuentra la película
        }

        return new ModelAndView("pelicula")
                .addObject("pelicula", pelicula);
    }

    /**
     * Muestra las películas asociadas a un género específico.
     */
    @GetMapping("peliculas/genero/{id}")
    public ModelAndView listarPeliculasPorGenero(@PathVariable Integer id) {
        Genero genero = generoService.obtenerGeneroPorId(id);
        if (genero == null) {
            return new ModelAndView("error/404");
        }

        List<Pelicula> peliculasPorGenero = peliculaService.listarPeliculasPorGenero(genero);
        List<Genero> generos = generoService.listGenero();

        return new ModelAndView("peliculas_por_genero")
                .addObject("peliculas", peliculasPorGenero)
                .addObject("genero", genero)
                .addObject("generos", generos);
    }

    /**
     * Muestra una lista de todas las películas.
     */
    @GetMapping("peliculas")
    public ModelAndView listarPeliculas() {
        List<Pelicula> peliculas = peliculaService.listarTodasLasPelicuas();
        List<Genero> generos = generoService.listGenero();

        return new ModelAndView("peliculas")
                .addObject("peliculas", peliculas)
                .addObject("generos", generos);
    }

    /**
     * Busca géneros por nombre.
     */
    @GetMapping("peliculas/generos/buscar")
    public List<Genero> buscarGeneros(@RequestParam String nombre) {
        return generoService.buscarPorNombre(nombre);
    }
}
