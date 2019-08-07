package com.medtronic.ndt.carelink.tests.smoketests

import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import spock.lang.Stepwise
import spock.lang.Title

@Title('Smoke test - View all the logged events')
@SmokeTest
@Stepwise
class LogBookSpec extends CareLinkSpec{
    static SignInPage signInPage
    static LogbookPage logBookPage

    def 'User Login' () {
        when: 'A user supplies valid carelink credentials'
        signInPage = browser.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
    }
    def 'Search Patient' () {
        when: 'A user is logged into Carelink application'

        then: 'User should be able to search for a patient'
        signInPage.searchForPatient("Sample")
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
    def 'Open Logbook' () {
        when: 'Open logbook'
        logBookPage = browsers.at LogbookPage
        then: 'I am on logbook page'
        logBookPage.openLogBook()
    }
    def 'Close Logbook' () {
        when: 'A logbook is opened'
        sleep(2000)
        then: 'User should be able to log events and close logbook'
        assert logBookPage.isCloseLogBookDisplayed()
        logBookPage.closeLogBook()
    }
    def 'Close Patient' () {
        when: 'A patient is opened'
        signInPage = browsers.at SignInPage
        then: 'User should be able to close patient'
        signInPage.closePatient()
    }
    def 'User Logout' () {
        when:'A user is logged into carelink'

        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
    }
}

