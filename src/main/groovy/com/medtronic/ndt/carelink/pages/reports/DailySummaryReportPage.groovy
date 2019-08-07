package com.medtronic.ndt.carelink.pages.reports

import geb.Page
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.openqa.selenium.By

class DailySummaryReportPage extends Page {

    static at = {
        waitFor { patientInfo.displayed || dailySummaryReport.displayed }
        patientInfo.displayed || dailySummaryReport.displayed
    }
    static content = {
        patientInfo(required: false, wait: 5) { $("input", id: "study:details") }
        dailySummaryReport(required: false) { $(By.xpath('//*[contains(@id,\'study:WS_title\')]')) }
    }

    void verifyReportMmolL() {
        def pdfLink = downloadStream($("tbody > tr > td.overview > ul > li:nth-child(5) > a").@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        assert output.contains("Daily Summary")
        assert output.contains("(7 days)")
        assert output.contains("Note Target RangeUnknown size Small Medium LargeMeal:Meds Insulin\r\n" + "Bedtime / Wake-upUnknown intensity Light Moderate IntenseExercise:BG Calibration BG\r\n" +
                "A thicker flat sensor trace at 2.2 or 22.2 mmol/L indicates CGM values can be outside these limits.")
        assert output.contains("Note Target RangeUnknown size Small Medium LargeMeal:Meds Insulin\r\n" +
                "Bedtime / Wake-upUnknown intensity Light Moderate IntenseExercise:BG Calibration BG\r\n" +
                "A thicker flat sensor trace at 2.2 or 22.2 mmol/L indicates CGM values can be outside these limits.\r\n" + "Page: 2")
        assert output.contains("(mmol/L)")
        assert output.contains("≤2.2\r\n" +
                "≥22.2\r\n" +
                "Tue Sep 29 (mmol/L) Sensor")
        assert output.contains("≤2.2\r\n" +
                "≥22.2\r\n" +
                "Wed Sep 30 (mmol/L) Sensor")
        assert output.contains("≤2.2\r\n" +
                "≥22.2\r\n" +
                "Thu Oct 1 (mmol/L) Sensor")
        assert output.contains("≤2.2\r\n" +
                "≥22.2\r\n" +
                "Sat Oct 3 (mmol/L) Sensor")
        assert output.contains("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        parser.getPDDocument().close()
    }

    void verifyReportMgdL() {
        def pdfLink = downloadStream($("tbody > tr > td.overview > ul > li:nth-child(5) > a").@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        assert output.contains("Daily Summary")
        assert output.contains("(7 days)")
        assert output.contains("Note Target RangeUnknown size Small Medium LargeMeal:Meds Insulin\r\n" + "Bedtime / Wake-upUnknown intensity Light Moderate IntenseExercise:BG Calibration BG\r\n" +
                "A thicker flat sensor trace at 40 or 400 mg/dL indicates CGM values can be outside these limits.")
        assert output.contains("Note Target RangeUnknown size Small Medium LargeMeal:Meds Insulin\r\n" +
                "Bedtime / Wake-upUnknown intensity Light Moderate IntenseExercise:BG Calibration BG\r\n" +
                "A thicker flat sensor trace at 40 or 400 mg/dL indicates CGM values can be outside these limits.\r\n" + "Page: 2")
        assert output.contains("(mg/dL)")
        assert output.contains("≤40\r\n" +
                "≥400\r\n" +
                "Tue Sep 29 (mg/dL) Sensor")
        assert output.contains("≤40\r\n" +
                "≥400\r\n" +
                "Wed Sep 30 (mg/dL) Sensor")
        assert output.contains("≤40\r\n" +
                "≥400\r\n" +
                "Thu Oct 1 (mg/dL) Sensor")
        assert output.contains("≤40\r\n" +
                "≥400\r\n")
        assert output.contains("Fri Oct 2 (mg/dL) Sensor")
        assert output.contains("≤40\r\n" +
                "≥400\r\n" +
                "Sat Oct 3 (mg/dL) Sensor")
        assert output.contains("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        parser.getPDDocument().close()
    }
}
