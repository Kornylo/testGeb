package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SaUI010 - Patient Information Screen Functionality')
@Stepwise
@RegressionTest
@Slf4j
class SAUI010Spec extends CareLinkSpec{

    static SignInPage signInPage
    static LoginPage loginPage
    static CreatePatientPage createPatientPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Open Carelink application' () {

        when: 'Open MMT-7340 application (Carelink system)'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address'
        log.info(driver.getCurrentUrl())
        when: 'At the new clinic registration page'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then: 'Click on register clinic and click continue'
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
        when: 'At the locale page'
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Select the locale under which clinic will be created'
        clinicLocalePage.selectLocale()
        when: 'At the EULA page'
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Accept the EULA'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        when: 'At the Clinic Info page'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Enter all the clinic info'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        when: 'At the Clinic admin info page'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Enter all the user details'
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
        when: 'At the login screen'
        loginPage = browsers.at LoginPage
        then: 'Sign into the MMT-7340 application using the above credentials'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        sleep(10000)
        then: 'From the home screen navigate to new patient record screen'
        signInPage.clickNewPatient()
    }
    def 'Patient information screen' () {
        when: 'At the create patient page'
        createPatientPage = browsers.at CreatePatientPage
        then: 'Enter the patient info and create new patient'
        createPatientPage.patientFirsName()
        createPatientPage.patientLastName()
        createPatientPage.patientID()
        createPatientPage.patientEmail()
        createPatientPage.physicianName()
        createPatientPage.createPatient()
        createPatientPage.clickCancelBtn()

        then: 'Navigate to patient information screen.'
        then: 'Update all the user input fields with test< '
        then: 'Verify first name cannot be saved with < symbol.'
        then: 'Verify last name cannot be saved with < symbol.'
        then: 'Verify patient ID cannot be saved with < symbol.'
        then: 'Verify patient email cannot be saved with < symbol.'
        then: 'Verify physician name cannot be saved with < symbol.'
        then: 'Update all the user input fields with test> '
        then: 'Verify first name cannot be saved with > symbol.'
        then: 'Verify last name cannot be saved with > symbol.'
        then: 'Verify patient ID cannot be saved with > symbol.'
        then: 'Verify patient email cannot be saved with > symbol.'
        then: 'Verify physician name cannot be saved with > symbol.'
        then: 'Update all the user input fields with test& '
        then: 'Verify first name cannot be saved with & symbol.'
        then: 'Verify last name cannot be saved with & symbol.'
        then: 'Verify patient ID cannot be saved with & symbol.'
        then: 'Verify patient email cannot be saved with & symbol.'
        then: 'Verify physician name cannot be saved with & symbol.'
        then: 'Update all the user input fields with test= '
        then: 'Verify first name cannot be saved with = symbol.'
        then: 'Verify last name cannot be saved with = symbol.'
        then: 'Verify patient ID cannot be saved with = symbol.'
        then: 'Verify patient email cannot be saved with = symbol.'
        then: 'Verify physician name cannot be saved with = symbol.'
        then: 'Leave first name, last name, patient id fields blank and save changes.'
        then: 'Verify first name is required when patient id is blank.'
        then: 'Verify last name is required when patient id is blank.'
        then: 'Update first name field with more than 40 characters'
        then: 'Verify first name field cannot be more than 40 characters.'
        then: 'Update last name field with more than 40 characters'
        then: 'Verify last name field cannot be more than 40 characters.'
        then: 'Update patient ID field with more than 20 characters'
        then: 'Verify patient ID field cannot be more than 20 characters'
        then: 'Update patient email field with more than 80 characters'
        then: 'Verify patient email field cannot be more than 80 characters'
        then: 'Update physician name text field with more than 80 characters.'
        then: 'Verify physician name cannot be more than 80 characters'
        then: 'Update the first name field with 40 characters'
        then: 'Verify that a patient with 40 characters in first name field is created.'
        then: 'Update the last name field with 40 characters'
        then: 'Verify that a patient with 40 characters in last name field is created.'
        then: 'Update the patient ID with 20 characters'
        then: 'Verify that a patient with 20 characters in patient ID field is created.'
        then: 'Update patient email with 80 characters'
        then: 'Verify that a patient with 80 characters in patient email field is created.'
        then: 'Update physician name with 80 characters'
        then: 'Verify that a patient with 80 characters in physician name field is created.'
        then: 'Verify first name was saved without any leading and trailing spaces.'
        then: 'Verify last name was saved without any leading and trailing spaces.'
        then: 'Verify patient ID was saved without any leading and trailing spaces.'
        then: 'Verify physician name was saved without any leading and trailing spaces.'
        then: 'Verify patient email value cannot contain any spaces.'
        then: 'Fill in patient email text field without @ and save changes.'
        then: 'Verify patient email value should include @ character.'
        then: 'Fill in patient email text field without . character'
        then: 'Verify patient email value should include . character'
        then: 'Fill in patient email text field with test@. and save changes.'
        then: 'Verify patient email value should not be saved with @ and . characters in a row.'
        then: 'Fill in patient email with one after character after . like test@test.c and save changes.'
        then: 'Verify patient email value cannot be saved with only one trailing character after .'
        then: 'Fill in patient email text field with two alphanumeric characters after . and save changes'
        then: 'Verify patient email value should not be saved with numeric trailing character after .'
        then: 'Fill in patient email text field with two letters after . like test@test.uk and save changes'
        then: 'Verify new patient record is created when patient email contains two letters after .'

    }
}
