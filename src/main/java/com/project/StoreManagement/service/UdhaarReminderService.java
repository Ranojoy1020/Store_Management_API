package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Udhaar;
import com.project.StoreManagement.entity.UdhaarReminder;
import com.project.StoreManagement.enums.UdhaarStatus;
import com.project.StoreManagement.repository.UdhaarReminderRepository;
import com.project.StoreManagement.repository.UdhaarRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UdhaarReminderService {
    private final UdhaarRepository udhaarRepository;
    private final UdhaarReminderRepository udhaarReminderRepository;
    private final NotificationService notificationService;

    public UdhaarReminderService(UdhaarRepository udhaarRepository, UdhaarReminderRepository udhaarReminderRepository, NotificationService notificationService) {
        this.udhaarRepository = udhaarRepository;
        this.udhaarReminderRepository = udhaarReminderRepository;
        this.notificationService = notificationService;
    }

    // Runs every day at 9 AM to send reminders
    @Scheduled(cron = "0 42 11 * * ?")
    public void sendOverdueReminders() {
        List<Udhaar> overdueUdhaar = udhaarRepository.findByStatus(UdhaarStatus.OVERDUE);

        for (Udhaar udhaar : overdueUdhaar) {
            String message = "Your Udhaar of ₹" + udhaar.getAmountDue() + " was due on " + udhaar.getDueDate() + ". Please clear your dues.";

            boolean isSent = notificationService.sendReminder(
                    udhaar.getCustomer().getPhone(),
                    udhaar.getCustomer().getEmail(),
                    udhaar.getCustomer().getFName(),
                    message
            );

            // Log reminder
            UdhaarReminder reminder = new UdhaarReminder();
            reminder.setUdhaar(udhaar);
            reminder.setReminderDate(LocalDate.now());
            reminder.setStatus(isSent ? "SENT" : "FAILED");

            udhaarReminderRepository.save(reminder);
        }
    }
}
