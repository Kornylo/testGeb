package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI257 - Home Screen')
class SCUI257Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static HomePage homePage
    static CreatePatientPage createPatientPage
    static PatientStudyPage patientStudyPage

    def 'ScUI257 - Home Screen'() {

        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        signInPage.checkIncludedFooterElements()
        and: 'Create a Clinic with New Username and Password.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.getUserName()
        clinicEnterAdminInfoPage.clickContinue()
        (waitFor { browser.getCurrentUrl().contains("enroll/user.jsf") })
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        and: 'Record the Username'
        println("Username: " + adminUser)
        and: 'Record the Password'
        println("Password: Test1234@")
        finishClinicCreationPage.clickFinish()
        and: 'At the login screen login into with Username and password recorded above.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUser)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        then: 'Verify that Software navigate to the home screen.'
        when: ''
        homePage = browsers.at HomePage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        homePage.pleaseWaitPopup()
        then: 'Verify that there is a Open Patient Record Button, labeled as “Open patient”,'
        homePage.verifyOpenPatientBtn()
        then: 'Verify that Open patient button is inactive.'
        homePage.verifyOpenPatientBtnIsInactive()
        then: 'Verify that a Sample Patient Demo link labeled as “Sample Patient Demo”.'
        homePage.verifySamplePatientDemo()
        then: 'Verify that there is a new patient Button, labeled as “New patient”.'
        homePage.verifyNewPatientBtn()

        when: 'Press the New patient button.'
        homePage.clickNewPatientBtn()
        then: 'Verify that system navigate to the New Patient Record Screen.'
        when: ''
        createPatientPage = browsers.at CreatePatientPage
        then: ''

        when: 'Create patient with the First name and Last name and keep the patient ID blank.'
        and: 'Record the Patient First Name'
        and: 'Record the Patient Last Name'
        createPatientPage.createPatientWithoutID("Caporte", "Ahmedo")
        println("Firstname: Caporte")
        println("Lastname: Ahmedo")
        and: 'Fill all the required field and press save button.'
        createPatientPage.clickSaveBtn()
        then: 'Verify that System navigate to the Patient Record Screen for the newly created patient.'
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.verifyUserName("Ahmedo, Caporte")
        then: ''

        when: 'Press the Close patient button .'
        patientStudyPage.exitPatient()
        then: 'Verify that System navigate to the Home Screen.'
        when: ''
        homePage = browsers.at HomePage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        homePage.pleaseWaitPopup()
        then: 'Verify that Patient List shows “---“for patient ID as data element of patient earlier created.'
        homePage.searchInput("Ahmedo")
        homePage.verifyPatientID()

        when: 'Press the New patient button.'
        homePage.clickNewPatientBtn()
        then: 'Verify that system navigate to the New Patient Record Screen.'
        when: ''
        createPatientPage = browsers.at CreatePatientPage
        then: ''

        when: 'Create a patient with the Patient ID only with the First Name and Last Name blank.'
        createPatientPage.createPatientWithoutNames("666123")
        and: 'Record the Patient ID'
        println("Id: 666123")
        and: 'Fill all the required field and press save button.'
        createPatientPage.clickSaveBtn()
        then: 'Verify that System navigate to the Patient Record Screen for the newly created patient.'
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.verifyUserName("666123")
        then: ''

        when: 'Press the EXIT Button .'
        sleep(2000)
        patientStudyPage.exitPatient()
        then: 'Verify that System navigate to the Home Screen.'
        when: ''
        homePage = browsers.at HomePage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        homePage.pleaseWaitPopup()
        then: 'Verify that Patient List shows “---“for First name as data element of patient earlier created.'
        homePage.patientListFirstName("---")
        then: 'Verify that Patient List shows “---“for Last name as data element of patient earlier created.'
        homePage.patientListlastName("---")

        when: 'Press the Sample Patient Demo button.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        then: 'Verify that system navigate to the Patient Record Screen and display the Sample Patient Record created during enrollment.'
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.verifyUserName("Patient, Sample M.")
        then: ''
    }
}