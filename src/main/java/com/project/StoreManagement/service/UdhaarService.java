package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Customer;
import com.project.StoreManagement.entity.Udhaar;
import com.project.StoreManagement.entity.Sales;
import com.project.StoreManagement.enums.UdhaarStatus;
import com.project.StoreManagement.repository.CustomerRepository;
import com.project.StoreManagement.repository.UdhaarRepository;
import com.project.StoreManagement.repository.SalesRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

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

    public List<Udhaar> getAllUdhaar() {
        return udhaarRepository.findAll();
    }

    public Udhaar addUdhaar(Udhaar udhaar) {
        udhaar.setStatus(String.valueOf(UdhaarStatus.PENDING));
        return udhaarRepository.save(udhaar);
    }

    public void markAsPaid(Long udhaarId) {
        Udhaar udhaar = udhaarRepository.findById(udhaarId).orElseThrow(() -> new RuntimeException("Udhaar not found"));
        udhaar.setStatus(String.valueOf(UdhaarStatus.PAID));
        udhaarRepository.save(udhaar);
    }

    public List<Udhaar> getUdhaarByCustomer(Long customerId) {
        return udhaarRepository.findByCustomer_CustomerId(customerId);
    }

    public Udhaar createUdhaarForSale(Long salesId, double amountDue, LocalDate dueDate) {
        Sales sale = salesRepository.findById(salesId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        Udhaar udhaar = new Udhaar();
        udhaar.setSales(sale);
        udhaar.setCustomer(sale.getCustomer());
        udhaar.setAmountDue(amountDue);
        udhaar.setDueDate(dueDate);
        udhaar.setStatus(String.valueOf(UdhaarStatus.PENDING));

        return udhaarRepository.save(udhaar);
    }

    // Runs daily at 1 AM to update overdue Udhaar
    @Scheduled(cron = "0 0 1 * * ?") // Runs at 1 AM
    public void updateOverdueUdhaarStatus() {
        List<Udhaar> pendingUdhaar = udhaarRepository.findByDueDateBeforeAndStatus(LocalDate.now(), "PENDING");

        for (Udhaar udhaar : pendingUdhaar) {
            udhaar.setStatus("OVERDUE");
            udhaarRepository.save(udhaar);

            // Update customer overdue count and total due amount
            Customer customer = udhaar.getCustomer();
            customer.setOverdueCount(customer.getOverdueCount() + 1);
            customer.setTotalUdhaarAmount(customer.getTotalUdhaarAmount() + udhaar.getAmountDue());
            customerRepository.save(customer);

            System.out.println("Updated Udhaar ID: " + udhaar.getUdhaarId() + " to OVERDUE");
        }
    }
}
