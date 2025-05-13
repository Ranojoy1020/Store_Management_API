package com.project.StoreManagement.controller;

import com.project.StoreManagement.dto.UdhaarDTO;
import com.project.StoreManagement.entity.Udhaar;
import com.project.StoreManagement.service.UdhaarService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/udhaar")
public class UdhaarController {
    private final UdhaarService udhaarService;

    public UdhaarController(UdhaarService udhaarService) {
        this.udhaarService = udhaarService;
    }

    @GetMapping("/allUdhaar")
    public List<UdhaarDTO> getAllUdhaar() {
        return udhaarService.getAllUdhaar();
    }

    @GetMapping("/allUnpaidUdhaar")
    public List<UdhaarDTO> getAllUnpaidUdhaar() {
        return udhaarService.getAllUnpaidUdhaar();
    }

    @PostMapping("/addUdhaar")
    public Udhaar addUdhaar(@RequestBody Udhaar udhaar) {
        return udhaarService.addUdhaar(udhaar);
    }

    @PutMapping("/{udhaarId}/pay")
    public void markUdhaarAsPaid(@PathVariable Long udhaarId) {
        udhaarService.markAsPaid(udhaarId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Udhaar> getUdhaarByCustomer(@PathVariable Long customerId) {
        return udhaarService.getUdhaarByCustomer(customerId);
    }

    @PostMapping("/sale/{salesId}")
    public Udhaar createUdhaarForSale(@PathVariable Long salesId) {
        return udhaarService.createUdhaarForSale(salesId);
    }
}
