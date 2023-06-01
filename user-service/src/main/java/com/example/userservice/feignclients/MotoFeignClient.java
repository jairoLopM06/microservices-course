package com.example.userservice.feignclients;

import com.example.userservice.models.Motorcycle;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "moto-serivce", url = "http://localhost:8083", path = "/moto")
public interface MotoFeignClient {

    @PostMapping
    Motorcycle save(@RequestBody Motorcycle motorcycle);

    @GetMapping("/user/{userId}")
    List<Motorcycle> getMotorcycles(@PathVariable("userId") int userId);
}
