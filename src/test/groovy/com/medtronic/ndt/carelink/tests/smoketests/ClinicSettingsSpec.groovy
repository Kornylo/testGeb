package com.medtronic.ndt.carelink.tests.smoketests

import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import spock.lang.Stepwise
import spock.lang.Title

@Title('Smoke test - Verify the clinic settings test')
@SmokeTest
@Stepwise
class ClinicSettingsSpec extends CareLinkSpec {
    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage

    def 'User Login' () {
        when: 'A user supplies valid carelink credentials'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
    }
    def 'Click Clinic Settings' () {
        given: 'When user successfully logged into carelink application'

        when: 'User clicks on the clinic settings tab'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'User is able to view clinic settings and close it'
        clinicSettingsPage.clickOnClinicSettings()

    }
    def 'Close Clinic Settings' () {
        given: 'When user successfully logged into carelink application'
        when: 'User clicks on the clinic information'

        then: 'User is able to view clinic information and close it'
        clinicSettingsPage.closeClinicSettings()
    }
    def 'User Logout' () {
        when:'A user is logged into carelink'

        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
    }
}
