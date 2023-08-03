package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.models.Car;
import com.example.userservice.models.Motorcycle;
import com.example.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = userService.getAll();
        return users.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id){
        User user = userService.getUserById(id);

        return user == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user){
        User newUser = userService.save(user);

        return ResponseEntity.ok(newUser);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackGetCars")
    @GetMapping("/cars/{userId}")
    public ResponseEntity<List<Car>> getCars(@PathVariable int userId){
        User user = userService.getUserById(userId);

        if(user == null) return ResponseEntity.notFound().build();

        List<Car> userCars = userService.getCars(userId);

        return ResponseEntity.ok(userCars);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotos(@PathVariable int userId){
        User user = userService.getUserById(userId);

        if(user == null) return ResponseEntity.notFound().build();

        List<Motorcycle> userMotos = userService.getMotos(userId);

        return ResponseEntity.ok(userMotos);
    }

    @CircuitBreaker(name = "carsCB", fallbackMethod = "fallBackSaveCar")
    @PostMapping("/car/{userId}")
    public ResponseEntity<Car> saveCar(
            @PathVariable("userId") int userId, @RequestBody Car car){

        Car newCar = userService.saveCar(userId, car);
        return ResponseEntity.ok(newCar);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMotorcycle")
    @PostMapping("/moto/{userId}")
    public ResponseEntity<Motorcycle> saveMoto(
            @PathVariable("userId") int userId, @RequestBody Motorcycle motorcycle){

        Motorcycle newMotorcycle = userService.saveMotorcycle(userId, motorcycle);
        return ResponseEntity.ok(newMotorcycle);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/all/{userId}")
    public ResponseEntity<Map<String, Object>> listAllVehicles(@PathVariable int userId){

        Map<String, Object> result = userService.getUserVehicles(userId);

        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Car>> fallBackGetCars(
            @PathVariable("userId") int id, RuntimeException exception){

        return new ResponseEntity(
                "The user " + id + " has his vehicles in the garage.",
                HttpStatus.OK);
    }

    private ResponseEntity<Car> fallBackSaveCar(
            @PathVariable("userId") int id,
            @RequestBody Car car,
            RuntimeException exception){

        return new ResponseEntity(
                "The user " + id + " cannot save cars",
                HttpStatus.OK);
    }

    private ResponseEntity<List<Car>> fallBackGetMotos(
            @PathVariable("userId") int id, RuntimeException exception){

        return new ResponseEntity(
                "The user " + id + " cannot get his motorcycles",
                HttpStatus.OK);
    }

    private ResponseEntity<Motorcycle> fallBackSaveMotorcycle(
            @PathVariable("userId") int id,
            @RequestBody Car car,
            RuntimeException exception){

        return new ResponseEntity(
                "The user " + id + " cannot save motorcycles",
                HttpStatus.OK);
    }

    private ResponseEntity<Map<String, Object>> fallBackGetAll(
            @PathVariable("userId") int id,
            RuntimeException exception){

        return new ResponseEntity(
                "The user " + id + " cannot get all of his vehicles", HttpStatus.OK);
    }
}
