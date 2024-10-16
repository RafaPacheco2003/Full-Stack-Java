package com.mini.trailers.mini_trailers.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    // -------------------------------
    // Métodos para gestionar Películas
    // -------------------------------

    @GetMapping
    public ModelAndView listarPeliculas(@PageableDefault(sort = "titulo", size = 5) Pageable pageable) {
        Page<Pelicula> peliculas = peliculaService.listarPeliculas(pageable);
        return new ModelAndView("admin/index")
                .addObject("peliculas", peliculas)
                .addObject("paginaActual", peliculas.getNumber())
                .addObject("totalPaginas", peliculas.getTotalPages())
                .addObject("totalElementos", peliculas.getTotalElements());
    }

    @GetMapping("/peliculas/nuevo")
    public ModelAndView mostrarFormularioDeNuevaPelicula() {
        List<Genero> generos = generoService.listGenero();
        return new ModelAndView("admin/nueva-pelicula")
                .addObject("pelicula", new Pelicula())
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/guardar")
    public String registrarPelicula(@Validated Pelicula pelicula, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors() || pelicula.getPortada().isEmpty()) {
            if (pelicula.getPortada().isEmpty()) {
                bindingResult.rejectValue("portada", "MultipartNotEmpty");
            }
            model.addAttribute("generos", generoService.listGenero());
            return "admin/nueva-pelicula";
        }

        String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
        pelicula.setRutaPortada(rutaPortada);
        peliculaService.guardarPelicula(pelicula);
        return "redirect:/admin";
    }

    @GetMapping("/peliculas/{id}/editar")
    public ModelAndView editarPelicula(@PathVariable Integer id) {
        Pelicula pelicula = peliculaService.obtenerPeliculaPorId(id);
        List<Genero> generos = generoService.listGenero();
        return new ModelAndView("admin/editar-pelicula")
                .addObject("pelicula", pelicula)
                .addObject("generos", generos);
    }

    @PostMapping("/peliculas/{id}/actualizar")
    public String actualizarPelicula(@PathVariable Integer id, @Validated Pelicula pelicula,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("generos", generoService.listGenero());
            return "admin/editar-pelicula";
        }

        Pelicula peliculaExistente = peliculaService.obtenerPeliculaPorId(id);
        peliculaExistente.setTitulo(pelicula.getTitulo());
        peliculaExistente.setSinopsis(pelicula.getSinopsis());
        peliculaExistente.setGeneros(pelicula.getGeneros());
        peliculaExistente.setFechaEstreno(pelicula.getFechaEstreno());

        if (!pelicula.getPortada().isEmpty()) {
            String rutaPortada = almacenServicio.almacenarArchivo(pelicula.getPortada());
            peliculaExistente.setRutaPortada(rutaPortada);
        }
        peliculaService.actualizarPelicula(peliculaExistente);
        return "redirect:/admin";
    }

    @PostMapping("/peliculas/{id}/eliminar")
    public String eliminarPelicula(@PathVariable Integer id) {
        peliculaService.eliminarPelicula(id);
        return "redirect:/admin";
    }

    // -------------------------------
    // Métodos para gestionar Géneros
    // -------------------------------

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
    public String guardarGenero(@Validated @ModelAttribute Genero genero, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/form-genero";
        }
        generoService.guardarGenero(genero);
        return "redirect:/admin/generos";
    }

    @GetMapping("/genero/editar/{id}")
    public ModelAndView mostrarFormEditarGenero(@PathVariable Integer id) {
        Genero genero = generoService.obtenerGeneroPorId(id);
        return new ModelAndView("admin/editar-genero")
                .addObject("genero", genero);
    }

    @PostMapping("/generos/actualizar")
    public String actualizarGenero(@Validated @ModelAttribute Genero genero, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/editar-genero";
        }
        generoService.actualizarGenero(genero);
        return "redirect:/admin/generos";
    }

    @PostMapping("/generos/eliminar/{id}")
    public String eliminarGenero(@PathVariable Integer id) {
        generoService.eliminarGenero(id);
        return "redirect:/admin/generos";
    }
}
