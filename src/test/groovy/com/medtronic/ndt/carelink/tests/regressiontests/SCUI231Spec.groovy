package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Slf4j
@Title('ScUI231 - Enrollment Clinic Information Screen')
@Stepwise
@Screenshot
class SCUI231Spec extends CareLinkSpec implements ScreenshotTrait {

    static SignInPage signInPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicLocalePage clinicLocalePage

    def 'Login screen'() {
        when: 'Open MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: 'Verify the system navigates to login screen. Login screen is displayed.'
        signInPage.checkIncludedFooterElements()
    }

    def 'Enrollment clinic information validations'() {
        when:
        'Click on the Change Country/Territory and Language link.\n' +
                'Select Portugal and Português.'
        and:
        'Open dev tools.\n' +
                'Select [System Communication Area Image] for "Medtronic Diabetes" as DOM element.'
        then: 'Verify the [System Communication Area Image] Image location.[System Communication Area Image] Image location is \'/marcom/ipro2/pt /PT/banner.jpg\''
        when:
        'Click on the Change Country/Territory and Language link.\n' +
                'Select the country and language as per Local and Language under test.\n' +
                'Click on Register Clinic link.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: ' Verify that system opens Enrollment Info Screen Enrollment Info Screen titled “Clinic Registration” is displayed.'
        then: 'Click on continue link.'
        newClinicRegistrationPage.clickOnContinue()
        when: 'Verify that system navigates to Enrollment Country/Territory and Language Selection Screen.   Enrollment Country/Territory and Language Selection Screen is displayed.'
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicEULAPage = browsers.at ClinicEULAPage
        then:
        'Choose the Country/Territory and Language.\n' +
                'Click on [Continue] button.'
        clinicLocalePage.selectLocale()
        then: 'Verify the system opens Enrollment Terms Acceptance Screen.  \tEnrollment Terms Acceptance Screen is displayed.'
        then: 'Check the both check boxes mentioned for “I am a resident of the United States” and “I have read, understood, and accept the Terms of Use and Privacy Statement”.'
        when: 'Click on [Accept] button.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.clinicEnterInfoText('Enter information about the clinic that will be using the CareLink Therapy Management Software for Diabetes.')
        clinicEnterInfoPage.validationInformationClinic()
        clinicEnterInfoPage.validationPhone()
        clinicEnterInfoPage.validationPostalCode()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickCancelLink()
        then: 'Verify that system navigates to Enrollment Clinic Information Screen.   Enrollment Clinic Information Screen is displayed.'
        then: 'Verify that Enrollment Clinic Information Screen only displays the Medtronic Logo and the CareLink Logo from the Common Screen Header Elements.   Medtronic Logo and the CareLink Logo are displayed in theCommon Screen Header Elements'
        then: 'Verify that Enrollment Clinic Information Screen does not display any of the Common Screen Footer Elements. No Common Screen Footer Elements are displayed.'
        then: 'Verify that screen displays an enrollment step indicator in place of a title. Enrollment step indicator is displayed.'
        then: 'Verify that Enrollment Clinic Information Screen displays the text.\tThe text: "Enter information about the clinic that will be using the CareLink Therapy Management Software for Diabetes." is displayed.'
        then:
        'Verify the input fields for the following elements:\n' +
                'Clinic Name\n' +
                'Address 1\n' +
                'Address 2\n' +
                'City\n' +
                'State or Province or Prefecture/Region\n' +
                'Zip / Postal Code\n' +
                'Country/Territory\n' +
                'Language\n' +
                'Phone'
        then: 'Verify [Clinic_Info_Name] field. [Clinic_Info_Name] is present'
        then: ' Insert 101 characters into [Clinic_Info_Name] field.'
        then: 'Verify the [Clinic_Info_Name] field is up to 100 characters long.  The field is allowed to contain 100 characters.'
        then: 'Verify [Clinic_Info_Address1] field. [Clinic_Info_Address1] is present.'
        then: 'Insert 121 characters into [Clinic_Info_Adress1] field.'

        then: ' Verify the [Clinic_Info_Adress1] field is up to 120 characters long.   The field is allowed to contain 120 characters."'
        then: ' Verify [Clinic_Info_Address2] filed. [Clinic_Info_Address2] field is present."'
        then: ' Insert 121 characters into [Clinic_Info_Address2] field. N/A"'
        then: ' Verify the [Clinic_Info_Address2] field is up to 120 characters long.   The field is allowed to contain 120 characters."'
        then: ' Verify [Clinic_Info_City] field. [Clinic_Info_City] field is present."'
        then: ' Insert 121 characters into [Clinic_Info_City] field. N/A"'
        then: ' Verify the [Clinic_Info_City] field is up to 120 characters long.   The field is allowed to contain 120 characters."'
        then: ' Verify [Clinic_Info_State] field. [Clinic_Info_State] field is present."'
        then: ' Verify [Clinic_Info_State] field for Japan..'
        then:
        'Verify the [Clinic_Info_State] field contains the list of States: ' +
                'ALABAMA \tAL\n' +
                'ALASKA \tAK\n' +
                'AMERICAN SAMOA \tAS\n' +
                'ARIZONA \tAZ\n' +
                'ARKANSAS \tAR\n' +
                'CALIFORNIA \tCA\n' +
                'COLORADO \tCO\n' +
                'CONNECTICUT \tCT\n' +
                'DELAWARE \tDE\n' +
                'DISTRICT OF COLUMBIA \tDC\n' +
                'FEDERATED STATES OF MICRONESIA \tFM\n' +
                'FLORIDA \tFL\n' +
                'GEORGIA \tGA\n' +
                'GUAM \tGU\n' +
                'HAWAII \tHI\n' +
                'IDAHO \tID\n' +
                'ILLINOIS \tIL\n' +
                'INDIANA \tIN\n' +
                'IOWA \tIA\n' +
                'KANSAS \tKS\n' +
                'KENTUCKY \tKY\n' +
                'LOUISIANA \tLA\n' +
                'MAINE \tME\n' +
                'MARSHALL ISLANDS \tMH\n' +
                'MARYLAND \tMD\n' +
                'MASSACHUSETTS \tMA\n' +
                'MICHIGAN \tMI\n' +
                'MINNESOTA \tMN\n' +
                'MISSISSIPPI \tMS\n' +
                'MISSOURI \tMO\n' +
                'MONTANA \tMT\n' +
                'NEBRASKA \tNE\n' +
                'NEVADA \tNV\n' +
                'NEW HAMPSHIRE \tNH\n' +
                'NEW JERSEY \tNJ\n' +
                'NEW MEXICO \tNM\n' +
                'NEW YORK \tNY\n' +
                'NORTH CAROLINA \tNC\n' +
                'NORTH DAKOTA \tND\n' +
                'NORTHERN MARIANA ISLANDS \tMP\n' +
                'OHIO \tOH\n' +
                'OKLAHOMA \tOK\n' +
                'OREGON \tOR\n' +
                'PALAU \tPW\n' +
                'PENNSYLVANIA \tPA\n' +
                'PUERTO RICO \tPR\n' +
                'RHODE ISLAND \tRI\n' +
                'SOUTH CAROLINA \tSC\n' +
                'SOUTH DAKOTA \tSD\n' +
                'TENNESSEE \tTN\n' +
                'TEXAS \tTX\n' +
                'UTAH \tUT\n' +
                'VERMONT \tVT\n' +
                'VIRGIN ISLANDS \tVI\n' +
                'VIRGINIA \tVA\n' +
                'WASHINGTON \tWA\n' +
                'WEST VIRGINIA \tWV\n' +
                'WISCONSIN \tWI\n' +
                'WYOMING \tWY\n' +
                'ARMED FORCES AREAS \tAA\n' +
                'ARMED FORCES AREAS \tAE\n' +
                'ARMED FORCES AREAS \tAP'
        then:
        ' Verify the [Clinic_Info_State] field contains the list of Provinces:' +
                'ProvinceAlberta\tAB\n' +
                'British Columbia \tBC\n' +
                'Manitoba \tMB\n' +
                'New Brunswick \tNB\n' +
                'Newfoundland and Labrador \tNL\n' +
                'Northwest Territories \tNT\n' +
                'Nova Scotia \tNS\n' +
                'Nunavut \tNU\n' +
                'Ontario \tON\n' +
                'Prince Edward Island \tPE\n' +
                'Quebec \tQC\n' +
                'Saskatchewan \tSK\n' +
                'Yukon \tYT'
        then: ' Verify [Clinic_Info_Postal_Code] field.	 [Clinic_Info_Postal_Code] is present."'
        then: ' Enter 21 characters into [Clinic_Info_Postal_Code]. N/A"'
        then: ' Verify the [Clinic_Info_Postal_Code] field is up to 20 characters long. [Clinic_Info_Postal_Code] field contains 20 characters maximum."'
        then: ' Verify the [Clinic_Info_Postal_Code] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification.The [Clinic_Info_Postal_Code] value is special for definite country."'
        then: ' Verify [Clinic_Info_Country] field	 [Clinic_Info_Country] is present."'
        then: ' Verify the [Clinic_Info_Country] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification. [Clinic_Info_Country] value is displayed as specified in Internationalization Specification."'
        then: ' Verify [Clinic_Info_Language] field.	[Clinic_Info_Language] is present."'
        then: ' Verify the [Clinic_Info_Language] value is determined by the user accounts Clinic_info_Country as specified in Internationalization Specification. [Clinic_Info_Language] value is determined by each user account country."'
        then: ' Verify [Clinic_Info_Phone] field. [Clinic_Info_Phone] field is present."'
        then: ' Enter 21 characters into [Clinic_Info_Phone]. N/A"'
        then: ' Verify the [Clinic_Info_Phone] field is up to 20 characters long. [Clinic_Info_Phone] field contains 20 characters maximum."'
        then: ' Verify that system indicates that Clinic Name, Postal Code and Phone fields are required fields.Clinic Name, Postal Code and Phone fields are marked as required fields."'
        when:
        'Update textfields with valid value and “<” symbol (e.g. “test<”):' +
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City\n' +
                '- Zip/Postal Code\n' +
                '- Phone\tN/A'
        and: 'Click [Continue] button.'
        then: 'Verify Clinic Name cannot be saved with “<” symbol on Enrollment Clinic Information screen\tClinic Name with “<” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Address 1 cannot be saved with “<” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 1 with “<” symbol is not saved and Error message is displayed near textfield'

        and:
        'Verify Address 2 cannot be saved with “<” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 2 with “<” symbol is not saved and Error message is displayed near textfield'
        and:
        'Verify City cannot be saved with “<” symbol on\n' +
                'Enrollment Clinic Information screen\tCity with “<” symbol is not saved and Error message is displayed near textfield'
        then: 'Verify Zip/Postal Code cannot be saved with “<” symbol on Enrollment Clinic Information screen\tZip/Postal Code with “<” symbol is not saved and Error message is displayed near textfield'
        and:
        'Verify Phone cannot be saved with “<” symbol on\n' +
                'Enrollment Clinic Information screen\tPhone with “<” symbol is not saved and Error message is displayed near textfield'
        then:
        'Update textfields with valid value and “>” symbol (e.g. “>test”):\n' +
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City\n' +
                '- Zip/Postal Code\n' +
                '- Phone'
        when: 'Click [Continue] button.'
        then:
        'Verify Clinic Name cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tClinic Name with “>” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Address 1 cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 1 with “>” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Address 2 cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 2 with “>” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify City cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tCity with “>” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Zip / Postal Code cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tZip / Postal Code with “>” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Phone cannot be saved with “>” symbol on\n' +
                'Enrollment Clinic Information screen\tPhone with “>” symbol is not saved and Error message is displayed near textfield'
        when:
        'Update textfields with valid value and “=” symbol (e.g. “te=st”):\n' +
                '\n' +
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City\n' +
                '- Zip/Postal Code\n' +
                '- Phone'
        and: 'Click [Continue] button.'
        then:
        'Verify Clinic Name cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tClinic Name with “=” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Address 1 cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 1 with “=” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Address 2 cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tAddress 2 with “=” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify City cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tCity with “=” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Zip / Postal Code cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tZip / Postal Code with “=” symbol is not saved and Error message is displayed near textfield'
        then:
        'Verify Phone cannot be saved with “=” symbol on\n' +
                'Enrollment Clinic Information screen\tPhone with “=” symbol is not saved and Error message is displayed near textfield'
        when:
        'Update textfields with leading space (e.g. “ test”):\n' +
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City\n' +
                '- Zip/Postal Code\n' +
                '- Phone'
        and: 'Proceed to the end of Clinic Registration.'
        and: 'Record the Clinic name, Username and Password.'
        and: 'Login in using the credentials of the Clinic created in this step.'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Navigate to Clinic Settings section Clinic Information tab. '
        then: 'Verify Clinic Name is saved without leading space.\tClinic Name field contains data without leading space.'
        then: 'Verify Address 1 is saved without leading space.	Address 1 field contains data without leading space.'
        then: 'Verify Address 2 is saved without leading space.\tAddress 2 field contains data without leading space.'
        then: 'Verify City is saved without leading space.\tCity field contains data without leading space.'
        then: 'Verify Zip/Postal Code is saved without leading space.\tZip/Postal Code field contains data without leading space.'
        then: 'Verify Phone is saved without leading space.\tPhone field contains data without leading space.'
        when: 'Click on Sign out link.'
        and: 'Click on Register Clinic link.'
        and: 'Proceed to Enrollment Clinic Information screen.'
        and:
        'Update textfields with trailing space (e.g. “test ”):\n' +
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City\n' +
                '- Zip/Postal Code\n' +
                '- Phone'
        and: 'Proceed to the end of Clinic Registration.'
        and: 'Record the Clinic name, Username and Password.'
        and: 'Login in using the credentials of the Clinic created in this step.'
        and: 'Navigate to Clinic Settings section Clinic Information tab.'
        then: 'Verify Clinic Name is saved without trailing space.\tClinic Name contains data without trailing spaces.'
        then: 'Verify Address 1 is saved without trailing spaces.\tAddress 1 field contains data without trailing spaces.'
        then: 'Verify Address 2 is saved without trailing spaces.\tAddress 2 field contains data without trailing spaces.'
        then: 'Verify City is saved without trailing spaces.\tCity field contains data without trailing spaces.'
        then: 'Verify Zip/Postal Code is saved without trailing spaces.\tZip/Postal Code field contains data without trailing spaces.'
        then: 'Verify Phone is saved without trailing spaces.\tPhone field contains data without trailing spaces.'
        when: 'Click on Sign out link.'
        and: 'Click on Register Clinic link.'
        and: 'Proceed to Enrollment Clinic Information screen.'
        and:
        'Update textfields with "&" (e.g. “test&”):\n' + //Note: All other fields left blank.
                '- Clinic Name\n' +
                '- Address 1\n' +
                '- Address 2\n' +
                '- City'
        and: 'Click [Continue] button'
        then: 'Verify Clinic Name field.\tClinic Name field contains "&" and the error message is absent.'
        then: 'Verify Address 1 field.\tAddress 1 field contains "&" and error message is absent.'
        then: 'Verify Address 2 field.\tAddress 2 field contains "&" and error message is absent.'
        then: 'Verify City field.\tCity field contains "&" and error message is absent.'
        when:
        'Update textfields with "&" (e.g. “123&”):\n' +
                '- Zip/Postal Code\n' +
                '- Phone\n' +
                '\n'
        and: 'Click [Continue] button.'
        then:'Verify Zip/Postal Code field.\tError message is displayed next to Zip/Postal Code field.'
        then: 'Verify Phone field.\tError message is displayed next to Phone field.'
        when: 'Delete the data from:\n' +
                '- Zip/Postal Code\n' +
                '- Phone\n' +
                'Add into both fields any valid data that contains 20 characters.'
        and: 'Click [Continue] button.'
        then: ' Verify [Continue] button. [Continue] button is labelled as "Continue"'
        then: ' Verify the [Continue] button is active. [Continue] button is enabled.'
        then: 'Verify the system navigates to Enrollment Administrator Information Screen after validate the user entry fields .\t Enrollment Administrator Information Screen is displayed.'
        when: ' Click Cancel link.\n' +
                'From Login page click on Register Clinic link.\n' +
                'Proceed to Enrollment Clinic Information screen'
        then: ' Verify there is Cancel link labeling. Cancel link labeled as “Cancel”'
        when: ' Click on Cancel link.'
        then: ' Verify it discards the enrollee recordLogin Screen is displayed.'
    }
}
