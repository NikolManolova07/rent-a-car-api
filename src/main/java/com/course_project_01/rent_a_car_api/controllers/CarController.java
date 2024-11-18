package com.course_project_01.rent_a_car_api.controllers;

import com.course_project_01.rent_a_car_api.dtos.CarDTO;
import com.course_project_01.rent_a_car_api.entities.Car;
import com.course_project_01.rent_a_car_api.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable int carId) {
        Car response = carService.getCarById(carId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAllCarsByLocation(@RequestParam String location) {
        List<Car> response = carService.getCarsByLocation(location);
        return ResponseEntity.ok(response);
    }

    @GetMapping("locationByCustomer/{customerId}")
    public ResponseEntity<List<Car>> getAllCarsByCustomerLocation(@PathVariable int customerId) {
        List<Car> response = carService.getCarsByCustomerLocation(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody CarDTO car) {
        Car response = carService.createCar(car);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Car> updateCar(@PathVariable int carId, @RequestBody CarDTO car) {
        Car response = carService.updateCar(carId, car);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable int carId) {
        boolean response = carService.deleteCar(carId);
        return ResponseEntity.ok(response);
    }
}
