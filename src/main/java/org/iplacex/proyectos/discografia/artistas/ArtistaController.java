package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin  
public class ArtistaController {

    @Autowired 
    private IArtistaRepository artistaRepository;

    @PostMapping(
        value = "/artista", 
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista temp = artistaRepository.insert(artista);
        return new ResponseEntity<Artista>(temp, HttpStatus.CREATED);
    }


    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> temp = artistaRepository.findById(id);
        if(!temp.isPresent()) {
            return new ResponseEntity<Artista>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Artista>(temp.get(), HttpStatus.OK);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = artistaRepository.findAll();
        return new ResponseEntity<List<Artista>>(artistas, HttpStatus.OK); 
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable("id") String id, @RequestBody Artista artista) {
        if(!artistaRepository.existsById(id)) {
            return new ResponseEntity<Artista>(HttpStatus.NOT_FOUND);
        }
        artista._id = id;
        Artista temp = artistaRepository.save(artista);
        return new ResponseEntity<Artista>(temp, HttpStatus.OK);
    }

    @DeleteMapping(
        value = "/artista/{id}"
    )
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id ) {
        if(!artistaRepository.existsById(id)) {
            return new ResponseEntity<Artista>(HttpStatus.NOT_FOUND);
        }

        Artista temp = artistaRepository.findById(id).get();
        artistaRepository.deleteById(id);
        return new ResponseEntity<Artista>(temp, HttpStatus.OK);
    }
}
