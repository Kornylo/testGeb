package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.util.SwitchingUtil
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j

@Slf4j
@SmokeTest
class LoginPageSpec extends CareLinkSpec {
    static SignInPage signInPage
    static LoginPage loginPage
    static SwitchingUtil globalDataUtil
    def "User able to see carelink banner image in login page"() {
        given: "User is at the carelink login page"
        loginPage = browsers.to LoginPage
//        when: 'User is hovering over an image'
        and: 'User is able to see banner image'
        assert loginPage.isBannerImageDisplayed()
    }

    def 'Login Error'() {
        when: 'Verification that error message is showing up'
        signInPage = browsers.to signInPage
        then: 'Unsuccessfully login to Carelink '
        signInPage.enterUsername("****")
        signInPage.enterPassword("1234")
        signInPage.clickOnSignIn()
        and: 'User is able to see error message'
        signInPage.assertErrorMsg(true)
        log.info( 'Verifying the error message is displayed')
        signInPage.assertPageErrorMsgContains('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
    }




}