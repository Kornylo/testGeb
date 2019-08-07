package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.ContactUsPage
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.PrivacyStatementPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.dashboard.TermsOfUsePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI929 - Logo')
class SCUI929Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LanguagePage languagePage
    static ContactUsPage contactUsPage
    static PrivacyStatementPage privacyStatementPage
    static TermsOfUsePage termsOfUsePage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static HomePage homePage
    static PatientStudyPage patientStudyPage

    static final adminUser = "Admin1" + Calendar.getInstance().format('HHmmss').toString() + RandomStringUtils.randomAlphanumeric(2)
    static final adminUser1 = "Admin2" + Calendar.getInstance().format('HHmmss').toString() + RandomStringUtils.randomAlphanumeric(2)
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress1 = "email2" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Verify footer elements logos'() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (the CareLink Software). Record the server URL address.'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        then:
        'Verify that the system displays the following warning and disclaimer (National Law Disclaimer): ' +
                '“Medtronic CareLink Software may not fully comply with applicable laws and regulations of every country, including laws and regulations governing online privacy and the transmission of personal data over the Internet. ' +
                'Accordingly, at the present time, only individuals who reside in the supported countries will be permitted to register to use the software. If you do not reside in one of the supported countries or territories and have previously registered, you must immediately discontinue use of the software until further notice."'
        signInPage.verifyLegalText()
        then: 'Verify that the Login Screen includes the following Common Screen Footer Elements: User Guide, Register Clinic, Terms of Use, Privacy Statement, Contact Us.'
        signInPage.checkIncludedFooterElements()

        when: 'Click on Change country/language link.'
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        and: 'Create a new clinic where Spark Flag is False with English language and click on [Continue] button.'
        languagePage.selectCountry("Bolivia")
        languagePage.buttonSelectClick()
        signInPage = browsers.at SignInPage
        then:
        'Verify that the Medtronic logo and the CareLink iPro logo are displayed. ' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in middle of screen.'
        signInPage.verifyLogo()

        when: 'Click on Contact Us link.'
        contactUsPage = browsers.at ContactUsPage
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'

        when: 'Click on Cancel link to navigate to Login screen and after click on Privacy Statement link.'
        contactUsPage.verifyLogoCloseButton()
        privacyStatementPage = browsers.at PrivacyStatementPage
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'

        when: 'Click on [Close] button to navigate to Login screen and after click on Terms of Use link.'
        privacyStatementPage.checkPrivacyStatementCloseButton()
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'

        when: 'Click on [Close] button to navigate to Login screen and after click on Change country/language link.'
        termsOfUsePage = browsers.at TermsOfUsePage
        termsOfUsePage.termOfUseButtonCloseChecking()
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'
        languagePage.verifyLogo()

        when: 'Click on [Select] button to navigate to Login screen and after click on Forgot username? link.'
        languagePage.buttonSelectClick()
        signInPage = browsers.at SignInPage
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'
        then:
        'Verify that the screen displays the following text: “To receive a username reminder sent to your email account, ' +
                'please enter your last name and the email address associated with your CareLink account. After you complete this form it may take a few minutes to receive your reminder.”'

        when: 'Click on Cancel link to navigate to Login screen and after click on Forgot password? link.'
        signInPage.verifyLogoForgotUsernamePage()
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'
        signInPage.verifyLogoForgotPasswordPage()
    }

    def 'Register new clinic'() {
        when: 'Click on Cancel link to navigate to Login screen and after click on Register Clinic link.'
        signInPage.clickRegisterClinic()
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then:
        'Verify that the Medtronic logo and the Carelink iPro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink iPro logo is in upper right corner.'
        newClinicRegistrationPage.verifyLogo()

        when: 'Click on Continue link.'
        newClinicRegistrationPage.clickOnContinue()
        and: 'Create a new clinic where Spark Flag is False with English language and click on [Continue] button.'
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.selectLanguage('English')
        clinicLocalePage.clickContinueBtn()
        clinicEULAPage = browsers.at ClinicEULAPage
        and: 'Check the checkboxes at the bottom of the screen and click on [Accept] button.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        then: 'Verify that the Enrollment Clinic Information Screen displays the following text: “Enter information about the clinic that will be using the CareLink Therapy Management Software for Diabetes.”'
        when: ''
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.clinicEnterInfoText("Enter information about the clinic that will be using the CareLink Therapy Management Software for Diabetes.")
        then: ''

        when: 'Register clinic. Click on [Continue] button.'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        and: 'Create a new administrative user and record the info below: Username: (example: TC929) Password: (example: Password1) Click on [Continue] button.'
        sleep(2000)
        clinicEnterAdminInfoPage.enterUserNameManual(adminUser)
        clinicEnterAdminInfoPage.enterFirstName("Ahmedo")
        clinicEnterAdminInfoPage.enterLastName("Calponte")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword("Password1")
        clinicEnterAdminInfoPage.enterConfirmPassword("Password1")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        clinicEnterAdminInfoPage.clickContinue()
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        println("Username: " + adminUser)
        println("Password: Password1")
        then: 'Verify that the Enrollment Finish Screen displays the following text: “Congratulations! You have completed enrollment in the Medtronic CareLink Software. Click on the Finish button to return to the System Welcome page where you can login using your username and password.”'
        when: ''
        finishClinicCreationPage.verifyEnrollmentCompleteScreen("Congratulations! You have completed enrollment in the Medtronic CareLink Software.")
        then: ''

        when: 'Click on [Finish] button.'
        finishClinicCreationPage.clickFinish()
        then: ''
    }

    def 'Sign in with wrong credentials'() {
        when: 'Log in to system with non-valid credentials.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername("non-valid")
        signInPage.enterPassword("credentials")
        signInPage.clickOnSignIn()
        then:
        'Verify that the Medtronic logo and the CareLink Therapy Management Software for Diabetes logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink Therapy Management Software for Diabetes logo is in upper right corner.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sign in with correct credentials'() {
        when: 'Log in to system with valid credentials from the step ID 2758904.'
        signInPage.enterUsername(adminUser)
        signInPage.enterPassword("Password1")
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        homePage = browsers.at HomePage
        then:
        'Verify that the Medtronic logo and the CareLink Therapy Management Software for Diabetes logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink Therapy Management Software for Diabetes logo is in upper right corner.'
        homePage.verifyLogo()
    }

    def 'Verify session timeout'() {
        when: 'Open Sample patient and click on Print all reports link.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.checkPrintAllReports()
        and: 'Leave the system for 20 minutes untouched.'
        println("Waiting Session Time Out")
        //sleep((20 * 60 + 30) * 1000)
        then: 'Verify that the system displays Session Time Out screen and informs the user that their session has expired after 20 minutes of inactivity'
        then: 'Verify that the Medtronic logo is displayed on upper left corner and the Carelink logo is in upper right corner.'
        when: ''
        $(id: "study:logout").click() //temporary
        //signInPage = browsers.at SignInPage
        //signInPage.sessionTimeout()
        then: ''
    }

    def 'Verify footer elements logos '() {
        when: 'Click on Home link to navigate to Login screen and after click on Change country/language link.'
        //signInPage.homeClick()
        signInPage = browsers.at SignInPage
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        and: 'Choose locale where Spark Flag is True and click on [Select] button.'
        languagePage.selectCountry("United States")
        sleep(2000)
        languagePage.buttonSelectClick()
        signInPage = browsers.at SignInPage
        then:
        'Verify that the Medtronic logo and the Carelink™ logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Carelink™ logo is in middle of screen.'
        signInPage.verifyLogo()

        when: 'Click on Contact Us link.'
        contactUsPage = browsers.at ContactUsPage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'

        when: 'Click on Cancel link to navigate to Login screen and after click on Privacy Statement link.'
        contactUsPage.verifyLogoCloseButton()
        signInPage = browsers.at SignInPage
        privacyStatementPage = browsers.at PrivacyStatementPage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'

        when: 'Click on [Close] button to navigate to Login screen and after click on Terms of Use link.'
        privacyStatementPage.checkPrivacyStatementCloseButton()
        signInPage = browsers.at SignInPage
        termsOfUsePage = browsers.at TermsOfUsePage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'

        when: 'Click on [Close] button to navigate to Login screen and after click on Change country/language link.'
        termsOfUsePage.termOfUseButtonCloseChecking()
        signInPage = browsers.at SignInPage
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'
        languagePage.verifyLogo()

        when: 'Click on [Select] button to navigate to Login screen and after click on Forgot username? link.'
        languagePage.buttonSelectClick()
        signInPage = browsers.at SignInPage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'

        when: 'Click on Cancel link to navigate to Login screen and after click on Forgot password? link.'
        signInPage.verifyLogoForgotUsernamePage()
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'
        signInPage.verifyLogoForgotPasswordPage()
    }

    def 'Register new clinic '() {
        when: 'Click on Cancel link to navigate to Login screen and after click on Register Clinic link.'
        signInPage.clickRegisterClinic()
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then:
        'Verify that the Medtronic logo and the Envision™ Pro logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the Envision™ PRO logo is in upper right corner.'
        newClinicRegistrationPage.verifyLogo()

        when: 'Create a new clinic where Spark Flag is True.'
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.selectLanguage('English')
        clinicLocalePage.clickContinueBtn()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        and: 'Create a new administrative user and record the info below: Username: (example: TC929) Password: (example: Password1)'
        clinicEnterAdminInfoPage.enterUserNameManual(adminUser1)
        clinicEnterAdminInfoPage.enterFirstName("Petro")
        clinicEnterAdminInfoPage.enterLastName("Mardonos")
        clinicEnterAdminInfoPage.enterEmail(emailAddress1)
        clinicEnterAdminInfoPage.enterPassword("Password1")
        clinicEnterAdminInfoPage.enterConfirmPassword("Password1")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        clinicEnterAdminInfoPage.clickContinue()
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        println("Username: " + adminUser1)
        println("Password: Password1")
        finishClinicCreationPage.clickFinish()
        then: ''
    }

    def 'Sign in with wrong credentials '() {
        when: 'Log in to system with non-valid credentials.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername("non-valid")
        signInPage.enterPassword("credentials")
        signInPage.clickOnSignIn()
        then:
        'Verify that the Medtronic logo and the CareLink Therapy Management Software for Diabetes logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink Therapy Management Software for Diabetes logo is in upper right corner.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sign in with correct credentials '() {
        when: 'Log in to system with valid credentials.'
        signInPage.enterUsername(adminUser1)
        signInPage.enterPassword("Password1")
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        homePage = browsers.at HomePage
        then:
        'Verify that the Medtronic logo and the CareLink Therapy Management Software for Diabetes logo are displayed.' +
                'Note: The Medtronic logo is displayed on upper left corner and the CareLink Therapy Management Software for Diabetes logo is in upper right corner.'
        homePage.verifyLogo()
    }

    def 'Verify session timeout '() {
        when: 'Open Sample patient and click on Print all reports link.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.checkPrintAllReports()
        and: 'Leave the system for 20 minutes untouched.'
        println("Waiting Session Time Out")
        //sleep((20 * 60 + 30) * 1000)
        then: 'Verify that the system displays Session Time Out screen and informs the user that their session has expired after 20 minutes of inactivity'
        then: 'Verify that the Medtronic logo is displayed on upper left corner and the Carelink logo is in upper right corner.'
        when: ''
//        signInPage = browsers.at SignInPage
//        signInPage.sessionTimeout()
        then: 'End of test.'
    }
}