package com.mini.trailers.mini_trailers.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Repositorio.PeliculaRepositorio;
import com.mini.trailers.mini_trailers.Servicio.GeneroService;
import com.mini.trailers.mini_trailers.Servicio.PeliculaService;

@Controller
public class HomeControlador {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private GeneroService generoService;

    /*
     * Metodos de cliente del inicio
     * 
     */


    
    @GetMapping("")
    public ModelAndView verPaginaDeInicio(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        List<Pelicula> ultimasPeliculas = peliculaService.listarUltimPeliculas();
        List<Pelicula> peliculasAntiguas = peliculaService.ListarPeliculasAntiguas();
        List<Genero> generos = generoService.listGenero(); // Obtén la lista de géneros

        // Combina ambos datos en un solo ModelAndView
        return new ModelAndView("index")
                .addObject("ultimasPeliculas", ultimasPeliculas)
                .addObject("peliculasAntiguas", peliculasAntiguas)
                .addObject("generos", generos); // Añade la lista de géneros al modelo
    }

    /*
     * Vemos detalles de la pelicula
     */

    @GetMapping("peliculas/{id}")
    public ModelAndView mostrarDetallesDePeliculas(@PathVariable Integer id) {
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);

        return new ModelAndView("pelicula").addObject("pelicula", pelicula);
    }


    /*
     * Bucamos peliculas por genero
     */

     @GetMapping("peliculas/genero/{id}")
     public ModelAndView listarPeliculasPorGenero(@PathVariable Integer id) {
         // Obtener el género por su ID
         Genero genero = generoService.obtenerGeneroPorId(id);
     
         // Obtener la lista de películas asociadas al género
         List<Pelicula> peliculasPorGenero = peliculaService.listarPeliculasPorGenero(genero);
        
         List<Genero> generos = generoService.listGenero(); // Obtén la lista de géneros
         // Devolver el ModelAndView con las películas del género
         return new ModelAndView("peliculas_por_genero")
                 .addObject("peliculas", peliculasPorGenero)
                 .addObject("genero", genero)
                 .addObject("generos", generos);
     }




    @GetMapping("/peliculas")
    public ModelAndView listarPeliculas() {
        // Obtener la lista de películas recientes usando el servicio
        List<Pelicula> peliculas = peliculaService.listarTodasLasPelicuas();
        List<Genero> generos = generoService.listGenero(); // Obtén la lista de géneros

        // Crear y devolver el ModelAndView
        return new ModelAndView("peliculas")
                .addObject("peliculas", peliculas)
                .addObject("generos", generos);
    }

    @GetMapping("peliculas/generos/buscar")
    public List<Genero> buscarGeneros(@RequestParam String nombre) {
        return generoService.buscarPorNombre(nombre);
    }

}
