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
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Screenshot
@Title('ScUI255 - Incorrect login screen')
class SCUI255Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static LoginPage loginPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static CreatePatientPage createPatientPage
    static PatientStudyPage patientStudyPage

    def 'Login screen'() {
        when: 'Open MMT-7340 application(CareLink system).'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address below:'
        log.info(driver.getCurrentUrl())
    }
   def 'Register clinic'() {
        when: 'Click on Register Clinic link.'
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
        when: 'At the login page'
        signInPage = browsers.at SignInPage
        then: 'Sign into the MMT-7340 application using the above credentials'
        signInPage.enterUsername(adminUser)
        signInPage.enterPassword('Test1234@')
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
    }

    def 'Create new patient'() {
        when: 'A user is logged into CareLink application'
        signInPage = browsers.at SignInPage
        then: 'User should be able to create a new patient'
        signInPage.clickNewPatient()
        when:
        createPatientPage = browsers.at CreatePatientPage
        then:
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        sleep(500)
        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.exitPatient()
        when:'Click the Sign out link.'
        signInPage = browsers.at SignInPage
        then: 'Logged out of the application'
        signInPage.clickOnSignOutLink()
    }

    def 'Login Screen navigate'() {
        when: 'A user is logged into Carelink application'
        loginPage = browsers.at LoginPage
        then:'Verify that system navigate to the Login Screen.'
        loginPage.signInButtonDisplayed()
        then: 'Fill in the username and wrong password to log in to the system. Click the [Sign in] button.'
        loginPage.enterUsername("sss")
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        then:'Verify that System navigate to Incorrect Login Screen.'
        loginPage.loginErrorPageTitle("Login Error")
        loginPage.errorMsgLogin('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
        then:'Verify that Incorrect Login Screen display only the Medtronic Logo and CareLink Logo of the Common Screen Header Elements.'
        loginPage.waitForPageLoaded()
        loginPage.loginErrorLogoEnvision()
        loginPage.loginErrorLogoMedtronic()
        loginPage.verifyIsElementsMissing()
        then:'Verify that Incorrect Login Screen display Common Screen Footer Elements: User Guide, Terms of use, Privacy statement, Contact us.'
        loginPage.UserGuideLinkDisplayed()
        loginPage.trainingLinkDisplayed()
        loginPage.termsOfUseLinkDisplayed()
        loginPage.privacyStatementLinkDisplayed()
        loginPage.contactUsLinkDisplayed()
        then:'Verify that there is a Home link labeled as “Home”.'
        loginPage.homeLinkDisplayed()
        then:'Verify that Incorrect Login Screen titled as “Login Error”.'
        loginPage.loginErrorPageTitle("Login Error")
        then:'“Verify that screen showing text as:'
        loginPage.errorMsgLogin('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
        screenshot('Login','IncorrectLogin')
        then:'Verify that there is Username and Password user input fields with identical functionality to those in the Login Screen.  '
        loginPage.loginErrorFormDisplayed()
        loginPage.enterUsernameDisplayed()
        loginPage.enterPasswordDisplayed()
        loginPage.signInButtonDisplayed()
        then:'Verify that there is Forgot username and forgot password with identical functionality to those in the Login Screen.'
        assert loginPage.forgotUsernameLink.text().toString().contains('Forgot username?')
        assert loginPage.forgotPasswordLink.text().toString().contains('Forgot password?')
        screenshot('Login', 'ForgotUsername')
        then:'Verify that there is a Sign in button with identical functionality to those in the Login Screen.'
        loginPage.enterUsername("test")
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        then:'Verify that System navigate to Incorrect Login Screen.'
        loginPage.loginErrorPageTitle("Login Error")
        loginPage.errorMsgLogin('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
    }
}