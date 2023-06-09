package com.example.userservice.feignclients;

import com.example.userservice.models.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "car-serivce", url = "http://localhost:8002", path = "/car")
public interface CarFeignClient {

    @PostMapping
    Car save(@RequestBody Car car);

    @GetMapping("/user/{userId}")
    List<Car> getCar(@PathVariable("userId") int userId);
}
