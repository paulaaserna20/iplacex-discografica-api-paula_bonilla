package com.iplacex.discografia.artistas;

import java.util.List;

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
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    private final IArtistaRepository artistaRepository;

    @Autowired
    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(
            @RequestBody Artista artista) {

        Artista nuevoArtista = artistaRepository.save(artista);

        return new ResponseEntity<>(
            nuevoArtista,
            HttpStatus.CREATED
        );
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {

        List<Artista> artistas = artistaRepository.findAll();

        return new ResponseEntity<>(
            artistas,
            HttpStatus.OK
        );
    }

    @GetMapping(
    value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public ResponseEntity<Object> HandleGetArtistaRequest(
        @PathVariable String id) {

    if (!artistaRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }

    Artista artista = artistaRepository.findById(id).orElse(null);

    return new ResponseEntity<>(
        artista,
        HttpStatus.OK
    );
}

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable String id,
            @RequestBody Artista artista) {

        if (!artistaRepository.existsById(id)) {
    return ResponseEntity.notFound().build();
}

        artista._id = id;

        Artista artistaActualizado = artistaRepository.save(artista);

        return new ResponseEntity<>(
            artistaActualizado,
            HttpStatus.OK
        );
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(
            @PathVariable String id) {

       if (!artistaRepository.existsById(id)) {
    return ResponseEntity.notFound().build();
}

        Artista artista = artistaRepository.findById(id).orElse(null);

        artistaRepository.deleteById(id);

        return new ResponseEntity<>(
            artista,
            HttpStatus.OK
        );
    }
}
