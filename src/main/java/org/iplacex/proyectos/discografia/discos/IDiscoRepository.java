package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IDiscoRepository extends MongoRepository<Disco, String>  {
    List<Disco> findByIdArtista(String idArtista);
}
