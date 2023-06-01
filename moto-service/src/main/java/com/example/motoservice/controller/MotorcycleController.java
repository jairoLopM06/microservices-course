package com.example.motoservice.controller;

import com.example.motoservice.entity.Motorcycle;
import com.example.motoservice.service.MotorcycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moto")
public class MotorcycleController {

    private MotorcycleService motorcycleService;

    @Autowired
    public MotorcycleController(MotorcycleService motorcycleService){
        this.motorcycleService = motorcycleService;
    }

    @GetMapping
    public ResponseEntity<List<Motorcycle>> listMotorcycles(){
        List<Motorcycle> motorcycles = motorcycleService.getAll();
        return motorcycles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(motorcycles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMoto(@PathVariable int id){
        Motorcycle motorcycle =
                motorcycleService.getCarById(id);

        return motorcycle == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(motorcycle);
    }

    @PostMapping
    public ResponseEntity<Motorcycle> saveMotorcycle(@RequestBody Motorcycle motorcycle){
        Motorcycle newMotorcycle =
                motorcycleService.save(motorcycle);

        return ResponseEntity.ok(newMotorcycle);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> listMotosByUserId(
            @PathVariable int userId){

        List<Motorcycle> motorcycles = motorcycleService.getCarsByUserId(userId);

        return motorcycles.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(motorcycles);
    }
}
