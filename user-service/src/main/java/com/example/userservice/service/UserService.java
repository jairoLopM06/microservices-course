package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.feignclients.CarFeignClient;
import com.example.userservice.feignclients.MotoFeignClient;
import com.example.userservice.models.Car;
import com.example.userservice.models.Motorcycle;
import com.example.userservice.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private IUserRepository userRepository;
    private RestTemplate restTemplate;
    private CarFeignClient carFeignClient;
    private MotoFeignClient motoFeignClient;
    @Autowired
    public UserService(
            RestTemplate restTemplate,
            IUserRepository userRepository,
            CarFeignClient carFeignClient,
            MotoFeignClient motoFeignClient){
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.carFeignClient = carFeignClient;
        this.motoFeignClient = motoFeignClient;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(int id){
        return userRepository.findById(id)
                .orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List getCars(int userId){
        return restTemplate.getForObject(
                "http://car-service/car/user/" + userId,
                List.class);
    }

    public List getMotos(int userId){
        return restTemplate.getForObject(
                "http://moto-service/moto/user/" + userId,
                List.class);
    }

    public Car saveCar(int userId, Car car){
        car.setUserId(userId);
        return carFeignClient.save(car);
    }

    public Motorcycle saveMotorcycle(int userId, Motorcycle motorcycle){
        motorcycle.setUserId(userId);
        return motoFeignClient.save(motorcycle);
    }

    public Map<String, Object> getUserVehicles(int userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            result.put("Message", "User does not exists");
            return result;
        }

        result.put("User", user);
        List<Car> cars = carFeignClient.getCar(userId);

        if(cars.isEmpty()){
            result.put("Message", "User does not have cars");
        } else {
            result.put("Cars", cars);
        }

        List<Motorcycle> motorcycles = motoFeignClient.getMotorcycles(userId);

        if(motorcycles.isEmpty()){
            result.put("Message", "User does not have motorcycles");
        } else {
            result.put("Motorcycles", motorcycles);
        }

        return result;
    }
}
