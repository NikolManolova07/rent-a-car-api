package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Offer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OfferRepository {

    List<Offer> findAll();
    Optional<Offer> findById(int offerId);
    List<Offer> findByCustomerId(int customerId);
    Offer create(Offer offer);
    boolean accept(int offerId);
    boolean delete(int offerId);
    boolean isCarInAcceptedOffer(int carId);
    boolean isCustomerInAcceptedOffer(int customerId);
    void archiveUnacceptedOffersForCar(int carId);
    void archiveUnacceptedOffersForCustomer(int customerId);
    boolean isCarAvailableForPeriod(int carId, LocalDate startDate, int days);
}
