package com.medtronic.ndt.carelink.tests.smoketests

import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import spock.lang.Stepwise

@SmokeTest
@Stepwise
class signInPageSpec extends CareLinkSpec {
    static SignInPage signInPage

    def 'User Login' () {
        when: 'A user supplies valid carelink credentials'
        signInPage = browsers.to SignInPage
        signInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
    }
    def 'Search Patient' () {
        when: 'A user is logged into Carelink application'

        then: 'User should be able to search for a patient'
        signInPage.searchForPatient("Test")
    }
    def 'Select Patient' () {
        when: 'A user is logged into Carelink application'
        then: 'User should be able to select a patient'
        signInPage.selectFirstPatient()
    }
    def 'Open Patient' () {
        when: 'A patient is selected'
        then: 'User should be able to open patient'
        signInPage.openPatient()
    }
    def 'Close Patient' () {
        when: 'A patient is opened'
        then: 'User should be able to close patient'
        signInPage.closePatient()
    }
    def 'User Logout' () {
        when:'A user is logged into carelink'
        signInPage = browsers.at SignInPage
        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
    }
    def 'error flows' () {
        when: 'Username and password fields are left blank'
        signInPage = browsers.at SignInPage

        then: 'Check error message is displayed'
        signInPage.enterUsername('')
        signInPage.enterPassword('')
        signInPage.clickOnSignIn()
        signInPage.assertErrorMsg(true)
        and: 'Check login warning message'
        signInPage.assertPageErrorMsgContains('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
    }
}
