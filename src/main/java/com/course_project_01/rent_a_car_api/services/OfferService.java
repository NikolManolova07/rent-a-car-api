package com.course_project_01.rent_a_car_api.services;

import com.course_project_01.rent_a_car_api.dtos.OfferDTO;
import com.course_project_01.rent_a_car_api.entities.Car;
import com.course_project_01.rent_a_car_api.entities.Customer;
import com.course_project_01.rent_a_car_api.entities.Offer;
import com.course_project_01.rent_a_car_api.exceptions.ResourceNotFoundException;
import com.course_project_01.rent_a_car_api.repositories.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final CarService carService;
    private final CustomerService customerService;

    public OfferService(OfferRepository offerRepository, CarService carService, CustomerService customerService) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.customerService = customerService;
    }

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    public Offer getOfferById(int offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id: " + offerId));
    }

    public List<Offer> getOffersByCustomerId(int customerId) {
        if (!customerService.existsCustomerById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return offerRepository.findByCustomerId(customerId);
    }

    public Offer createOffer(OfferDTO createOfferDTO) {
        if (createOfferDTO.getCarId() != null && createOfferDTO.getCustomerId() != null &&
                createOfferDTO.getStartDate() != null && createOfferDTO.getDays() != null) {
            Car car = carService.getCarById(createOfferDTO.getCarId());
            Customer customer = customerService.getCustomerById(createOfferDTO.getCustomerId());

            LocalDate startDate = createOfferDTO.getStartDate();
            int days = createOfferDTO.getDays();

            validateStartDate(startDate);
            validateDays(days);

            if (!offerRepository.isCarAvailableForPeriod(createOfferDTO.getCarId(), startDate, days)) {
                throw new IllegalStateException("The selected car is already booked for the requested period");
            }

            Offer offer = new Offer();
            offer.setCarId(createOfferDTO.getCarId());
            offer.setCustomerId(createOfferDTO.getCustomerId());
            offer.setStartDate(startDate);
            offer.setDays(days);
            offer.setTotalPrice(calculateTotalPrice(createOfferDTO, car, customer));
            return offerRepository.create(offer);
        }
        else {
            throw new IllegalArgumentException("Partial data provided");
        }
    }

    public boolean acceptOffer(int offerId) {
        Offer offer = getOfferById(offerId);
        if (offer.isAccepted()) {
            throw new IllegalStateException("Offer is already accepted");
        }
        if (offer.isArchived()) {
            throw new IllegalStateException("You cannot accept an archived offer");
        }
        if (!offerRepository.isCarAvailableForPeriod(offer.getCarId(), offer.getStartDate(), offer.getDays())) {
            throw new IllegalStateException("The selected car is already booked for the requested period");
        }
        return offerRepository.accept(offerId);
    }

    public boolean deleteOffer(int offerId) {
        offerRepository.findById(offerId)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found with id: " + offerId));
        offerRepository.delete(offerId);
        return true;
    }

    private double calculateTotalPrice(OfferDTO offerDTO, Car car, Customer customer) {
        double basePrice = offerDTO.getDays() * car.getDailyRate();
        int weekendDays = calculateWeekendDays(offerDTO.getStartDate(), offerDTO.getDays());
        if (weekendDays > 0) {
            basePrice = basePrice + car.getDailyRate() * 0.10 * weekendDays;
        }
        if (customer.getHasAccidents()) {
            basePrice += 200;
        }
        return basePrice;
    }

    private int calculateWeekendDays(LocalDate startDate, int days) {
        int weekendDays = 0;
        for (int i = 0; i < days; i++) {
            LocalDate rentalDate = startDate.plusDays(i);
            if (rentalDate.getDayOfWeek() == DayOfWeek.SATURDAY || rentalDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                weekendDays++;
            }
        }
        return weekendDays;
    }

    private void validateStartDate(LocalDate startDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past");
        }
    }

    private void validateDays(int days) {
        if (days <= 0) {
            throw new IllegalArgumentException("Days must be positive");
        }
    }
}
