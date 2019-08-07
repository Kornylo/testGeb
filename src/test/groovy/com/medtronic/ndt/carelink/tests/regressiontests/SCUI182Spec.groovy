package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Slf4j
@Stepwise
@Title('ScUI182 - General Client Configuration OS & Browser Test')
@Screenshot
class SCUI182Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static ClinicLocalePage clinicLocalePage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Browser Warning' () {
        when: 'Open MMT-7340 application (Carelink system)'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address'
        log.info(driver.getCurrentUrl())
        then: 'If the "Security Alert" message appears click Continue to this website (not recommended) button.'
        signInPage.isIncompatibleTableDisplayed()
        then: 'If the configuration is CONTINUE'
        signInPage.isContinueDisplayed()
        then: 'Verify Client Configuration Error page is displayed containing the following:'
        signInPage.unsupportedText1('You have attempted to access the Medtronic CareLink Therapy Management Software for Diabetes.')
        signInPage.unsupportedText2('Unfortunately, the configuration of your PC or web browser is not compatible with our standard system requirements. Displayed below is a comparison of the configuration of your PC and the requirements of the Medtronic CareLink Software. Please review this comparison, and adjust the configuration of your PC or browser software if possible.')
        signInPage.unsupportedTextContent()
        screenshot('Unsupported browser', 'Incompatability')
        then: 'Click on the [Continue] button on the Client Configuration error page.'
        signInPage.clickOnContinue()
        then: 'Verify the client configuration error page closes and allows the user to continue to the Medtronic Carelink iPro website'
        when: 'At the login page'
        signInPage = browsers.at SignInPage
        then: 'Verify that the login page is displayed as expected.'
        when: 'Click on Register Clinic link. Create a new clinic and record the info: Clinic Name.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Click continue on clinic registration'
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
        clinicEnterAdminInfoPage.enterSecurityAnswer("Lakshmi")
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
        then: 'Create an administrative user and record the info: Username and password'
        then: 'Sign into the MMT-7340 application using the credentials above.'
        signInPage.login(false, 'Akilaa', 'Test1234')
        then: 'Verify the user can login successfully.'
        then: 'Click on the sign out link'
        signInPage.clickOnSignOutLink()
        then: 'If this is an unsupported OS + browser combination client configuration error page is displayed.'
        signInPage.isIncompatibleTableDisplayed()
        signInPage.isContinueDisplayed()
        signInPage.unsupportedText1('You have attempted to access the Medtronic CareLink Therapy Management Software for Diabetes.')
        signInPage.unsupportedText2('Unfortunately, the configuration of your PC or web browser is not compatible with our standard system requirements. Displayed below is a comparison of the configuration of your PC and the requirements of the Medtronic CareLink Software. Please review this comparison, and adjust the configuration of your PC or browser software if possible.')
        signInPage.unsupportedTextContent()
        then: 'Click on the continue button'
        signInPage.clickOnContinue()
        then: 'Verify the Carelink iPro home page is displayed again.'
    }
}
