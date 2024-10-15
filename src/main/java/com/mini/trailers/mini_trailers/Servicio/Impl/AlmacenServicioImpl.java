package com.mini.trailers.mini_trailers.Servicio.Impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mini.trailers.mini_trailers.Excepciones.AlmacenExcepcion;
import com.mini.trailers.mini_trailers.Excepciones.FileNotFoundException;
import com.mini.trailers.mini_trailers.Servicio.AlmacenServicio;

import jakarta.annotation.PostConstruct;



@Service
public class AlmacenServicioImpl implements AlmacenServicio{

    @Value("${storage.location}")
    private String storageLocation;


    @PostConstruct//Indica que este metodo se va ejecutar cada ves que haya una nueva instancia de esta clase
    @Override
    public void iniciarAlmacenDeArchivo() {
    
        try {
            Files.createDirectories(Paths.get(storageLocation));//Indicar donde vamos almacenar las fotos

        } catch (IOException exception) {
            throw new AlmacenExcepcion("Error al inicializar la ubicacion en el almacen de archivos");
        }
    }

    @Override
    public String almacenarArchivo(MultipartFile archivo) {
        String nombreArchivo = archivo.getOriginalFilename(); // Obtenemos el archivo
    
        if (archivo.isEmpty()) {
            throw new AlmacenExcepcion("No se puede almacenar un archivo vacío");
            
        }



        try {
            InputStream inputStream = archivo.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // Lanzar una excepción personalizada con el mensaje y la causa original
            throw new AlmacenExcepcion("Error al almacenar el archivo " + nombreArchivo, e);
        }

        return nombreArchivo;
    }
    

    @Override
    public Path cargarArchivo(String nombreArchivo) {
       return Paths.get(storageLocation).resolve(nombreArchivo);//almacenamos archivpos
    }

    @Override
    public Resource cargarComoRecurso(String nombreArchivo) {
       try {

        Path archvio = cargarArchivo(nombreArchivo);//Cargamos file
        Resource recurso = new UrlResource(archvio.toUri());


        if(recurso.exists() || recurso.isReadable()) {//Si este recurso existe o es legible
       
            return recurso;
        }else{
            throw new FileNotFoundException("No se pudo encontrar el archivo" +nombreArchivo);
        }
       } catch (MalformedURLException e) {
        // TODO: handle exception
        throw new FileNotFoundException("No se pudo encontrar el archivo" +nombreArchivo, e);
       }
    }

    @Override
    public void eiminarArchivo(String nombreArchivo) {
        Path archvio = cargarArchivo(nombreArchivo);

        try {
            FileSystemUtils.deleteRecursively(archvio);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
