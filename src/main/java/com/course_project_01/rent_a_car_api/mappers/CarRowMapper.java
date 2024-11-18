package com.course_project_01.rent_a_car_api.mappers;

import com.course_project_01.rent_a_car_api.entities.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car = new Car();
        car.setCarId(rs.getInt("car_id"));
        car.setModel(rs.getString("model"));
        car.setLocation(rs.getString("location"));
        car.setDailyRate(rs.getDouble("daily_rate"));
        return car;
    }
}
