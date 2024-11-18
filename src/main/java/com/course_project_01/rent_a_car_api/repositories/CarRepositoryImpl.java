package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Car;
import com.course_project_01.rent_a_car_api.mappers.CarRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepositoryImpl implements CarRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CarRowMapper carRowMapper = new CarRowMapper();

    public CarRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Car> findById(int carId) {
        String selectSql = "SELECT * FROM cars WHERE car_id = ? AND is_active = 1";
        List<Car> cars = jdbcTemplate.query(selectSql, carRowMapper, carId);
        return cars.stream().findFirst();
    }

    @Override
    public List<Car> findAllByLocation(String location) {
        String selectSql = "SELECT * FROM cars WHERE location = ? AND is_active = 1";
        return jdbcTemplate.query(selectSql, carRowMapper, location);
    }

    @Override
    public Car create(Car car) {
        String insertSql = "INSERT INTO cars (model, location, daily_rate, is_active) VALUES (?, ?, ?, 1)";
        jdbcTemplate.update(insertSql, car.getModel(), car.getLocation(), car.getDailyRate());
        String selectSql = "SELECT * FROM cars ORDER BY car_id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, carRowMapper);
    }

    @Override
    public Car update(Car car) {
        String updateSql = "UPDATE cars SET model = ?, location = ?, daily_rate = ? WHERE car_id = ? AND is_active = 1";
        jdbcTemplate.update(updateSql, car.getModel(), car.getLocation(), car.getDailyRate(), car.getCarId());
        return car;
    }

    @Override
    public boolean delete(int carId) {
        String updateSql = "UPDATE cars SET is_active = 0 WHERE car_id = ?";
        jdbcTemplate.update(updateSql, carId);
        return true;
    }
}
