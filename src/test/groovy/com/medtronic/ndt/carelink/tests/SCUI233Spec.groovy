package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('ScUI233 - Enrollment screen flow')
@Screenshot
@SmokeTest
@Stepwise
@Slf4j
class SCUI233Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Login screen' () {
        when: 'Open MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: 'Verify the system navigates to login screen'
    }
    def 'Register clinic'() {
        when: 'Click on register clinic'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then: 'Verify the system navigates to enrollment info screen and click on continue'
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
    }
    def 'Choose locale for clinic'() {
        when: 'Verify the system navigates to enrollment country and language selection screen'
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Choose the country and language and click on continue button'
        clinicLocalePage.selectLocale()
    }
    def 'Accept EULA'() {
        when:
        clinicEULAPage = browsers.at ClinicEULAPage
        then:
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
    }
    def 'Enrollment Clinic Information'()
    {
        when: 'Verify the system navigates to enrollment clinic information screen'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Enter required details and click on continue button'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
    }
    def 'Enrollment Admin Information'(){
        when: 'Verify the system navigates to enrollment administrator information screen'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Enter required information in user entry fields and click on continue button'
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Lakshmi")
        clinicEnterAdminInfoPage.clickContinue()
    }
    def 'Finish Clinic Registration' (){
        when: 'Verify the system navigates to enrollment finish screen'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Click on finish button'
        finishClinicCreationPage.clickFinish()
        log.info('Verify the system closes the enrollment browser')
    }

}