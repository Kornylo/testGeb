package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.pages.clinic.ClinicInformationPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('ScUI225 - CLiP: Clinic Information Screen')
@Slf4j
@RegressionTest
@Screenshot
@Stepwise
class SCUI225Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static LoginPage loginPage
    static ClinicSettingsPage clinicSettingsPage
    static ClinicInformationPage clinicInformationPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Login screen' () {
        when: 'Open MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: 'Verify the system navigates to login screen'
    }
    def 'Register clinic'() {
        when: 'Click on Register Clinic link. Create a new clinic and record the info below:' +
                'Clinic Name:' +
                'Create a new user and record:' +
                'Username' +
                'Password'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        finishClinicCreationPage.clickFinish()
        loginPage = browsers.at LoginPage
        then: 'Sign into the MMT-7340 application using the above credentials' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
    }
    def 'Click Clinic Settings' () {
        when: 'User clicks on the clinic settings tab'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'User is able to view clinic settings and close it'
        clinicSettingsPage.clickOnClinicSettings()
    }
    def 'Clinic Information' () {
        when: 'Navigate to clinic information screen'
        then: 'Verify that this section contains a Clinic Report Settings Link.'
        when: 'Clinic Information Screen contains Clinic Report Settings Link.'
        then: 'Verify that this section contains a Clinic Staff User Accounts Link.'
        then: '(Note: User Accounts Link will not be visible for non-admin accounts. NA this step.)'
        when: ''
        clinicInformationPage = browsers.at ClinicInformationPage
        clinicInformationPage.clickOnClinicInformation()
        clinicInformationPage.validationInformationClinic()
        clinicInformationPage.validationAddress1()
        clinicInformationPage.validationAddress2()
        clinicInformationPage.validationCity()
        clinicInformationPage.validationZipCode()
        clinicInformationPage.validationPhone()
        screenshot('Clinic Information', 'ClinicInfoValidationerror')
        log.info("Clicked on the clinic information tab")
        clinicSettingsPage.closeClinicSettings()
        then: 'Clinic Information Screen contains a Clinic Staff User Accounts Link.'
        then: 'Verify that this section contains a Clinic Information Link is highlighted and inactive. ' +
                'Clinic Information Screen contains a Clinic Information Link shall be highlighted and inactive.'

        when: 'Make any changes in any row on Clinic Information screen and click on the Report Settings link.'
        then: 'Verify that the screen navigates to Report Settings screen and any unsaved changes are discarded.' +
                'Report Settings link navigates to Report Settings screen and any unsaved changes are discarded.'

        when: 'Navigate to Clinic Information Screen.'
        then: 'Verify the system provides a Clinic Information Screen. Clinic Information Screen is provided.'

        when: 'Make any changes in any row on Clinic Information screen and click on the Users link , if applicable.'
        then: 'Verify that the screen navigates to Staff User Accounts Screen, and any unsaved changes are discarded.' +
                '(Note: NA this step if it does not apply.)' +
                'Staff User Accounts Screen is displayed and any unsaved changes are discarded.'

        when: 'Navigate to Clinic Information Screen.'
        then: 'Verify that there is a Close clinic settings link on the Clinic Settings screen. ' +
                'Close clinic settings link is on the Clinic Settings screen.'

        when: 'Make any changes in any row on Clinic Information screen and click on the Close clinic settings link'
        then: 'Verify that the system aborts any user modifications and navigates to Home screen and any unsaved changes are discarded . ' +
                'The system aborts any user modifications and navigates to Home screen and any unsaved changes are discarded.'

        when: 'Navigate back to Clinic Information Page'
        then: 'Verify that the user input fields for the following [Clinic_Info] elements provided ' +
                'and populated with data stored in the system upon entry into the Clinic Information Screen: ' +
                'Clinic Name, Address 1, Address 2, City, State/Province, Zip/Postal Code, Country/Territory, Language, Phone. ' +
                'The user input fields for the following [Clinic_Info] elements are provided ' +
                'and populated with data stored in the system upon entry into the Clinic Information Screen: ' +
                'Clinic Name, Address 1, Address 2, City, State/Province, Zip/Postal Code, Country/Territory, Language, Phone.'
        then: 'Verify that [Clinic_Info_Name] is displayed. [Clinic_Info_Name] is displayed.'
        then: 'Verify that [Clinic_Info_Address1] is displayed. [Clinic_Info_Address1] is displayed.'
        then: 'Verify that [Clinic_Info_Address2] is displayed. [Clinic_Info_Address2] is displayed.'
        then: 'Verify that [Clinic_Info_City] is displayed. [Clinic_Info_City] is displayed.'
        then: 'Verify the [Clinic_Info_State] field is displayed and contains the list of States.' +
                'Note: this step applicable to US States only. ' +
                'The [Clinic_Info_State] field is displayed and contains the list of States' +
                'Select (default), Select State: ALABAMA, ALASKA, AMERICAN SAMOA, ARIZONA, ARKANSAS, CALIFORNIA, COLORADO,' +
                'CONNECTICUT, DELAWARE, DISTRICT OF COLUMBIA, FEDERATED STATES OF MICRONESIA, FLORIDA, GEORGIA, GUAM, HAWAII' +
                'IDAHO, ILLINOIS, INDIANA, IOWA, KANSAS, KENTUCKY, LOUISIANA, MAINE, MARSHALL ISLANDS, MARYLAND, MASSACHUSETTS, ' +
                'MICHIGAN, MINNESOTA, MISSISSIPPI, MISSOURI, MONTANA, NEBRASKA, NEVADA, NEW HAMPSHIRE, NEW JERSEY, NEW MEXICO, ' +
                'NEW YORK, NORTH CAROLINA, NORTH DAKOTA, NORTHERN MARIANA ISLANDS, OHIO, OKLAHOMA, OREGON, PALAU, PENNSYLVANIA, ' +
                'PUERTO RICO, RHODE ISLAND, SOUTH CAROLINA, SOUTH DAKOTA, TENNESSEE, TEXAS, UTAH, VERMONT, VIRGIN ISLANDS, ' +
                'VIRGINIA, WASHINGTON, WEST VIRGINIA, WISCONSIN, WYOMING, ARMED FORCES AREAS, ARMED FORCES AREAS, ARMED FORCES AREAS'
        then: 'Verify the [Clinic_Info_State] field is displayed and contains the list of Provinces.' +
                'Note: this step applicable to Canada Provinces.' +
                'The [Clinic_Info_State] field is displayed and contains the list of States or Provinces:' +
                'Select (default), Select ProvinceAlberta, British Columbia, Manitoba, New Brunswick, Newfoundland and Labrador,' +
                'Northwest Territories, Nova Scotia, Nunavut, Ontario, Prince Edward Island, Quebec, Saskatchewan, Yukon'
        then: 'Verify that [Clinic_Info_State] is displayed for Japan Locale.' +
                '(Note : this step applicable to Japan Locale Only )” ' +
                '[Clinic_Info_State] is displayed for Japan Locale.'
        then: 'Verify that [Clinic_Info_ Postal_Code] is displayed.' +
                '[Clinic_Info_ Postal_Code] is displayed.'
        then: 'Verify that [Clinic_Info_Country] is displayed.' +
                '[Clinic_Info_Country] is displayed.'
        then: 'Verify that [Clinic_Info_Language] is displayed.' +
                '[Clinic_Info_Language] is displayed.'
        then: 'Verify that [Clinic_Info_Phone] is displayed.' +
                '[Clinic_Info_Phone] is displayed.'
        then: 'Verify that the system indicates the [Clinic_Info_Name], [Clinic_Info_Postal_Code], and [Clinic_Info_Phone] fields are required fields. ' +
                'The system indicates the [Clinic_Info_Name], [Clinic_Info_Postal_Code], and [Clinic_Info_Phone] fields are required fields.'
        then: 'Verify that a Save button is displayed. Save button is displayed.'

        when: 'Make some modifications to the User Entry Fields.'
        and: 'Click on the Save button.'
        then: 'Verify that upon pressing the Save button the system stores the [Clinic_Info] and then present the user with the confirmation dialog. ' +
                'Upon pressing the Save button the system stores the [Clinic_Info] and then present the user with the confirmation dialog.'
        then: 'Verify that the confirmation dialogue displays the following text:' +
                '“ SUCCESS! Your changes have been saved. ” ' +
                'Confirmation dialogue displays the following text: ' +
                '“ SUCCESS! Your changes have been saved. ”'
        then: 'Verify that the confirmation dialog has OK button. The confirmation dialog has OK button.'

        when: 'Click on the OK button.'
        then: 'Verify that screen navigates to Clinic Information Screen. Screen navigates to Clinic Information Screen.'

        when: 'Update textfields with valid value and “<” symbol (e.g. “test<”):' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City' +
                '- Zip/Postal Code' +
                '- Phone'
        and: 'Save changes' +
                'Note: user stays on Clinic Information screen after saving changes'
        then: 'Verify Clinic Name cannot be saved with “<” symbol on Clinic Information screen' +
                'Clinic Name with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 1 cannot be saved with “<” symbol on Clinic Information screen' +
                'Address 1 with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 2 cannot be saved with “<” symbol on Clinic Information screen' +
                'Address 2 with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify City cannot be saved with “<” symbol on Clinic Information screen' +
                'City with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Zip/Postal Code cannot be saved with “<” symbol on Clinic Information screen' +
                'Zip/Postal Code with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Phone cannot be saved with “<” symbol on Clinic Information screen' +
                'Phone with “<” symbol is not saved and Error message is displayed near textfield'

        when: 'Update textfields with valid value and “>” symbol (e.g. “>test”):' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City' +
                '- Zip/Postal Code' +
                '- Phone'
        and: 'Save changes' +
                'Note: user stays on Clinic Information screen after saving changes'
        then: 'Verify Clinic Name cannot be saved with “>” symbol on Clinic Information screen' +
                'Clinic Name with “>” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 1 cannot be saved with “>” symbol on Clinic Information screen' +
                'Address 1 with “>” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 2 cannot be saved with “>” symbol on Clinic Information screen' +
                'Address 2 with “>” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify City cannot be saved with “>” symbol on Clinic Information screen' +
                'City with “>” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Zip/Postal Code cannot be saved with “>” symbol on Clinic Information screen' +
                'Zip/Postal Code with “>” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Phone cannot be saved with “>” symbol on Clinic Information screen' +
                'Phone with “>” symbol is not saved and Error message is displayed near textfield'

        when: 'Update textfields with valid value and “&” symbol (e.g. “te&st”):' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City'
        and: 'Save changes' +
                'Note: user stays on Clinic Information screen after saving changes'
        then: 'Verify Clinic Name can be saved with “&” symbol on Clinic Information screen' +
                'Clinic Name with “&” symbol is saved.'
        then: 'Verify Address 1 can be saved with “&” symbol on Clinic Information screen' +
                'Address 1 with “&” symbol is saved.'
        then: 'Verify Address 2 can be saved with “&” symbol on Clinic Information screen' +
                'Address 2 with “&” symbol is saved.'
        then: 'Verify City can be saved with “&” symbol on Clinic Information screen' +
                'City with “&” symbol is saved and Error message is not displayed near textfield'

        when: 'Update textfields with valid value and “&” symbol (e.g. “te&st”):' +
                '- Zip/Postal Code' +
                '- Phone' +
                'Click [Save] button.'
        then: 'Verify Zip/Postal Code cannot be saved with “&” symbol on Clinic Information screen' +
                'Zip/Postal Code with “&” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Phone cannot be saved with “&” symbol on Clinic Information screen' +
                'Phone with “&” symbol is not saved and Error message is displayed near textfield'

        when: 'Update textfields with valid value and “=” symbol (e.g. “te=st”):' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City' +
                '- Zip/Postal Code' +
                '- Phone'
        and: 'Save changes' +
                'Note: user stays on Clinic Information screen after saving changes'
        then: 'Verify Clinic Name cannot be saved with “=” symbol on Clinic Information screen' +
                'Clinic Name with “=” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 1 cannot be saved with “=” symbol on Clinic Information screen' +
                'Address 1 with “=” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Address 2 cannot be saved with “=” symbol on Clinic Information screen' +
                'Address 2 with “=” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify City cannot be saved with “=” symbol on Clinic Information screen' +
                'City with “=” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Zip/Postal Code cannot be saved with “=” symbol on Clinic Information screen' +
                'Zip/Postal Code with “=” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Phone cannot be saved with “=” symbol on Clinic Information screen' +
                'Phone with “=” symbol is not saved and Error message is displayed near textfield'

        when: 'Insert 101 characters into [Clinic_Info_Name] field.'
        then: 'Verify the [Clinic_Info_Name] field is up to 100 characters long.' +
                'The field is allowed to contain 100 characters.'

        when: 'Insert 121 characters into [Clinic_Info_Address1] field.'
        then: 'Verify the [Clinic_Info_Address1] field is up to 120 characters long.' +
                'The field is allowed to contain 120 characters.'

        when: 'Insert 121 characters into [Clinic_Info_Address2] field.'
        then: 'Verify the [Clinic_Info_Address2] field is up to 120 characters long.' +
                'The field is allowed to contain 120 characters'

        when: 'Insert 121 characters into [Clinic_Info_City] field.'
        then: 'Verify the [Clinic_Info_City] field is up to 120 characters long.' +
                'The field is allowed to contain 120 characters.'

        when: 'Insert 21 characters into [Clinic_Info_Postal_Code] field.'
        then: 'Verify the [Clinic_Info_Postal_Code] field is up to 20 characters long.' +
                'The field is allowed to contain 20 characters.'
        then: 'Verify the [Clinic_Info_Postal_Code] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification. ' +
                'The [Clinic_Info_Postal_Code] value is special for definite country.'
        then: 'Verify the [Clinic_info_Country] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification. ' +
                '[Clinic_info_Country] value is displayed as specified in Internationalization Specification.'
        then: 'Verify the [Clinic_info_Language] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification. ' +
                '[Clinic_info_Language] value is determined by each user account country.'

        when: 'Insert 21 characters into [Clinic_info_Phone] field.'
        then: 'Verify the [Clinic_info_Phone] field is up to 20 characters long. The field is allowed to contain 20 characters.'

        when: 'Update following text fields with leading spaces:' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City' +
                '- Zip/Postal Code' +
                '- Phone' +
                'Click [Save] button and confirm changes.'
        then: 'Verify [Clinic_info_Name] is saved without leading space.' +
                '[Clinic_info_Name] field contains data without leading space.'
        then: 'Verify [Clinic_info_Address1] is saved without leading space.' +
                '[Clinic_info_Address1] field contains data without leading space.'
        then: 'Verify [Clinic_info_Address2] is saved without leading space.' +
                '[Clinic_info_Address2] field contains data without leading space.'
        then: 'Verify [Clinic_info_City] is saved without leading space.' +
                '[Clinic_info_City] field contains data without leading space.'
        then: 'Verify [Clinic_info_Postal_Code] is saved without leading space.' +
                '[Clinic_info_Postal_Code] field contains data without leading space'
        then: 'Verify [Clinic_info_Phone] is saved without leading space.' +
                '[Clinic_info_Phone] field contains data without leading space'

        when: 'Update following text fields with trailing spaces:' +
                '- Clinic Name' +
                '- Address 1' +
                '- Address 2' +
                '- City' +
                '- Zip/Postal Code' +
                '- Phone' +
                'Click [Save] button and confirm changes.'
        then: 'Verify [Clinic_info_Name] is saved without trailing space.' +
                '[Clinic_info_Name] field contains data without trailing space.'
        then: 'Verify [Clinic_info_Address1] is saved without trailing space.' +
                '[Clinic_info_Address1] field contains data without trailing space.'
        then: 'Verify [Clinic_info_Address2] is saved without trailing space.' +
                '[Clinic_info_Address2] field contains data without trailing space.'
        then: 'Verify [Clinic_info_City] is saved without trailing space.' +
                '[Clinic_info_City] field contains data without trailing space.'
        then: 'Verify [Clinic_info_Postal_Code] is saved without trailing space.' +
                '[Clinic_info_Postal_Code] field contains data without trailing space.'
        then: 'Verify [Clinic_info_Phone] is saved without trailing space.' +
                '[Clinic_info_Phone] field contains data without trailing space.'
        then: 'Verify [Clinic_Info_Password_Expiration] is displayed.' +
                '[Clinic_Info_Password_Expiration] is displayed.'
        then: 'Verify the [Clinic_Info_Password_Expiration] checkbox set on ""Off"" by default.' +
                '[Clinic_Info_Password_Expiration] checkbox set on ""Off"" by default.'
        then: 'Verify the system displays a [Clinic_Info_ID]. ' +
                '[Clinic_Info_ID] is displayed.'
        then: 'Verify the system displays a QR code, a machine-readable code consisting of an array of black and white squares generated by the system.'
        then: 'Note: If Spark_Flag is True.' +
                'The system displays a QR code.'
        then: 'End of test.'
    }
//    def 'User Logout' () {
//        when:'A user is logged into carelink'
//
//        then: 'User should be able to sign out from carelink'
//        assert signInPage.signoutButtonDisplayed()
//        signInPage.clickOnSignOutLink()
//    }
}