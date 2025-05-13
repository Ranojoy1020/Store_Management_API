package com.project.StoreManagement.service;

import com.project.StoreManagement.entity.Inventory;
import com.project.StoreManagement.util.PdfGenerator;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public boolean sendReminder(String phoneNumber, String email, String name, String message) {
        // Simulate SMS API Call
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        boolean smsSent = true; // Simulated

        // Send Email
        String emailSubject = "Payment Reminder - Udhaar Due";
        String emailContent = "<p>Dear " + name + ",</p>"
                + "<p>Your Udhaar of <strong>₹" + message + "</strong> is overdue. Please make the payment at the earliest.</p>"
                + "<p>Thank you,</p><p>Platinum Market</p>";

        boolean emailSent = emailService.sendEmail(email, emailSubject, emailContent);

        return smsSent && emailSent;
    }

    public void sendLowStockAlert(String productName, int stockQuantity) {

        // TODO: Integrate SMS or Push Notification
        System.out.println("⚠️ Alert: Product '" + productName + "' is low in stock. Only " + stockQuantity + " left!");

        String subject = "⚠️ Low Stock Alert: " + productName;
        String message = "<h3>⚠️ Warning! Stock Running Low</h3>"
                + "<p>Product: <b>" + productName + "</b></p>"
                + "<p>Remaining Stock: <b>" + stockQuantity + "</b></p>"
                + "<p>Please restock soon.</p>";

        // TODO: Change this to the store owner's email
        emailService.sendEmail("ronban.14@gmail.com", subject, message);
    }

    public void sendLowInventoryAlert(List<Inventory> itemList) throws Exception {

        // TODO: Integrate SMS or Push Notification
//        System.out.println("⚠️ Alert: Product '" + productName + "' is low in stock. Only " + stockQuantity + " left!");

        String subject = "Low Stock Alert 🚨";

        byte[] attachmentbytes = PdfGenerator.generateLowStockReport(itemList);

        // TODO: Change this to the store owner's email
        emailService.sendEmailWithAttachment("ronban.14@gmail.com", subject, "Please find attached the daily low stock report.",attachmentbytes, "Low_Stock_Report.pdf" );
    }
}
