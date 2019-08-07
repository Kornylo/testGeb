package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('INTS002 - Support Countries and Languages: Russian Language Cannot be Selected in Bulgaria Locale')
@RegressionTest
@Stepwise
@Slf4j
@Screenshot
class INTS002Spec extends CareLinkSpec implements ScreenshotTrait{

    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static Precondition precondition

    static final adminClinic = "user" + Calendar.getInstance().format('MMMddHHmmss').toString()
    def 'Login screen'() {
        when: 'Open MMT-7340 application (Carelink system)'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address'
        log.info(driver.getCurrentUrl())
        then: 'Click on Change country/territory and language link'
        signInPage.clickSelectCountryLanguage()
    }

    def 'Select Bulgaria through the change country/language flow'() {
        when: ''
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'In Country/Territory dropdown select: България (Bulgaria).\t'
        clinicLocalePage.selectCountry('България')
        then: 'Verify Russian (русский) language is not present in Language dropdown. User is not able to select Russian (русский) language in Language dropdown on Select Country/Territory and Language Screen.'
        clinicLocalePage.verifyLanguageOption()
        screenshot('Locale','verifyLanguageOptions')
    }

    def 'Navigate Login screen'() {
        when:
        signInPage = browsers.to SignInPage
        then: 'Navigate back to login screen'
    }

    def 'Register clinic'() {
        when: 'Click on register clinic link'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Navigate to Enrollment Country/Territory and Language Selection Screen from The Enrollment Info Screen.'
        newClinicRegistrationPage.clickOnContinue()
        when: ''
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'In Country/Territory dropdown select: Bulgaria.'
        clinicLocalePage.selectCountry('България')
        then: 'Click on Language dropdown'
        then: 'Verify Russian (русский) language is not present in Language dropdown. User is not able to select Russian (русский) language in Language dropdown on\n' +
                'Enrollment Country/Territory and Language Selection Screen.'
        clinicLocalePage.verifyLanguageOption()
        then: 'In language dropdown select English '
        clinicLocalePage.selectLanguage('English')
        then: 'Continue to Enrollment Terms Acceptance Screen'
        clinicLocalePage.selectLocale()
        when: ''
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Click on change link near the country name.'
        clinicEULAPage.clickChangeCountry()
        when: 'Continue to Enrollment Terms Acceptance Screen'
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Click on Language dropdown.'
        clinicLocalePage.selectLanguage('English')
        then: 'Verify Russian (русский) language is not present in Language dropdown. User is not able to select Russian (русский) language in Language dropdown on\n' +
                'Enrollment Country/Territory and Language Selection Screen.'
        clinicLocalePage.verifyLanguageOption()
        then: 'In Language dropdown select: English.'
        clinicLocalePage.selectLanguage('English')
        clinicLocalePage.selectLocale()
        when: 'Continue to Enrollment Terms Acceptance Screen'
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Continue to Enrollment terms acceptance screen and Accept Terms of Use and Privacy Policy'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        when: ''
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Fill in required textfields on Enrollment Clinic Information Screen.'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        and: 'Continue to the Enrollment Administrator Information Screen.'
        when: 'Fill in required textfields on\n' +
                'Enrollment Administrator Information Screen.'
        then: 'Record the clinic name'
        when: 'At the enrollment administrator info screen'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Continue to Enrollment Finish Screen.'
        then: 'Fill in required text fields on enrollment administrator information screen'
        clinicEnterAdminInfoPage.enterUserNameManual(adminClinic)
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("test@1medtronictest.mailiniator.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Lakshmi")
        clinicEnterAdminInfoPage.clickContinue()
        then: 'Record the username and password'
        println('Username '+adminClinic+' , ' +'Password Test1234@')
        when: 'Continue to enrollment finish screen'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Confirm to finish registration process'
        finishClinicCreationPage.clickFinish()
    }

    def 'Country dropdown Select Bulgaria'() {
        when: ''
        precondition = browser.to(Precondition)
        and: 'Sign into the MMT-7340 application using the credentials for newly created administrative user.'
        precondition.signInAsClinicAdmin(adminClinic, 'Test1234@')
        and: 'Navigate to Clinic Settings screen'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        and: 'Navigate to Clinic Information tab'
        clinicSettingsPage.openClinicInformation()
        clinicSettingsPage.checkClinicCountry('Bulgaria')
        then: 'Click on language dropdown'
        then: 'Verify Russian (русский) language is not present in Language dropdown. User is not able to select Russian (русский) language in Language dropdown on Clinic Information screen'
        clinicSettingsPage.verifyLanguageOption()
    }
}