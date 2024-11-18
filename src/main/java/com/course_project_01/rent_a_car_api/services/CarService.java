package com.course_project_01.rent_a_car_api.services;

import com.course_project_01.rent_a_car_api.dtos.CarDTO;
import com.course_project_01.rent_a_car_api.entities.Car;
import com.course_project_01.rent_a_car_api.entities.Customer;
import com.course_project_01.rent_a_car_api.exceptions.ResourceNotFoundException;
import com.course_project_01.rent_a_car_api.repositories.CarRepository;
import com.course_project_01.rent_a_car_api.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final OfferRepository offerRepository;
    private final CustomerService customerService;
    private static final List<String> SUPPORTED_LOCATIONS = List.of("Sofia", "Plovdiv", "Varna", "Burgas");

    public CarService(CarRepository carRepository, OfferRepository offerRepository, CustomerService customerService) {
        this.carRepository = carRepository;
        this.offerRepository = offerRepository;
        this.customerService = customerService;
    }

    public Car getCarById(int carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
    }

    public List<Car> getCarsByLocation(String location) {
        validateLocation(location);
        return carRepository.findAllByLocation(location);
    }

    public List<Car> getCarsByCustomerLocation(int customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        String location = customer.getAddress();
        validateLocation(location);
        return carRepository.findAllByLocation(location);
    }

    public Car createCar(CarDTO createCarDTO) {
        if (createCarDTO.getModel() != null && createCarDTO.getLocation() != null && createCarDTO.getDailyRate() != null) {
            validateLocation(createCarDTO.getLocation());
            validateDailyRate(createCarDTO.getDailyRate());
            Car car = new Car();
            car.setModel(createCarDTO.getModel());
            car.setLocation(createCarDTO.getLocation());
            car.setDailyRate(createCarDTO.getDailyRate());
            return carRepository.create(car);
        }
        else {
            throw new IllegalArgumentException("Partial data provided");
        }
    }

    public Car updateCar(int carId, CarDTO updateCarDTO) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        if (updateCarDTO.getModel() != null) {
            car.setModel(updateCarDTO.getModel());
        }
        if (updateCarDTO.getLocation() != null) {
            validateLocation(updateCarDTO.getLocation());
            car.setLocation(updateCarDTO.getLocation());
        }
        if (updateCarDTO.getDailyRate() != null) {
            validateDailyRate(updateCarDTO.getDailyRate());
            car.setDailyRate(updateCarDTO.getDailyRate());
        }
        return carRepository.update(car);
    }

    public boolean deleteCar(int carId) {
        carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        if (offerRepository.isCarInAcceptedOffer(carId)) {
            throw new IllegalStateException("Cannot delete a car in accepted offers");
        }
        offerRepository.archiveUnacceptedOffersForCar(carId);
        carRepository.delete(carId);
        return true;
    }

    private void validateLocation(String location) {
        if (!SUPPORTED_LOCATIONS.contains(location)) {
            throw new IllegalArgumentException("Location not supported");
        }
    }

    private void validateDailyRate(double dailyRate) {
        if (dailyRate <= 0) {
            throw new IllegalArgumentException("Daily rate must be positive");
        }
    }
}
