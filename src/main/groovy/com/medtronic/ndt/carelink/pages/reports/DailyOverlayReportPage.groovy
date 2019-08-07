package com.medtronic.ndt.carelink.pages.reports


import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import org.openqa.selenium.By

@Slf4j
class DailyOverlayReportPage extends Page {
    def timeFormat30min24h = ['0:00', '0:30', '1:00', '1:30', '2:00', '2:30', '3:00', '3:30', '4:00', '4:30', '5:00', '5:30', '6:00', '6:30', '7:00', '7:30', '8:00', '8:30', '9:00', '9:30', '10:00', '10:30', '11:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30', '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30']
    def timeFormat30min = ['12:00 AM', '12:30 AM', '1:00 AM', '1:30 AM', '2:00 AM', '2:30 AM', '3:00 AM', '3:30 AM', '4:00 AM', '4:30 AM', '5:00 AM', '5:30 AM', '6:00 AM', '6:30 AM', '7:00 AM', '7:30 AM', '8:00 AM', '8:30 AM', '9:00 AM', '9:30 AM', '10:00 AM', '10:30 AM', '11:00 AM', '11:30 AM', '12:00 PM', '12:30 PM', '1:00 PM', '1:30 PM', '2:00 PM', '2:30 PM', '3:00 PM', '3:30 PM', '4:00 PM', '4:30 PM', '5:00 PM', '5:30 PM', '6:00 PM', '6:30 PM', '7:00 PM', '7:30 PM', '8:00 PM', '8:30 PM', '9:00 PM', '9:30 PM', '10:00 PM', '10:30 PM', '11:00 PM', '11:30 PM']
    def timeFormat30minJapan = ['午前 12:00', '午前 12:30', '午前 1:00', '午前 1:30', '午前 2:00', '午前 2:30', '午前 3:00', '午前 3:30', '午前 4:00', '午前 4:30', '午前 5:00', '午前 5:30', '午前 6:00', '午前 6:30', '午前 7:00', '午前 7:30', '午前 8:00', '午前 8:30', '午前 9:00', '午前 9:30', '午前 10:00', '午前 10:30', '午前 11:00', '午前 11:30', '午後 12:00', '午後 12:30', '午後 1:00', '午後 1:30', '午後 2:00', '午後 2:30', '午後 3:00', '午後 3:30', '午後 4:00', '午後 4:30', '午後 5:00', '午後 5:30', '午後 6:00', '午後 6:30', '午後 7:00', '午後 7:30', '午後 8:00', '午後 8:30', '午後 9:00', '午後 9:30', '午後 10:00', '午後 10:30', '午後 11:00', '午後 11:30']

    static at = {
        header.displayed || logo.displayed || patientName.displayed
    }
    static content = {
        patientName(wait: true) { $ id: "patientName" }
        patientInfo(wait: true) { $ "input", id: "study:details" }
        dailyOverlayReport(wait: true) { $(By.xpath('//*[contains(@id,\'study:SO_title\')]')) }
        logo(required: false) { $ id: "envision" }
        clinicSettingsPage(wait: true) { $ "a", id: "list:clinicsettings" }
        header(required: false) { $('div.header') }
        continueBtn(wait: true) { $ "input", id: "localeform:selectbtn" }
        signinButton(wait: true) { $("input", id: "signinbtn") }
        acceptBtn(wait: true) { $ "input", id: "mainform:acceptbtn" }
        residentCheckbox(wait: true) { $ "input", id: "challenge1" }
        glucoseUnitsMmol(wait: true) { $ "input", id: "bgunit_mmoll" }
        glucoseUnitsMmolPopup(wait: true) { $ "input", id: "j_id_id48:convert" }
        timeDisplay24(wait: true) { $ "input", id: "timedisplay_hr24" }
        reportSettings(wait: true) { $ "input", id: "reportpreference:savePrefs" }
        glucoseUnitsMg(wait: true) { $ "input", id: "bgunit_mgdl" }
        glucoseTargetLow(wait: true) { $ "input", id: "reportpreference:glucoseTargetLow" }
        glucoseTargetHigh(wait: true) { $ "input", id: "reportpreference:glucoseTargetHigh" }
        glucoseTargetXtremeHigh(wait: true) { $ "input", id: "reportpreference:glucoseTargetXtremeHigh" }
        glucoseTargetXtremeLow(wait: true) { $ "input", id: "reportpreference:glucoseTargetXtremeLow" }
        beforeBreakfastGlucoseTargetLow(wait: true) {
            $ "input", id: "reportpreference:beforeBreakfastGlucoseTargetLow"
        }
        beforeBreakfastGlucoseTargetHigh(wait: true) {
            $("input", id: "reportpreference:beforeBreakfastGlucoseTargetHigh")
        }
        lunchBeforeLow(wait: true) { $("input", id: "reportpreference:beforeLunchGlucoseTargetLow") }
        lunchBeforeHigh(wait: true) { $("input", id: "reportpreference:beforeLunchGlucoseTargetHigh") }
        lunchAfterLow(wait: true) { $("input", id: "reportpreference:afterLunchGlucoseTargetLow") }
        lunchafterHigh(wait: true) { $("input", id: "reportpreference:beforeLunchGlucoseTargetLow") }
        beforeDinnertGlucoseTargetLow(wait: true) {
            $("input", id: "reportpreference:beforeDinnerGlucoseTargetLow")
        }
        beforeDinnertGlucoseTargetHigh(wait: true) {
            $("input", id: "reportpreference:beforeDinnerGlucoseTargetHigh")
        }
        afterDinnertGlucoseTargetLow(wait: true) { $("input", id: "reportpreference:afterDinnerGlucoseTargetLow") }
        afterDinnertGlucoseTargetHigh(wait: true) {
            $("input", id: "reportpreference:afterDinnerGlucoseTargetHigh")
        }
        beforeEveningGlucoseTargetLow(wait: true) { $("input", id: "reportpreference:eveningGlucoseTargetLow") }
        beforeEveningGlucoseTargetHigh(wait: true) { $("input", id: "reportpreference:eveningGlucoseTargetHigh") }
        beforeSleepingGlucoseTargetLow(wait: true) { $("input", id: "reportpreference:sleepingGlucoseTargetLow") }
        beforeSleepingGlucoseTargetHigh(wait: true) { $("input", id: "reportpreference:sleepingGlucoseTargetHigh") }
        afterBreakfastGlucoseTargetLow(wait: true) {
            $("input", id: "reportpreference:beforeBreakfastGlucoseTargetLow")
        }
        afterBreakfastGlucoseTargetHigh(wait: true) {
            $("input", id: "reportpreference:beforeBreakfastGlucoseTargetHigh")
        }
        sleepingTime(wait: true) { $(id: "reportpreference:sleepingStart") }
        timedisplay_hr12Selector(wait: true) { $(id: "timedisplay_hr12") }
        buttonSaveGeneraSettings(wait: true) {
            $(id: 'convert_dialog').find('.confirm_dialog_btn_div').find('input', id: ~/(.*?):convert/)
        }
        afterLunchGlucoseTargetAfterHigh(wait: true) {
            $ "input", id: "reportpreference:afterLunchGlucoseTargetHigh"
        }
        sleepingPeriodToSel(wait: true) { $(By.id("reportpreference:sleepingEnd")) }
        eveningTimePeriodToSel(wait: true) { $(By.id("reportpreference:eveningEnd")) }

        eveningTimePeriodFromSelector(wait: true) { $(By.id("reportpreference:eveningStart")) }
        dinnerTimePeriodToSelector(wait: true) { $(By.id("reportpreference:dinnerEnd")) } //todo
        dinnerTimePeriodFromSelector(wait: true) { $(By.id("reportpreference:dinnerStart")) }
        lunchTimePeriodToSelector(wait: true) { $(By.id("reportpreference:lunchEnd")) }
        lunchTimePeriodFromSelector(wait: true) { $(By.id("reportpreference:lunchStart")) }
        breakfastTimePeriodToSelector(wait: true) { $(id: "reportpreference:breakfastEnd") }
        breakfastTimePeriodFromSelector(wait: true) { $(id: "reportpreference:breakfastStart") }
        postBreakfastMealAnalysisToSelector(wait: true) { $(By.id("reportpreference:breakfastPostMealDuration")) }
        postBreakfastMealAnalysisFromSelector(wait: true) { $(By.id("reportpreference:breakfastPostMealFrom")) }
        postDinnerMealAnalysisToSelector(wait: true) { $(By.id("reportpreference:dinnerPostMealDuration")) }
        postDinnerMealAnalysisFromSelector(wait: true) { $(By.id("reportpreference:dinnerPostMealFrom")) }
        postLunchMealAnalysisToSelector(wait: true) { $(By.id("reportpreference:lunchPostMealDuration")) }
        postLunchMealAnalysisFromSelector(wait: true) { $(By.id("reportpreference:lunchPostMealFrom")) }
        successPopupSavedOkBtnClick(wait: true) { $(By.id("saved:okBtn")) }
        breakfastSectionArea(wait: true) { $("#breakfastsection > tr.underline > td:nth-child(2)") }
        lunchSectionArea(wait: true) { $("#lunchsection > tr:nth-child(2) > td:nth-child(1)") }
        dinnerSectionArea (wait: true) {$("#dinnersection > tr:nth-child(3) > td:nth-child(1)")}
        overnightSectionArea (wait: true) {$("#overnightsection > tr:nth-child(4) > td:nth-child(1)")}
        patientHomeTab (wait: true) { $('.tab_home > a')}
    }

