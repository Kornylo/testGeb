package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Title('ScUI 229 - Enrollment Select Country and Language Screen')
@Stepwise
@Slf4j
@Screenshot
class SCUI229Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage

    def 'Enrollment Screen'() {
        when: 'Open the MMT – 7340 software under test.'
        signInPage = browsers.to SignInPage
        and: 'Navigate to Log-in screen.'
        and: 'Click on Register Clinic link.'
        signInPage.checkIncludedFooterElements()
        signInPage.clickRegisterClinic()
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then: 'Verify the system opens Enrollment Info Screen.'
        newClinicRegistrationPage.verifyEnrollmentInfo()

        when: 'Click on Continue.'
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at(ClinicLocalePage)
        then: 'Verify the system opens Enrollment Select Country and Language Screen.'
        then: 'Verify the screen displays the Medtronic Logo and the CareLink Logo from the Common Screen Header Elements.'
        then: 'Verify the screen does not display any of the Common Screen Footer Elements.'
        then: 'Verify the screen displays a process step indicator.'
        then: 'Verify there is “Select country and language” on the screen.'
        then: 'Verify that the screen displays the Country and Language Selection Screen content.'
        then: ' Verify that Country under test is in the country dropdown list.'
        then: ' Verify that Language under test is in the language dropdown list for country under test.'
        then: 'Verify there is a Continue button labeled as “Continue”.'
        then: 'Verify there is a Cancel link labeled as “Cancel”.'
        clinicLocalePage.verifyOnEnrollmentSelectCountryAndLanguageScreen()

        when: 'Click on Continue.'
        clinicLocalePage.clickContinueBtn()
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Verify the system shall record the selection of Country and Language and navigates to the Enrollment Terms Acceptance Screen.'
        clinicEULAPage.verifyCountry()

        when: 'Click on change link.Navigate back to the Enrollment Country and Language Selection Screen.'
        clinicEULAPage.clickChangeLink()
        clinicLocalePage = browsers.at(ClinicLocalePage)
        and: 'Click on Cancel link.'
        clinicLocalePage.clickCancelLink()
        then: 'Verify the system discards the Enroll Record and navigates to the Login Screen .'
        when: ''
        signInPage = browsers.at(SignInPage)
        then: 'End of the test.'
    }
}