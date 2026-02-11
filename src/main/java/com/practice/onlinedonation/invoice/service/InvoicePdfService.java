package com.practice.onlinedonation.invoice.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.practice.onlinedonation.invoice.payload.DonationInvoiceData;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Service
public class InvoicePdfService {

    public byte[] generateInvoice(DonationInvoiceData data) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            // Header
            Paragraph header = new Paragraph("DONATION INVOICE")

                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(header);

            document.add(new Paragraph("\n"));

            // Body
            document.add(line("Name", data.getName()));
            document.add(line("Donation Amount", "₹" + data.getAmount()/100.0));
            document.add(line("Donation Organization", data.getOrganization()));
            document.add(line("Status", data.getStatus()));
            document.add(line("Currency", data.getCurrency()));
            document.add(line("Email", data.getEmail()));
            document.add(line("Payment Method", data.getPaymentMethod()));
            document.add(line("Payment Date", data.getPaymentDate().toString()));
            document.add(line("Invoice Number", data.getInvoiceNumber()));



            // Footer
            Paragraph footer = new Paragraph(
                    "Address: XYZ Street, Hyderabad\n" +
                            "Date: " + LocalDate.now()
            )
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10);

            document.add(footer);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    private Paragraph line(String label, String value) {
        return new Paragraph(label + ": " + value)
                .setFontSize(12)
                .setMarginBottom(5);
    }
}


