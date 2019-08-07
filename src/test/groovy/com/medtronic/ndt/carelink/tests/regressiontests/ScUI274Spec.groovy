package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HelpPage
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
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Screenshot
@Title('ScUI 274 - Patient Information Screen - Cancel Confirmation Window')

class ScUI274Spec extends CareLinkSpec {
    static SignInPage signInPage
    static LoginPage loginPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static CreatePatientPage createPatientPage
    static HelpPage helpPage
    static HomePage homePage

    static final email = "email" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4) + '@medtronictest.mailinator.com'

    def 'Patient Information Screen'() {
        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        then: 'Create a Clinic with New Username and Password.'
        when: 'Click on Register Clinic link.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then: 'Click on register clinic and click continue'
        newClinicRegistrationPage.clickRegisterClinic()
        sleep(3000)
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
        sleep(4000)
        when: 'At the Clinic Info page'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Enter all the clinic info'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        when: 'At the Clinic admin info page'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Enter all the user details'
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Ahmedo")
        clinicEnterAdminInfoPage.enterLastName("Abram")
        clinicEnterAdminInfoPage.enterEmail(email)
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
        then: 'Record the Username and Password'
        log.info("Username and Password, " + adminUser + ", " + "Test1234@")
        when: 'At the login screen login into with Username and password recorded above.'
        signInPage = browsers.at signInPage
        then: 'Sign into the application using the above credentials'
        signInPage.enterUsername(adminUser)
        signInPage.enterPassword('Test1234@')
        signInPage.clickOnSignIn()
        sleep(3000)
        signInPage.postponeMFA()
        sleep(500)
        when: ''
        homePage = browsers.at HomePage
        homePage.clickNewPatientBtn()
        then: ''
    }

    def 'Create New Patient and Verify '() {
        when: 'Create patient with the First name and Last name and patient ID.'
        createPatientPage = browsers.at CreatePatientPage
        then: ''
        createPatientPage.enterPatientFirstName("March", "20", "1978")
        createPatientPage.enterPatientID("March", "20", "1978")
        String firstName = createPatientPage.getPatientFirstName()
        String lastNam = createPatientPage.getPatientLastName()
        String id = createPatientPage.getPatientID()
        then: 'Record the Patient First Name, Last Name and Patient ID'
        log.info(lastNam, firstName, id)
        then: 'Fill all the required field and press save button.'
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        sleep(2000)
        then: 'Verify that System navigate to the Patient Record Screen for the newly created patient'
        createPatientPage.valueNewPatient(lastNam,firstName, id)
        then: 'Verify that there is an Edit Patient Information Link, labeled as “Edit patient information”.'
        createPatientPage.editPatientInformation()
        createPatientPage.editPatientText("Edit patient information")
        when: 'Press the Edit Patient Information Link.'
        createPatientPage.editPatientInformationClick()
        then: 'Verify that the system navigates to the Patient Information Screen.'
        then: 'Verify that Patient information screen titled as “Patient Information”.'
        createPatientPage.patientTitleText("Patient Information")
        then: 'Verify that there is a Save button labeled as “Save”.'
        createPatientPage.labelSaveValue("Save")
        then: 'Verify that save button is active.'
        createPatientPage.patientSave()
        then: 'Verify that there is a Cancel Link Labeled as “Cancel”.'
        createPatientPage.labelCancelText("Cancel")
        when: 'Press the Save button.'
        createPatientPage.patientSaveClick()
        then: 'Verify that system navigate to the Patient Record Screen for the patient and does NOT show the cancel confirmation message .'
        createPatientPage.verifyConfirmationMessage()
        sleep(300)
        createPatientPage.editPatientText("Edit patient information")
        when: 'Press the Edit Patient Information Link.'
        createPatientPage.editPatientInformationClick()
        then: 'Verify that system navigate to the Patient Information Screen.'
        createPatientPage.patientTitleText("Patient Information")
        createPatientPage.labelSaveValue("Save")
        createPatientPage.labelCancelText("Cancel")
        when: 'Make some changes to the Patient Information Screen fields.'
        createPatientPage.lastNameValue("test")
        and: 'Press the cancel link .'
        createPatientPage.patientCancelClick()
        then: 'Verify that system invokes the Cancel Confirmation Window.'
        createPatientPage.patientConfigWindowDisplayed()
        then: 'Verify that system display the Cancel Confirmation Window in a modal window and include the following:'
        then: 'Verify that dialogue window titled as “Cancel Confirmation”.'
        createPatientPage.patientConfigWindowTitle("Cancel Confirmation")
        then: 'Verify that dialogue window showing text as: “Are you sure you want to cancel?”'
        createPatientPage.patientConfigWindowTextDisplayed("Are you sure you want to cancel?")
        then: 'Verify that there is yes button labeled as “Yes”.'
        createPatientPage.configWindowButtonYes()
        then: 'Verify that there is no button labeled as “No”.'
        createPatientPage.сonfigWindowButtonNo()
        when: 'Press no button.'
        createPatientPage.configWindowButtonNoClick()
        then: 'Verify that system navigates to Patient Information Screen.'
        createPatientPage.patientTitleText("Patient Information")
        createPatientPage.labelSaveValue("Save")
        createPatientPage.labelCancelText("Cancel")
        when: 'Modify the last name and record the changed last name'
        createPatientPage.lastNameValue('test')
        and: 'Press the cancel link .'
        createPatientPage.patientCancelClick()
        then: 'Verify that system invokes the Cancel Confirmation Window.'
        createPatientPage.patientConfigWindowDisplayed()
        then: 'Verify that system display the Cancel Confirmation Window in a modal window and include the following:'
        then: 'Verify that dialogue window titled as “Cancel Confirmation”.'
        createPatientPage.patientConfigWindowTitle("Cancel Confirmation")
        then: 'Verify that dialogue window showing text as: “Are you sure you want to cancel?”'
        createPatientPage.patientConfigWindowTextDisplayed("Are you sure you want to cancel?")
        then: 'Verify that there is yes button labeled as “Yes”.'
        createPatientPage.configWindowButtonYes()
        then: 'Verify that there is no button labeled as “No”.'
        createPatientPage.сonfigWindowButtonNo()
        when: 'Press no button.'
        createPatientPage.configWindowButtonNoClick()
        then: 'Verify that system navigates to Patient Information Screen and show the last name according to the Step 2806501.'
        createPatientPage.lastNameValue('Lastname')
        and: 'Press the cancel link .'
        createPatientPage.patientCancelClick()
        then: 'Verify that system invokes the Cancel Confirmation Window.'
        createPatientPage.patientConfigWindowDisplayed()
        then: 'Verify that system display the Cancel Confirmation Window in a modal window and include the following:'
        then: 'Verify that dialogue window titled as “Cancel Confirmation”.'
        createPatientPage.patientConfigWindowTitle("Cancel Confirmation")
        then: 'Verify that dialogue window showing text as: “Are you sure you want to cancel?”'
        createPatientPage.patientConfigWindowTextDisplayed("Are you sure you want to cancel?")
        then: 'Verify that there is yes button labeled as “Yes”.'
        createPatientPage.configWindowButtonYes()
        then: 'Verify that there is no button labeled as “No”.'
        createPatientPage.сonfigWindowButtonNo()
        when: 'Press Yes Button.'
        createPatientPage.configWindowButtonYesClick()
        then: 'Verify that system navigates to patient record screen.'
        createPatientPage.editPatientText("Edit patient information")
        when: 'Press the Edit Patient Information Link.'
        createPatientPage.editPatientInformationClick()
        then: 'Verify that system navigate to the Patient Information Screen and show the last name according to the Step 2806371, 2806375, 2806379.'
        createPatientPage.firstNameValue("test")
        createPatientPage.lastNameValue('12333')
        createPatientPage.idValue("test")
    }
}