    void enterSleepingBeforeLow(String Low) {
        beforeSleepingGlucoseTargetLow = Low
        assert beforeSleepingGlucoseTargetLow.value() == Low
    }

    void enterSleepingBeforeHigh(String High) {
        beforeSleepingGlucoseTargetHigh = High
        assert beforeSleepingGlucoseTargetHigh.value() == High
    }

    void enterEveningBeforeLow(String Low) {
        beforeEveningGlucoseTargetLow = Low
        assert beforeEveningGlucoseTargetLow.value() == Low
    }

    void enterEveningBeforeHigh(String High) {
        beforeEveningGlucoseTargetHigh = High
        assert beforeEveningGlucoseTargetHigh.value() == High
    }

    void enterDinnerBeforeLow(String Low) {
        beforeDinnertGlucoseTargetLow = Low
        assert beforeDinnertGlucoseTargetLow.value() == Low
    }

    void enterDinnerBeforeHigh(String High) {
        beforeDinnertGlucoseTargetHigh = High
        assert beforeDinnertGlucoseTargetHigh.value() == High
    }

    void enterDinnerAfterLow(String Low) {
        afterDinnertGlucoseTargetLow = Low
        assert afterDinnertGlucoseTargetLow.value() == Low
    }

    void enterDinnerAfterHigh(String High) {
        afterDinnertGlucoseTargetHigh = High
        assert afterDinnertGlucoseTargetHigh.value() == High
    }

    void enterLunchBeforeLow(String Low) {
        lunchBeforeLow = Low
        assert lunchBeforeLow.value() == Low
    }

    void enterLunchAfterLow(String Low) {
        lunchAfterLow = Low
        assert lunchAfterLow.value() == Low
    }

    void enterLunchAfterHigh(String High) {
        lunchafterHigh = High
        assert lunchafterHigh.value() == High
    }

    void sleepingTimeDisabled() {
        assert sleepingTime.getAttribute("disabled")
    }

    void savePrefsIsActive() {
        waitFor { $(id: "reportpreference:savePrefs").displayed }
    }

    void buttonSaveSettings() {
        waitFor { buttonSaveGeneraSettings.displayed }
        buttonSaveGeneraSettings.click()
    }

    void enterLunchBeforeHigh(String High) {
        lunchBeforeHigh = High
    }

    void enterBreakfastBeforeLow(String Low) {
        waitFor { beforeBreakfastGlucoseTargetLow.displayed }
        beforeBreakfastGlucoseTargetLow = Low
    }

    void enterBreakfastBeforeHigh(String High) {
        beforeBreakfastGlucoseTargetHigh = High
        assert beforeBreakfastGlucoseTargetHigh.value() == High
    }

    void enterBreakfastAfterLow(String Low) {
        afterBreakfastGlucoseTargetLow = Low
        assert afterBreakfastGlucoseTargetLow.value() == Low
    }

    void enterBreakfastAfterHigh(String High) {
        afterBreakfastGlucoseTargetHigh = High
        assert afterBreakfastGlucoseTargetHigh.value(High)
    }

