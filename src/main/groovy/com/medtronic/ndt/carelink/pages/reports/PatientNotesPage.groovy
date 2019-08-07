package com.medtronic.ndt.carelink.pages.reports

import geb.Page
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.openqa.selenium.By

class PatientNotesPage extends Page{

    static at ={
        patientInfo.displayed
    }
    static content ={
        patientInfo(required:false){$"a", id:"study:details"}
        patientNotesReport(required: false){$ (By.xpath('//*[contains(@id,\'study:ED_title\')]'))}
    }

    void clickPatientNotesReport() {
        patientNotesReport.click()
    }
    void backToMainPage() {
        driver.findElement(By.id("study:home")).click()
    }


    void verifyPatientNotes(String text1, String text2) {
        def pdfLink = downloadStream($("tbody > tr > td.overview > ul > li:nth-child(6) > a").@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        assert output.contains(text1)
        assert output.contains(text2)
        parser.getPDDocument().close()
    }
}
