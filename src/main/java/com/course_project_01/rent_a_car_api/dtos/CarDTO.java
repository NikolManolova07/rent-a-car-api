package com.course_project_01.rent_a_car_api.dtos;

public class CarDTO {

    private String model;
    private String location;
    private Double dailyRate;

    public CarDTO(String model, String location, Double dailyRate) {
        this.model = model;
        this.location = location;
        this.dailyRate = dailyRate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }
}