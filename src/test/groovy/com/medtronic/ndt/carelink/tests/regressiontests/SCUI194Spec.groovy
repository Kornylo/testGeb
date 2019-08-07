package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.pages.ContactUsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI194 - Contact Us Request.')
class SCUI194Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static ContactUsPage сontactUsPage
    static Precondition precondition
    static HomePage homePage

    static final admin = "user" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password = "Test1234@"
    static final emailAddress1 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Register clinic & sign in'() {
        when: 'Open the browser as specified in the ETP.'
        precondition = browsers.to Precondition
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        println(driver.getCurrentUrl())
        and: 'Click on Register Clinic link. Create new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:'
        and: 'Username: (example: TC934)'
        and: 'Password: (example: Password1)'
        precondition.registerClinic(admin, LanguagePage.setCountry(), emailAddress, password)
        println("Username: " + admin + ", Password: " + password)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        precondition.signInAsClinicAdmin(admin, password)
        homePage = browsers.at HomePage
        then: 'Verify that system navigates to the Home screen.'
        homePage.homeScreenIsAppeared()
    }

    def 'Verify Contact us link & email on Home page'() {
        when: 'Press the Contact us link on the Home screen footer.'
        сontactUsPage = browsers.at ContactUsPage
        then: 'Verify that system open a new window or tab and display the contact us page.'
        then: 'Verify that the User Name field is not editable.'
        then: 'Verify that the User Name field is automatically populated with the Username associated with the current user.'
        then: 'Verify that the Clinic Name field is not editable.'
        then: 'Verify that the Clinic Name field is automatically populated with the Clinic Name associated with the current user.'
        then: 'Verify that the First Name field is not editable.'
        then: 'Verify that the First Name field is automatically populated with the First Name associated with the current user.'
        then: 'Verify that the Last Name field is not editable.'
        then: 'Verify that the Last Name field is automatically populated with the Last Name associated with the current user.'
        then: 'Verify that the Email field is not editable.'
        then: 'Verify that the Email field is automatically populated with the Email associated with the current user.'
        then: 'Verify that the Telephone field is editable.'
        then: 'Verify that the Telephone field is automatically populated with the Phone associated with the current user.'

        when: 'Enter the Text in the detail section.'
        and: 'Record the text you entered in the detail section:'
        then: 'Verify that Submit button is active.'

        when: 'Press submit button.'
        then: 'Verify that system send contact us email to the Contact us info email.'
        then: 'Verify that system navigates to the Contact Us Acknowledgement screen.'
        сontactUsPage.openContactUsAndVerifyUiContentForClinic(admin,"Dmytro", "Stadnik",
                emailAddress,"777-345-6789")

        when: 'Navigate to [Contact_Us_Info_Email] mailbox (Medtronic support email).'
        then: 'Note: ask Medtronic Support representative to check this email box for you.'
        then: 'Verify that Contact us email include following text and information:' +
                'Sent From: “Medtronic CareLink System Support [donotreply@medtronic.com]' +
                'Subject: “RE: Thank you for contacting Medtronic Diabetes” ' +
                'Body:' +
                'Thank you for contacting Medtronic Diabetes. Your inquiry has been forwarded to the appropriate individual at Medtronic Diabetes. We will be back to you shortly.' +
                'Product: CareLink iPro2' +
                'User Name : <User Name>' +
                'Clinic Name : < Clinic Name>' +
                'First Name: <First Name>' +
                'Last Name: <Last Name>' +
                'Email: <Email>' +
                'Telephone: <Telephone>' +
                'Details: <Details>' +
                'This is an automated message; please do not reply. If you need further assistance, please call customer support at [Contact_Us_Info_Phone].' +
                'Thank You,' +
                'Medtronic Diabetes'
        //todo manual step for email checking?
        when: 'Press Close button on the Contact Us Acknowledgement screen.'
        then: 'Verify that system closes the browser window the Contact Us Acknowledgement screen.'
    }

    def 'Verify Contact us link & email on Login page'(){
        when: 'Press the Sign out button on the Home screen.'
        homePage.homeScreenIsAppeared()
        homePage.clickSignOutButton()
        signInPage = browsers.at SignInPage
        signInPage.verifyLegalText()
        and: 'Press Contact us link on the Login page without login into the system.'
        сontactUsPage = browsers.at ContactUsPage
        then: 'Verify that system open a new window or tab and display the contact us page.'
        then: 'Verify that the User Name field is blank and editable.'
        then: 'Verify that the Clinic Name field is blank and editable.'
        then: 'Verify that the First Name field is blank and editable.'
        then: 'Verify that the Last Name field is blank and editable.'
        then: 'Verify that the Email field is blank and editable.'
        then: 'Verify that the Telephone field is blank and editable.'
        then: 'Verify that all the user fields are blank and editable.'

        when: 'Fill up all the user fields and note below:' +
                'Username:' +
                'Clinic Name:' +
                'First Name:' +
                'Last Name:' +
                'E-mail:' +
                'Telephone:' +
                'Details:'
        then: 'Verify that Submit button is active.'

        when: 'Press submit button.'
        then: 'Verify that system send contact us email to the Contact us info email.'
        then: 'Verify that system navigates to the Contact Us Acknowledgement screen.'
        сontactUsPage.openContactUsLoginScreen(emailAddress1)
        then: 'Navigate to [Contact_Us_Info_Email] mailbox (Medtronic support email).'
        then: 'Note: ask Medtronic Support representative to check this email box for you.'
        then: 'Verify that Contact us email include following text and information:' +
                'Sent From: “Medtronic CareLink System Support [donotreply@medtronic.com]' +
                'Subject: “RE: Thank you for contacting Medtronic Diabetes” ' +
                'Body:' +
                'Thank you for contacting Medtronic Diabetes. Your inquiry has been forwarded to the appropriate individual at Medtronic Diabetes. We will be back to you shortly.' +
                'Product: CareLink iPro2' +
                'User Name : <User Name>' +
                'Clinic Name : < Clinic Name>' +
                'First Name: <First Name>' +
                'Last Name: <Last Name>' +
                'Email: <Email>' +
                'Telephone: <Telephone>' +
                'Details: <Details>' +
                'This is an automated message; please do not reply. If you need further assistance, please call customer support at [Contact_Us_Info_Phone].' +
                'Thank You,' +
                'Medtronic Diabetes'
        //todo manual step for email checking?
        when: 'Press Close button on the Contact Us Acknowledgement screen.'
        then: 'Verify that system closes the browser window the Contact Us Acknowledgement screen.'
        then: 'End of test.'
    }
}

