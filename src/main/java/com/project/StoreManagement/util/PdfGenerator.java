package com.project.StoreManagement.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.project.StoreManagement.entity.Inventory;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGenerator {

    public static byte[] generateLowStockReport(List<Inventory> lowStockItems) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);

        document.open();

        // Define fonts
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 18, Font.BOLD);
        Font headerFont = new Font(baseFont, 12, Font.BOLD);
        Font cellFont = new Font(baseFont, 12, Font.NORMAL);

        // Add title
        Paragraph title = new Paragraph("Low Stock Inventory Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(Chunk.NEWLINE); // Add a blank line

        // Create a table with 3 columns
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{4, 2, 2}); // Column widths (relative)

        // Table headers
        addTableHeader(table, "Product Name", headerFont);
        addTableHeader(table, "Current Stock", headerFont);
        addTableHeader(table, "Minimum Threshold", headerFont);

        // Table rows
        for (Inventory item : lowStockItems) {
            table.addCell(new PdfPCell(new Phrase(item.getProduct().getName(), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getStockQuantity()), cellFont)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(item.getMinStockThreshold()), cellFont)));
        }

        document.add(table);

        document.close();
        return out.toByteArray();
    }

    private static void addTableHeader(PdfPTable table, String headerTitle, Font font) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(1);
        header.setPhrase(new Phrase(headerTitle, font));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(header);
    }
}
