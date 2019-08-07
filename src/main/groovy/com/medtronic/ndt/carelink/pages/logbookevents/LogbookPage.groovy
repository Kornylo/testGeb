package com.medtronic.ndt.carelink.pages.logbookevents

import com.medtronic.ndt.carelink.data.ReadProperties
import geb.Page

class LogbookPage extends Page {
    static at = {
        closePatient.displayed || openLogbookButton.displayed || closeLogBook.displayed || uploadIpro.displayed
    }

    static content = {
        openLogbookButton(required: false) { $('div', class: 'head').$('input') }
        logBookContinue(required: false) { $(id: 'logbookfrm:continue') }
        closePatient(required: false) { $("a", id: "study:close") }
        addLogBookDataButton { $('div', class: 'tail').$('input') }
        editButton(required: false) { $(id: "log_edit_btn") }
        bgBox(required: false) { $(id: "add_bg") }
        enterButton(required: false) { $(id: "add_enter") }
        selectHour(required: false) { $(id: "add_time_hour") }
        selectMinute(required: false) { $(id: "add_time_minute") }
        ampm(required: false) { $(id: 'add_time_ampm') }
        addButton(required: false) { $(id: "log_add_btn") }
        closePopup(required: false) { $("div.ui-dialog:nth-child(10) > div:nth-child(1) > button:nth-child(2)") }
        uploadIpro(required: false) { $(id: "study:study") }
        signOutButton(required: false) { $("div.nav_right > ul > li:nth-child(1) > a") }
        logBookTable1(wait: true) { $(id: 'logbktable') }
        logBookTable2(wait: true) { $(id: 'logbktable_rt') }
        logBookTableContent(wait: true) { $(id: 'div_table') }
    }

    void openLogBook() {
        openLogbookButton.click()
        waitFor { driver.getCurrentUrl().contains("patient/logbook.jsf") }
    }

    void confirmLogBook() {
        if ($(id: 'btn_yes').displayed) (
                $(id: 'btn_yes').click()
        )
    }

    void isCloseLogBookDisplayed() {
        assert closeLogBook.displayed
    }

    void isAddLogBookDisplayed() {
        assert addLogBookDataButton.displayed
    }

    void closeLogBook() {
        logBookContinue.click()
        waitFor { driver.getCurrentUrl().contains("/patient/record.jsf") }
    }

    void updateRow(bg) {
        waitFor { $(id: "excludeTd4").displayed }
        sleep(2000)
        $(id: "excludeTd4").click()
        $(id: "excludeTd4").click()
        editButton.click()
        bgBox.value(bg)
        enterButton.click()
    }

    void clickAddButton() {
        waitFor { !$('.shadowcontent').displayed }
        waitFor { addButton.displayed && addButton.module(geb.module.FormElement).enabled }
        addButton.click()
    }

    void addNewRecordWithMealAndInsulin(hour, minute) {
        selectHour.value(hour)
        selectMinute.value(minute)
        $(id: "mealSize-small").click()
        $(id: "insulinType-premix").click()
        enterButton.click()
    }

    void addNewRecordWithMeds(hour, minute) {
        selectHour.value(hour)
        selectMinute.value(minute)
        $(id: "add_medication").click()
        $(id: "insulinType-premix").click()
        enterButton.click()
    }

    void addNewRecordWithBG(hour, minute, bg) {
        $(id: "add_date").value("9/29/2009")
        ampm.value("PM")
        selectHour.value(hour)
        selectMinute.value(minute)
        bgBox.value(bg)
        enterButton.click()
    }

    void clickClosePopup() {
        closePopup.click()
    }

    void logbookReturnPatientRecordPage() {
        assert $('.sv_btn_lbook').click()
        waitFor { $('#record > li:nth-child(2)> input').displayed }
    }

    void logbookSelectXbutton() {
        waitFor { $('div.ui-dialog:nth-child(10) > div:nth-child(1) > button:nth-child(2)').displayed }
        $('div.ui-dialog:nth-child(10) > div:nth-child(1) > button:nth-child(2)').click()
    }

