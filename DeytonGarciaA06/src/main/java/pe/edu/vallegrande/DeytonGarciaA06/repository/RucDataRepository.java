package pe.edu.vallegrande.DeytonGarciaA06.repository;

import pe.edu.vallegrande.DeytonGarciaA06.model.RucData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RucDataRepository extends MongoRepository<RucData, String> {
    List<RucData> findByRucAndActivo(String ruc, boolean activo);
    List<RucData> findByRuc(String ruc);
    List<RucData> findByActivo(boolean activo);
    List<RucData> findAll();
}