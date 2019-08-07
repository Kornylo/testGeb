package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.pages.reports.DailySummaryReportPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Screenshot
@Title('RPRT399 - Daily Summary Report')
class RPRT399Spec extends CareLinkSpec {
    static SignInPage signInPage
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static ClinicSettingsPage clinicSettingsPage
    static LogbookPage logBookPage
    static DailySummaryReportPage dailySummaryReportPage
    static Precondition precondition

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Register new clinic'() {
        when: 'Execute RPRT399 per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below:'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on the Register Clinic link. Create a new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below: Username: (example: TC399) Password: (example: Password1)'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, "Test1234@")
        println("Username: " + adminUs + " Password: Test1234@")
        println("Email: " + emailAddress)
        then: ''
    }

    def 'Sign in with correct credentials'() {
        when: 'Sign into the MMT-7340 application using the credentials above.'
        signInPage.verifyLegalText()
        precondition.signInAsClinicAdmin(adminUs, "Test1234@")
        homePage = browsers.at HomePage
        and: 'Record the clinicId'
        homePage.getClinicIdFromHomePage()
        then: ''
    }

    def 'Add info on Logbook Page'() {
        when: 'Select Sample M. patient from list and click on [Open patient] button.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        patientStudyPage = browsers.at PatientStudyPage
        and: 'Click on [Edit] button in Patient Report Settings section.' +
                'If the default Glucose Units are “mg/dL” and Time Display "24 hour" proceed with the following steps:' +
                'Change the Glucose units to “mmol/L”. After the pop-up displays click on [OK] button. Change the Time Display to ' +
                '"12 hour" and then click on [Save] button. After the pop-up displays click on [Yes] button'
        patientStudyPage.clickEditButton()
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.selectGlucoseUnitsMmol()
        clinicSettingsPage.modalWindowVerifyClickOK('OK')
        clinicSettingsPage.selectTime12()
        clinicSettingsPage.clickSaveButton()
        and: 'Click on [Open Logbook] button.'
        logBookPage = browsers.at LogbookPage
        logBookPage.openLogBook()
        and: 'Update the fifth row according to this:' +
                '09/28/2009, 08:49 PM, BG: 21 mmol/L' +
                'Click on [Enter] button.'
        logBookPage.updateRow(21)
        and: 'Click on [Add] button.'
        logBookPage.clickAddButton()
        and: 'Enter Time and select two events (e.g. Meal and Insulin) then click on the [Enter] button.'
        logBookPage.addNewRecordWithMealAndInsulin("10", "00")
        and: 'Enter Time one minute later per events above and select one event (e.g. Meds) then click on the [Enter] button.'
        logBookPage.addNewRecordWithMeds("10", "01")
        and: 'Enter time between 10:55-11:59 PM on any day during the study (except the last day) and enter BG value then click on the [Enter] button.'
        logBookPage.addNewRecordWithBG("10", "57", "15")
        and: 'Close Add new record window.'
        logBookPage.clickClosePopup()
        then: ''
    }

    def 'Verify PDF'() {
        when: 'Click on [Done] button.'
        logBookPage.closeLogBook()
        and: 'Click on Daily Summary report link.'
        dailySummaryReportPage = browsers.at DailySummaryReportPage
        then: 'Verify present and availability of the Daily Summary Report.'
        then: 'The system provides a Daily Summary Report.'
        then: 'Verify that the report title is “Daily Summary” and report includes up to seven 24-hours of study data displayed in 7 calendar days.'
        then: 'The system provides seven 24-hours of study data displayed in 8 calendar days.'
        then: 'Verify that a legend identifies the following:' +
                '- Calibration BG' +
                '- BG' +
                '- Meal: Unknown size, Small, Medium and Large' +
                '- Insulin' +
                '- Exercise: Unknown intensity, Light, Moderate, and Intense' +
                '- Meds' +
                '- Note' +
                '- Target Range' +
                '- Bedtime / Wake-up' +
                'at the top of both pages.'
        then: 'The legend includes all of these identifications at the top of both pages.'
        then: 'Verify that the system displays Meter BG data points for all readings.'
        then: 'The markers are displayed in the graph.'
        then: 'Verify that the system plots event marker data from uploaded meter readings and logsheet entries and icons are not overlapped.'
        then: 'The event markers are plotted as: Meal Size, Exercise, Medication, Notes, Insulin Type, Sleep icon, BG icon ' +
                'and BG calibration icon using the respective symbol indicated in legend.'
        then: 'Verify that the BG reading entered in step 1.5.15 is displayed in the chart and BG icon is not preceding the next day\'s chart.'
        then: 'BG icon is plotted correctly in the chart.'
        then: 'Verify that if [Patient_Settings_Glucose_Units] is mmol/L and there are sensor values below 2.2 or above 22.2, ' +
                'the following text is displayed above the first graph “A thicker flat sensor trace at 2.2 ' +
                'or 22.2 mmol/L indicates CGM values can be outside these limits.”'
        then: 'The message “A thicker flat sensor trace at 2.2 or 22.22 mmol/L indicates CGM values can be outside these limits.” Is displayed.'
        then: 'Verify that the scale range is from 0 to 22.2 labelled “≥22.2”.'
        then: 'The scale range is from 0 to 22.2 labelled “≥22.2”.'
        then: 'Verify that the value of “≤2.2” (mmol/L) is marked on the Y-axis.'
        then: 'The value of “≤2.2” (mmol/L) is marked on the Y-axis.'
        then: 'Verify that the plotted line is thicker when the sensor data value is 2.2 or 22.2.'
        then: 'The plotted line is thicker when the sensor data value is 2.2 or 22.2.'
        dailySummaryReportPage.verifyReportMmolL()
        println("Next steps should be verify manually from the Perfecto logs:\r\n" +
                "The markers are displayed in the graph.\r\n" +
                "Verify that the system plots event marker data from uploaded meter readings and logsheet entries and icons are not overlapped.\r\n" +
                "The event markers are plotted as: Meal Size, Exercise, Medication, Notes, Insulin Type, Sleep icon, BG icon and BG calibration icon using the respective symbol indicated in legend.\r\n" +
                "Verify that the BG reading entered in step 1.5.15 is displayed in the chart and BG icon is not preceding the next day's chart.\r\n" +
                "BG icon is plotted correctly in the chart.\r\n" +
                "Verify that the value of “≤2.2” (mmol/L) is marked on the Y-axis.\r\n" +
                "The value of “≤2.2” (mmol/L) is marked on the Y-axis.\r\n" +
                "Verify that the plotted line is thicker when the sensor data value is 2.2 or 22.2.\r\n" +
                "The plotted line is thicker when the sensor data value is 2.2 or 22.2.")
    }

    def 'Update units'() {
        when: 'Close the report. Click on [Edit] button in Patient Report Settings section.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickEditButton()
        and: 'Change the Glucose units to “mg/dL”. After the pop-up displays click on [OK] button and then click on [Save] button. After the pop-up displays click on [Yes] button.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.bgunitMgdlClick()
        clinicSettingsPage.modalWindowVerifyClickOK('OK')
        clinicSettingsPage.clickSaveButton()
        then: ''
    }

    def 'Verify updated PDF'() {
        when: 'Click on Daily Summary report link.'
        dailySummaryReportPage = browsers.at DailySummaryReportPage
        and: 'Navigate to the graph section.'
        then: 'Verify that if [Patient_Settings_Glucose_Units] is mg/dL and there are sensor values below 40 or above 400, ' +
                'the following text is displayed above the first graph “A thicker flat sensor trace at 40 ' +
                'or 400 mg/dL indicates CGM values can be outside these limits.”'
        then: 'The message “A thicker flat sensor trace at 40 or 400 mg/dL indicates CGM values can be outside these limits.”'
        then: 'Verify that the scale range is from 0 to 400 labelled “≥400”.'
        then: 'The scale range is from 0 to 400 labelled “≥400”.'
        then: 'Verify that the value of “≤40” (mg/dL) is marked on the Y-axis.'
        then: 'The value of “≤40” (mg/dL) is marked on the Y-axis.'
        then: 'Verify that the plotted line is thicker when the sensor data value is 40 or 400.'
        then: 'The plotted line is thicker when the sensor data value is 40 or 400.'
        then: 'Verify that the text and icons in the legend do not overlap.'
        then: 'The text and icons in the legend are displayed correctly.'
        dailySummaryReportPage.verifyReportMgdL()
        println("Next steps should be verify manually from the Perfecto logs:\r\n" +
                "Verify that the value of “≤40” (mg/dL) is marked on the Y-axis.\r\n" +
                "The value of “≤40” (mg/dL) is marked on the Y-axis.\r\n" +
                "Verify that the plotted line is thicker when the sensor data value is 40 or 400.\r\n" +
                "The plotted line is thicker when the sensor data value is 40 or 400.\r\n" +
                "Verify that the text and icons in the legend do not overlap.\r\n" +
                "The text and icons in the legend are displayed correctly.")
        then: 'End of test.'
    }
}