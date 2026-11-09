package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/api")
@CrossOrigin  
public class DiscoController {


    @Autowired 
    private IDiscoRepository discoRepository;

    @Autowired 
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco){

        if(!artistaRepository.existsById(disco.idArtista)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Disco temp = discoRepository.insert(disco);
        return new ResponseEntity<>(temp,HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<List<Disco>>(discos, HttpStatus.OK); 
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> temp = discoRepository.findById(id);
        if(!temp.isPresent()) {
            return new ResponseEntity<Disco>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Disco>(temp.get(), HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtisString) {
        /**
         * Primero validemos que existe el artista
         */
        if(!artistaRepository.existsById(idArtisString)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        /**
         * Pasada la validación anterior, verificamos que existan discos para ese artista
         */
        List<Disco> discos = discoRepository.findByIdArtista(idArtisString);
        return new ResponseEntity<List<Disco>>(discos, HttpStatus.OK);
    }



}
