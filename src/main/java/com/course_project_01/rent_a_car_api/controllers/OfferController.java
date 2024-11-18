package com.course_project_01.rent_a_car_api.controllers;

import com.course_project_01.rent_a_car_api.dtos.OfferDTO;
import com.course_project_01.rent_a_car_api.entities.Offer;
import com.course_project_01.rent_a_car_api.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> response = offerService.getOffers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable int offerId) {
        Offer response = offerService.getOfferById(offerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Offer>> getOffersByCustomerId(@PathVariable int customerId) {
        List<Offer> offers = offerService.getOffersByCustomerId(customerId);
        return ResponseEntity.ok(offers);
    }

    @PostMapping
    public ResponseEntity<Offer> createOffer(@RequestBody OfferDTO createOfferDTO) {
        Offer response = offerService.createOffer(createOfferDTO);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{offerId}/accept")
    public ResponseEntity<Boolean> acceptOffer(@PathVariable int offerId) {
        boolean response = offerService.acceptOffer(offerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{offerId}")
    public ResponseEntity<Boolean> deleteOffer(@PathVariable int offerId) {
        boolean response = offerService.deleteOffer(offerId);
        return ResponseEntity.ok(response);
    }
}
