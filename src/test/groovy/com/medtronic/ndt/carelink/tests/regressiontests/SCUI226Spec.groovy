package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.ContactUsPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('ScUI 226 - CLiP: Contact Us Screen Flow')
@Slf4j
@RegressionTest
@Screenshot
@Stepwise
class SCUI226Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static ContactUsPage contactUsPage
    static HomePage homePage

    def admin = 'Akilaa'
    def loginPassword = 'Test1234'

    def 'Contact Us Screen'() {
        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        and: 'Click on the Contact Us link.'
        contactUsPage = browsers.at ContactUsPage
        then: 'Verify that software opens up Contact Us screens in a new browser window or tab .'
        then: 'Verify that the Contact Us screen displays the Medtronic Logo and CareLink Logo from the Common Screen Header Elements. Note: NA this step if it does not apply.'
        then: 'Verify that the Contact Us screen displays the Medtronic Logo and CareLink iPro Clinical Logo from the Common Screen Header Elements. Note: NA this step if it does not apply.'
        then: 'Verify that the Contact Us screen did not display any of the Common Screen Footer Elements.'
        then: 'Verify that all user input fields are blank.'
        when: 'Click into any user input field and input some information.'
        then: 'Verify there is a Cancel link, labeled as Cancel.'
        when: 'Click on the Cancel button. A popup window opens up and click on “ Yes”'
        then: 'Verify the system aborted the Contact Us request by closing the browser window and discarded any data entered into the input fields.'
        contactUsPage.openContactUsAndVerifyUiContent()
    }
    def 'Sign in to clinic'(){
        when: 'Login with username'
        and: 'Navigate to the Login screen.'
        and: 'Create a new and record the User ID and Password'
        signInPage = browsers.to(SignInPage)
        then: 'Login using an existing username and password.'
        signInPage.enterUsername(admin)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
    }

    def 'ContactUs link'() {
        when: 'Click on the Contact Us link.'
        homePage = browsers.at HomePage
        homePage.clinicNameDisplayed()
        contactUsPage = browsers.at ContactUsPage
        then: 'Verify that software opens up Contact Us screens in a new browser window or tab .'
        then: 'Verify that the Contact Us screen displays the title “Contact Us”.'
        then: 'Verify that software displays the following text states “Please enter (or correct) your contact information below, add a description of how we may help you, and press “Submit” to send your request to our support team.” '
        then: 'Verify there is a Username user input field, labeled as “User Name”.'
        then: 'Verify software displays the current user account.(Clinic_Staff_User_LoginID) when accessed from within a user session.'
        then: 'Verify there is a Clinic Name user input field, labeled as “Clinic Name”'
        then: 'Verify software displays the current user account (Clinic_Info_Name) when accessed from within a user session.'
        then: 'Verify there is a First Name input field, labeled as “ First Name”'
        then: 'Verify there is a Last Name input field, labeled as “Last Name”'
        then: 'Verify software displays the current user account (Clinic_Staff_User_Last_Name) when accessed from within a user session.'
        then: 'Verify there is an “ Email ” user input field.'
        then: 'Verify that the system indicates this is a required field if the field is editable by the user .'
        then: 'Verify software displays the current user (Clinic_Staff_User_Email) when accessed from within a user session.'
        then: 'Verify there is a Telephone user input field.'
        then: 'Verify software displays the current user (Clinic_Info_Phone) when accessed from within a user session.'
        then: 'Verify Details user input field is provided and allow entry of up to 500 characters.'
        then: 'Verify that the system indicates details field as required field.'

        when: 'Click into the Details user input field and input a sentence.'
        then: 'Verify there is a Submit button, labeled as “Submit”.'
        then: 'Verify the Submit button become active when all required data fields are non-blank.'

        when: 'Click on the Submit button.'
        then: 'Verify the system shall submit the Contact _ Us _Request, and display the Contact Us Acknowledgement Screen.'
        then: 'Verify the Contact Us Acknowledgement screen displays the title “Request Submitted”.'
        then: 'Verify that application displays the following text states “Thank you. Your request has been submitted and someone from our support team will be in touch with you soon."'
        then: 'Verify there is a Close button, labeled as “Close”.'
        when: 'Click on the Close button.A popup window opens up and click on “ Yes”'
        then: 'Verify application closed the Contact Us Acknowledgement screen browser.'
        contactUsPage.openContactUsAndVerifyUiContentForClinic(admin, "Akila", "Agandeswaran", "akila.agandeswaran@medtronic.com", "4564564545")
    }
}