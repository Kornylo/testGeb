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
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('SaUI003 - New patient record screen functionality')
class SAUI003Spec extends CareLinkSpec implements ScreenshotTrait{
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
        then: 'Sign into the MMT-7340 application using the above credentials' +
        'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.\n'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        sleep(10000)
        then: 'From the home screen navigate to new patient record screen'
        signInPage.clickNewPatient()
    }
    def 'New patient screen functionality' () {
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

        when: 'Fill in all required text fields except First Name'
        and: 'Leave First Name text field blank and save changes.'
        then: 'Verify first name value is required when last name is not blank'

        when: 'Update First Name text field: Type valid First Name with each symbol from <, >, &, or = one by one in turn and save changes.'
        then: 'Verify patient record is not created when first name contains <, >, &, ='

        when: 'Fill in the first name with valid value and leave last name field blank.'
        then: 'Verify last name is required when first name is not blank.'

        when: 'Update Last Name text field: Type valid Last Name with each symbol from <, >, &, or = one by one in turn and save changes.'
        then: 'Verify patient record is not created when last name contains <, >, &, ='

        when: 'Leave first name, last name, patient id fields blank and save changes.'
        then: 'Verify first name is required when patient id is blank.'
        then: 'Verify last name is required when patient id is blank.'

        when: 'Update first name field with more than 40 characters'
        then: 'Verify first name field cannot be more than 40 characters.'

        when: 'Update last name field with more than 40 characters'
        then: 'Verify last name field cannot be more than 40 characters.'

        when: 'Update patient ID field with more than 20 characters'
        then: 'Verify patient ID field cannot be more than 20 characters'

        when: 'Update patient email field with more than 80 characters'
        then: 'Verify patient email field cannot be more than 80 characters'

        when: 'Update physician name text field with more than 80 characters.'
        then: 'Verify physician name cannot be more than 80 characters'

        when: 'Update the first name field with 40 characters'
        then: 'Verify that a patient with 40 characters in first name field is created.'

        when: 'Update the last name field with 40 characters'
        then: 'Verify that a patient with 40 characters in last name field is created.'

        when: 'Update the patient ID with 20 characters'
        then: 'Verify that a patient with 20 characters in patient ID field is created.'

        when: 'Update patient email with 80 characters'
        then: 'Verify that a patient with 80 characters in patient email field is created.'

        when: 'Update physician name with 80 characters'
        then: 'Verify that a patient with 80 characters in physician name field is created.'

        when: 'Navigate to Home screen and navigate to new patient record screen.'
        and: 'Fill in all required text fields first name, last name, patient ID, date of birth, diabetes type and diabetes treatment save changes.'
        then: 'Verify patient email is not a required field for new patient record creation'
        then: 'Verify physician name is not a required field for new patient record creation'

        when: 'Navigate to home screen and navigate to new patient record screen.'
        and: 'Fill in all the required fields and update physician name with each symbol from <, >, &, or = one by one in turn'
        then: 'Verify new patient record is not created when physician name contains any symbol <, >, &, or ='

        when: 'Fill in the same patient ID with same value as previously created patients.'
        then: 'Verify a new patient record is not created when patient ID is not unique within a clinic'

        when: 'Update the patient ID with each symbol from <, >, &, or = one by one in turn and save changes.'
        then: 'Verify new patient record is not created when patient ID contains any symbol <, >, &, or ='

        when: 'Fill in the first name, last name and date of birth with same value as previously created patient, leave patient ID blank and save changes.'
        then: 'Verify new patient record is not created when patient ID is blank and first name, last name and date of birth are not unique within a clinic.'

        when: 'Fill in first name, last name, patient ID and physician name with leading and trailing spaces and save changes.'
        then: 'Verify first name was saved without any leading and trailing spaces.'
        then: 'Verify last name was saved without any leading and trailing spaces.'
        then: 'Verify patient ID was saved without any leading and trailing spaces.'
        then: 'Verify physician name was saved without any leading and trailing spaces.'

        when: 'Navigate to home screen and new patient record screen.'
        and: 'Fill in all required text fields except patient ID and save changes.'
        then: 'Verify new patient record is created so that patient ID value is not required if first name and last name are filled.'

        when: 'Navigate to home screen and new patient record screen.'
        and: 'Fill in all required text fields except first name,last name and save changes.'
        then: 'Verify new patient record is created so that first name and last name values are not required if patient ID is filled.'

        when: 'Navigate to home screen and new patient record screen.'
        then: 'Verify the default entry for month selector specifies month'
        then: 'Verify the default entry for day selector specifies day'
        then: 'Verify the default entry for year selector specifies year'

        when: 'Fill in all required text fields except date of birth and save changes.'
        then: 'Verify date of birth is required.'

        when: 'Click on year drop down.'
        then: 'Verify that system allows to select value of 100 years back till current year.'

        when: 'Set tomorrows date in the date of birth field and save changes.'
        then: 'Verify new patient record is not created if date of birth is later than the current date.'
        then: 'Verify date selector dropdown values are displayed in the long date format.'

        when: 'Set todays date in date of birth field and save changes.'
        then: 'Verify patient record is created if date of birth is equal to the current date.'

        when: 'Navigate to home screen and navigate to new patient record screen.'
        and: 'Fill in email text field with a space between two characters and save changes.'
        then: 'Verify patient email value cannot contain any spaces.'

        when: 'Fill in patient email text field without @ and save changes.'
        then: 'Verify patient email value should include @ character.'

        when: 'Fill in patient email text field without . character'
        then: 'Verify patient email value should include . character'

        when: 'Fill in patient email text field with test@. and save changes.'
        then: 'Verify patient email value should not be saved with @ and . characters in a row.'

        when: 'Update patient email with each symbol from <, >, &, or = one by one in turn and save changes.'
        then: 'Verify patient email is not created when it s value contains <, >, &, or ='

        when: 'Fill in patient email with one after character after . like test@test.c and save changes.'
        then: 'Verify patient email value cannot be saved with only one trailing character after .'

        when: 'Fill in patient email text field with two alphanumeric characters after . and save changes'
        then: 'Verify patient email value should not be saved with numeric trailing character after .'

        when: 'Fill in patient email text field with two letters after . like test@test.uk and save changes'
        then: 'Verify new patient record is created when patient email contains two letters after .'

    }
}
