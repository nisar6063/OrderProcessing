package com.order.processor.util;

import java.io.FileOutputStream;
import java.util.Arrays;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.order.processor.bean.BuyLeadBean;
import com.order.processor.bean.BuyLeadItemBean;
import com.order.processor.bean.CustomerBean;

public class PdfCreator {
    private static String FILE = "/Users/nisar.adappadathil/Desktop/FirstPdf.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public static void main(String[] args) throws Exception {
        BuyLeadItemBean buyLeadItem = new BuyLeadItemBean(1, 11, "buyLead 100", 100, "", "88", "90", "178", "");
        CustomerBean custBean = new CustomerBean(123, "ajith", "bangalore", "987654", "asd@ymail.com", 333);
        BuyLeadBean buyLead = new BuyLeadBean(12, custBean, "new", 12, 333, Arrays.asList(buyLeadItem), "12");
        new PdfCreator().createPDF(buyLead);
    }

    public void createPDF(BuyLeadBean buyLead) throws Exception {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document, buyLead);
            //addTitlePage(document, buyLead);
            addContent(document, buyLead);
            document.close();
        } catch (Exception e) {
            throw e;
        }
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private void addMetaData(Document document, BuyLeadBean buyLead) {
        document.addTitle("BuyLead");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Ajith PS");
        document.addCreator("Ajith PS");
    }

//    private void addTitlePage(Document document, BuyLeadBean buyLead) throws DocumentException {
//        Paragraph preface = new Paragraph("Core Technologies");
//        document.add(preface);
//        //document.newPage();
//    }

    private void addContent(Document document, BuyLeadBean buyLead) throws DocumentException {
        Anchor anchor = new Anchor("Buy Lead Details", catFont);
        anchor.setName("Buy Lead Details");
        Anchor anchor2 = new Anchor("Core Technologies", catFont);
        anchor2.setName("Core Technologies");
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        Paragraph subPara = new Paragraph("", subFont);
        Section subCatPart = catPart.addSection(subPara);
        createTable(subCatPart, buyLead);
        document.addTitle("CCDD");
        document.addHeader("Core Technologies Report", "abcd");
        document.add(catPart);
    }

    private void createTable(Section subCatPart, BuyLeadBean buyLead) throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        table.setHeaderRows(1);
        table.addCell("Status");
        table.addCell(buyLead.getStatus());
        table.addCell("User Id");
        table.addCell(buyLead.getUserId() + "");
        table.addCell("followUpCount");
        table.addCell(buyLead.getFollowUpCount() + "");
        table.addCell("uniqueCode");
        table.addCell(buyLead.getUniqueCode());
        CustomerBean custBean = buyLead.getCustBean();
        table.addCell("Customer Name");
        table.addCell(custBean.getCustName());
        table.addCell("Customer Location");
        table.addCell(custBean.getCustLocation());
        table.addCell("Customer Phone");
        table.addCell(custBean.getCustPhone());
        table.addCell("Customer Email");
        table.addCell(custBean.getCustEmail());
        table.addCell("2.2");
        table.addCell("2.3");
        subCatPart.add(table);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
