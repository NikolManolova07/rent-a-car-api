package com.course_project_01.rent_a_car_api.dtos;

import java.time.LocalDate;

public class OfferDTO {

    private Integer carId;
    private Integer customerId;
    private LocalDate startDate;
    private Integer days;

    public OfferDTO(Integer carId, Integer customerId, LocalDate startDate, Integer days) {
        this.carId = carId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.days = days;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}