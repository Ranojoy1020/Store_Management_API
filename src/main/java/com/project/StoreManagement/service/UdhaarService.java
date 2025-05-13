package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Customer;
import com.project.StoreManagement.entity.Udhaar;
import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.dto.UdhaarDTO;
import com.project.StoreManagement.enums.UdhaarStatus;
import com.project.StoreManagement.repository.CustomerRepository;
import com.project.StoreManagement.repository.UdhaarRepository;
import com.project.StoreManagement.repository.SalesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UdhaarService {
    private final UdhaarRepository udhaarRepository;
    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;


    public UdhaarService(UdhaarRepository udhaarRepository, SalesRepository salesRepository, CustomerRepository customerRepository) {
        this.udhaarRepository = udhaarRepository;
        this.salesRepository = salesRepository;
        this.customerRepository = customerRepository;

    }

    public List<UdhaarDTO> getAllUdhaar() {
        List<Udhaar> udhaarList = udhaarRepository.findAll();

        return udhaarList.stream()
                .map(UdhaarDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Udhaar addUdhaar(Udhaar udhaar) {
        udhaar.setStatus(UdhaarStatus.PENDING);
        return udhaarRepository.save(udhaar);
    }

    public void markAsPaid(Long udhaarId) {
        Udhaar udhaar = udhaarRepository.findById(udhaarId).orElseThrow(() -> new RuntimeException("Udhaar not found"));
        udhaar.setStatus(UdhaarStatus.PAID);
        Udhaar saved = udhaarRepository.save(udhaar);


    }

    public List<Udhaar> getUdhaarByCustomer(Long customerId) {
        return udhaarRepository.findByCustomer_CustomerId(customerId);
    }

    public Udhaar createUdhaarForSale(Long salesId) {
        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        Udhaar udhaar = new Udhaar();
        udhaar.setSale(sale);
        udhaar.setCustomer(sale.getCustomer());
        udhaar.setAmountDue(sale.getTotalAmount());
        udhaar.setDueDate(LocalDate.now().plusDays(7));
        udhaar.setStatus(UdhaarStatus.PENDING);

        return udhaarRepository.save(udhaar);
    }

    // Runs daily at 1 AM to update overdue Udhaar
    @Scheduled(cron = "0 0 1 * * ?") // Runs at 1 AM
    public void updateOverdueUdhaarStatus() {
        List<Udhaar> pendingUdhaar = udhaarRepository.findByDueDateBeforeAndStatus(LocalDate.now(), UdhaarStatus.PENDING);

        for (Udhaar udhaar : pendingUdhaar) {
            udhaar.setStatus(UdhaarStatus.OVERDUE);
            udhaarRepository.save(udhaar);

            // Update customer overdue count and total due amount
            Customer customer = udhaar.getCustomer();
            customer.setOverdueCount(customer.getOverdueCount() + 1);
            customer.setTotalUdhaarAmount(customer.getTotalUdhaarAmount() + udhaar.getAmountDue());
            customerRepository.save(customer);

            System.out.println("Updated Udhaar ID: " + udhaar.getUdhaarId() + " to OVERDUE");
        }
    }

    public List<UdhaarDTO> getAllUnpaidUdhaar() {
        List<UdhaarStatus> statuses = List.of(new UdhaarStatus[]{UdhaarStatus.PENDING, UdhaarStatus.OVERDUE});
        List<Udhaar> udhaarList = udhaarRepository.findByStatusIn(statuses);

        return udhaarList.stream()
                .map(UdhaarDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
