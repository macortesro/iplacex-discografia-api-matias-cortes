package org.iplacex.proyectos.discografia.api.artistas;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IArtistaRepository extends MongoRepository<Artista, String>{

}
