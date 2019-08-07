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
@Title('ScUI 232 - Enrollment Finish Screen')
@Stepwise
@Screenshot
class SCUI232Spec extends CareLinkSpec implements ScreenshotTrait {

    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Enrollment Finish screen'() {
        when: 'Open MMT – 7340 software under test.'
        signInPage = browsers.to SignInPage
        then: 'Verify the system navigates to login screen.'
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()

        when: 'Click on Register Clinic.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        clinicEULAPage = browsers.at ClinicEULAPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Verify the system opens Enrollment Info Screen.'
        clinicEULAPage.enrollmentInfoScreen()

        when: 'Click on “Continue”.'
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Verify the system navigates to Enrollment Country and Language Selection Screen.'
        clinicLocalePage.enrollmentCountryAndLanguageSelectScreen()

        when: 'Click on Country and Language Selection link.'
        and: 'Choose the Country and Language.'
        clinicLocalePage.selectCountryAndLanguage("United States", "English")
        and: 'Click on Continue button.'
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Verify the system opens Enrollment Terms Acceptance Screen.'
        clinicEULAPage.opensEnrollmentTermsAcceptanceScreen()

        when: 'Check the both check boxes mentioned for “I am a resident of the United States” and “I have read, understood, and accept the Terms of Use and Privacy Statement”.'
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.residentCheckboxOn()
        clinicEULAPage.acceptCheckboxOn()
        and: 'Click on Accept.'
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Verify the system navigates to Enrollment Clinic Information Screen.'
        clinicEnterInfoPage.enrollmentClinicInformationScreen()

        when: 'Enter all details needed.'
        clinicEnterInfoPage.configureClinic()
        and: 'Click on Continue.'
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Verify the system navigates to Enrollment Administrator Information Screen.'
        clinicEnterAdminInfoPage.clinicEnterAdminInfoText('Create the administrative user responsible for maintaining this clinic\'s CareLink system.')
        when: 'Enter the information required.'
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Dmytro")
        clinicEnterAdminInfoPage.enterLastName("Stadnik")
        clinicEnterAdminInfoPage.enterEmail("dmytro.stadnik1@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        and: 'Click on “Continue” '
        clinicEnterAdminInfoPage.clickContinue()
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Verify the system navigates to Enrollment Finish Screen.'
        then: 'Verify the Enrollment Finish Screen shall only display the Medtronic Logo and the CareLink Logo from the Common Screen Header Elements.'
        then: 'Verify the Enrollment Finish Screen shall not display any of the Common Screen Footer Elements.'
        then: 'Verify the Enrollment Finish Screen shall display the title ” Enrollment Complete”.'
        then: 'Verify the Enrollment Finish Screen shall display the following text. “ Congratulations! You have completed enrollment in the Medtronic CareLink iPro Software.'
        finishClinicCreationPage.verifyEnrollmentCompleteScreen("Congratulations! You have completed enrollment in the Medtronic CareLink Software.")

        when: 'Click on Finish Button to return to the System Welcome page where you can login using your username and password.'
        then: 'Verify there is a finish button labeled as “Finish”.'
        finishClinicCreationPage.chechFinishButton()

        when: 'Click on Finish button.'
        finishClinicCreationPage.clickFinish()
        signInPage = browsers.to SignInPage
        then: 'Verify the system Navigates to the Login Screen.'
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        and: 'End of the test.'

    }
}