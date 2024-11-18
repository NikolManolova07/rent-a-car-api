package com.course_project_01.rent_a_car_api.mappers;

import com.course_project_01.rent_a_car_api.entities.Offer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OfferRowMapper implements RowMapper<Offer> {
    @Override
    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setOfferId(rs.getInt("offer_id"));
        offer.setCarId(rs.getInt("car_id"));
        offer.setCustomerId(rs.getInt("customer_id"));
        offer.setStartDate(rs.getDate("start_date").toLocalDate());
        offer.setDays(rs.getInt("days"));
        offer.setTotalPrice(rs.getDouble("total_price"));
        offer.setAccepted(rs.getBoolean("is_accepted"));
        offer.setArchived(rs.getBoolean("is_archived"));
        return offer;
    }
}
