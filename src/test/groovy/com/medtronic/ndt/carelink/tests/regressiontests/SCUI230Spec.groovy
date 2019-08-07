package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Title('ScUI 230 - Enrollment Terms Acceptance Screen')
@Stepwise
@Slf4j
@Screenshot
class SCUI230Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage

    def 'Register Clinic'() {
        when: 'Open the MMT – 7340 software under test.'
        and: 'Navigate to Log-in screen.'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        clinicEULAPage = browsers.at ClinicEULAPage
        and: 'Click on Register Clinic.'
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Verify the system opens Enrollment Info Screen.'
        clinicEULAPage.enrollmentInfoScreen()

        when: 'Click on Continue.'
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Verify the system opens a window called Enrollment Country and Language Select Screen.'
        clinicLocalePage.enrollmentCountryAndLanguageSelectScreen()

        when: 'Click on Country and Language option.'
        and: 'Choose the Country and Language.'
        clinicLocalePage.selectCountryAndLanguage("United States", "English")
        and: 'Click on Continue button.'
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Verify the system opens Enrollment Terms Acceptance Screen.'
        clinicEULAPage.opensEnrollmentTermsAcceptanceScreen()
        def enrollmentTermsAcceptanceScreenUrl = browser.getCurrentUrl()
        then: 'Verify the Enrollment Terms Acceptance Screen shall only display the Medtronic Logo and the CareLink Logo from the Common Screen Header Elements.'
        then: 'Verify the Enrollment Terms Acceptance Screen shall not display any of the Common Screen Footer Elements.'
        then: 'Verify the Enrollment Terms Acceptance Screen shall display an enrollment step indicator in place of a title.'
        then: 'Verify the Enrollment Terms Acceptance Screen shall display the following text: “Please remember that our Terms of Use and Privacy Statement govern your use of and submissions to this site. You must agree to all of the Terms of Use and Privacy Statement in order to use this site. By submitting this form you confirm your agreement with the provisions of the terms of Use and Privacy Statement.” '
        then: 'Verify that screen shall select an indicator for the [Clinic_Info_Country] selected.'
        then: 'Verify that the screen displays a link, labeled as “Change”, which navigates back to the Enrollment country and Language Selection Screen.'
        clinicEULAPage.verifyEnrollmentTermsAcceptanceScreen()

        when: 'Click on “Change” link.'
        then: 'Verify the system navigates back to the Enrollment Country and Language Selection Screen.'
        clinicEULAPage.verifyChangeLink()

        when: 'Click on Continue Button'
        then: 'Verify there is Terms of Use in Enrollment Terms and Acceptance Screen.'
        then: 'Verify the Enrollment Terms and Acceptance Screen displays the Terms of Use as detailed in Appendix B.'
        then: 'Verify the Terms of Use is scrollable.'
        then: 'Verify there is Privacy Statement in Enrollment Terms Acceptance Screen.'
        then: 'Verify the Enrollment Terms and Acceptance Screen displays the Privacy Statement as detailed in Appendix A.'
        then: 'Verify the Privacy Statement is Scrollable.'
        then: 'Verify there is a Residency Confirmation Indicator shall display an acceptance toggle indicator associated with the following statement: “I am a resident of [Clinic_Info_Country]” where [Clinic_Info_Country] is equal to the country selected.'
        clinicEULAPage.verifyEnrollmentTermsAcceptanceScreen2()

        when: 'Click check box mentioned for “I am a resident of [Clinic_Info_country]” '
        then: 'Verify the check box is selected.'
        clinicEULAPage.residentCheckboxOff()
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOn()

        when: 'Click the same check box again.'
        then: 'Verify the check box is unselected.'
        then: 'Verify that Terms Acceptance Indicator shall display an acceptance toggle indicator associated with the following statement:  “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOff()

        when: 'Click check box mentioned for “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        then: 'Verify the check box is selected.'
        clinicEULAPage.acceptCheckboxOff()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.acceptCheckboxOn()

        when: 'Click the same check box again.'
        then: 'Verify the check box is unselected.'
        then: 'Verify there is an Accept button, labeled as “Accept”.'
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.acceptCheckboxOff()

        when: 'Ensure that both the check boxes are selected for “I am a resident of [Clinic_Info_country]” and “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        clinicEULAPage.verifyAcceptButtonDisable()
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.residentCheckboxOn()
        clinicEULAPage.acceptCheckboxOn()
        then: 'Verify “Accept” button is active.'
        clinicEULAPage.verifyAcceptButtonEnable()

        when: 'Uncheck the both check boxes for “I am a resident of [Clinic_Info_country]” and “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.residentCheckboxOff()
        clinicEULAPage.acceptCheckboxOff()
        then: 'Verify “Accept” button is deactivated.'
        clinicEULAPage.verifyAcceptButtonDisable()

        when: 'Check the both check boxes for “I am a resident of [Clinic_Info_country]” and “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.residentCheckboxOn()
        clinicEULAPage.acceptCheckboxOn()
        and: 'Click on “Accept” button.'
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Verify the system shall navigate to Enrollment Clinic Information Screen .'
        clinicEnterInfoPage.enrollmentClinicInformationScreen()

        when: 'Click Cancel link on Enrollment Clinic Information Screen. From Login page navigate to Enrollment Terms Accept Screen again.'
        clinicEnterInfoPage.clickCancelLink()
        waitFor { browser.getCurrentUrl().contains("login") }
        go(enrollmentTermsAcceptanceScreenUrl)
        waitFor { clinicEULAPage.continueBtn.displayed }
        clinicEULAPage.continueBtn.click()
        waitFor { browser.getCurrentUrl().contains("enroll/locale.jsf") }
        clinicEULAPage = browsers.at ClinicEULAPage
        and: 'Check only check box mentioned for “I am a resident of [Clinic_Info_country]” '
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOn()
        then: 'Verify “Accept” button is not activated.'
        clinicEULAPage.verifyAcceptButtonDisable()

        when: 'Check only check box mentioned for “I have read, understood, and accept the Terms of Use and Privacy Statement.” '
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOff()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.acceptCheckboxOn()
        then: 'Verify “Accept” button is not activated.'
        clinicEULAPage.verifyAcceptButtonDisable()
        then: 'Verify there is a decline link, labeled as “Decline”.'
        clinicEULAPage.verifyDeclineButton()

        when: 'Click on decline link.'
        clinicEULAPage.buttonDeclineClick()
        signInPage = browsers.at SignInPage
        then: 'Verify the system shall discard the enrollee record and navigate to the Login Screen.'
        and: 'End of the test.'
    }
}