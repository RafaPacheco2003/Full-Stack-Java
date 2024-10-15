package com.mini.trailers.mini_trailers.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mini.trailers.mini_trailers.Entidades.Genero;
import com.mini.trailers.mini_trailers.Entidades.Pelicula;
import com.mini.trailers.mini_trailers.Servicio.AlmacenServicio;
import com.mini.trailers.mini_trailers.Servicio.GeneroService;
import com.mini.trailers.mini_trailers.Servicio.PeliculaService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PeliculaService peliculaService;

    @Autowired
    private GeneroService generoService;

    @Autowired
    private AlmacenServicio almacenServicio;

    @GetMapping
    public ModelAndView listarPeliculas(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Pelicula> peliculas = peliculaService.listarPeliculas(pageable);
        return new ModelAndView("admin/index")
                .addObject("peliculas", peliculas)
                .addObject("paginaActual", peliculas.getNumber())
                .addObject("totalPaginas", peliculas.getTotalPages())
                .addObject("totalElementos", peliculas.getTotalElements());
    }

    /*
     * to create movies
     */

    @GetMapping("/peliculas/nuevo")
    public ModelAndView mostrarFormularioDeNuevaPelicula() {
        List<Genero> generos = generoService.listGenero();
        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/guardar")
    public ModelAndView registrarPelicula(@Validated Pelicula pelicula, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
            if (pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }

            List<Genero> generos = generoService.listGenero();
            return new ModelAndView("admin/nueva-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }

        String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);

        peliculaService.guardarPelicula(pelicula);

        return new ModelAndView("redirect:/admin");
    }

    /*
     * The methods to update movies
     */

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView editarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);
        List<Genero> generos = generoService.listGenero();
        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/{id}/actualizar")
    public ModelAndView actualizarPelicula(@PathVariable Integer id, @Validated Pelicula pelicula,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Genero> generos = generoService.listGenero();
            return new ModelAndView("admin/editar-pelicula")
                    .addObject("pelicula", pelicula)
                    .addObject("generos", generos);
        }

        // Obtén la película existente
        Pelicula peliculaExistente = peliculaService.obtenerPeliculaPorId(id);

        // Actualiza los campos de la película existente
        peliculaExistente.setTitulo(pelicula.getTitulo());
        peliculaExistente.setSinopsis(pelicula.getSinopsis());
        peliculaExistente.setGeneros(pelicula.getGeneros());
        peliculaExistente.setFechaEstreno(pelicula.getFechaEstreno());

        if (!pelicula.getPortada().isEmpty()) {
            String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
            peliculaExistente.setRutaPortada(rutaPortada);
        }

        peliculaService.actualizarPelicula(peliculaExistente);

        return new ModelAndView("redirect:/admin");
    }

    /*
     * Methot to delete movie
     * 
     */

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        peliculaService.eliminarPelicula(id);
        return "redirect:/admin";
    }








    
    /*
     * Genero
     */
    @GetMapping("/generos")
    public ModelAndView listarGeneros() {
        List<Genero> generos = generoService.listGenero();
        return new ModelAndView("admin/listar-generos")
                .addObject("generos", generos);
    }

    @GetMapping("/genero/nuevo")
    public ModelAndView mostrarFormGenero() {
        return new ModelAndView("admin/form-genero")
                .addObject("genero", new Genero());
    }

    @PostMapping("/genero/guardar")
    public String guardarGenero(@ModelAttribute Genero genero) {
        generoService.guardarGenero(genero); // Guarda el género
        return "redirect:/admin/generos"; // Redirige a la lista de géneros después de guardar
    }

    // Nuevo: Mostrar formulario para editar un género
@GetMapping("/genero/editar/{id}")
public ModelAndView mostrarFormEditarGenero(@PathVariable("id") Integer id) {
    Genero genero = generoService.obtenerGeneroPorId(id);
    return new ModelAndView("admin/editar-genero")
            .addObject("genero", genero); // Cargar el género existente para editar
}

@PostMapping("/generos/actualizar")
public String actualizarGenero(@ModelAttribute Genero genero) {
    generoService.actualizarGenero(genero); // Actualiza el género
    return "redirect:/admin/generos"; // Redirige a la lista de géneros después de actualizar
}

    @PostMapping("/generos/eliminar/{id}")
    public ModelAndView eliminarGenero(@PathVariable("id") Integer id) {
        generoService.eliminarGenero(id);
        return new ModelAndView("redirect:/admin/generos");
    }
}
