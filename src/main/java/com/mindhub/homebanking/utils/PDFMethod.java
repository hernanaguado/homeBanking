package com.mindhub.homebanking.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Transaction;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class PDFMethod {

    public static void createPDF(Set<Transaction> transactionArray) throws DocumentException, FileNotFoundException {

        var doc = new Document();

        String route = System.getProperty("user.home");
        PdfWriter.getInstance(doc, new FileOutputStream("Your-transactions.pdf"));
        PdfWriter.getInstance(doc, new FileOutputStream(route + "/Downloads/TransactionInfo.pdf"));

        doc.open();
        float columnWidth [] = {200f, 50f, 100f}; // DEFINO EL WIDTH DE CADA COLUMNA

        var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        var paragraph = new Paragraph(Paragraph.ALIGN_CENTER, "Your transactions", bold);
        var table = new PdfPTable(3);
        //Stream.of("Hello world").forEach(table::addCell);

        transactionArray.forEach(transaction -> {
            table.addCell("$" +transaction.getAmount());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getType()+"");
            table.addCell(transaction.getDate().format(DateTimeFormatter.BASIC_ISO_DATE));
        });

        paragraph.add(table);
        doc.add(paragraph);
        doc.close();
    }


}
