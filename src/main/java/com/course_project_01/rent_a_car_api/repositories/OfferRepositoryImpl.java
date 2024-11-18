package com.course_project_01.rent_a_car_api.repositories;

import com.course_project_01.rent_a_car_api.entities.Offer;
import com.course_project_01.rent_a_car_api.mappers.OfferRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class OfferRepositoryImpl implements OfferRepository {

    private final JdbcTemplate jdbcTemplate;
    private final OfferRowMapper offerRowMapper = new OfferRowMapper();

    public OfferRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Offer> findAll() {
        String selectSql = "SELECT * FROM offers WHERE is_active = 1";
        return jdbcTemplate.query(selectSql, offerRowMapper);
    }

    @Override
    public Optional<Offer> findById(int offerId) {
        String selectSql = "SELECT * FROM offers WHERE offer_id = ? AND is_active = 1";
        List<Offer> offers = jdbcTemplate.query(selectSql, offerRowMapper, offerId);
        return offers.stream().findFirst();
    }

    @Override
    public List<Offer> findByCustomerId(int customerId) {
        String selectSql = "SELECT * FROM offers WHERE customer_id = ? AND is_active = 1";
        return jdbcTemplate.query(selectSql, offerRowMapper, customerId);
    }

    @Override
    public Offer create(Offer offer) {
        String insertSql = "INSERT INTO offers (customer_id, car_id, start_date, days, total_price, is_accepted, is_active) VALUES (?, ?, ?, ?, ?, false, 1)";
        jdbcTemplate.update(insertSql, offer.getCustomerId(), offer.getCarId(), offer.getStartDate(), offer.getDays(), offer.getTotalPrice());
        String selectSql = "SELECT * FROM offers WHERE customer_id = ? AND car_id = ? ORDER BY offer_id DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectSql, offerRowMapper, offer.getCustomerId(), offer.getCarId());
    }

    @Override
    public boolean accept(int offerId) {
        String updateSql = "UPDATE offers SET is_accepted = true WHERE offer_id = ? AND is_archived = false AND is_active = 1";
        jdbcTemplate.update(updateSql, offerId);
        return true;
    }

    @Override
    public boolean delete(int offerId) {
        String updateSql = "UPDATE offers SET is_active = 0 WHERE offer_id = ?";
        jdbcTemplate.update(updateSql, offerId);
        return true;
    }

    @Override
    public boolean isCarInAcceptedOffer(int carId) {
        String selectSql = "SELECT COUNT(*) FROM offers WHERE car_id = ? AND is_accepted = true AND is_active = 1";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class, carId);
        return count != null && count > 0;
    }

    @Override
    public boolean isCustomerInAcceptedOffer(int customerId) {
        String selectSql = "SELECT COUNT(*) FROM offers WHERE customer_id = ? AND is_accepted = true AND is_active = 1";
        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class, customerId);
        return count != null && count > 0;
    }

    @Override
    public void archiveUnacceptedOffersForCar(int carId) {
        String updateSql = "UPDATE offers SET is_archived = true WHERE car_id = ? AND is_accepted = false AND is_active = 1";
        jdbcTemplate.update(updateSql, carId);
    }

    @Override
    public void archiveUnacceptedOffersForCustomer(int customerId) {
        String updateSql = "UPDATE offers SET is_archived = true WHERE customer_id = ? AND is_accepted = false AND is_active = 1";
        jdbcTemplate.update(updateSql, customerId);
    }

    // Check whether a car is available for a given period (from startDate to endDate)
    // We query the database for any overlapping offers that are accepted and active during the requested period
    // If no such offers exist, then the car is available and can be booked
    @Override
    public boolean isCarAvailableForPeriod(int carId, LocalDate startDate, int days) {
        // Calculate the end date of the offer
        LocalDate endDate = startDate.plusDays(days);

        /* Condition 1: (start_date <= ? AND DATEADD('DAY', offers.days, start_date) > ?)
        Check if an existing offer starts (S) before or on the requested startDate and ends (E) after the requested startDate
        which captures the cases where the existing offer overlaps with the beginning of the requested period

        Beginning Overlap:
        S --- startDate --- E
        (S, startDate) --- E

        Condition 2: (start_date < ? AND DATEADD('DAY', offers.days, start_date) >= ?)
        Check if an existing offer starts (S) before the requested endDate and ends (E) on or after the requested endDate
        which captures the cases where the existing offer overlaps with the end of the requested period

        Ending Overlap:
        S --- (E, endDate)
        S --- endDate --- E
         */

        String selectSql = "SELECT COUNT(*) FROM offers " +
                "WHERE car_id = ? AND is_accepted = true AND is_active = 1 " +
                "AND ((start_date <= ? AND DATEADD('DAY', offers.days, start_date) > ?) " +
                "OR (start_date < ? AND DATEADD('DAY', offers.days, start_date) >= ?))";

        Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class,
                carId,
                startDate,
                startDate,
                endDate,
                startDate
        );
        return count == 0;
    }
}
