package com.example.carservice.service;

import com.example.carservice.entity.Car;
import com.example.carservice.repository.ICarRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private ICarRepository carRepository;

    @Autowired
    public CarService(ICarRepository carRepository){
        this.carRepository = carRepository;
    }

    public List<Car> getAll(){
        return carRepository.findAll();
    }

    public Car getCarById(int id){
        return carRepository.findById(id)
                .orElse(null);
    }

    public Car save(Car car){
        return carRepository.save(car);
    }

    public List<Car> getCarsByUserId(int userId){
        return carRepository.findByUserId(userId);
    }
}
