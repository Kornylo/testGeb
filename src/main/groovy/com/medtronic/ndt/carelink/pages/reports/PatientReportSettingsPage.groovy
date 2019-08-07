package com.medtronic.ndt.carelink.pages.reports

import geb.Page
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.openqa.selenium.By

class PatientReportSettingsPage extends Page {
    def glucoseTarget = ["70", "150", "70", "180"]
    def glucoseTargetBeforeAfter = ["Before", "After"]
    static at = {
        glucoseUnitsMg.displayed || patientInfo.displayed
    }
    static content = {
        patientInfo(required: false) { $ "a", id: "study:details" }
        patientNotesReport(required: false) { $(By.xpath('//*[contains(@id,\'study:ED_title\')]')) }
        glucoseUnitsMmol(required: false) { $("input", id: "bgunit_mmoll") }
        glucoseUnitsMmolPopup(required: false) { $ "input", id: "j_id_id48:convert" }
        timeDisplay24(required: false) { $("input", id: "timedisplay_hr24") }
        reportSettings(required: false) { $ "input", id: "reportpreference:savePrefs" }
        glucoseUnitsMg(required: false) { $ "input", id: "bgunit_mgdl" }
        glucoseTargetLow(required: false) { $ "input", id: "reportpreference:glucoseTargetLow" }
        glucoseTargetHigh(required: false) { $ "input", id: "reportpreference:glucoseTargetHigh" }
        restoreDefaults(required: false) { $ "input", id: "reportpreference:revert" }
        reportSettingsTitle(required: false) { $ "#reportpreference > div > h3" }
        glucoseTargetRangeTitle(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(1) > span"
        }
        targetRangeHighHigh(required: false) { $ "#mealsection > tr.range > td:nth-child(4)" }
        targetRangeHighLow(required: false) { $ "#mealsection > tr.range > td:nth-child(2)" }
        glucoseLimits(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(2) > span"
        }
        glucoseUnits(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(3) > span"
        }
        timePeriod(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(4) > span"
        }
        timeDisplay12hour(required: false) { $("input", id: "timedisplay_hr12") }
        sectionAnalysisReportsSettings(required: false) { $ "#mealsection > tr:nth-child(1) > td > span" }
        sectionGlucoseTargetRange(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(2) > span" }
        sectionBreakfast(required: false) { $ "#breakfastsection > tr.underline > td:nth-child(1)" }
        sectionLunch(required: false) { $ "#lunchsection > tr.underline > td:nth-child(1)" }
        sectionDinner(required: false) { $ "#dinnersection > tr.underline > td:nth-child(1)" }
        sectionEvening(required: false) { $ "#overnightsection > tr.underline > td:nth-child(1)" }
        sectionSleeping(required: false) { $ "#overnightsection > tr:nth-child(2) > td:nth-child(1)" }
        sectionFasting(required: false) { $ "#overnightsection > tr:nth-child(3) > td:nth-child(1)" }
        analysisTimePeriod(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(3) > span" }
        postMealAnalysis(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(4) > span" }
        sectionGlucoseTargetRangeLow(required: false) { $ "#mealsection > tr.range > td:nth-child(2)" }
        sectionGlucoseTargetRangeHigh(required: false) { $ "#mealsection > tr.range > td:nth-child(4)" }
        breakfastBefore(required: false) { $ "#breakfastsection > tr.underline > td:nth-child(2)" }
        breakfastAfter(required: false) { $ "#breakfastsection > tr:nth-child(2) > td:nth-child(2)" }
        lunchBefore(required: false) { $ "#lunchsection > tr.underline > td:nth-child(2)" }
        lunchAfter(required: false) { $ "#lunchsection > tr:nth-child(2) > td:nth-child(2)" }
        dinnerBefore(required: false) { $ "#dinnersection > tr.underline > td:nth-child(2)" }
        dinnerAfter(required: false) { $ "#dinnersection > tr:nth-child(2) > td:nth-child(2)" }
        analysisTimePeriodFrom(required: false) { $ "#mealsection > tr.range > td:nth-child(5)" }
        analysisTimePeriodTo(required: false) { $ "#mealsection > tr.range > td:nth-child(7)" }
        postMealAnalysisFrom(required: false) { $ "#mealsection > tr.range > td:nth-child(8)" }
        postMealAnalysisTo(required: false) { $ "#mealsection > tr.range > td:nth-child(10)" }
        postMealAnalysisBreakfastHours(required: false) { $ "#breakfastsection > tr.underline > td:nth-child(12)" }
        postMealAnalysisLunchHours(required: false) { $ "#lunchsection > tr.underline > td:nth-child(12)" }
        postMealAnalysisDinnerHours(required: false) { $ "#dinnersection > tr.underline > td:nth-child(12)" }
        patientlabelSave(required: false) { $ "input", id: "reportpreference:savePrefs" }
        patientlabelCancel(required: false) { $ id: "reportpreference:cancelPrefs" }
        patientName(required: false) { $("div.nav_gray > h2") }
        patientEdit(required: false) { $("#report_settings > p.change_settings > a") }
        selectTime12(wait: true) { $(By.id("timedisplay_hr12")) }
        clucoseUnitsMmolapprove(wait: true) { $('.confirm_dialog_btn_div').find(id: contains(':convert')) }
        breakfastsectionSection(required: false) { $(id: "breakfastsection") }
        lunchsectionSection(required: false) { $ id: "lunchsection" }
        dinnersectionSection(required: false) { $ id: "dinnersection" }
        dataTableReportPdf() { $('td.data_table_r>ul>li>a') }
    }

    def sectionPostMealAnalysisFrom(String text) {
        postMealAnalysisFrom.text() == text
    }

    def sectionPostMealAnalysisTo(String text) {
        postMealAnalysisTo.text() == text
    }

    def sectionAnalysisReportsTimeFrom(String text) {
        analysisTimePeriodFrom.text() == text
    }

    def sectionAnalysisReportsTimeTo(String text) {
        analysisTimePeriodTo.text() == text
    }

    def restoreDefaultsclick() {
        restoreDefaults.click()
    }


    void postMealAnalysisDisplayed(String text) {
        assert postMealAnalysis.text() == text
    }

    void postMealAnalysisFromDisplayed(String text) {
        assert postMealAnalysisFrom.text() == text
    }

    void postMealAnalysisToDisplayed(String text) {
        assert postMealAnalysisTo.text() == text
    }

    void postMealAnalysisBreakfastHoursDisplayed(String text) {
        assert postMealAnalysisBreakfastHours.text() == text
    }

    void postMealAnalysisLunchHoursDisplayed(String text) {
        assert postMealAnalysisLunchHours.text() == text
    }

    void postMealAnalysisDinnerHoursDisplayed(String text) {
        assert postMealAnalysisDinnerHours.text() == text
    }

    void analysisTimePeriodToDisplayed(String text) {
        assert analysisTimePeriodTo.text() == text
    }

    void analysisTimePeriodFromDisplayed(String text) {
        assert analysisTimePeriodFrom.text() == text
    }

    void analysisTimePeriodDisplayed(String text) {
        assert analysisTimePeriod.text() == text
    }

    void breakfastBeforeDisplayed(String text) {
        assert breakfastBefore.text() == text
    }

    void breakfastAfterDisplayed(String text) {
        assert breakfastAfter.text() == text
    }

    void lunchBeforeDisplayed(String text) {
        assert lunchBefore.text() == text
    }

    void lunchAfterDisplayed(String text) {
        assert lunchAfter.text() == text

    }

    void dinnerBeforeDisplayed(String text) {
        assert dinnerBefore.text() == text
    }

    void dinnerAfterDisplayed(String text) {
        assert dinnerAfter.text() == text
    }

    void sectionGlucoseTargetRangeHighDisplayed(String text) {
        assert sectionGlucoseTargetRangeHigh.text() == text

    }

    void sectionGlucoseTargetRangeLowDisplayed(String text) {
        assert sectionGlucoseTargetRangeLow.text() == text

    }

    void selectGlucoseUnitsMmol() {
        if (glucoseUnitsMmol.value() == 'on') {
            glucoseUnitsMg.click()
            waitFor { clucoseUnitsMmolapprove.displayed }
            clucoseUnitsMmolapprove.click()
            assert glucoseUnitsMg.value() == 'on'
            glucoseUnitsMmol.click()
            waitFor { clucoseUnitsMmolapprove.displayed }
            clucoseUnitsMmolapprove.click()
            assert glucoseUnitsMmol.value() == 'on'
        } else {
            assert glucoseUnitsMmol.value() == null
            glucoseUnitsMmol.click()
            waitFor { clucoseUnitsMmolapprove.displayed }
            clucoseUnitsMmolapprove.click()
            assert glucoseUnitsMmol.value() == 'on'
        }
    }

    void selectGlucoseUnitsMg() {
        glucoseUnitsMg.click()
    }

    void glucoseUnitsMmolUprrove() {
        glucoseUnitsMmolPopup.click()
    }

    void selectTimeDisplay24() {
        waitFor { timeDisplay24.displayed }
        timeDisplay24.click()
        assert timeDisplay24.value() == 'on'
        assert timeDisplay12hour.value() == null
    }

    void saveReportSettings() {
        reportSettings.click()
        sleep(3000)
    }

    void patientlabelSaveValue(String value) {
        assert patientlabelSave.value() == value
    }

    void patientlabelCancelText(String text) {
        assert patientlabelCancel.text() == text
    }

    void labelRestoreDefaults(String title) {
        assert restoreDefaults.value() == title
    }

    void patientReportSettingsTitle(String title) {
        assert reportSettingsTitle.text() == title
    }

    void patientGlucoseTargetRangeTitle(String title) {
        assert glucoseTargetRangeTitle.text() == title
    }

    void patientTargetRangeHigh(String text) {
        assert targetRangeHighHigh.text() == text
    }

    void patientTargetRangeHighLow(String text) {
        assert targetRangeHighLow.text() == text
    }

    void patientGlucoseLimits(String title) {
        assert glucoseLimits.text() == title
    }

    void patienGlucoseUnits(String title) {
        assert glucoseUnits.text() == title
    }

    void sectionGlucoseUnitsMmolDisplayed() {
        assert glucoseUnitsMmol.displayed
    }

    void sectionGlucoseUnitsMgDisplayed() {
        assert glucoseUnitsMg.displayed
    }

    void sectionGlucoseUnitsMgValue(String value) {
        assert glucoseUnitsMg.value() == value
    }

    void sectionTimePeriodMgDisplayed(String title) {
        assert timePeriod.text() == title
    }

    void sectionAnalysisReportsSettingsDisplayed(String title) {
        assert sectionAnalysisReportsSettings.text() == title
    }

    void sectionGlucoseTargetRangeDisplayed(String title) {
        assert sectionGlucoseTargetRange.text() == title
    }

    void glucoseUnitsMmolValue(String value) {
        assert glucoseUnitsMmol.value() == value
    }

    void sectionBreakfastDisplayed(String title) {
        assert sectionBreakfast.text() == title
    }

    void sectionLunchDisplayed(String title) {
        assert sectionLunch.text() == title
    }

    void sectionDinnerDisplayed(String title) {
        assert sectionDinner.text() == title
    }

    void sectionEveningDisplayed(String title) {
        assert sectionEvening.text() == title
    }

    void sectionSleepingDisplayed(String title) {
        assert sectionSleeping.text() == title
    }

    void sectionFastingDisplayed(String title) {
        assert sectionFasting.text() == title
    }

    void timeDisplayChanging() {
        if (timeDisplay12hour.value() == 'on') {
            assert timeDisplay24.value() == null
            timeDisplay24.click()
            assert timeDisplay24.value() == 'on'
            timeDisplay12hour.click()
            assert timeDisplay12hour.value() == 'on'
            assert timeDisplay24.value() == null
        } else {
            assert timeDisplay12hour.value() == null
            timeDisplay12hour.click()
            assert timeDisplay12hour.value() == 'on'
            assert timeDisplay24.value() == null
        }
    }


    void timeDisplay12() {
        assert timeDisplay12hour.displayed
    }

    void verifyTitle(String title) {
        assert title == title
    }

    def verifyPatientName(String lastNam, String firstName, String id) {
        patientName.text().contains(lastNam)
        patientName.text().contains(firstName)
        patientName.text().contains(id)
    }

    void patientEditClick() {
        waitFor { patientEdit.displayed }
        patientEdit.click()
    }

    void selectTime12click() {
        selectTime12.click()
    }

    void fullGlucoseTargetRangeVerify() {
        assert breakfastsectionSection.find("input")*.value() == glucoseTarget
        assert lunchsectionSection.find("input")*.value() == glucoseTarget
        assert dinnersectionSection.find("input")*.value() == glucoseTarget
        assert breakfastsectionSection.find("td.textright")*.text().getAt(0) == glucoseTargetBeforeAfter[0]
        assert breakfastsectionSection.find("td.textright")*.text().getAt(4) == glucoseTargetBeforeAfter[1]
        assert lunchsectionSection.find("td.textright")*.text().getAt(0) == glucoseTargetBeforeAfter[0]
        assert lunchsectionSection.find("td.textright")*.text().getAt(4) == glucoseTargetBeforeAfter[1]
        assert dinnersectionSection.find("td.textright")*.text().getAt(0) == glucoseTargetBeforeAfter[0]
        assert dinnersectionSection.find("td.textright")*.text().getAt(4) == glucoseTargetBeforeAfter[1]
    }

    void openGenerateDataTable(String text) {
        waitFor { $('div.study').find(class: 'grn_blue').displayed }
        assert $('div.study').find(class: 'grn_blue').find('option:nth-child(2)').text().contains(text)
        $('div.study').find(class: 'grn_blue').find('option:nth-child(2)').click()
        waitFor { !$('td.data_table_r>ul>li>a').find('img').getAttribute('src').contains('red-swirl-small.gif') }
    }

    void openDataTableReport(String date, String data) {
        def pdfLink = downloadStream(dataTableReportPdf.@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains(date)
        assert output.contains(data)
        parser.getPDDocument().close()
    }

    void successRegenerateReportsDialog(String success) {
        waitFor { $(id: 'report_dialog').displayed }
        assert $(id: 'report_dialog').text().contains(success)
        $(id: 'report_dialog').find('.confirm_dialog_btn_div').find(id: contains(':yes')).click()
        waitFor { patientEdit.displayed }

    }
}