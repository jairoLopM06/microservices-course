package com.example.motoservice.repository;

import com.example.motoservice.entity.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMotorcycleRepository extends JpaRepository<Motorcycle, Integer> {

    List<Motorcycle> findByUserId(int userId);
}