    void enterLunchGlucoseTargetAfterHigh(String High) {
        afterLunchGlucoseTargetAfterHigh = High
        assert afterLunchGlucoseTargetAfterHigh.value(High)
    }

    void postLunchMealAnalysisFrom(String value) {
        postLunchMealAnalysisFromSelector.value(value).click()
        assert postLunchMealAnalysisFromSelector.value(value)
    }

    void postLunchMealAnalysisTo(String value) {
        postLunchMealAnalysisToSelector.value(value).click()
        assert postLunchMealAnalysisToSelector.value() == value
    }

    void postDinnerMealAnalysisFrom(String value) {
        waitFor { postDinnerMealAnalysisFromSelector.displayed }
        postDinnerMealAnalysisFromSelector.value(value).click()
        assert postDinnerMealAnalysisFromSelector.value() == value
    }

    void postDinnerMealAnalysisTo(String value) {
        waitFor { postDinnerMealAnalysisToSelector.displayed }
        postDinnerMealAnalysisToSelector.value(value).click()
        assert postDinnerMealAnalysisToSelector.value() == value
    }

    void postBreakfastMealAnalysisFrom(String value) {
        waitFor { postBreakfastMealAnalysisFromSelector.displayed }
        postBreakfastMealAnalysisFromSelector.value(value).click()
        assert postBreakfastMealAnalysisFromSelector.value() == value
    }

    void postBreakfastMealAnalysisTo(String value) {
        waitFor { postBreakfastMealAnalysisToSelector.displayed }
        postBreakfastMealAnalysisToSelector.value(value).click()
        assert postBreakfastMealAnalysisToSelector.value() == value
    }

    void breakfastTimePeriodFrom(String value) {
        waitFor { breakfastTimePeriodFromSelector.displayed }
        breakfastTimePeriodFromSelector.value(value)
        sleep(300)
        assert breakfastTimePeriodFromSelector.value() == value
    }

    void breakfastTimePeriodTo(String value) {
        waitFor { breakfastTimePeriodToSelector.displayed }
        waitFor { breakfastTimePeriodToSelector.value(value) }
        if (breakfastTimePeriodToSelector.value() == value) {
            assert breakfastTimePeriodToSelector.value() == value
        } else {
            breakfastTimePeriodToSelector.value(value).click()
            assert breakfastTimePeriodToSelector.value() == value
        }

    }

    void lunchTimePeriodFrom(String value) {
        waitFor { lunchTimePeriodFromSelector.displayed }
        waitFor { lunchTimePeriodFromSelector.value(value) }
        if (lunchTimePeriodFromSelector.value() == value) {
            assert lunchTimePeriodFromSelector.value() == value
        } else {
            lunchTimePeriodFromSelector.value(value).click()
            assert lunchTimePeriodFromSelector.value() == value
        }
    }

    void lunchTimePeriodTo(String value) {
        waitFor { lunchTimePeriodToSelector.displayed }
        waitFor { lunchTimePeriodToSelector.value(value) }
        if (lunchTimePeriodToSelector.value() == value) {
            assert lunchTimePeriodToSelector.value() == value
        } else {
            lunchTimePeriodToSelector.value(value).click()
            assert lunchTimePeriodToSelector.value() == value
        }
    }

    void dinnerTimePeriodFrom(String value) {
        waitFor { dinnerTimePeriodFromSelector.displayed }
        waitFor { dinnerTimePeriodFromSelector.value(value) }
        if (dinnerTimePeriodFromSelector.value() == value) {
            assert dinnerTimePeriodFromSelector.value() == value
        } else {
            dinnerTimePeriodFromSelector.value(value).click()
            assert dinnerTimePeriodFromSelector.value() == value
        }
    }


    void dinnerTimePeriodTo(String value) {
        waitFor { dinnerTimePeriodToSelector.displayed }
        waitFor { dinnerTimePeriodToSelector.value(value) }
        if (dinnerTimePeriodToSelector.value() == value) {
            assert dinnerTimePeriodToSelector.value() == value
        } else {
            dinnerTimePeriodToSelector.value(value).click()
            assert dinnerTimePeriodToSelector.value() == value
        }
    }


