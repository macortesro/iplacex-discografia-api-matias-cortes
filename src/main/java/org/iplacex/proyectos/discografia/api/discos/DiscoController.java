package org.iplacex.proyectos.discografia.api.discos;

import java.util.List;
import java.util.Optional;

import org.iplacex.proyectos.discografia.api.artistas.Artista;
import org.iplacex.proyectos.discografia.api.artistas.IArtistaRepository;
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
@RequestMapping("/api/")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    // POST 

    @PostMapping(
            value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
        )

        public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco){
            // PARA VERIFICAR SI EXISTE EL ARTISTA
            if (!artistaRepo.existsById(disco.idArtista)){
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            // SI YA EXISTE SE INSERTA
            Disco temp = discoRepo.insert(disco);
            return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
        }

         // GET 


    @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<List<Disco>> HandleGetDiscosRequest(){
            List<Disco> discos = discoRepo.findAll();

            return new ResponseEntity<>(discos, null, HttpStatus.OK);
        }

        // GET 


    @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        
        public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id){
            Optional<Disco> temp = discoRepo.findById(id);

            if (!temp.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
        }  

        // GET discos por artista


        @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
            List<Disco> discos = discoRepo.findDiscosByIdArtista(idArtista);
        
            if (discos.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        
            return new ResponseEntity<>(discos, null, HttpStatus.OK);
        }



}