    void logbookDecimalSeparatorMmolL() {
        $('#bg_td_select > label:nth-child(3)').text().contains(' mmol/L')
    }

    void logbookDecimalSeparator() {
        $(id: 'add_bg').value().contains('5.6')
    }

    void logbookDefaultGlucoseUnitLabel() {
        $(id: 'edit-bg-column-header').text().contains('BG')
    }

    void logbookAMPMTime() {
        assert ampm.click().value('AM').displayed
        assert ampm.click().value('PM').displayed
    }

    void logbookTimeHourSeparator() {
        $('span.add_time_hour_minute').text().contains(':')
    }

    void logbookCorrespondingDefaultTimeFormat(String AM, String PM) {
        assert ampm.click().value(AM).displayed
        assert ampm.click().value(PM).displayed
    }

    void logbookTimeLabel(String time) {
        assert $(id: 'event-time-label').text() == time
    }

    void logbookDateValue(String date) {
        assert $('#add_date').find("option").getAt(1).value().contains(date)
    }

    void logbookSelectAnyRow() {
        $(id: 'dateTd1').click()
    }

    void logbookDateFormat() {
        assert ($(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('2009'))
    }

    void LogbookColumnShowsDefaultTimeFormat() {
        waitFor { $(id: 'logbooks').find("td").displayed }
        assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('AM')
        assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('PM')
    }

    void glucoseUnitsDisplayed() {
        if ($(id: 'bg_header').text().contains('BG (mmol/L)')) {
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('7.2')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('5.6')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('4.0')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('8.3')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('5.0')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('4.4')
        } else {
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('7')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('5')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('4')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('8')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('5')
            assert $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains('4')
        }
    }

    void logbookEditButton() {
        $(id: 'log_edit_btn').click()
    }

    void verifyEventsDisplayed() {
        assert $(id: "log_book_row0").displayed
    }

    void verifyEventsDeleted() {
        waitFor { browser.getCurrentUrl().contains("patient/logbook.jsf") }
        assert !$(id: "log_book_row2").displayed
    }

    void verifyEventCreation(String s, String date, String s2) {
        waitFor { !$('.ui-widget-overlay.ui-front').displayed && !$('.shadowcontent').displayed }
        waitFor { logBookTable1.displayed && logBookTable2.displayed && logBookTableContent.displayed }
        assert logBookTableContent.text().contains(s) && logBookTableContent.text().contains(date) && !logBookTableContent.text().contains(s2)
    }

    void clickSignOut() {
        waitFor { signOutButton.displayed }
        signOutButton.click()
    }

    void addNewRecordDialog() {
        sleep(2000)
        waitFor { $(id: 'add_dialog').displayed && $('.ui-dialog-title').getAt(0).displayed }
        assert $('.ui-dialog-title').getAt(0).text() == ReadProperties.getProperties().get('logbook.text.record.add')
    }

    void enterTime(String time, String ampmV) {
        String hours = time.split(':| ').getAt(0)
        String minutes = time.split(':| ').getAt(1)
        js.exec("document.getElementById('add_time_hour').value=${hours}")
        js.exec("document.getElementById('add_time_minute').value=${minutes}")
        js.exec("document.getElementById('add_time_ampm').value=" + ampmV)
        ampm.value(ampmV.replaceAll("'", ''))
        assert selectHour.value() == hours
        assert selectMinute.value() == minutes
        assert ampm.value() == ampmV.replaceAll("'", '')
    }

    void selectMealSize(String size) {
        $('#mealSize-' + "$size").click()
        assert $('#mealSize-' + "$size").getAttribute('waschecked') == 'checked'
    }

    void enterCarbs(String amount) {
        js.exec("document.getElementById('carb-amount').value=${amount}")
        assert $('#carb-amount').value() == amount
    }

    void confirmationDialog() {
        waitFor { $('#add_msg_box').displayed }
        println($('#add_msg_box').text())
    }
}