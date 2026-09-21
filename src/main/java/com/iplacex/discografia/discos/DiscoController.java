package com.iplacex.discografia.discos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iplacex.discografia.artistas.IArtistaRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    private final IDiscoRepository discoRepository;
    private final IArtistaRepository artistaRepository;

    @Autowired
    public DiscoController(
            IDiscoRepository discoRepository,
            IArtistaRepository artistaRepository) {

        this.discoRepository = discoRepository;
        this.artistaRepository = artistaRepository;
    }

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(
            @RequestBody Disco disco) {

        if (!artistaRepository.existsById(disco.idArtista)) {
            return ResponseEntity.notFound().build();
        }

        Disco nuevoDisco = discoRepository.save(disco);

        return ResponseEntity
                .status(201)
                .body(nuevoDisco);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {

        List<Disco> discos = discoRepository.findAll();

        return ResponseEntity.ok(discos);
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(
            @PathVariable String id) {

        if (!discoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Disco disco = discoRepository.findById(id).orElse(null);

        return ResponseEntity.ok(disco);
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(
            @PathVariable String id) {

        List<Disco> discos =
                discoRepository.findDiscosByIdArtista(id);

        return ResponseEntity.ok(discos);
    }
}
