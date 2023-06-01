package com.example.carservice.controller;

import com.example.carservice.entity.Car;
import com.example.carservice.service.CarService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService){
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> listCars(){
        List<Car> cars = carService.getAll();
        return cars.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cars);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable int id){
        Car car = carService.getCarById(id);

        return car == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> saveCar(@RequestBody Car car){
        Car newCar = carService.save(car);

        return ResponseEntity.ok(newCar);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Car>> listCarByUserId(
            @PathVariable int userId){

        List<Car> cars = carService.getCarsByUserId(userId);

        return cars.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cars);
    }

}
