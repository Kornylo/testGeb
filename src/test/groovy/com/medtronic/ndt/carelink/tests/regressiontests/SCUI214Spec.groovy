package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
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
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import org.junit.Before
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('SСUI214 - CLiP: Patient Information Screen Content')
@Screenshot
@RegressionTest
@Stepwise
class SCUI214Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static CreatePatientPage createPatientPage
    static PatientStudyPage patientStudyPage
    static ClinicSettingsPage clinicSettingsPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static HomePage homePage
    static Precondition precondition

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final loginPassword = "MaluyScUI214"

    @Before
    void precondition() {
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, loginPassword)
        println("Username: " + adminUs + " Password: " + loginPassword)
    }

    def ' Patient Information Screen Content Verify'() {
        given: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below'
        when: 'Open software under test.'
        signInPage = browsers.at SignInPage
        then: 'Confirm that the Login Screen Opens'
        println("URL address: " + browser.getCurrentUrl())
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        when: 'Verify that there is a System Communication Area that shall link to display an image hosted on an external server system.'
        loginPage = browsers.at LoginPage
        then: ''
        loginPage.isBannerImageDisplayed()
        then: 'Verify that the image shall also link to an external webpage.'
        then: 'Go to the View > Source from the toolbar.'
        then: 'Look for the url.'
        then: 'Verify that the displayed image link shall be available to the user and open the URL marcom/ipro2/en/US/banner.xml'
        driver.getPageSource().contains("/marcom/ipro2/en/US/banner.xml");
        then: 'Login using an existing username and password.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        sleep(3000)
        signInPage.postponeMFA()
        then: 'Record the Username, Record the Password'
        println("Username: " + adminUs + " Password: " + loginPassword)
        then: 'At the Home Screen, click on the “New patient ” button.'
        sleep(3000)
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
        String firstName = createPatientPage.firstName.getAttribute("value")
        String lastName = createPatientPage.lastName.getAttribute("value")
        String patientID = createPatientPage.id.getAttribute("value")
        println(firstName)
        println(lastName)
        println(patientID)
        println('Physician: test')
        println('Email Address: test@tesc.com')
        println('Date of Birth: March, 20, 1978, Diabetes Type: Insulin Pump')
        then: 'Record entries below. (Applies to MMT-7340 only)'
        then: 'At the New Patient Record Screen, click on the “Save” button.'
        createPatientPage.clickSaveBtn()
        when: 'At the Patient Record Screen , click on the “Edit patient information” button.'
        patientStudyPage = browsers.at PatientStudyPage
        createPatientPage.editPatientInformationClick()
        then: 'Observe the Patient Information Screen.'
        then: 'Common Screen Header Elements'
        then: 'Observe the Header (top) section.'
        then: 'Verify that the Common Screen Header Elements are present.'
        patientStudyPage.patientHeaderDisplayed()
        then: 'Verify that the Medtronic Rising man logo is displayed in the upper left corner.'
        patientStudyPage.patientLogoDisplayed()
        then: 'Verify that the clinic name , [Clinic Info Name] and [Clinic Info ID] is displayed in the upper center of the screen.'
        patientStudyPage.patientClinicInfoCss()
        then: 'Verify that The CareLink iPro Therapy Management Software for Diabetes brand logo is displayed in the upper right of the screen.'
        patientStudyPage.patientIprologoDisplayed()
        then: 'Verify that there is a Home link labeled “Home”.'
        patientStudyPage.patientHomeLabelText('Home')
        then: 'Verify that there is a Patients link labeled “Patient” .'
        patientStudyPage.patientLabelPatientText('Patient')
        then: 'Verify that there is a My Info link labeled “My info”.'
        patientStudyPage.patientLabelMyInfoText('My Info')
        then: 'Verify that there is a Sign Out link labeled “Sign out”.'
        patientStudyPage.patientLabelSignOutText('Sign out')
        then: 'Common Screen Footer Elements, Observe the Footer (bottom) section.'
        patientStudyPage.patientFooterDisplayed()
        when: ''
        homePage = browsers.at HomePage
        then: ''
        then: 'Verify that there is a Materials link labeled “Resources”.'
        homePage.resourcesTextDisplayed('Resources')
        then: 'Verify that there is a User Guide link labeled “User Guide”.'
        homePage.userGuideTextDisplayed('User Guide')
        then: 'Verify that there is an Order System Supplies link labeled “Order supplies”.'
        homePage.orderSuppliesTextDisplayed('Order supplies')
        then: 'Verify that there is a Terms of Use link labeled “Terms of Use”.'
        homePage.termsOfUseTextDisplayed('Terms of Use')
        then: 'Verify that there is a Privacy Statement link labeled “Privacy Statement”.'
        homePage.privacyStatementTextDisplayed('Privacy Statement')
        then: 'Verify that there is a Contact Us link labeled “Contact Us”.'
        homePage.contactUsDisplayed('Contact Us')
        then: 'Patient Information Screen : (This section applies to only CareLink iPro MMT-7340)'
        then: 'Observe the main section (middle) of the screen.'
        then: 'Verify that the screen displays the selected patient’s name in the following format: [Patient_Info_Last_Name], [Patient_Info_First_Name] [Patient_ID]'
        patientStudyPage.patientNameInformationForFiveBrowser(lastName, firstName, patientID)
        then: 'Note: Truncated to the available space. The Comma in the name may not appear in some locales.'
        then: 'Verify that the screen displays the title “Patient Information”.'
        createPatientPage.patientTitleText("Patient Information")
        then: 'Verify that there is a user input field for Patient ID labeled “Patient ID”.'
        patientStudyPage.patientIDlabelDisplayed('Patient ID*')
        then: 'Verify that there is a user input field for First Name labeled “First Name”.'
        patientStudyPage.firstNamelabelDisplayed('First Name*')
        then: 'Verify that there is a user input field for Last Name labeled “Last Name”.'
        patientStudyPage.lastNamelabelDisplayed('Last Name*')
        then: 'Verify that there is a user input field for Date of Birth labeled “Date of Birth”.'
        patientStudyPage.dateofBirthlabelDisplayed('Date of Birth*')
        then: 'Verify that there is a user input field for Email Address labeled “ Patient Email ”.'
        patientStudyPage.patientEmaillabelDisplayed('Patient Email')
        then: 'Verify that there is a user input field for Physician labeled “Physician Name ”.'
        patientStudyPage.physicianNamelabelDisplayed('Physician Name')
        then: 'Verify that there is a user input field for Diabetes Type labeled “Diabetes Type”.'
        patientStudyPage.diabetesTypelabelDisplayed()
        then: 'Verify that the Patient ID or First Name and Last Name, as well as Diabetes Type , Therapy Type, and Date of Birth , are indicated as being required.'
        then: 'Verify that there is a Save button labeled “Save”.'
        createPatientPage.labelSaveValue("Save")
        then: 'Verify that there is a Cancel link labeled “Cancel”.'
        createPatientPage.labelCancelText("Cancel")
        then: 'Open the software in the another browser or tab and go to the New Patient record screen.'
        then: 'Verify that the Patient Information Screen indicates the same required fields as the New Patient Record Screen.'
        patientStudyPage.patientHomeClick()
        waitFor { homePage.newPatient.displayed }
        homePage.clickNewPatientBtn()
        patientStudyPage.patientIDlabelDisplayed('Patient ID*')
        patientStudyPage.firstNamelabelDisplayed('First Name*')
        patientStudyPage.lastNamelabelDisplayed('Last Name*')
        patientStudyPage.dateofBirthlabelDisplayed('Date of Birth*')
        patientStudyPage.diabetesTypelabelDisplayed()
        patientStudyPage.therapyTypeLabelDisplayed()
        then: 'End of test.'
    }




}
