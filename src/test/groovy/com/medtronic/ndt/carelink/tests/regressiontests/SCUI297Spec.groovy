package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.pages.reports.PatientReportSettingsPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@Title('SCUI297 - Patient Report Settings Screen - Time Periods (12h)')
@RegressionTest
@Slf4j
@Stepwise
@Screenshot

class SCUI297Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static CreatePatientPage createPatientPage
    static PatientReportSettingsPage patientReportSettingsPage
    static DailyOverlayReportPage dailyOverlayReportPage
    static Precondition precondition

    static final adminUs = Calendar.getInstance().format('MMMddHHss').toString().toLowerCase()
    static final emailAddress = Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final loginPassword = "MaluyScUI221"


    def 'Open Carelink application'() {
        when: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below'
        signInPage = browsers.to SignInPage
        signInPage.checkIncludedFooterElements()
        then:''
    }
    def 'Pre-condition Register US clinic'() {
        when: 'Click on Register Clinic link. Create US clinic and record the info below: Clinic Name:'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, loginPassword)
        then: 'Create a new administrative user and record the info below: Username: (example: TCXXX) Password: ___________________________ (example: Password1)'
        println("Username: " + adminUs + " Password: " + loginPassword)
    }
    def 'Sign in'() {
        when: 'At the login screen login into with Username and password recorded above.'
        then: 'Sign into the  application using the above credentials'
        precondition.signInAsClinicAdmin(adminUs,loginPassword)
        sleep(3000)
        then: 'Record the Username. Record the Password.'
        println("Username: " + adminUs + " Password:" + loginPassword)
    }
    def 'Create new Patient'() {
        when: 'At the Home Screen, click on the “New patient” button.'
        sleep(3000)
        signInPage.postponeMFA()
        signInPage.signoutButtonDisplayed()
        signInPage.clickNewPatient()
        then: 'At the New Patient Record Screen, enter in valid data for the User Input Fields.'
        when: ''
        createPatientPage = browsers.at CreatePatientPage
        then:
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.enterPatientEmail('test@tesc.com')
        createPatientPage.physicianEnter('test')
        createPatientPage.selectTherapyTypeInsulinPump()
        then: 'Record entries below. "Patient ID","First Name,"Last Name","Date of Birth","Email Address"," Physician"," Diabetes Type"'
        String FirstName = createPatientPage.getPatientFirstName()
        String LastName = createPatientPage.getPatientLastName()
        String PatientID = createPatientPage.getPatientID()
        println(FirstName)
        println(LastName)
        println(PatientID)
        println(PatientID)
        println('Physician: test')
        println('Email Address: test@tesc.com')
        println('Date of Birth: March, 20, 1978, Diabetes Type: Insulin Pump')
        then: 'At the New Patient Record Screen, click on the “Save” button'
        createPatientPage.clickSaveBtn()
    }
    def 'Patient Report Settings'(){
        when: 'At the Patient Record Screen press the Edit link.'
        createPatientPage.settingsPatient()
        then: 'Observe the Patient Report Settings Screen.'
        when: ''
        patientReportSettingsPage = browsers.at PatientReportSettingsPage
        then: 'Change the time display to 12 hour.'
        patientReportSettingsPage.selectTime12click()
        then: 'Observe the time periods.'
        when: ''
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: 'Verify that, for Breakfast From time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        waitFor {dailyOverlayReportPage.glucoseTargetXtremeHigh.displayed}
        dailyOverlayReportPage.verifyBreakfastСonverted12hour30min()
        then: 'Enter in a time for Breakfast From Time that is earlier than the Sleeping To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodFrom('5:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing threportpreference:breakfastEnde failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period cannot overlap with Sleeping Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodFromSelector, '255, 255')
        then: 'Enter in a time for Breakfast From Time that is equal to the Sleeping To time and press the Save button.'
        then: 'Verify that, for Breakfast From time must be earlier the Sleeping To time.'
        sleep(3000)
        dailyOverlayReportPage.breakfastTimePeriodFrom('6:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in a time for Breakfast From Time that is later than the Sleeping To time and press the Save button.'
        then: 'Verify that, for Breakfast From time must be earlier the Sleeping To time'
        dailyOverlayReportPage.breakfastTimePeriodFrom('6:00 AM')
        then: 'Navigate back to Patient Report Settings'
        patientReportSettingsPage.saveReportSettings()
        createPatientPage.settingsPatient()
        then: 'Enter in a Breakfast From time that is greater than the Breakfast To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodFrom('11:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Breakfast From time that is equal to the Breakfast To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodFrom('10:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Breakfast From time that is earlier to the Breakfast To time and press the Save button.'
        then: 'Verify that, for Breakfast From time must be earlier Breakfast To time.'
        dailyOverlayReportPage.breakfastTimePeriodFrom('9:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Breakfast From time.'
        then: 'Verify that, for Breakfast To time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyBreakfastСonverted12hour30min()
        then: 'Enter in a Breakfast To time that is equal to the Breakfast From time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo('6:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Breakfast To time that is earlier to the Breakfast From time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo('5:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodToSelector, '255, 255')
        then: 'Enter in a Breakfast To time that is later to the Breakfast From time and press the Save button.'
        then: 'Verify that, for Breakfast To time must be later Breakfast From time.'
        dailyOverlayReportPage.breakfastTimePeriodTo('11:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a time for Breakfast To Time that is greater than the Lunch From time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo('12:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodToSelector, '255, 255')
        then: 'Enter in a Breakfast To time that is equal to the Lunch From time and press the Save button.'
        then: 'Verify that, for Breakfast To time must be equal to the Lunch From time.'
        dailyOverlayReportPage.breakfastTimePeriodTo('10:30 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in a Breakfast To time that is earlier to the Lunch From time and press the Save button.'
        then: 'Verify that, for Breakfast To time must be earlier to the Lunch From time'
        dailyOverlayReportPage.breakfastTimePeriodTo('10:30 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Breakfast To time.'
        then: 'Verify that, for Lunch From time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyLunchСonverted12hour30min()
        then: 'Enter in a Lunch From time that is earlier than the Breakfast To time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodFrom('9:30 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Breakfast Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Lunch From time that is equal than the Breakfast To time and press the Save button.'
        then: 'Verify that, for Lunch From time must be earlier the Breakfast To time.'
        dailyOverlayReportPage.lunchTimePeriodFrom('11:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Lunch From time that is later than the Breakfast To time and press the Save button.'
        then: 'Verify that, for Lunch From time must be earlier the Breakfast To time'
        then: 'Enter in a Lunch From time that is greater than the Lunch To time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodFrom('4:30 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Breakfast Time Period.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Lunch From time that is equal than the Lunch To time and press the Save button'
        dailyOverlayReportPage.lunchTimePeriodFrom('3:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Lunch From time that is earlier than the Lunch To time and press the Save button.'
        then: 'Verify that, for Lunch From time must be earlier To time.'
        dailyOverlayReportPage.lunchTimePeriodFrom('11:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Lunch From time .'
        then: 'Verify that, for Lunch To time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyLunchСonverted12hour30min()
        then: 'Enter in a Lunch To time that is equal than the Lunch From time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodTo('11:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodToSelector, '255, 255')
        then: 'Enter in a Lunch To time that is earlier than the Lunch From time and press the Save button'
        dailyOverlayReportPage.lunchTimePeriodTo('10:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodToSelector, '255, 255')
        then: 'Enter in a Lunch To time that is later than the Lunch From time and press the Save button.'
        then: 'Verify that, for Lunch To time must be later, From time.'
        dailyOverlayReportPage.lunchTimePeriodTo('4:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Lunch To time that is greater than the Dinner From time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodTo('6:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Dinner Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.lunchSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.breakfastTimePeriodToSelector, '255, 255')
        then: 'Enter in a Lunch To time that is equal than the Dinner From time and press the Save button.'
        then: 'Verify that, for Lunch To time must be equal to the Dinner From time.'
        dailyOverlayReportPage.lunchTimePeriodTo('3:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in a Lunch To time that is earlier than the Dinner From time and press the Save button'
        then: 'Verify that, for Lunch To time must be earlier to the Dinner From time'
        dailyOverlayReportPage.lunchTimePeriodTo('2:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        then: 'Enter in an appropriate selection for Lunch To time.'
        createPatientPage.settingsPatient()
        then: 'Verify that, for Dinner From time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyDinnerСonverted12hour30min()
        then: 'Enter in a Dinner From time that is earlier than the Lunch To time and press the Save'
        dailyOverlayReportPage.dinnerTimePeriodFrom('1:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period cannot overlap with Lunch Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Dinner From time that is equal than the Lunch To time and press the Save'
        then: 'Verify that, for Dinner From time must be earlier the Lunch To time.'
        dailyOverlayReportPage.dinnerTimePeriodFrom('5:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Dinner From time that is greater than the Lunch To time and press the Save'
        then: 'Verify that, for Dinner From time must be earlier the Lunch To time'
        dailyOverlayReportPage.dinnerTimePeriodFrom('4:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Dinner From time that is greater than the Dinner To time and press the Save'
        dailyOverlayReportPage.dinnerTimePeriodFrom('9:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Dinner From time that is equal than the Dinner To time and press the Save.'
        dailyOverlayReportPage.dinnerTimePeriodFrom('8:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Dinner From time that is earlier than the Dinner To time and press the Save'
        then: 'Verify that, for Dinner From time must be earlier Dinner To time.'
        dailyOverlayReportPage.dinnerTimePeriodFrom('5:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Dinner From time.'
        then: 'Verify that, for Dinner To time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyDinnerСonverted12hour30min()
        then: 'Enter in a Dinner To time that is earlier than the Dinner From time and press the Save'
        dailyOverlayReportPage.dinnerTimePeriodTo('4:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodToSelector, '255, 255')
        then: 'Enter in a Dinner To time that is equal than the Dinner From time and press the Save'
        dailyOverlayReportPage.dinnerTimePeriodTo('5:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodToSelector, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Enter in a Dinner To time that is later to the Dinner From time and press the Save button'
        dailyOverlayReportPage.dinnerTimePeriodTo('8:00 PM')
        patientReportSettingsPage.saveReportSettings()
        createPatientPage.settingsPatient()
        then: 'Verify that, for Dinner To time must be later From time.'
        then: 'Enter in a Dinner To time that is later to the Evening From time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodTo('11:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.dinnerSectionError("Time Period cannot overlap with Evening Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodToSelector, '255, 255')
        then: 'Enter in a Dinner To time that is earlier to the Evening From time and press the Save button.'
        then: 'Verify that, for Dinner To time must be earlier to the Evening From time.'
        dailyOverlayReportPage.dinnerTimePeriodTo('10:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Record Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Dinner To time that is equal to the Evening From time and press the Save button.'
        then: 'Verify that, for Dinner To time, the value must be earlier than or equal to the Evening From time.  '
        dailyOverlayReportPage.dinnerTimePeriodTo('10:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Dinner To time.'
        then: 'Verify that, for Evening From time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyEveningСonverted12hour30min()
        then: 'Enter in an Evening From that is earlier to the Dinner To time and press the Save button'
        dailyOverlayReportPage.eveningTimePeriodFrom('7:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionSectionErrorFirst("Time Period 'From' must be before 'To'.","Time Period cannot overlap with Dinner Time Period.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Evening From time that is equal to the Dinner To time and press the Save button.'
        then: 'Verify that, for Evening From time, the value must be equal to or later than the Dinner To time.  '
        dailyOverlayReportPage.eveningTimePeriodFrom('8:00 PM')
        then: 'Navigate back to Patient Report Settings.'
        patientReportSettingsPage.saveReportSettings()
        then: 'Enter in a Evening From time that is later to the Dinner To time and press the Save button.'
        then: 'Verify that, for Evening From time, the value must be equal to or later than the Dinner To time'
        dailyOverlayReportPage.eveningTimePeriodFrom('11:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in a Evening From time that is later to the Evening To time and press the Save button.'
        dailyOverlayReportPage.eveningTimePeriodFrom('4:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionErrorDouble("Time Period 'From' must be before 'To'.", "Time Period cannot overlap with Dinner Time Period.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Evening From time that is equal to the Evening To time and press the Save button'
        dailyOverlayReportPage.eveningTimePeriodFrom('3:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.dinnerTimePeriodFromSelector, '255, 255')
        then: 'Enter in a Evening From time that is earlier to the Evening To time and press the Save button'
        then: 'Verify that, for Evening From time, the value must be earlier than the Evening To time.'
        dailyOverlayReportPage.eveningTimePeriodFrom('11:30 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Evening From time.'
        then: 'Verify that, for Evening To time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifyEveningСonverted12hour30min()
        then: 'Enter in a Evening To time that is equal to the Evening From time and press the Save button'
        dailyOverlayReportPage.eveningTimePeriodTo('11:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.eveningTimePeriodToSel, '255, 255')
        then: 'Enter in a Evening To time that is earlier to the Evening From time and press the Save button'
        dailyOverlayReportPage.eveningTimePeriodTo('4:00 PM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period 'From' must be before 'To'.", "Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted.'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.eveningTimePeriodToSel, '255, 255')
        then: 'Enter in an Evening To time that is later to the Evening From time and press the Save button'
        dailyOverlayReportPage.eveningTimePeriodTo('12:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings.'
        createPatientPage.settingsPatient()
        then: 'Verify that, for Evening To time, the value must be later than the Evening From time.'
        then: 'Verify that, for Sleeping From time, the value is equal to Evening To time and is not user modifiable. .'
        then: 'Enter in an appropriate selection for Sleeping To time.'
        dailyOverlayReportPage.eveningTimePeriodFrom('11:30 PM')
        dailyOverlayReportPage.eveningTimePeriodTo('12:00 AM')
        then: 'Verify that, for Sleeping To time, user can select from 12:00 AM to 11:30 PM in 30 min steps.'
        dailyOverlayReportPage.verifySleepingСonverted12hour30min()
        dailyOverlayReportPage.restoreDefaults()
        then: 'Enter in a Sleeping To that is equal to the Sleeping From time and press the Save button'
        dailyOverlayReportPage.sleepingPeriodTo('3:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period must be at least 0.5 hours long.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
       dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.sleepingPeriodToSel, '255, 255')
        then: 'Enter in a Sleeping To that is earlier to the Sleeping From time and press the Save button.'
        dailyOverlayReportPage.sleepingPeriodTo('2:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
       dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.sleepingPeriodToSel, '255, 255')
        then: 'Enter in a Sleeping To that is later to the Sleeping From time and press the Save button'
        then: 'Verify that, for Sleeping To time, the value must be later than the Sleeping From time.'
        dailyOverlayReportPage.sleepingPeriodTo('6:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Record Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in a Sleeping To that is later to the Breakfast From time and press the Save button'
        dailyOverlayReportPage.sleepingPeriodTo('7:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Verify that a validation error message describing the failure is displayed.'
        dailyOverlayReportPage.overnightSectionError("Time Period cannot overlap with Breakfast Time Period.")
        then: 'Verify that the erring field is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.overnightSectionArea, '255, 255')
        then: 'Verify that the entire Time Period section is highlighted'
        dailyOverlayReportPage.verifyErrorColor(dailyOverlayReportPage.sleepingPeriodToSel, '255, 255')
        then: 'Enter in a Sleeping To that is earlier to the Breakfast From time and press the Save button.'
        then: 'Verify that, for Sleeping To time, the value must be earlier than or equal to the Breakfast From time.  '
        dailyOverlayReportPage.sleepingPeriodTo('4:00 AM')
        patientReportSettingsPage.saveReportSettings()
        createPatientPage.settingsPatient()
        then: 'Enter in a Sleeping To that is equal to the Breakfast From time and press the Save button.'
        then: 'Verify that, for Sleeping To time, the value must be earlier than or equal to the Breakfast From time  '
        dailyOverlayReportPage.sleepingPeriodTo('4:00 AM')
        patientReportSettingsPage.saveReportSettings()
        then: 'Navigate back to Patient Report Settings'
        createPatientPage.settingsPatient()
        then: 'Enter in an appropriate selection for Sleeping To time.'
        dailyOverlayReportPage.sleepingPeriodTo('7:00 AM')
        then: 'Close software under test.'

    }
}
