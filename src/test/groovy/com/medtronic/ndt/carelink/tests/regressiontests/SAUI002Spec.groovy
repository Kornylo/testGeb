package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import com.medtronic.ndt.carelink.util.SwitchingUtil
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Title('SaUI002 - Session Timed Out Login Screen')
class SAUI002Spec extends CareLinkSpec {
    static SignInPage signInPage
    static Precondition precondition
    static LoginPage loginPage
    static SwitchingUtil globalDataUtil
    static CreatePatientPage createPatientPage
    static HomePage homePage

    static final admin = "Admin" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Register clinic & sign in'() {
        when: 'Execute SaUI002 per test plan.'
        and: 'Open the MMT-7340 application (CareLink system).' +
                'Record the server URL address:'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on Register Clinic link ' +
                'Create a new clinic and record the info: ' +
                '- Clinic name '
        and: 'Create a new administrative user and record the info: \n' +
                '- Username' +
                '- Password'
        precondition = browsers.to Precondition
        precondition.registerClinic(admin, LanguagePage.setCountry(), emailAddress, "Test1234@")
        and: 'Sign into the MMT-7340 application using the credentials above.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        then: ''
    }

    def homeURL = browser.getCurrentUrl()

    def 'Verify Timeout on Home screen'() {
        when: 'Do not perform any activity on Home page until the Inactivity Warning Window appears. Note: about 18 minutes'
        sleep((18 * 60) * 1000)
        loginPage = browsers.at LoginPage
        then: 'Verify that the Inactivity Warning Window is displayed after at least 18 minutes and no more than 20 minutes. ' +
                'The Application shall display a dialog indicating that the session is about to expire 2 minutes before auto logging out the user due to inactivity.'
        loginPage.isSessionTimeoutPopupTextDisplayed()
        when: 'Click on [Resume] button on the Inactivity Warning Window'
        then: 'Verify that the application briefly replaces the Inactivity Warning Window with a Session Updated Window. ' +
                'The application briefly replaced the Inactivity Warning Window with a Session Updated Window'
        then: 'Verify that user user session is resumed ' +
                'The user is able to access pages that requires him to be logged in'
        loginPage.clickResume()
        when: ''
        homePage = browsers.at HomePage
        homePage.patientListDisplayed()
        and: 'Do NOT perform any activity on the page for 20 min.'
        sleep((20 * 60) * 1000)
        loginPage = browsers.at LoginPage
        then: 'Verify that system logged out the user. The application shall log out the user after 20 minutes of inactivity.'
        then: 'Verify that system displayed a Session Timeout Login screen with a message indicating that the user’s session has timed out after 20 minutes of inactivity.'
        then: 'Verify that Forgot Username and Forgot Password links are provided on Session Timeout Login screen ' +
                'Forgot Username and Forgot Password links shall be provided'
        then: 'Verify that the Username and Password input fields are provided for re-login on Session Timeout Login screen ' +
                'The Application shall provide the capability to re-login after the user is logged out due to inactivity'
        then: 'Verify the Session Timed Out Login Screen include the following Common Screen Footer Elements: User Guide, ' +
                'Training (if locale is US), Terms of Use, Privacy Statement, Contact Us. ' +
                'The Session Timed Out Login Screen shall include the following Common Screen Footer Elements: User Guide, Training (if locale is US), Terms of Use, Privacy Statement, Contact Us.'
        then: 'Verify that [Sign In] button is provided on the Session Time Out Login screen. ' +
                'A [Sign In] button shall be provided with identical functionality to those in the Login screen.'
        then: 'Verify that the Home link is provided on Session Timeout Login screen.'
        loginPage.verifyTimeOutError()
        driver.navigate().refresh()
        sleep(2000)
        when: 'Navigate to the Home page using the direct link in browser address bar: ' +
                '“[iPro URL]/ipro/hcp/main/home.jsf”'
        browser.go(homeURL)
        then: 'Verify user is not able to access pages that requires to be logged in when his session has expired.User is redirected to the Login page'
        assert browsers.getCurrentUrl().contains("hcp/login.jsf")
    }

    def 'Verify Timeout on Patient Record screen'() {
       when: 'Enter credentials from Step ID 2898561 and click on [Sign In] button. ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        def signInUrl = browser.getCurrentUrl()
        loginPage = browsers.at LoginPage
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        and: 'Open second tab in browser and navigate to the link from step ID 2898557. ' +
                'Sign into the MMT-7340 application using the credentials from step ID 2898561. ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        globalDataUtil = browsers.at SwitchingUtil
        loginPage.userGuidePageOpen()
        sleep(1500)
        globalDataUtil.switchToNewTab()
        browser.go(signInUrl)
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        sleep(1500)
        globalDataUtil.backToTab()
        and: 'Open the first tab. ' +
                'Click on [New patient] button.'
                'Create a new patient and record the info:' +
                '- First Name ' +
                '- Last Name ' +
                '- Date of Birth ' +
                '- Patient Email ' +
                '- Physician Name ' +
                '- Diabetes Type ' +
                '- Therapy Type'
        homePage = browsers.at HomePage
        homePage.clickNewPatientBtn()
        createPatientPage = browsers.at CreatePatientPage
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        println("Date of Birth: March, 20, 1978\n " +
                "Diabetes Type: Type 2\n" +
                "Therapy Type: Other")
        and: 'On the Patient Record screen do NOT perform any activity until the Inactivity Warning Window appears.' +
                'Note: about 18 minute'
        sleep((18 * 60) * 1000)
        then: 'Verify that the Inactivity Warning Window is displayed after at least 18 minutes and no more than 20 minutes. ' +
                'The application shall display a dialog indicating that the session is about to expire 2 minutes before auto logging out the user due to inactivity'
        loginPage.isSessionTimeoutPopupTextDisplayed()
        when: 'Click on Home link.'
        browsers.go(homeURL)
        then: 'Verify the system briefly replaces the Inactivity Warning Window with a Session Updated Window. ' +
                'The Application shall notify the user that the session has been updated after the user confirms to resume the current session'
        loginPage.verifyTimeOutIsNotDisplayed()

        when: 'Do NOT perform any activity on the page for 20 min.'
        sleep((20 * 60) * 1000 + 5000)
        then: 'Verify that system logged out the user to the Session Timed Out Login Screen.' +
                'Verify that the Application shall navigate to the Session Timed Out Login Screen if the user is logged out due to inactivity'
        loginPage.verifyTimeOutError()
        when: 'Switch back to the second browser tab.'
        then: 'Verify that system logged out the user from all browser tabs ' +
                'User is logged out to Session Timed Out Login screen on the second tab'
        globalDataUtil.backToTab()
        assert browser.getCurrentUrl().contains("session-timeout")
        when: 'Close the second browser tab.'
        browser.close()
        and: 'Re-sign into the MMT-7340 application using the credentials from step ID 2898561.' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        sleep(2000)
        browser.go(signInUrl)
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        then: 'Verify the System provides the capability to re-login after the user is logged out due to inactivity. User is successfully logged in'
        assert browser.getCurrentUrl().contains("home.jsf")
    }

    def 'Verify Timeout on Home screen '() {
        when: 'Open new window in browser and navigate to the link from step ID 2898557. ' +
                'Sign into the MMT-7340 application using the credentials from step ID 2898561. ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        def signInUrl = browser.getCurrentUrl()
        globalDataUtil = browsers.at SwitchingUtil
        loginPage = browsers.at LoginPage
        loginPage.userGuidePageOpen()
        sleep(1500)
        globalDataUtil.switchToNewTab()
        sleep(2000)
        browser.go(signInUrl)
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        and: 'Do NOT perform any activity on the second browser window for 20 min.'
        sleep((20 * 60) * 1000)
        then: 'Verify that system logged out the user to the Session Timeout Login Screen on first and second browser windows.' +
                'System logged out the user to the Session Timeout Login Screen on the first and second browser windows.'
        loginPage.verifyTimeOutError()
        globalDataUtil.backToTab()
        sleep(2000)
        loginPage.verifyTimeOutError()
        then: 'Close both browser windows.'
        then: 'End of test.'
    }
}