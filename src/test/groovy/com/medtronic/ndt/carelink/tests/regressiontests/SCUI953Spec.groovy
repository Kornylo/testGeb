package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.reports.PatientReportSettingsPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('SCUI 953 - Training link, disclaimer enhancements')
class SCUI953Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage
    static HomePage homePage
    static PatientReportSettingsPage patientReportSettingsPage
    static Precondition precondition

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final adminJapan = "JA" + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final emailAddress = Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final newPassword = 'Test1234@'

    def 'Open Carelink application'() {
        given: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        println("URL address: " + browser.getCurrentUrl())
    }

    def 'Register US clinic'() {
        when: 'Click on Register Clinic link. Create US clinic and record the info below: Clinic Name:'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, newPassword)
        then: 'Create a new administrative user and record the info below: Username: (example: TCXXX) Password: ___________________________ (example: Password1)'
        println("Username: " + adminUs + " Password: Test1234@")
    }

    def 'Verify Sign in Screen'() {
        when: ''
        signInPage = browsers.to SignInPage
        then: 'Verify that the Login Screen includes the following Common Screen Footer Element: Training (if locale is US).'
        signInPage.checkIncludedFooterElements()
        then: 'Training link is displayed.'
        when: 'Click on Training link.'
        then: 'Verify that you were navigated to the Marcom Training content.'
        then: 'Marcom Training content is displayed.'
        signInPage.verifyTrainingLink()
        when: 'Navigate back to Login screen.'
        then: 'Verify that the system displays the UDI associated with the particular production version of the software e.g. (01)00643169358430(10)3.6A'
        then: 'UDI associated with the particular production version of the software is displayed.'
        signInPage.verifyUDI("(01)00763000239473(10)2.2B")
    }

    def 'Sign in with wrong credentials'() {
        when: 'Sign into the MMT-7340 application using the invalid credentials.'
        signInPage.enterUsername("wrongLogin")
        signInPage.enterPassword('wrongPassword')
        signInPage.clickOnSignIn()
        then: 'Verify that the Incorrect Login Screen includes the Training (if locale is US) Footer Element.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
        then: 'Training link is displayed.'
        assert signInPage.trainingLink.displayed
    }

    def 'Sign in with correct credentials US clinic'() {
        when: 'Sign into the MMT-7340 application using the credentials from step 4.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(newPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Verify that if the [Clinic_Info_Country] value is US, a Training link, labeled as “Training” is displayed.'
        signInPage.signoutButtonDisplayed()
        then: 'Training link is displayed.'
        when: 'Click on Training link.'
        then: 'Verify that you were navigated to the Marcom Training content.'
        then: 'Marcom Training content is displayed.'
        when: 'Navigate back to Home screen.'
        signInPage.verifyTrainingLink()
        then: ''
    }

    def 'Extra step for commented session timeouts checking'() {
        when: ''
        then: ''
        driver.navigate().refresh()
        waitFor { browser.getCurrentUrl().contains("hcp/main/home.jsf") }
        signInPage.clickOnSignOutLink()
        signInPage.signInButtonDisplayed()
    }

//    def 'Verify session timeout'() {
//        when: 'Verify that the Session Timed Out Login Screen includes the Training (if locale is US) Footer Element.'
//        then: 'Leave the system for 60 minutes untouched.'
//        sleep((20 * 60 + 30) * 1000) //wait for 20 minutes and extra 30 seconds
//        signInPage.sessionTimeout()
//        then: 'Training link is displayed.'
//        signInPage.trainingLink.displayed
//        when: 'Click on Home link.'
//        signInPage.homeClick()
//        then: ''
//    }

    def 'Register Japan clinic'() {
        when: 'Click on Register Clinic link. Create Japan clinic and record the info below: Clinic Name:'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminJapan, "日本", emailAddress, newPassword)
        then: 'Create a new administrative user and record the info below: Username: (example: TCXXX) Password: ___________________________ (example: Password1)'
        println("Username: " + adminJapan + " Password: " + newPassword)
        when: ''
        signInPage = browsers.to SignInPage
        then: 'Verify that the Japanese disclaimer is displayed.'
        then: 'Japanese disclaimer is displayed.'
        then: 'Verify that the Training link is not displayed.'
        then: 'Training link is not displayed.'
        signInPage.checkJapanDisclaimer()
    }

    def 'Sign in with credentials for Japan clinic'() {
        when: 'Sign into the MMT-7340 application using the credentials from step 21.'
        precondition.signInAsClinicAdmin(adminJapan, newPassword)
        then: ''
        signInPage.signoutButtonDisplayed()
    }

    def 'Open Clinic Settings'() {
        when: ''
        clinicSettingsPage = browsers.at(ClinicSettingsPage)
        then: 'Click on “Clinic Settings” tab.'
        clinicSettingsPage.clinicSettingsClick()
    }

    def 'Change language to 日本語'() {
        when: 'If the screen is in English, click on the “Clinic Information” tab and switch language to Japanese. Click on [Save] button.'
        clinicSettingsPage.openClinicInformation()
        clinicSettingsPage.swicthLangToJapan(['日本語', 'English'])
        then: 'Click on the “Report Settings” tab.'
        clinicSettingsPage.openReportSettings()
        when: 'Enter "100" into all the Glucose text fields and click on the [Save] button.'
        clinicSettingsPage.enter100toAllGlucoseInput()
        then: 'Verify that the error messages are displayed in Japanese language.'
        then: 'Error messages are displayed in Japanese language.'
        clinicSettingsPage.verifyJapaneseError()
    }

    def 'Open patient by double-click on Sample Patient.'() {
        when: 'Navigate to the Home Page Screen and select and double-click on Sample Patient.'
        clinicSettingsPage.goToHomePageClick()
        homePage = browsers.at(HomePage)
        then:''
        homePage.openPatientByDoubleClick()
    }

    def 'Verify PDF'() {
        when: ''
        patientReportSettingsPage = browsers.at(PatientReportSettingsPage)
        then: 'Navigate to the dropdown menu and choose the second option from there (Generate data table).'
        patientReportSettingsPage.openGenerateDataTable("データテーブルの作成")
        when: 'Open Data table report.'
        then: 'Verify that the Date range for data is displayed in Long_Date_Format : yyyy M dd. Note:  is ‘年’,  is ‘月’, is ‘日’'
        then: 'Long date format is displayed correctly.'
        patientReportSettingsPage.openDataTableReport("2009 9. 28 - 2009 10. 04", "2009年9月28日(月)")
        then: 'End of test.'
    }
}
