package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HCP.MyInfoPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('ScUI 208 - CLiP: Home Screen - Content')
@Screenshot
@RegressionTest
@Stepwise

class SCUI208Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static HomePage homePage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static CreatePatientPage createPatientPage
    static PatientStudyPage patientStudyPage
    static ClinicSettingsPage clinicSettingsPage
    static MyInfoPage myInfoPage
    static final adminUs = "AdminUS" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final emailAddress = "email" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4) + '@medtronictest.mailinator.com'
    static final loginPassword = "MaluyScUI221"


    def 'Open Carelink application'() {
        given: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        println("URL address: " + browser.getCurrentUrl())
    }

    def 'Pre-condition Register US clinic'() {
        when: 'Click on Register Clinic link. Create US clinic and record the info below: Clinic Name:'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        clinicEULAPage = browsers.at ClinicEULAPage
        newClinicRegistrationPage.clickRegisterClinic()
        clinicEULAPage.enrollmentInfoScreen()
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.enrollmentCountryAndLanguageSelectScreen()
        clinicLocalePage.selectCountryAndLanguage("United States", "English")
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.opensEnrollmentTermsAcceptanceScreen()
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOn()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.acceptCheckboxOn()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.enrollmentClinicInformationScreen()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Create a new administrative user and record the info below: Username: (example: TCXXX) Password: ___________________________ (example: Password1)'
        clinicEnterAdminInfoPage.enterUserNameManual(adminUs)
        clinicEnterAdminInfoPage.enterFirstName("kornylo")
        clinicEnterAdminInfoPage.enterLastName("dmytro")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword("MaluyScUI221")
        clinicEnterAdminInfoPage.enterConfirmPassword("MaluyScUI221")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        println("Username: " + adminUs + " Password: " + loginPassword)
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
    }

    def 'Login in application'() {
        when: 'Open MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: 'Login using an existing username and password.'
        signInPage.signInButtonDisplayed()
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        then: 'Record the username and password'
        println("Logging in as " + adminUs + ", " + loginPassword)
        when: 'Click Sign in button.'
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Verify the Home Screen Appears.'
        when:
        homePage = browsers.at HomePage
        then: 'Verify the screen is titled “Patient List'
        homePage.clinicNameDisplayed()
    }

    def 'Home screen'() {
        when: 'Verify'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        signInPage = browsers.at SignInPage
        then: 'Verify there is a Medtronic Logo at the left-aligned on top of the Home Screen page.'
        clinicSettingsPage.loginLogoMedtronicDisplayed()
        then: 'Verify the Clinic name is displayed at the centered on top of the Home Screen page.'
        clinicSettingsPage.clinickNameDisplayed()
        then: 'Verify there is a Carelink iPro logo displayed right-aligned on top of the Home Screen page'
        clinicSettingsPage.loginLogoEnvision()
        then: 'Verify there is a Carelink iPro Clinical logo displayed right-aligned on top of the Home Screen Page.'
        clinicSettingsPage.loginLogoMedtronicDisplayed()
        then: 'Verify there is a Home Link, labeled “Home” on the Home Screen page.'
        clinicSettingsPage.homeUrlTextDisplayed("Home")
        then: 'Verify there is a Clinic Settings link, labeled “Clinic Settings” on the Home Screen page.'
        clinicSettingsPage.clinicSettingsDisplayed("Clinic Settings")
        then: 'Click on the Clinic Settings link.'
        clinicSettingsPage.clickOnClinicSettings()
        then: 'Verify the software navigates to the Clinic Settings Screen: Report Settings Screen.'
        clinicSettingsPage.closeClinicSettingsIsDisplayed()
        when: 'Click on Home Link.'
        clinicSettingsPage.goToHomePageClick()
        then: 'Verify the software navigates to the Select a Patient Screen.'
        when: ''
        homePage = browsers.at HomePage
        homePage.listLoaded()
        homePage.homeScreenIsAppeared()
        homePage.clinicNameDisplayed()
        then: ''
        when: 'Verify there is a My Info link, labeled, “My Info'
        myInfoPage = browsers.at MyInfoPage
        myInfoPage.clickOnMyInfoDisplayed()
        then: 'Click on the My Info link.'
        sleep(300)
        myInfoPage.clickOnMyInfo()
        then: 'Verify the software navigates to My Info Screen.'
        myInfoPage.myInfoCloseDisplayed()
        when: 'Return to the Home Screen.'
        myInfoPage.closeMyInfo()
        then: 'Verify there is a Sign out link, labeled “Sign out” on the Home Screen page .'
        when: 'Click the Sign out link.'
        signInPage.clickOnSignOutLink()
        then: 'Verify the software terminates the current user session and navigates to the Login Screen.'
        signInPage.signInButtonDisplayed()
    }

    def 'Home URL verify'() {
        when: 'Login again with same User ID and Password.'
        signInPage = browsers.to SignInPage
        then: 'Login using an existing username and password.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        when: 'Click Sign in button.'
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Verify there is a Resources link, labeled “Resources” on the bottom of the Home Screen.'
        when:
        homePage = browsers.at HomePage
        homePage.resourcesDisplayed()
        and: 'Click on the Resources link and Verify'
        homePage.verifyResourcesLink()
        homePage.userGuideDisplayed()
        and: 'Click on the User Guide link and Verify'
        homePage.verifyUserGuideLink()
        and: 'Click on Order Supplies link and Verify'
        homePage.verifyOrderSuppliesLink()
        homePage.privacyStatementDisplayed()
        and: 'Click on Privacy Statement link and Verify'
        homePage.verifyPrivacyStatementLink()
        homePage.termsOfUseDisplayed()
        and: 'Click on Terms Of Use link and Verify'
        homePage.verifyTermsOfUseLink()
        homePage.contactUsDisplayed()
        and: 'Click on Contact Us link and Verify'
        homePage.verifyContactUsLink()
        then: 'Verify the Home Screen Appears.'
        when: 'Return to the Home Screen'
        homePage = browsers.at HomePage
        then:
        homePage.clinicNameDisplayed()
        then: 'Verify there is a search field titled “Search”.'
        signInPage.searchDisplayed()
        then: 'Verify the user is able to type something into the search field.'
        signInPage.searchForPatient("Sample")
        then: 'Verify there is a button labeled “New Patient” at the bottom of the patient list box.'
        signInPage.buttonNewPatientDisplayed()
        signInPage.verifyTitleButtonNewPatient()
        then: 'Verify there is a List box with a list of Patients with 4 columns.'
        then: 'Verify there is a column titled “Last Name” that displays each patient’s last name.'
        homePage.patientListlastName("Last Name")
        then: 'Verify there is a column titled “First Name” that display each patient first name.'
        homePage.patientListFirstName("First Name")
        then:
        'Verify there is a column titled “Date of Birth” that displays the date of birth for each patient for MMT-7340.'
        homePage.patientListDateOfBirth("Date of Birth")
        then: 'Verify there is a column titled “Patient ID” that display patient Id for MMT-7340'
        homePage.patientListPatientID("Patient ID")
        signInPage.clickNewPatient()
    }

    def 'Create New Patient'() {
        when:
        createPatientPage = browsers.at CreatePatientPage
        then: ''
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        sleep(500)
    }

    def 'Home screen verify'() {
        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.exitPatient()
        then: 'Verify the patient list is scrollable.'
        when: 'Click on the last name column title twice.'
        homePage = browsers.at HomePage
        homePage.sortLastName()
        then: 'Verify the patient list can be sorted alphabetically ascending or descending by the last name.'
        homePage.verifyLastNameSorted()
        when: 'Click on the first name column title twice'
        homePage.sortFirstName()
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        homePage.pleaseWaitPopup()
        then: 'Verify the list is sorted alphabetically ascending or descending by the first name.'
        homePage.verifyFirstNameSorted()
        when: 'Click on the date of birth column title twice'
        homePage.sortdDateOfBirth()
        then: 'Verify the list is sorted chronologically ascending or descending by the date of birth.'
        homePage.verifyLlistDateOfBirthSorted()
        when: 'Click on the Patient ID column title twice.'
        homePage.sortPatientID()
        then: 'Verify the list is sorted chronologically ascending or descending by Patient ID.'
        homePage.verifyPatientListSorted()
        when: 'Click on any row in the patient list.'
        homePage.selectPatientFromList()
        then: 'Verify the patient row is highlighted.'
        homePage.highlightedPatientRow()
        when: 'Double click on the highlighted row.'
        homePage.openPatient()
        then: 'Verify the software navigates to the Patient Record screen for the highlighted patient.'
        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.isUploadiProDisplayed()
        then: 'Click on the Close Patient button on the top of the patient tab to return to the home screen.'
        signInPage.closePatient()

        then: 'Verify there is a Select Patient Button labeled as “Open patient ”.'
        signInPage.openPatientDisplayed()
        when: 'Ensure there no patient is highlighted. (Refresh page if necessary)'
        then: 'Verify the Open Patient Button is not active.'
        signInPage.openPatientButtonNotActive()
        when: 'Highlight a patient.'
        homePage.selectPatientFromList()
        then: 'Verify the Open Patient button is now active.'
        signInPage.openPatientDisplayed()
        then: 'Click on the Open patient button.'
        signInPage.openPatient()
        when:
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Verify the software navigates to the Patient Record screen for the highlighted patient.'
        patientStudyPage.isUploadiProDisplayed()
        when: 'Click on the Close Patient button on the top of the patient tab to return to the home screen.'
        signInPage.closePatient()
        then: 'Verify there is a New Patient button titled “New patient ”.'
        signInPage.verifyTitleButtonNewPatient()
        when: 'Click on the New Patient button.'
        signInPage.buttonNewPatientDisplayed()
        signInPage.clickNewPatient()
        then: 'Verify the software navigates to the New Patient Record Screen.'
    }

    def 'Create New Patient and Verify '() {
        when:
        createPatientPage = browsers.at CreatePatientPage
        then: 'Create patient with the First name and Last name and keep the patient ID blank.'
        createPatientPage.titleCreateNewPatient("Create New Patient")
        createPatientPage.enterPatientFirstName('March', '20', '1978')
        String FirstName = createPatientPage.getPatientFirstName()
        String LastName = createPatientPage.getPatientLastName()
        then: 'Record the Patient First and Last Name'
        log.info(FirstName)
        log.info(LastName)
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        then: 'Fill all the required field and press save button.'
        createPatientPage.clickSaveBtn()
        then: 'Verify that System navigate to the Patient Record Screen for the newly created patient'
        createPatientPage.valueNewPatient(LastName, FirstName)
        then: 'Press the Close patient button .'
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        sleep(2000)
        then: ''
        patientStudyPage.exitPatient()
        when: ''
        signInPage = browsers.at SignInPage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        when:
        homePage = browsers.at HomePage
        then:
        homePage.pleaseWaitPopup()
        then: 'Verify that System navigate to the Home Screen.  '
        homePage.patientListlastName("Last Name")
        homePage.patientListFirstName("First Name")
        homePage.patientListDateOfBirth("Date of Birth")
        homePage.patientListPatientID("Patient ID")
        signInPage.searchDisplayed()
        signInPage.searchForPatient(FirstName)
        then: 'Verify that Patient List shows “---“for patient ID as data element of patient earlier created.  '
        homePage.verifyPatientID()
        then: 'Press the New patient button.'
        signInPage.clickNewPatient()
        then: 'Verify that system navigate to the New Patient Record Screen.'
        createPatientPage.titleCreateNewPatient("Create New Patient")
        then: 'Create a patient with the Patient ID only with the First Name and Last Name blank.'
        createPatientPage.enterPatientID('March', '20', '1978')
        String id = createPatientPage.getPatientID()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        then: 'Fill all the required field and press save button.'
        createPatientPage.clickSaveBtn()
        then: 'Verify that System navigate to the Patient Record Screen for the newly created patient.'
        createPatientPage.verifyNewlyCreatedPatient(id)
        then: 'Record the Patient ID'
        log.info(id)
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Press the EXIT Button .'
        patientStudyPage.exitPatient()
        sleep(1000)
        when:
        homePage = browsers.at HomePage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        homePage.pleaseWaitPopup()
        then: 'Verify that System navigate to the Home Screen'
        homePage.patientListlastName("Last Name")
        homePage.patientListFirstName("First Name")
        homePage.patientListDateOfBirth("Date of Birth")
        homePage.patientListPatientID("Patient ID")
        when: ''
        signInPage = browsers.at SignInPage
        then: ''
        signInPage.searchDisplayed()
        signInPage.searchForPatient(id)
        then: 'Verify that Patient List shows “---“ for First name as data element of last created patient.'
        homePage.verifyLastNamePatient()
        then: 'Verify that Patient List shows “---“ for Last name as data element of last created patient'
        homePage.verifyFirstNamePatient()
    }
}