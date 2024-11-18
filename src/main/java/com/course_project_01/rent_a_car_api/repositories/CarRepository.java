package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Optional<Car> findById(int carId);
    List<Car> findAllByLocation(String location);
    Car create(Car car);
    Car update(Car car);
    boolean delete(int carId);
}
