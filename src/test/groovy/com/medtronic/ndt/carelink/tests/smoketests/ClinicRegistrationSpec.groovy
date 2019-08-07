package com.medtronic.ndt.carelink.tests.smoketests

import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import spock.lang.Stepwise
import spock.lang.Title
import groovy.util.logging.Slf4j

@Slf4j
@Title('Smoke test - Register a clinic test')
@Screenshot
@SmokeTest
@Stepwise
class ClinicRegistrationSpec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

   def 'When a user is in the Login screen' () {
        when: 'A user enters a valid carelink URL'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully get to carelink login page'
    }
    def 'Click on register clinic'() {
        when:
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then:
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
    }
    def 'Choose locale for clinic'() {
        when:
        clinicLocalePage = browsers.at ClinicLocalePage
        then:
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
    def 'Enter Clinic Information'()
    {
        when:
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then:
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
    }
    def 'Enter Clinic Admin Information'(){
        when:
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then:
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
        when:
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then:
        finishClinicCreationPage.clickFinish()
    }

}