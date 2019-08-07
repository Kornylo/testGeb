package com.medtronic.ndt.carelink.pages.reports

import geb.Page
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.openqa.selenium.By
import groovy.util.logging.Slf4j

@Slf4j
class OverlayByMealReportPage extends Page{

    static at ={
        patientInfo.displayed || patientName.displayed
    }
    static content ={
        patientInfo(required:false){$"input", id:"study:details"}
        overlayByMealReport(required: false){$ (By.xpath('//*[contains(@id,\'study:OM_title\')]'))}
        goToHomePage (required:false, wait: 5) {$( "span","confirm-marker")}
        selectParient(required:false){$ id:"firstNameTd0"}
        patientName(required:false){$ id:"patientName"}
    }
    void selectFirstParient() {
        selectParient.click()
    }
    boolean clickOverlayByMealReport() {
        overlayByMealReport.click()
    }
    boolean goToHomePageClick() {
        goToHomePage.click()
    }
    void switchToWindowsIfVisibleAndVerifyPDFMg(String platform) {
        String winHandleBefore = driver.getWindowHandle()
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle)
        }
                File file = new File("${System.properties.'user.dir'}/build/file.pdf")
                FileInputStream fis = new FileInputStream(file)
                PDFParser parser = new PDFParser(fis)
                parser.parse()
                COSDocument cos = parser.getDocument()
                PDDocument pdDoc = new PDDocument(cos)
                PDFTextStripper strip = new PDFTextStripper()
                String output = strip.getText(pdDoc)
                println(output)
                output.contains "Overlay by Meal Event (mg/dL)"
                output.contains "Sep 28 - Oct 4, 2009"
                output.contains "(7 days)"
                output.contains "≤40"
                output.contains "40"
                output.contains "400"
                parser.getPDDocument().close()
                driver.switchTo().window(winHandleBefore)
            }


    void switchToWindowsIfVisibleAndVerifyPDFMml(String platform) {
        String winHandleBefore = driver.getWindowHandle()
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle)
        }
                File file = new File("${System.properties.'user.dir'}/build/file.pdf")
                FileInputStream fis = new FileInputStream(file)
                PDFParser parser = new PDFParser(fis)
                parser.parse()
                COSDocument cos = parser.getDocument()
                PDDocument pdDoc = new PDDocument(cos)
                PDFTextStripper strip = new PDFTextStripper()
                String output = strip.getText(pdDoc)
                println(output)
                output.contains "Overlay by Meal Event (mmol/L)"
                output.contains "Sep 28 - Oct 4"
                output.contains "(7 days)"
                output.contains "≤2.2"
                output.contains "≥22.2"
                output.contains "22.2"
                parser.getPDDocument().close()
                driver.switchTo().window(winHandleBefore)
            }


    void backToMainPage() {
        driver.findElement(By.id("study:home")).click()
    }
    void selectFirstPatient() {
        driver.findElement(By.cssSelector("#studyStatusTd0 > p")).click()
    }

    boolean backToHomepage() {
        driver.findElement(By.cssSelector("span.confirm-marker")).click()
    }}
