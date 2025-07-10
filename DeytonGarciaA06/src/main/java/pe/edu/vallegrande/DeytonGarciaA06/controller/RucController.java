package pe.edu.vallegrande.DeytonGarciaA06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.DeytonGarciaA06.model.RucData;
import pe.edu.vallegrande.DeytonGarciaA06.service.RucService;

import java.util.List;

@RestController
@RequestMapping("/api/ruc")
public class RucController {

    @Autowired
    private RucService rucService;

    @PostMapping("/register/{ruc}")
    public ResponseEntity<RucData> registerRuc(@PathVariable String ruc) {
        RucData newRucData = rucService.registerRucQuery(ruc);
        if (newRucData != null) {
            return ResponseEntity.ok(newRucData);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<RucData>> listRuc(@RequestParam(required = false) String ruc, @RequestParam(required = false) String estado) {
        Boolean activo = null;
        if (estado != null) {
            if (estado.equalsIgnoreCase("ACTIVO")) {
                activo = true;
            } else if (estado.equalsIgnoreCase("INACTIVO")) {
                activo = false;
            }
        }
        List<RucData> rucDataList = rucService.listRucQueries(ruc, activo);
        return ResponseEntity.ok(rucDataList);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRuc(@PathVariable String id) {
        if (rucService.logicallyDeleteRucQuery(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RucData> updateRuc(@PathVariable String id, @RequestBody RucData rucData) {
        RucData updatedRuc = rucService.updateRucData(id, rucData);
        if (updatedRuc != null) {
            return ResponseEntity.ok(updatedRuc);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/restore/{id}")
    public ResponseEntity<Void> restoreRuc(@PathVariable String id) {
        if (rucService.restoreRucQuery(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}