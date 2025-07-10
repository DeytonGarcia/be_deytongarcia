package pe.edu.vallegrande.DeytonGarciaA06.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pe.edu.vallegrande.DeytonGarciaA06.model.RucData;
import pe.edu.vallegrande.DeytonGarciaA06.repository.RucDataRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RucService {

    private static final String API_URL = "https://dniruc.apisperu.com/api/v1/ruc/";
    private final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImRleXRvbi5nYXJjaWFAdmFsbGVncmFuZGUuZWR1LnBlIn0.DBnDFJkEeocMg2Ag6766npoZcRBk8DAipKHa5zta1Nk";

    @Autowired
    private RucDataRepository rucDataRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public RucData registerRucQuery(String ruc) {
        String url = API_URL + ruc + "?token=" + API_TOKEN;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("ruc")) {
            RucData rucData = new RucData();
            rucData.setRuc((String) response.get("ruc"));
            rucData.setRazonSocial((String) response.get("razonSocial"));
            rucData.setCondicion((String) response.get("condicion"));
            rucData.setDireccion((String) response.get("direccion"));
            rucData.setDepartamento((String) response.get("departamento"));
            rucData.setProvincia((String) response.get("provincia"));
            rucData.setDistrito((String) response.get("distrito"));
            rucData.setActivo(true);
            rucData.setLastUpdated(new java.util.Date());
            return rucDataRepository.save(rucData);
        }
        return null;
    }

    public List<RucData> listRucQueries(String ruc, Boolean activo) {
        if (ruc != null && !ruc.isEmpty()) {
            if (activo != null) {
                return rucDataRepository.findByRucAndActivo(ruc, activo);
            } else {
                return rucDataRepository.findByRuc(ruc);
            }
        } else {
            if (activo != null) {
                return rucDataRepository.findByActivo(activo);
            } else {
                return rucDataRepository.findAll();
            }
        }
    }

    public boolean logicallyDeleteRucQuery(String id) {
        Optional<RucData> rucDataOptional = rucDataRepository.findById(id);
        if (rucDataOptional.isPresent()) {
            RucData rucData = rucDataOptional.get();
            rucData.setActivo(false);
            rucDataRepository.save(rucData);
            return true;
        }
        return false;
    }

    public RucData updateRucData(String id, RucData updatedRucData) {
        Optional<RucData> existingRucDataOptional = rucDataRepository.findById(id);
        if (existingRucDataOptional.isPresent()) {
            RucData existingRucData = existingRucDataOptional.get();
            existingRucData.setRuc(updatedRucData.getRuc());
            existingRucData.setRazonSocial(updatedRucData.getRazonSocial());
            existingRucData.setCondicion(updatedRucData.getCondicion());
            existingRucData.setDireccion(updatedRucData.getDireccion());
            existingRucData.setDepartamento(updatedRucData.getDepartamento());
            existingRucData.setProvincia(updatedRucData.getProvincia());
            existingRucData.setDistrito(updatedRucData.getDistrito());
            existingRucData.setActivo(updatedRucData.isActivo()); // Assuming isActivo() is available or will be added back
            existingRucData.setLastUpdated(new java.util.Date());
            return rucDataRepository.save(existingRucData);
        }
        return null;
    }

    public boolean restoreRucQuery(String id) {
        Optional<RucData> rucDataOptional = rucDataRepository.findById(id);
        if (rucDataOptional.isPresent()) {
            RucData rucData = rucDataOptional.get();
            rucData.setActivo(true);
            rucDataRepository.save(rucData);
            return true;
        }
        return false;
    }
}