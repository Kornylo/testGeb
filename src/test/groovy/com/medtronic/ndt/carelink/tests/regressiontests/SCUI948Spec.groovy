package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HCP.MyInfoPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.MailUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI948 - 2FA_My Info&Clinic Settings')
class SCUI948Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static HomePage homePage
    static Precondition precondition
    static ClinicSettingsPage clinicSettingsPage
    static MyInfoPage myInfoPage

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress2 = "email2" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress3 = "email3" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Register new clinic & sign in'() {
        when: 'Execute ScUI 948 as per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink Software).' +
                'Record the server URL address.'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on the Register Clinic link.' +
                'Create a new clinic.' +
                'Record the info: -Clinic Name:'
        and: 'Create a new administrative user.' +
                ' Record the info:' +
                '- Username (example: TCXXXX);' +
                '- Password (example: Password1).'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, "Belgique", emailAddress, "Test1234@")
        println("Username: " + adminUs + " Password: Test1234@")
        and: 'Sign into the MMT-7340 application using the credentials above.'
        and: 'Click on Postpone link.'
        precondition.signInAsClinicAdmin(adminUs, "Test1234@")
        then: ''
    }

    def 'Create new user'() {
        when: 'Click on Clinic Settings tab.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        and: 'Click on Users tab.'
        clinicSettingsPage.assertUsersClick()
        and: 'Click on [Create new user] button.'
        and: 'Fill in required fields marked with “*” and click on [Save] button.' +
                'Record the info:' +
                '- Username:' +
                '- Password:'
        clinicSettingsPage.createNewUser(emailAddress2)
        String patientUserName = clinicSettingsPage.getUserName()
        clinicSettingsPage.enterCurrentPass("Test1234@")
        clinicSettingsPage.enterPass("Password123")
        println("Username: " + patientUserName + " Password: Password123")
        clinicSettingsPage.waitForUserButton()
        and: 'Choose user created in step above and click on [Open user] button.'
        then: 'Verify that there is user input field for the following [Clinic_Staff_User] element provided and populated with data stored in the system upon entry into the Clinic Information Screen:' +
                'A Reset MFA option in a column labeled as “Reset Two-Factor Authentication” is displayed.'

        when: 'Click on Cancel link.'
        clinicSettingsPage.verifyPatientForm(emailAddress2, patientUserName, patientUserName, 'Maluy013+')
        then: ''
    }

    def 'Verify user with admin privileges & Clinic info'() {
        when: 'Open the user with admin privileges. (His username must be the same as username recorded in step 2764069.)'
        clinicSettingsPage.selectAdminUser()
        then: 'Verify that the following text is displayed associated with the [Clinic_Staff_User_Reset_MFA] setting:' +
                '“Check this box to reset this user’s two-factor authentication information”.'
        then: 'Verify that Help icon is displayed associated with the [Clinic_Staff_User_Reset_MFA] setting.'

        when: 'Click on Help icon.'
        then: 'Verify that after clicking on Help icon, the Help text for the MFA feature in a new window is displayed.'

        when: 'Click on Close Window link.'
        and: 'Click on Cancel link.'
        clinicSettingsPage.verifyPatientForm(emailAddress, "Dmytro", "Stadnik", "Amazing")
        and: 'Click on Clinic Information tab.'
        clinicSettingsPage.openClinicInformation()
        then: 'Verify that default value for MFA is Enabled, in case of Two_Factor_Flag is True, Disabled otherwise.'
        then: 'Verify that there are user input fields for the following [Clinic_Info] elements provided and populated with data stored in the system upon entry into the Clinic Information Screen:' +
                '[Clinic_Info_Password_Expiration] labeled as “90-day Password Expiration” [Clinic_Info_MFA_Enabled] labeled as “Two-Factor Authentication”'
        then: 'Verify that system displays Help icon for MFA.'

        when: 'Click on Help icon.'
        then: 'Verify that after clicking on Help icon, the help text for the MFA feature is displayed in a new window.'

        when: 'Click on Close Window link.'
        then: 'Verify that system displays disclaimer text.'
        clinicSettingsPage.verifyClinicInformationTab()
    }

    def 'Add Email address'() {
        when: 'Click on My Info link.'
        myInfoPage = browsers.at MyInfoPage
        myInfoPage.clickOnMyInfo()
        then: 'Verify that the screen displays the User Entry Fields, populated with data for the currently logged in [Clinic_Staff_User], as defined for the Staff User Information Window except for [Clinic_Staff_User_Admin_Privilege] and Reset MFA option.' +
                'Note: Admin privilege and Reset Two-Factor checkboxes are not present.'
        then: 'Verify that the text indicating that MFA email will only be used for MFA is displayed.'
        then: 'Verify that if the currently logged in [Clinic_Staff_User] does not have MFA email already used, the screen displays Add Email address link.'
        myInfoPage.verifyMFA(emailAddress, "Dmytro", "Stadnik", "Amazing")
        when: 'Click on Add Email address link.'
        then: 'Verify that system navigates to MFA Configuration Screen.'

        when: 'Enter your valid Medtronic email address into the text field.'
        and: 'Click on [Send email now] button.'
        and: 'Navigate to your email address and enter the code from received e-mail to the application.'
        and: 'Click on [Verify Code] button.'
        myInfoPage.addEmailAddress(emailAddress3)
        MailUtil.waitForLetter(emailAddress3, 1)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress3)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress3).last.body.split('\n').getAt(3).replace('\r', '').trim())
        and: 'The system navigates to My Info screen.'
        then: 'Verify that if [Clinic_Staff_User_MFA_Email] contains an email address, the screen displays email address labeled as “Email address”.'
        then: 'Verify that for the email displayed, the screen displays a Change link.'

        when: 'Click on Change link for Email.'
        then: 'Verify that change link navigates you to MFA Configuration Screen.'

        when: 'Click on Cancel link.'
        then: 'Verify that My Info screen is displayed.'
        myInfoPage.verifyEmail(emailAddress3)
    }

    def 'Reset Two-Factor Authentication'() {
        when: 'Click on Clinic Settings tab.'
        myInfoPage.openClinicSettingsTab()
        clinicSettingsPage = browsers.at ClinicSettingsPage
        and: 'Click on Users tab.'
        clinicSettingsPage.assertUsersClick()
        and: 'Open the user with admin privileges.' +
                'Note: the username must be the same as username recorded in step 2764069.'
        clinicSettingsPage.selectAdminUser()
        and: 'Check the checkbox for Reset Two-Factor Authentication and click on [Save] button.'
        clinicSettingsPage.resetTwoFactorAuthentication()
        and: 'Navigate to My Info link.'
        myInfoPage = browsers.at MyInfoPage
        myInfoPage.clickOnMyInfo()
        then: 'Verify that the MFA Email address was cleared and Add email address link is displayed.'
        myInfoPage.verifyMFA(emailAddress, "Dmytro", "Stadnik", "Amazing")
    }

    def 'Disable Two-Factor Authentication'() {
        when: 'Click on Clinic Settings tab.'
        myInfoPage.openClinicSettingsTab()
        clinicSettingsPage = browsers.at ClinicSettingsPage
        and: 'Click on Clinic Information tab.'
        and: 'Select “Disabled” radio button for Two-Factor Authentication element and click on [Save] button.'
        and: 'Click on [OK] button in pop-up window.'
        clinicSettingsPage.openClinicInformation()
        clinicSettingsPage.disableTwoFactorAuthenClinicInformationTab()
        and: 'Navigate to Users tab.'
        clinicSettingsPage.assertUsersClick()
        and: 'Open the user with admin privileges.' +
                'Note: the username must be the same as username recorded in step 2764069.'
        clinicSettingsPage.selectAdminUser()
        then: 'Verify that the following text is not displayed associated with the [Clinic_Staff_User_Reset_MFA] setting: “Check this box to reset this user’s two-factor authentication information”.'

        when: 'Click on Cancel link.'
        clinicSettingsPage.verifyTwoFactorAuthenIsNotDisplayed()
        and: 'Click on My Info link.'
        myInfoPage = browsers.at MyInfoPage
        myInfoPage.clickOnMyInfo()
        then: 'Verify that the Two-Factor Authentication section is not displayed when the clinic has [Clinic_Info_MFA_Enabled] set to disabled.'
        myInfoPage.verifyTwoFactorAuthenSectionIsNotDisplayed()
        then: 'End of test.'
    }
}