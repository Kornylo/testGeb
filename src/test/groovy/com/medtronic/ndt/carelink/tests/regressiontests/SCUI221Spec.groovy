package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.reports.PatientReportSettingsPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('ScUI221 - CLiP: Clinic Report Settings Screen Content')
@Slf4j
@RegressionTest
@Screenshot
@Stepwise

class SCUI221Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage
    static HomePage homePage
    static PatientReportSettingsPage patientReportSettingsPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final loginPassword = "MaluyScUI221"
    static final loginPatientPassword = "enterPassScUI221"
    static final loginPatientNewPassword = "newPassScUI221+"

    def 'Open Carelink application'() {
        given: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()
        println("URL address: " + browser.getCurrentUrl())
    }

    def 'Pre-condition Register US clinic'() {
        when: 'Click on Register Clinic link. Create US clinic and record the info below: Clinic Name:'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        clinicEULAPage = browsers.at ClinicEULAPage
        newClinicRegistrationPage.clickRegisterClinic()
        clinicEULAPage.enrollmentInfoScreen()
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.enrollmentCountryAndLanguageSelectScreen()
        clinicLocalePage.selectCountryAndLanguage("United States", "English")
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.opensEnrollmentTermsAcceptanceScreen()
        clinicEULAPage.selectResidentCheckbox()
        clinicEULAPage.residentCheckboxOn()
        clinicEULAPage.selectAcceptCheckbox()
        clinicEULAPage.acceptCheckboxOn()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.enrollmentClinicInformationScreen()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Create a new administrative user and record the info below: Username: (example: TCXXX) Password: ___________________________ (example: Password1)'
        clinicEnterAdminInfoPage.enterUserNameManual(adminUs)
        clinicEnterAdminInfoPage.enterFirstName("kornylo")
        clinicEnterAdminInfoPage.enterLastName("dmytro")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword("MaluyScUI221")
        clinicEnterAdminInfoPage.enterConfirmPassword("MaluyScUI221")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        println("Username: " + adminUs + " Password: " + loginPassword)
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
    }

    def 'Clinic Report Settings Screen '() {
        when: 'Open software under test.'
        signInPage = browsers.at SignInPage
        then: 'Login to an already existing clinic.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Navigate to Clinic Report Settings Screen.'
        when: ''
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        then: 'Verify the software shall provide a Clinic Report Settings Screen.'
        clinicSettingsPage.assertReportSettingsText('Report Settings')
        then: 'Verify the software system shall provide access to the Common Screen Header Elements on every screen for users logged into the system unless specified otherwise.'
        clinicSettingsPage.сlinicSettingsHeaderDisplayed()
        then: 'Verify the Medtronic Rising man brand logo shall be displayed in the upper left corner of all screens.'
        clinicSettingsPage.сlinicSettingsLogoDisplayed()
        then: 'Verify that clinic name, [Clinic_Info_Name] and [Clinic Info ID] is displayed in the upper center of the screen.'
        clinicSettingsPage.сlinicSettingsUserDisplayed()
        then: 'Verify the CareLink iPro Therapy Management Software for Diabetes brand logo shall be displayed in the upper right of the screen.   (Note: NA this step if it does not apply) '
        then: 'Verify the CareLink iPro Clinical Therapy Management Software for Diabetes brand logo shall be displayed in the upper right of the screen.   (Note: NA this step if it does not apply) '
        clinicSettingsPage.сlinicSettingsIprologoDisplayed()
        then: 'Verify there is a Home link labeled as “Home”.'
        clinicSettingsPage.goToHomePageDisplayed("Home")
        then: 'Verify there is a Clinic Settings link labeled as “Clinic Settings”.'
        clinicSettingsPage.clinicSettingsTextDisplayed('Clinic Settings')
        then: 'Click on the Home link.'
        clinicSettingsPage.goToHomePageClick()
        when: 'Verify that the user is able to navigate to the Home Screen.'
        homePage = browsers.at HomePage
        then: ''
        homePage.clinicNameDisplayed()
    }

    def 'Clinic Report Settings Screen Verify'() {
        when: 'Click on Clinic Settings link.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        then: 'Verify that the user is able to navigate to the Clinic Settings Screen: Report Settings Screen.'
        clinicSettingsPage.сlinicSettingsHeaderDisplayed()
        clinicSettingsPage.сlinicSettingsLogoDisplayed()
        then: 'Verify that Home Screen is available.'
        clinicSettingsPage.homePageAvailable()
        then: 'Verify that Clinic Settings Screen: Report Settings Screen is available.'
        clinicSettingsPage.reportSettingsAvailable()
        then: 'Verify that Clinic Settings Screen: Staff User Accounts Screen is available.'
        clinicSettingsPage.userAvailable()
        then: 'Verify that Clinic Settings Screen: Clinic Information Screen is available.'
        clinicSettingsPage.clinicInformationAvailable()
        when: 'Verify there is a resources link, labeled “Resources” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.resourcesDisplayedTextClinicSettings('Resources')
        then: 'Click on the resources link.'
        then: 'Verify the software opens the url (refer to I18N for the specific URL) in a new browser window or tab .'
        clinicSettingsPage.verifyResourcesLinkClinicSettings()
        then: 'Verify there is a user guide link, labeled “User Guide” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.userGuideDisplayedTextClinicSettings('User Guide')
        when: 'Click on the user guide link.'
        then: 'Verify the software opens the url (refer to I18N for the specific URL)   in a new browser window or tab .'
        clinicSettingsPage.verifyUserGuideLinkClinicSettings()
        then: 'Verify there is a Order System Supplies link, labeled “Order supplies” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.orderSuppliesDisplayedTextClinicSettings('Order supplies')
        when: 'Click on the Order Supplies link.'
        clinicSettingsPage.verifyOrderSuppliesLinkClinicSettings()
        then: 'Verify the software opens the url (refer to I18N for the specific URL)   in a new browser window or tab .'
        then: 'Verify there is a Privacy Statement link, labeled “Privacy Statement” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.privacyStatementDisplayedTextClinicSettings('Privacy Statement')
        when: 'Click on the Privacy Statement link.'
        then: 'Verify the software invokes the Privacy Statement screen.'
        clinicSettingsPage.verifyPrivacyStatementLinkClinicSettings()
        when: 'Return to the Clinic Report Settings Screen.'
        then: 'Verify there is a Terms of Use link, labeled “Terms of Use” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.termsOfUseDisplayedTextClinicSettings('Terms of Use')
        when: 'Click on the Terms of Use link.'
        then: 'Verify the software invokes the Terms of Use screen.'
        when: 'Return to the Clinic Report Settings Screen.'
        clinicSettingsPage.verifyTermsOfUseLinkClinicSettings()
        then: 'Verify there is a Contact Us link, labeled “Contact Us” on the bottom of the Patient Record Screen.'
        clinicSettingsPage.contactUsDisplayedTextClinicSettings('Contact Us')
        when: 'Click on the Contact Us link.'
        then: 'Verify the software invokes the Contact US screen.'
        clinicSettingsPage.verifyContactUsLinkClinicSettings()
        when: 'Return to the Clinic Report Settings Screen.'
        then: ' Verify that this section contains a Clinic Report Settings Link, labeled as "Report Settings", shall be highlighted and with the link inactive.'
        clinicSettingsPage.assertReportSettingsText('Report Settings')
        then: 'Verify that this section contains a Clinic Staff User Accounts Link, labeled as “Users ”.'
        clinicSettingsPage.assertUsersText('Users')
        then: 'Verify that this section contains a Clinic Information Link, labeled as “Clinic Information”.'
        clinicSettingsPage.assertInfoText('Clinic Information')
        then: 'Click on the “Staff User Accounts” link.'
        clinicSettingsPage.assertUsersClick()
        then: 'Verify that the screen navigates to “Clinic Staff User Accounts Screen”'
        clinicSettingsPage.userAvailable()
        then: 'Navigate to Clinic Report Settings Screen.'
        clinicSettingsPage.clickOnClinicSettingsFromUsers()
        and: 'Click on the Clinic Information Link.'
        clinicSettingsPage.openClinicInformation()
        then: 'Verify that the screen navigates to “CLINIC INFORMATION SCREEN” .'
        clinicSettingsPage.QRCodeDisplayed()
        then: 'Navigate to Clinic Report Settings Screen.'
        clinicSettingsPage.clickOnClinicSettingsFromClinicInformation()
        then: 'Verify that there is a “Close clinic settings” link on the Clinic Report settings screen.'
        clinicSettingsPage.closeClinicSettingsDisplayed()
        clinicSettingsPage.closeClinicSettingsTextLabeClick('Close clinic settings')
        then: 'Click on the link.'
        clinicSettingsPage.closeClinicSettings()
        when: 'Verify that the screen navigates to HOME SCREEN.'
        homePage = browsers.at HomePage
        then: ''
        homePage.clinicNameDisplayed()
        when: 'Navigate to Clinic Report Settings Screen.'
        clinicSettingsPage.clickOnClinicSettings()
        then: 'Verify that there is a Save Button labeled as “Save”.'
        clinicSettingsPage.saveButtonText('Save')
        then: 'Verify that there is a note text in the Clinic Report Settings Screen stating: NOTE: Changes to these settings will only apply to new patients added to the system . (However, changes to Glucose Units and Time Display will apply to all patients in the system).'
        clinicSettingsPage.noteTextDisplayed('Changes to these settings will only apply to new patients added to the system. (However, changes to Glucose Units and Time Display will apply to all patients in the system).')
        then: 'Verify the Clinic Report Settings Screen shall display a section titled “General Report Settings”'
        clinicSettingsPage.glucoseTargetRangeTitle("General Report Settings")
        then: 'Verify the Glucose Target Range settings section shall display the title “Glucose Target Range”'
        clinicSettingsPage.reportClinicSettings("Glucose Target Range")
        then: 'Verify that there is a High Glucose Target Range, labeled as “High”.'
        clinicSettingsPage.glucoseTargetRangeHighTitle("High")
        then: 'Verify the High Glucose Target Range displays the current set value.'
        then: 'Verify that there is a Low Glucose Target Range, labeled as “Low”.'
        clinicSettingsPage.glucoseTargetRangeHighTitle("Low")
        then: 'Verify the Low Glucose Target Range displays the current set value.'
        then: 'Verify the Glucose Units settings section shall display the title “Glucose Units”'
        clinicSettingsPage.glucoseUnitsTitle('Glucose Units')
        when: 'Verify the Glucose Units setting shall display the current set value.'
        patientReportSettingsPage = browsers.at PatientReportSettingsPage
        patientReportSettingsPage.sectionGlucoseUnitsMgDisplayed()
        then: 'Verify the Time Display settings section shall display the title “Time Display”.'
        clinicSettingsPage.timeDisplayTitle('Time Display')
        then: 'Verify the Time Display setting shall display the current set value.'
        patientReportSettingsPage.timeDisplay12()
        then: 'Verify the Clinic Report Settings Screen displays a section titled “Analysis Reports Settings”.'
        clinicSettingsPage.sectionAnalysisReportsSettingsText("Analysis Reports Settings")
        then: 'Verify this section has columns for Row Labels, Glucose Target Range, Time Period, and Post Meal Analysis.'
        clinicSettingsPage.analysisReportsGlucoseTargetRange('Glucose Target Range')
        clinicSettingsPage.analysisReportsTimePeriod('Time Period')
        clinicSettingsPage.analysisReportsPostMealAnalysis('Post Meal Analysis')
        when: 'Verify that If the [Clinic_Info_Country] is outside Japan the Settings shall be organized into rows with the following labels: “Breakfast”, “Lunch”, “Dinner”, “Evening ” and “ Sleeping”.'
        patientReportSettingsPage.sectionBreakfastDisplayed("Breakfast")
        patientReportSettingsPage.sectionLunchDisplayed("Lunch")
        patientReportSettingsPage.sectionDinnerDisplayed("Dinner")
        patientReportSettingsPage.sectionEveningDisplayed("Evening")
        patientReportSettingsPage.sectionSleepingDisplayed("Sleeping")
        patientReportSettingsPage.sectionFastingDisplayed("Fasting")
        then: 'Verify that If the [Clinic_Info_Country] is Japan the Settings are organized based on the row labels "Before Breakfast", "After Breakfast", "Before Lunch", "After Lunch", "Before Dinner", "After Dinner", "Evening", and "Sleeping”.'
        patientReportSettingsPage.breakfastBeforeDisplayed("Before")
        patientReportSettingsPage.breakfastAfterDisplayed("After")
        patientReportSettingsPage.lunchBeforeDisplayed("Before")
        patientReportSettingsPage.lunchAfterDisplayed("After")
        patientReportSettingsPage.dinnerBeforeDisplayed("Before")
        patientReportSettingsPage.dinnerAfterDisplayed("After")
        patientReportSettingsPage.sectionSleepingDisplayed("Sleeping")
        patientReportSettingsPage.sectionFastingDisplayed("Fasting")
        then: 'Verify a column is display ed the Glucose Target Range settings, labeled as “Glucose Target Range”'
        patientReportSettingsPage.sectionGlucoseTargetRangeDisplayed("Glucose Target Range")
        then: 'Verify the Glucose Target Range for the column is subdivided into “Low” and “High” columns.'
        patientReportSettingsPage.sectionGlucoseTargetRangeLowDisplayed("Low")
        patientReportSettingsPage.sectionGlucoseTargetRangeHighDisplayed("High")
        then: 'Verify that If the [Clinic_Info_Country] is outside Japan the Glucose Target Range for “Breakfast”, “Lunch”, and “Dinner” row s are subdivided into “Before” and “After” rows.'
        then: 'Verify the section display s the current set values aligned with appropriate column and row labels.'
        patientReportSettingsPage.fullGlucoseTargetRangeVerify()
        then: 'Verify a column display s the time period settings, labeled as “Time Period”.'
        patientReportSettingsPage.sectionTimePeriodMgDisplayed('Time Display')
        then: 'Verify the Glucose Target Range column are subdivided into “From” and “To” columns.'
        patientReportSettingsPage.sectionAnalysisReportsTimeFrom("From")
        patientReportSettingsPage.sectionAnalysisReportsTimeTo("To")
        then: 'Verify the section display s the current set values aligned with appropriate column and row labels.'
        then: 'Verify a column display s the Post meal analysis settings, labeled as “Post Meal Analysis”.'
        clinicSettingsPage.analysisReportsPostMealAnalysis('Post Meal Analysis')
        then: 'Verify the Glucose Target Range column are subdivided into “From” and “To” columns.'
        patientReportSettingsPage.sectionPostMealAnalysisFrom("From")
        patientReportSettingsPage.sectionPostMealAnalysisTo("To")
        then: 'Verify the section display s the current set values with a units label of “hours”, aligned with appropriate column and row labels.'
        patientReportSettingsPage.postMealAnalysisBreakfastHoursDisplayed("hours")
        then: 'LOGIN AS NON ADMINISTRATOR (Note: NA if does not apply)'
        clinicSettingsPage.assertUsersClick()
        clinicSettingsPage.createNewUser("kornylo@gmail.com")
        String notAdminUser = clinicSettingsPage.getUserName()
        clinicSettingsPage.enterCurrentPass(loginPassword)
        clinicSettingsPage.enterPass(loginPatientPassword)
        clinicSettingsPage.waitForUserButton()
        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        and: 'Log in to a non administrator account'
        then: ''
        signInPage.enterUsername(notAdminUser)
        signInPage.enterPassword(loginPatientPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Navigate to the Clinic Report Settings Screen.'
        when: ''
        signInPage.newPasswordScreen('Password Expired', null)
        signInPage.passwordExpiredPage(loginPatientNewPassword)
        then:''
    }

    def 'Verify the user login as not admin'() {
        when: ''
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        then: 'Verify that the “Users” link is missing.'
        clinicSettingsPage.usersTexNotdisplayed()
        and: 'End of test.'
    }

}
