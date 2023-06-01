package com.example.motoservice.service;

import com.example.motoservice.entity.Motorcycle;
import com.example.motoservice.repository.IMotorcycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorcycleService {

    private IMotorcycleRepository motorcycleRepository;

    @Autowired
    public MotorcycleService(IMotorcycleRepository motorcycleRepository){
        this.motorcycleRepository = motorcycleRepository;
    }

    public List<Motorcycle> getAll(){
        return motorcycleRepository.findAll();
    }

    public Motorcycle getCarById(int id){
        return motorcycleRepository.findById(id)
                .orElse(null);
    }

    public Motorcycle save(Motorcycle motorcycle){
        return motorcycleRepository.save(motorcycle);
    }

    public List<Motorcycle> getCarsByUserId(int userId){
        return motorcycleRepository.findByUserId(userId);
    }
}
