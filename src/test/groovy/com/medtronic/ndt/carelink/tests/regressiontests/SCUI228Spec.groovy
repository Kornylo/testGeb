package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Title('ScUI228 - Enrollment Info Screen')
@Stepwise
@Slf4j
@Screenshot
class SCUI228Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage

    def 'Login screen'() {
        when: 'Open MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: ''
        signInPage.checkIncludedFooterElements()
    }

    def 'Register clinic'() {
        when: 'Click on register clinic link'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Verify the system opened Enrollment Info Screen'
        then: 'Verify the Enrollment Info Screen displays the title as "Clinic Registration"'
        and:
        'Verify the Enrollment Info Screen displays the following text:\n' +
                '“Welcome to CareLink™, the online therapy management software for Professional CGM Systems.\n' +
                'Click Continue to register a clinic for the first time.\n' +
                'Important: \n' +
                'Is your clinic already registered with CareLink™? If so, click Cancel to go back to the sign-in screen. Do not register your clinic again. If you are trying to sign in and you do not have a username, contact your local CareLink™ administrator to request a new user account.”.'
        newClinicRegistrationPage.enrollmentText()
        screenshot('Enrollment', 'enrollmentInfoScreen')
        then: 'Verify there is a continue link, labeled as “Continue”.'
        newClinicRegistrationPage.continueButtonText()
        and: 'Click on the Continue link.'
        newClinicRegistrationPage.clickOnContinue()
    }

    def 'Enrollment Country and Language Selection Screen'() {
        when: 'Verify the system navigates to enrollment country and language selection screen'
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.enrollmentCountryAndLanguageSelectScreen()
        and: 'Navigate back to the Enrollment Info Screen'
        driver.navigate().back()
        driver.navigate().refresh()
        then: 'Verify there is a cancel button and labeled as “Cancel”'
        newClinicRegistrationPage.cancelButtonDisplayed()
        when: 'Click on the [Cancel] button.'
        newClinicRegistrationPage.cancelButton.click()
        then: 'Verify the system aborts the enrollment process and navigate to the Login Screen.'
        waitFor { browser.getCurrentUrl().contains('login') }
        waitFor { title != '' }
        assert title == ReadProperties.getProperties().get("login.title")
        screenshot('Enrollment', 'backToLogin')
    }
}