    void eveningTimePeriodFrom(String value) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { eveningTimePeriodFromSelector.displayed }
        waitFor { eveningTimePeriodFromSelector.value(value) }
        if (eveningTimePeriodFromSelector.value() == value) {
            assert eveningTimePeriodFromSelector.value() == value
        } else {
            eveningTimePeriodFromSelector.value(value).click()
            assert eveningTimePeriodFromSelector.value() == value
        }
    }

    void eveningTimePeriodTo(String value) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { eveningTimePeriodToSel.displayed }
        waitFor { eveningTimePeriodToSel.value(value) }
        if (eveningTimePeriodToSel.value() == value) {
            assert eveningTimePeriodToSel.value() == value
        } else {
            eveningTimePeriodToSel.value(value).click()
            assert eveningTimePeriodToSel.value() == value
        }
    }

    void sleepingPeriodTo(String value) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { sleepingPeriodToSel.displayed }
        waitFor { sleepingPeriodToSel.value(value) }
        if (sleepingPeriodToSel.value() == value) {
            assert sleepingPeriodToSel.value() == value
        } else {
            sleepingPeriodToSel.value(value).click()
            assert sleepingPeriodToSel.value() == value
        }
    }

    void restoreDefaults() {
        $(id: 'reportpreference:revert').click()
        waitFor { $(id: 'reportpreference:revert').displayed }
        waitFor { js.('document.readyState') == 'complete' }
    }

    void verifyErrorColor(Navigator element, String color) {
        waitFor { $(element).displayed }
        assert $(element).css("background-color").contains(color)
    }

    void verifyBreakfastСonverted12hour30min() {
        waitFor { breakfastTimePeriodFromSelector.displayed }
        sleep(3000)
        if ( breakfastTimePeriodFromSelector.find("option")*.text().contains('12:00 AM') ) {
            assert breakfastTimePeriodFromSelector.find("option")*.text() == timeFormat30min
            assert breakfastTimePeriodToSelector.find("option")*.text() == timeFormat30min
        } else {
            assert breakfastTimePeriodFromSelector.find("option")*.text() == timeFormat30minJapan
            assert breakfastTimePeriodToSelector.find("option")*.text() == timeFormat30minJapan
        }
    }
    void verifyLunchСonverted12hour30min() {
        waitFor { lunchTimePeriodFromSelector.displayed }
        if (lunchTimePeriodFromSelector.find("option")*.text().contains('12:00 AM')) {
            assert lunchTimePeriodFromSelector.find("option")*.text() == timeFormat30min
            assert lunchTimePeriodToSelector.find("option")*.text() == timeFormat30min
        }else {
            assert lunchTimePeriodFromSelector.find("option")*.text() == timeFormat30minJapan
            assert lunchTimePeriodToSelector.find("option")*.text() == timeFormat30minJapan
        }
    }

    void verifyDinnerСonverted12hour30min() {
        waitFor { dinnerTimePeriodFromSelector.displayed }
        if (dinnerTimePeriodFromSelector.find("option")*.text().contains('12:00 AM')) {
            assert dinnerTimePeriodFromSelector.find("option")*.text() == timeFormat30min
            assert dinnerTimePeriodToSelector.find("option")*.text() == timeFormat30min
        }else {
            assert dinnerTimePeriodFromSelector.find("option")*.text() == timeFormat30minJapan
            assert dinnerTimePeriodToSelector.find("option")*.text() == timeFormat30minJapan
        }
    }

    void verifyEveningСonverted12hour30min() {
        waitFor { eveningTimePeriodFromSelector.displayed }
        if (eveningTimePeriodFromSelector.find("option")*.text().contains('12:00 AM')){
            assert eveningTimePeriodFromSelector.find("option")*.text() == timeFormat30min
            assert eveningTimePeriodToSel.find("option")*.text() == timeFormat30min
        }else {
            assert eveningTimePeriodFromSelector.find("option")*.text() == timeFormat30minJapan
            assert eveningTimePeriodToSel.find("option")*.text() == timeFormat30minJapan
        }
    }

    void verifySleepingСonverted12hour30min() {
        sleep(3000)
        waitFor { sleepingPeriodToSel.displayed }
        if (sleepingPeriodToSel.find("option")*.text().contains('12:00 AM')) {
            assert sleepingPeriodToSel.find("option")*.text() == timeFormat30min
        }else {
            assert sleepingPeriodToSel.find("option")*.text() == timeFormat30minJapan
        }
    }

    void lunchSectionError(String firstError, String secondError) {
        waitFor { js.('document.readyState') == 'complete' }
        assert $("#lunchsection > tr > td > ul.error > li", 0..1)*.text() == [firstError, secondError]
    }

    void lunchSectionError(String firstError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#lunchsection > tr > td > ul.error > li").text() == firstError }
        $("#lunchsection > tr > td > ul.error > li").text() == firstError
    }

    void breakfastSectionErrorOne(String firstError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#breakfastsection > tr > td > ul.error > li").text().contains(firstError) }

    }

    void breakfastSectionError(String firstError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor {   waitFor { $("#breakfastsection > tr > td > ul.error > li").text().contains(firstError) }}
        waitFor { $("#breakfastsection > tr > td > ul.error > li").text().contains(firstError) }

    }

    void breakfastSectionError(String firstError, String secondError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#breakfastsection > tr > td > ul.error > li")*.text() == [firstError, secondError] }

    }

    void dinnerSectionError(String firstError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#dinnersection > tr > td > ul.error > li").text().contains(firstError) }

    }

    void overnightSectionError(String firstError, String secondError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#overnightsection > tr:nth-child(4)").text().contains(firstError) }
        $("#overnightsection > tr:nth-child(4)").text().contains(secondError)
    }

    void overnightSectionErrorDouble(String firstError, String secondError) {
        waitFor { $("#overnightsection > tr:nth-child(4)").text().contains(firstError) }
        $("#overnightsection > tr:nth-child(4)").text().contains(secondError)
    }

    boolean overnightSectionSectionErrorFirst(String errorfirst) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor {$("#overnightsection").text()}
        waitFor { $("#overnightsection").text().contains(errorfirst) }

    }

    boolean overnightSectionSectionErrorFirst(String errorfirst, String errorsecond) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#overnightsection").text().contains(errorfirst) }
        $("#overnightsection").text().contains(errorfirst)
        $("#overnightsection").text().contains(errorsecond)
    }

    void dinnerSectionError(String firstError, String secondError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#dinnersection > tr > td > ul.error > li")*.text() == [firstError, secondError] }
    }

    void overnightSectionError(String firstError) {
        waitFor { js.('document.readyState') == 'complete' }
        waitFor { $("#overnightsection > tr > td > ul.error > li").text().contains(firstError) }

    }
/*----------------------------------------------------------------198----------------------------------------------------------*/

    void enterBreakfastBeforeLowVerify(String Low) {
        beforeBreakfastGlucoseTargetLow*.text() == Low
    }

    void enterBreakfastBeforeHighVerify(String High) {
        beforeBreakfastGlucoseTargetHigh.text() == High
    }

    void enterBreakfastAfterLowVerify(String Low) {
        afterBreakfastGlucoseTargetLow.text() == Low
    }

    void enterBreakfastAfterHighVerify(String High) {
        afterBreakfastGlucoseTargetHigh.text() == High
    }

    void enterLunchGlucoseTargetAfterHighVerify(String High) {
        afterLunchGlucoseTargetAfterHigh.text() == High
    }

    void postLunchMealAnalysisFromVerify(String value) {
        postLunchMealAnalysisFromSelector.text() == value
    }

    void postLunchMealAnalysisToVerify(String value) {
        postLunchMealAnalysisToSelector.text() == value
    }

    void postDinnerMealAnalysisFromVerify(String value) {
        postDinnerMealAnalysisFromSelector.text() == value
    }

    void postDinnerMealAnalysisToVerify(String value) {
        postDinnerMealAnalysisToSelector.text() == value
    }

    void postBreakfastMealAnalysisFromVerify(String value) {
        postBreakfastMealAnalysisFromSelector.text() == value
    }

    void postBreakfastMealAnalysisToVerify(String value) {
        postBreakfastMealAnalysisToSelector.text() == value
    }

    void breakfastTimePeriodFromVerify(String value) {
        breakfastTimePeriodFromSelector.text() == value
    }

    void breakfastTimePeriodToVerify(String value) {
        breakfastTimePeriodToSelector.text() == value
    }

    void lunchTimePeriodFromVerify(String value) {
        lunchTimePeriodFromSelector.text() == value
    }

    void lunchTimePeriodToVerify(String value) {
        lunchTimePeriodToSelector.text() == value
    }

    void dinnerTimePeriodFromVerify(String value) {
        dinnerTimePeriodFromSelector.text() == value
    }

    void dinnerTimePeriodToVerify(String value) {
        dinnerTimePeriodToSelector.text() == value
    }

    void eveningTimePeriodFromVerify(String value) {
        eveningTimePeriodFromSelector.text() == value
    }

    void eveningTimePeriodToVerify(String value) {
        eveningTimePeriodToSel.text() == value
    }

    void sleepingPeriodToVerify(String value) {
        sleepingPeriodToSel.text() == value
    }

    void enterLunchBeforeLowVerify(String Low) {
        lunchBeforeLow*.text() == [Low]
    }

    void enterSleepingBeforeLowVerify(String Low) {
        beforeSleepingGlucoseTargetLow*.text() == Low
    }

    void enterSleepingBeforeHighVerify(String High) {
        beforeSleepingGlucoseTargetHigh*.text() == High
    }

    void enterEveningBeforeLowVerify(String Low) {
        beforeEveningGlucoseTargetLow*.text() == Low
    }

    void enterEveningBeforeHighVerify(String High) {
        beforeEveningGlucoseTargetHigh*.text() == High
    }

    void enterDinnerBeforeLowVerify(String Low) {
        beforeDinnertGlucoseTargetLow*.text() == Low
    }

    void enterDinnerBeforeHighVerify(String High) {
        beforeDinnertGlucoseTargetHigh*.text() == High
    }

    void enterDinnerAfterLowVerify(String Low) {
        afterDinnertGlucoseTargetLow*.text() == Low
    }

    void enterDinnerAfterHighVerify(String High) {
        afterDinnertGlucoseTargetHigh*.text() == High
    }

    void enterLunchAfterLowVerify(String Low) {
        lunchAfterLow*.text() == [Low]
    }

    void enterLunchAfterHighVerify(String High) {
        lunchafterHigh*.text() == [High]
    }

    void verifyСonverted24hour() {
        def format24 = $(id: startsWith("reportpreference:breakfastStart"))*.text()
        assert !format24.contains('PM')
        assert !format24.contains('AM')
    }
    def timeFormat = ['12:30 AM', '1:00 AM', '12:30 PM', '1:00 PM', '6:00 PM', '10:00 PM', '6:00 AM', '12:30 AM']

    void мerifyСonverted12hour() {
        breakfastTimePeriodToSelector*.text() == timeFormat
    }

    void clinicPatientTab() {
        $('#current > a > span').click()
    }

    void clickEditNewPatientSettings() {
        $("p.change_settings > a").click()
    }

    void reportPreferenceSavePrefs() {
       waitFor { $(By.id("reportpreference:savePrefs")).click()}
    }

    void glucoseTargetHighValue(String value) {
        $(By.id("reportpreference:glucoseTargetHigh")).value(value).isDisplayed()
    }

    void glucoseTargetLowValue(String value) {
        $(By.id("reportpreference:glucoseTargetLow")).value(value).isDisplayed()
    }

    void glucoseTargetXtremeHighValue(String value) {
        $(By.id("reportpreference:glucoseTargetXtremeHigh")).value(value).isDisplayed()
    }

    void glucoseTargetXtremeLowValue(String value) {
        $(By.id("reportpreference:glucoseTargetXtremeLow")).value(value).isDisplayed()
    }

    void clinicSettingsTab() {
        $(By.xpath('(.//*[normalize-space(text()) and normalize-space(.)=\'Home\'])[1]/following::span[1]')).click()
    }

    void bgunitMmollValue(String value) {
        $(By.id("bgunit_mmoll")).value("on").displayed
    }

    void bgunitMgdlValue(String value) {
        $(By.id("bgunit_mgdl")).value("on").displayed
    }

    void convertingSavedBtn() {
        $(By.id("j_id_id33:convert")).click()
    }

    void convertingPatientSavedBtn() {
        $(id: ~/(.*?):convert/).click()
    }

    void successPopupSavedOkBtn() {
        waitFor { successPopupSavedOkBtnClick.displayed }
        successPopupSavedOkBtnClick.click()
    }

    void successPopupSavedOkBtnvalue(String value) {
      waitFor {  successPopupSavedOkBtnClick.value() == value }
    }

    boolean breakfastSectionError(String firstError, String secondError, String thirdError) {
        $("#breakfastsection > tr > td > ul.error > li", 0..2)*.text() == [firstError, secondError, thirdError]
    }

    boolean lunchSectionError(String firstError, String secondError, String thirdError) {
        $("#lunchsection > tr > td > ul.error > li", 0..2)*.text() == [firstError, secondError, thirdError]
    }

    boolean dinnerSectionError(String firstError, String secondError, String thirdError) {
        $("#dinnersection > tr > td > ul.error > li", 0..2)*.text() == [firstError, secondError, thirdError]
    }

    boolean overnightSectionError(String firstError, String secondError, String thirdError) {
        $("#overnightsection > tr > td > ul.error > li", 0..2)*.text() == [firstError, secondError, thirdError]
    }

    boolean targetRangeSectionSectionError(String firstError, String secondError, String thirdError) {
        $(".error > li", 0..2)*.text() == [firstError, secondError, thirdError]
    }

    boolean successPopupAppear(String successText) {
        waitFor { $("span.success").displayed }
        $("span.success").text().contains(successText)
    }

    void bgunit_mgdlSelect() {
        $(By.id("bgunit_mgdl")).click()
    }

    void glucoseTargetErrorMissing() {
        int count = $(" ul.error > li").size()
        if (count > 0) {
            log.info("The element exists!!")
        }
    }

    void breakfastsectionErrorMissing() {
        int breakfastsection = $("#breakfastsection > tr > td > ul.error > li").size()
        if (breakfastsection > 0) {
            log.info("The breakfastsection error exists!!")
        }
    }

    void mealsectionErrorMissing() {
        int mealsection = $("#mealsection > tr > td > ul.error > li").size()
        if (mealsection > 0) {
            log.info("The mealsection error exists!!")
        }
    }

    void dinnersectionErrorMissing() {
        int dinnersection = $("#dinnersection > tr > td > ul.error > li").size()
        if (dinnersection > 0) {
            log.info("The dinnersection error exists!!")
        }
    }

    void overnightsectionErrorMissing() {
        int overnightsection = $("#overnightsection > tr > td > ul.error > li").size()
        if (overnightsection > 0) {
            log.info("The overnightsection error exists!!")
        }
    }

    public void glucoseTargetRange() {
        String valueAttribute = driver.findElement(By.id(lowValue())).getAttribute("value");
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (valueAttribute.equals(driver.findElement(By.id(lowValue())).getAttribute("value"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    void clickDailyOverlayReport() {
        dailyOverlayReport.click()
    }

    public void backToMainPage() {
        driver.findElement(By.xpath("//a[@id='j_id_id14:home']/span")).click()
    }

    public String highValue() {
        return "reportpreference:glucoseTargetHigh"
    }

    public String lowValue() {
        return "reportpreference:glucoseTargetHigh"
    }

    public String glucoseTargetXtremeHigh() {
        return "reportpreference:glucoseTargetXtremeHigh"
    }

    public String glucoseTargetXtremeLow() {
        return "reportpreference:glucoseTargetXtremeLow"
    }

    public String beforeBreakfastGlucoseTargetLow() {
        return "reportpreference:beforeBreakfastGlucoseTargetLow"
    }

    public String beforeBreakfastGlucoseTargetHigh() {
        return "reportpreference:beforeBreakfastGlucoseTargetHigh"
    }

    public String eveningGlucoseTargetLow() {
        return "reportpreference:eveningGlucoseTargetLow"
    }

    public String eveningGlucoseTargetHigh() {
        return "reportpreference:eveningGlucoseTargetHigh"
    }

    public String sleepingGlucoseTargetLow() {
        return "reportpreference:sleepingGlucoseTargetLow"
    }

    public String sleepingGlucoseTargetHigh() {
        return "reportpreference:sleepingGlucoseTargetHigh"
    }

    public String bgunit_mgdl() {
        return "bgunit_mgdl"
    }

    public String timedisplay_hr12() {
        return "timedisplay_hr12"
    }

    void bgunit_mgdlVerify() {
        $(By.id("bgunit_mgdl")).value("on")
    }

    void selectTimehr12displayed() {
        waitFor { timedisplay_hr12Selector.displayed }
        timedisplay_hr12Selector.click()
    }

    def mgdlSelect() {
        glucoseUnitsMg.value("on").displayed
    }

    void mgdlSelectPatient() {
        if ($(By.id("bgunit_mgdl")).value("off")) {
            $(By.id("bgunit_mgdl")).click()
            sleep(300)
            $(By.id("j_id_id34:convert")).click()
        } else {
            println("mg/dL selected")
        }

    }

    void mmollSelectPatient() {
        if ($(By.id("bgunit_mmoll")).value("off")) {
            $(By.id("bgunit_mmoll")).click()
            sleep(300)
            $(By.id("j_id_id34:convert")).click()
        } else {
            println(" mmoll selected")
        }

    }

    public void isTextGlucoseRangesDisplayedCssSelector(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (helpTitleText.equals(driver.findElement(By.cssSelector(helpTitleElements)).getText())) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public void isTextGlucoseRangesDisplayedXpath(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (helpTitleText.equals(driver.findElement(By.xpath(helpTitleElements)).getText())) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    public String textSelector() {
        return "div.report_settings_range > p"
    }

    public String breakfasrCss() {
        return "option[value=\"6:00 AM\"]"
    }

    public String breakfasrToXpath() {
        return "(//option[@value='11:00 AM'])[2]"
    }

    public String breakfastMealTo() {
        return "(//option[@value='6'])[2]"
    }

    public String lunchTimeFrom() {
        return "(//option[@value='11:00 AM'])[3]"
    }

    public String lunchTimeTo() {
        return "(//option[@value='4:00 PM'])[4]"
    }

    public String LunchPostAnalysisTo() {
        return "(//option[@value='2'])[3]"
    }

    public String LunchPostAnalysisFrom() {
        return "(//option[@value='6'])[4]"
    }

    public String dinnerTimeFrom() {
        return "(//option[@value='4:00 PM'])[5]"
    }

    public String dinnerTimeTo() {
        return "(//option[@value='8:00 PM'])[6]"
    }

    public String dinnerPostAnalysisTo() {
        return "(//option[@value='2'])[5]"
    }

    public String dinnerPostAnalysisFrom() {
        return "(//option[@value='6'])[6]"
    }

    public String eveningTimePeriodFrom() {
        return "(//option[@value='8:00 PM'])[7]"
    }

    public String eveningTimePeriodTo() {
        return "(//option[@value='12:00 AM'])[8]"
    }

    public String sleepingTimePeriodTrom() {
        return "(//option[@value='12:00 AM'])[9]"
    }

    public String sleepingTimePeriodTo() {
        return "(//option[@value='4:00 AM'])[11]"
    }

    public String dawnTimePeriodFrom() {
        return "(//option[@value='4:00 AM'])[10]"
    }

    public String dawnTimePeriodTo() {
        return "(//option[@value='6:00 AM'])[12]"
    }

    public String hour24BreakfasrCss() {
        return "option[value=\"6:00\"]"
    }

    public String hour24BreakfasrToXpath() {
        return "(//option[@value='11:00'])[2]"
    }

    public String hour24LunchTimeFrom() {
        return "(//option[@value='11:00'])[3]"
    }

    public String hour24LunchTimeTo() {
        return "(//option[@value='16:00'])[4]"
    }

    public String hour24DinnerTimeFrom() {
        return "(//option[@value='16:00'])[5]"
    }

    public String hour24DinnerTimeTo() {
        return "(//option[@value='20:00'])[6]"
    }

    public String hour24BedtimeTimeFrom() {
        return "(//option[@value='20:00'])[7]"
    }

    public String hour24BedtimeTimeTo() {
        return "(//option[@value='0:00'])[8]"
    }

    public String hour24EveningTimePeriodFrom() {
        return "(//option[@value='0:00'])[9]"
    }

    public String hour24EveningTimePeriodTo() {
        return "(//option[@value='4:00'])[10]"
    }

    public String hour24SleepingTimePeriodTrom() {
        return "(//option[@value='4:00'])[11]"
    }

    public String hour24SleepingTimePeriodTo() {
        return "(//option[@value='6:00'])[12]"
    }

    public String errorGlucoseHigh() {
        return "ul.error > li"
    }

    String glucoseAccepts() {
        return "p"
    }

    public String errorGlucoseHighest() {
        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[2]"
    }

    public String errorGlucoseLow() {

        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[3]"
    }

    public String errorGlucoseLowest() {
        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[2]"
    }

    public String errorGlucoseLow2() {
        return "ul.error > li"
    }


    void enterGlucoseTargetHigh(String High) {
        waitFor { glucoseTargetHigh.displayed }
        glucoseTargetHigh = High
    }

    void enterGlucoseTargetLow(String low) {
        glucoseTargetLow = low
    }

    void enterGlucoseTargetXtremeLow(String low) {
        glucoseTargetXtremeLow = low
    }

    void enterGlucoseTargetXtremeHigh(String High) {
        driver.findElement(By.id("reportpreference:glucoseTargetXtremeHigh")).clear()
        glucoseTargetXtremeHigh = High
    }

    void yesMmolUprrove() {
        driver.findElement(By.id("j_id_id42:yes")).click()
    }

    void mmollPopupApprove() {
        driver.findElement(By.id("j_id_id48:convert")).click()
    }

    void bgunit_mmollApprove() {
        driver.findElement(By.id("bgunit_mmoll")).click()
    }

    public String errorGlucoseLowfrom50() {
        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[2]"
    }

    void clickEditPatientSettings() {
        driver.findElement(By.name("study:j_id_id223")).click()
    }


    public void systemAcceptsValue(String string) throws InterruptedException {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if (string.equals(driver.findElement(By.cssSelector(selector())).getText())) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    String selector() {
        return "div.report_settings_range > p"
    }

    void saveReportSettings() {
        waitFor { $(id: "reportpreference:savePrefs").displayed }
        $(By.id("reportpreference:savePrefs")).click()
    }

    void selectTimeDisplay24() {
        driver.findElement(By.id("timedisplay_hr24")).click()
    }

    void glucoseTargetLowMmolDefaul() {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if ("3.9".equals(driver.findElement(By.id("reportpreference:glucoseTargetLow")).getAttribute("value"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    void selectGlucoseUnitsMg() {
        $(By.id("bgunit_mgdl")).click()
    }

    void glucoseTargetHighMmolDefault() {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if ("10.0".equals(driver.findElement(By.id("reportpreference:glucoseTargetHigh")).getAttribute("value"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    void glucoseUnitsMmolUprrove() {
        driver.findElement(By.id("j_id_id48:convert")).click()
    }

    void selectGlucoseUnitsMmol() {
        $(By.id("bgunit_mmoll")).click()
    }

    void breakfastPostMealAnalysis() {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if ("1".equals(driver.findElement(By.cssSelector("option[value=\"2\"]")).getText())) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    void glucoseTargetHighDefault() {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if ("150".equals(driver.findElement(By.id("reportpreference:glucoseTargetHigh")).getAttribute("value"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }

    void glucoseTargetLowDefault() {
        for (int second = 0; ; second++) {
            if (second >= 60) fail("timeout");
            try {
                if ("70".equals(driver.findElement(By.id("reportpreference:glucoseTargetLow")).getAttribute("value"))) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }
    }


    void switchToWindowsIfVisibleAndVerifyPDFMg(String platform) {
        String winHandleBefore = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        sleep(3000)
        log.info('Login in the new clinic created')
        File file = new File("D:/Daily_Overlay_filemg.pdf")
        FileInputStream fis = new FileInputStream(file)
        PDFParser parser = new PDFParser(fis)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        PDFTextStripper strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        output.contains "Daily Overlay for Sample M. Patient"
        output.contains "Sep 28 - Oct 4, 2009"
        output.contains "(7 days)"
        output.contains "Sensor Data (mg/dL)"
        output.contains "Sensor Values"
        output.contains "Highest"
        output.contains "Lowest"
        output.contains "Average"
        output.contains "Standard Dev."
        output.contains "0MAD %"
        output.contains "Correlation"
        output.contains "# Valid Calibrations"
        output.contains "Designation"
        output.contains "(mg/dL/day)"
        output.contains "Average / Total"
        output.contains "C: No Calibration BGsS: No Sensor DataX: Use Clinical Judgment"
        output.contains "Excursions"
        output.contains "High Excursions"
        output.contains "Low Excursions"
        output.contains "AUC Above Limit"
        output.contains "AUC Below Limit"
        output.contains "≤40"
        output.contains "40"
        output.contains "≥400"
        output.contains "Within (70 - 180)"
        parser.getPDDocument().close()
        driver.switchTo().window(winHandleBefore);
    }


    void switchToWindowsIfVisibleAndVerifyPDFMml(String platform) {
        String winHandleBefore = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        File file = new File("D:/Daily_Overlay_filemmol.pdf")
        FileInputStream fis = new FileInputStream(file)
        PDFParser parser = new PDFParser(fis)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        PDFTextStripper strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        output.contains "Daily Overlay for Sample M. Patient"
        output.contains "Sep 28 - Oct 4, 2009"
        output.contains "(7 days)"
        output.contains "Sensor Data (mmol/L)"
        output.contains "Sensor Values"
        output.contains "Highest"
        output.contains "Lowest"
        output.contains "Average"
        output.contains "Standard Dev."
        output.contains "0MAD %"
        output.contains "Correlation"
        output.contains "# Valid Calibrations"
        output.contains "Designation"
        output.contains "(mg/dL/day)"
        output.contains "Average / Total"
        output.contains "C: No Calibration BGsS: No Sensor DataX: Use Clinical Judgment"
        output.contains "Excursions"
        output.contains "High Excursions"
        output.contains "Low Excursions"
        output.contains "AUC Above Limit"
        output.contains "AUC Below Limit"
        output.contains "≤2.2"
        output.contains "≥22.2"
        output.contains "22.2"
        parser.getPDDocument().close()
        driver.switchTo().window(winHandleBefore)
    }


    void fastingPeriodFrom(String value) {
        $(id: "reportpreference:fastingStart").value(value).click()
    }

    void fastingPeriodTo(String value) {
        $(id: "reportpreference:fastingEnd").value(value).click()
    }

    void verifyBreakfastСonverted24hour30min() {
        waitFor { breakfastTimePeriodFromSelector.displayed }
        assert breakfastTimePeriodFromSelector.find("option")*.text() == timeFormat30min24h
        sleep(3000)
        assert breakfastTimePeriodToSelector.find("option")*.text() == timeFormat30min24h
    }

    void verifyLunchСonverted24hour30min() {
        waitFor { lunchTimePeriodFromSelector.displayed }
        assert lunchTimePeriodFromSelector.find("option")*.text() == timeFormat30min24h
        assert lunchTimePeriodToSelector.find("option")*.text() == timeFormat30min24h
    }

    void verifyDinnerСonverted24hour30min() {
        sleep(3000)
        waitFor { dinnerTimePeriodFromSelector.displayed }
        assert dinnerTimePeriodFromSelector.find("option")*.text() == timeFormat30min24h
        assert dinnerTimePeriodToSelector.find("option")*.text() == timeFormat30min24h
    }

    void verifyEveningСonverted24hour30min() {
        waitFor { eveningTimePeriodFromSelector.displayed }
        assert eveningTimePeriodFromSelector.find("option")*.text() == timeFormat30min24h
        assert eveningTimePeriodToSel.find("option")*.text() == timeFormat30min24h
    }

    void verifySleepingСonverted24hour30min() {
        waitFor { sleepingPeriodToSel.displayed }
        waitFor { js.('document.readyState') == 'complete' }
        assert sleepingPeriodToSel.find("option")*.text() == timeFormat30min24h
    }
     void selectedDefaultGlucoseUnit() {
        if ($(By.id("bgunit_mmoll")).getAttribute('disabled')) {
            assert ('By deffoult selected mmol/l')
        } else {
            assert $(By.id("bgunit_mgdl")).value('on')
            assert ('By deffoult selected mg/dL')
        }
    }

     void glucoseUnitsAreaСannotСhange() {
        if ($('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')) {
            assert $('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')
        }
    }

    void backOriginalGlucoseUnits() {
        if ($('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')) {
            assert $('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')
        } else {
            $(id: "bgunit_mmoll").click()
            $(id: 'convert_dialog').find('.confirm_dialog_btn_div').find('input', id: ~/(.*?):convert/).click()
            $(id: "reportpreference:savePrefs").click()
            waitFor {$(id: "saved:okBtn").click()}
            $('.box_con_left_table > tbody:nth-child(1)').text() == "8.3"
            $(id: 'reportpreference:glucoseTargetLow').value() == "3.9"
            $(id: 'reportpreference:glucoseTargetXtremeHigh').value() == "13.9"
            $(id: 'reportpreference:glucoseTargetXtremeLow').value() == "2.8"
            $(id: "bgunit_mgdl").click()
            $(id: 'convert_dialog').find('.confirm_dialog_btn_div').find('input', id: ~/(.*?):convert/).click()
            $(id: "reportpreference:savePrefs").click()
            waitFor {$(id: "saved:okBtn").click()}

        }
    }

     void decimalSeparatorForNumbersGroupSeparator() {
        if ($('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')) {
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'lunchsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'dinnersection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'overnightsection').find('input')*.value() == ['4.4', '8.3', '4.4', '8.3']
        } else {
            $(id: 'breakfastsection').find('input')*.value() == ['70', '150', '70', '180']
            $(id: 'breakfastsection').find('input')*.value() == ['70', '150', '70', '180']
            $(id: 'breakfastsection').find('input')*.value() == ['70', '150', '70', '180']
            $(id: 'breakfastsection').find('input')*.value() == ['80', '150', '80', '150']
        }
    }

     void timeDisplayAreaDefault() {
        if (waitFor { $(id: "timedisplay_hr12") .value("on").displayed }) {
            println('Time Display Area hr12')
        } else {
            waitFor { $(id: "timedisplay_hr24") .value("on").displayed }
            println('Time Display Area hr24')
        }
    }

     void timeDisplayFormat() {
        if ($(id: "timedisplay_hr12").value() == 'on') {
            assert $("input", id: "timedisplay_hr24") .value() == null
            $("input", id: "timedisplay_hr24") .click()
            assert $("input", id: "timedisplay_hr24") .value() == 'on'
        } else {
            assert $(id: "timedisplay_hr12").value() == null
            $(id: "timedisplay_hr12").click()
            assert $(id: "timedisplay_hr12").value() == 'on'
            assert $("input", id: "timedisplay_hr24") .value() == null
        }
    }
    void sectionGlucoseUnitsDifferentValue() {
        if ($('.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(5)').getAttribute("outerHTML").contains('disabled')) {
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'lunchsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'dinnersection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'overnightsection').find('input')*.value() == ['4.4', '8.3', '4.4', '8.3']
        } else {
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
            $(id: 'breakfastsection').find('input')*.value() == ['3.9', '8.3', '3.9', '10.0']
        }
    }
    void timestampsEachMealPeriod(String timeeachMealPeriodSelector, String timeeachMealPeriod) {
        $(timeeachMealPeriodSelector).text().contains(timeeachMealPeriod)
    }

    void valuesGlucoseTargetRange() {
        if ( $('#report_settings > p:nth-child(3)').text().contains('3.9   mmol/L – 8.3   mmol/L')){
            assert $('#report_settings > p:nth-child(3)').text().contains('3.9   mmol/L – 8.3   mmol/L')
        }else {
            assert $('#report_settings > p:nth-child(3)').text().contains('70   mg/dL – 150   mg/dL')
        }
    }
    void patientHomeTabClick() {
        patientHomeTab.click()
    }
    void goToLogbook() {
        waitFor { $('.head > input:nth-child(4)').click() }
    }
    void dialogSuccessWinPatient() {
        waitFor { $(id: "success_dialog").displayed }
    }

    void dateRangeDisplayed(String timeRange) {
        waitFor { $('.head > h3:nth-child(2)').text().contains(timeRange) }
    }
    void waitForDataLoaded() {
        $('div.study').find(class: 'grn_blue').find('option:nth-child(2)').click()
        sleep(10000)
        waitFor { !$('td.data_table_r>ul>li>a').find('img').getAttribute('src').contains('red-swirl-small.gif') }
        if (!$('td.data_table_r>ul>li>a').find('img').getAttribute('src').contains('red-swirl-small.gif')){
            println()
        }else {
            !$('td.data_table_r>ul>li>a').find('img').getAttribute('src').contains('red-swirl-small.gif')
        }
    }

    void selectGenerateDataTable() {
        $('select.grn_blue').value(2)
    }


    void selectRegenerateReports() {
        $('select.grn_blue').value(2)
    }

}

