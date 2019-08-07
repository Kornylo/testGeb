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
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.pages.reports.PatientReportSettingsPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('ScUI 216 - Patient Report Settings contents')
@Screenshot
@RegressionTest
@Stepwise
class SCUI216Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static CreatePatientPage createPatientPage
    static DailyOverlayReportPage dailyOverlayReportPage
    static ClinicSettingsPage clinicSettingsPage
    static HomePage homePage
    static PatientReportSettingsPage patientReportSettingsPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    static final adminUs = "AdminUS" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final emailAddress = "email" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4) + '@medtronictest.mailinator.com'
    static final loginPassword = "MaluyScUI221"


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
        sleep(3000)
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
    def 'Patient Report Settings'() {
        when: 'Log in to a clinic'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        signInPage.clickNewPatient()
        when: 'Precondition steps'
        createPatientPage = browsers.at CreatePatientPage
        then:
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.enterPatientEmail('test@tesc.com')
        createPatientPage.physicianEnter('test')
        createPatientPage.selectTherapyTypeInsulinPump()
        createPatientPage.clickSaveBtn()
        createPatientPage.goToList()
        then: 'Select an existing patient and open .'
        signInPage.waitForLoaderDisappear()
        signInPage.selectNonSamplePatientFromList()
        signInPage.openPatient()
        then: 'Click on the Patient tab.'
        signInPage.openPatientInformation()
        when: ''
        createPatientPage = browsers.at CreatePatientPage
        then: ''
        String firstName = createPatientPage.getPatientFirstName()
        String lastNam = createPatientPage.getPatientLastName()
        String id = createPatientPage.getPatientID()
        createPatientPage.clickSaveBtn()
        waitFor 60, {!createPatientPage.saveBtn.displayed}
        waitFor {signInPage.patientInfo.displayed }
        when: ''
        patientReportSettingsPage = browsers.at PatientReportSettingsPage
        then: 'Click on the link Edit to change the Patient Report Settings.'
        patientReportSettingsPage.patientEditClick()
        then: 'Verify System shall provide Patient report settings Screen with content and function describe below.'
        then: 'Verify the screen displays the Patient name in the format LastName, FirstName, Patient ID OR Management ID. Note: Truncated to the available space. The Comma in the name may not appear in some locales'
        sleep(3000)
        patientReportSettingsPage.verifyPatientName(lastNam, firstName, id)
        when: 'Verify the screen is titled “Patient Report Settings”.'
        patientReportSettingsPage.verifyTitle("Patient Record Page")
        then: 'Verify there is a Save button labeled “Save”.'
        sleep(3000)
        patientReportSettingsPage.patientlabelSaveValue("Save")
        then: 'Verify there is Cancel link labeled “Cancel”.'
        patientReportSettingsPage.patientlabelCancelText("Cancel")
        then: 'Verify there is a restore default settings button labeled” Restore defaults” .'
        patientReportSettingsPage.labelRestoreDefaults("Restore defaults")
        then: 'Verify there is a section titled “General Report Settings”.'
        patientReportSettingsPage.patientReportSettingsTitle("Patient Report Settings")
        then: 'Verify there a section for the glucose target range title “Glucose Target Range” .'
        patientReportSettingsPage.patientGlucoseTargetRangeTitle("Glucose Target Range")
        then: 'Verify the high value of the target range is displayed and is labeled “High”.'
        patientReportSettingsPage.patientTargetRangeHigh("High")
        then: 'Verify the low value of the target range is displayed and is labeled “Low”.'
        patientReportSettingsPage.patientTargetRangeHighLow("Low")
        then: 'Verify there is a section for the glucose unit setting titled “Glucose Units”'
        patientReportSettingsPage.patientGlucoseLimits("Glucose Limits")
        then: 'Verify the section displays the current glucose unit setting.'
        patientReportSettingsPage.patienGlucoseUnits("Glucose Units")
        patientReportSettingsPage.sectionGlucoseUnitsMmolDisplayed()
        patientReportSettingsPage.sectionGlucoseUnitsMgDisplayed()
        patientReportSettingsPage.selectGlucoseUnitsMmol()
        then: 'Verify the user can modify the glucose unit setting.'
        patientReportSettingsPage.glucoseUnitsMmolValue("on")
        then: 'Verify there is a section for the time display setting labeled “Time Display” '
        patientReportSettingsPage.patientGlucoseLimits("Glucose Limits")
        then: 'Verify the section displays the current time display setting.'
        patientReportSettingsPage.sectionTimePeriodMgDisplayed("Time Display")
        patientReportSettingsPage.selectTime12click()
        patientReportSettingsPage.saveReportSettings()
        patientReportSettingsPage.successRegenerateReportsDialog('SUCCESS!')
        patientReportSettingsPage.patientEditClick()
        waitFor {patientReportSettingsPage.timeDisplay12hour.displayed}
        then: 'Verify the time display settings can be modified by the user.'
        patientReportSettingsPage.timeDisplayChanging()
        then: 'Verify there is a section for the overlay by meal settings titled “Analysis Reports Settings” .'
        patientReportSettingsPage.sectionAnalysisReportsSettingsDisplayed("Analysis Reports Settings")
        then: 'Verify this section has columns for Row Labels, Glucose Target Range, Time Period, and Post Meal Analysis.'
        patientReportSettingsPage.sectionGlucoseTargetRangeDisplayed("Glucose Target Range")
        patientReportSettingsPage.analysisTimePeriodDisplayed("Time Period")
        patientReportSettingsPage.postMealAnalysisDisplayed("Post Meal Analysis")
        then: 'Verify that If the [Clinic_Info_Country] is outside Japan the settings are organized based on the row labels “Breakfast”, “Lunch”, “Dinner”, “ Evening”, “ Sleeping” and "Fasting". Note: N/A if does not apply and the current locale is Japan'
        patientReportSettingsPage.sectionBreakfastDisplayed("Breakfast")
        patientReportSettingsPage.sectionLunchDisplayed("Lunch")
        patientReportSettingsPage.sectionDinnerDisplayed("Dinner")
        patientReportSettingsPage.sectionEveningDisplayed("Evening")
        patientReportSettingsPage.sectionSleepingDisplayed("Sleeping")
        patientReportSettingsPage.sectionFastingDisplayed("Fasting")
        then: 'Verify there are columns that display the glucose target range for each meal period labeled “Glucose Target Range”.'
        patientReportSettingsPage.sectionGlucoseTargetRangeDisplayed("Glucose Target Range")
        then: 'Verify the glucose target range columns are subdivided into two columns labeled “Low””and “High”.'
        patientReportSettingsPage.sectionGlucoseTargetRangeLowDisplayed("Low")
        patientReportSettingsPage.sectionGlucoseTargetRangeHighDisplayed("High")
        then: 'Verify that If the [Clinic_Info_Country] is outside Japan the glucose target range rows are subdivided into two rows labeled “Before” and “Äfter” for each meal period (“Breakfast”, “Lunch”, and “Dinner” rows .'
        patientReportSettingsPage.breakfastBeforeDisplayed("Before")
        patientReportSettingsPage.breakfastAfterDisplayed("After")
        patientReportSettingsPage.lunchBeforeDisplayed("Before")
        patientReportSettingsPage.lunchAfterDisplayed("After")
        patientReportSettingsPage.dinnerBeforeDisplayed("Before")
        patientReportSettingsPage.dinnerAfterDisplayed("After")
        then: 'Verify there is a column to display the Time Period for each meal period labeled “Time Period””.'
        patientReportSettingsPage.analysisTimePeriodDisplayed("Time Period")
        then: 'Verify the Time Period column is subdivided into two columns labeled “From” and “To”.'
        patientReportSettingsPage.analysisTimePeriodFromDisplayed("From")
        patientReportSettingsPage.analysisTimePeriodToDisplayed("To")
        then: 'Verify there is a column to display Post Meal Analysis settings labeled “Post Meal Analysis”.'
        patientReportSettingsPage.postMealAnalysisDisplayed("Post Meal Analysis")
        then: 'Verify the post meal analysis column is subdivided into two columns labeled “From”and “To”.'
        patientReportSettingsPage.postMealAnalysisFromDisplayed("From")
        patientReportSettingsPage.postMealAnalysisToDisplayed("To")
        then: 'Verify the Low Limit for Before breakfast can be modified.'
        when: ''
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: ''
        dailyOverlayReportPage.enterBreakfastBeforeLow("39")
        then: 'Verify the High Limit for Before Breakfast can be modified.'
        dailyOverlayReportPage.enterBreakfastBeforeHigh("301")
        then: 'Verify the Low Limit for After breakfast can be modified.'
        dailyOverlayReportPage.enterBreakfastAfterLow("12")
        then: 'Verify the High Limit for After Breakfast can be modified.'
        dailyOverlayReportPage.enterBreakfastAfterHigh("22")
        then: 'Verify the start of the Breakfast time period under the column From can be modified.'
        dailyOverlayReportPage.breakfastTimePeriodFrom("10:00 PM")
        then: 'Verify the end of the Breakfast time period under the column To can be modifed.'
        dailyOverlayReportPage.breakfastTimePeriodTo("12:30 AM")
        then: 'Verify the Breakfast Post Meal Analysis duration is labeled “hours“.'
        patientReportSettingsPage.postMealAnalysisBreakfastHoursDisplayed("hours")
        then: 'Verify the Breakfast Post Meal Analysis From setting can be modified.'
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom("1")
        then: 'Verify the Breakfast Post Meal Analysis To setting can be modified.'
        dailyOverlayReportPage.postBreakfastMealAnalysisTo("7")
        then: 'Verify the Low Limit for Before Lunch can be modified.'
        dailyOverlayReportPage.enterLunchBeforeLow("123")
        then: 'Verify the High Limit for Before Lunch can be modified.'
        dailyOverlayReportPage.enterLunchBeforeHigh("32")
        then: 'Verify the Low Limit for After Lunch can be modified.'
        dailyOverlayReportPage.enterLunchAfterLow("33")
        then: 'Verify the High Limit for After Lunch can be modified.'
        dailyOverlayReportPage.enterLunchAfterHigh("22")
        then: 'Verify the start of the Lunch time period under the column From can be modified.'
        dailyOverlayReportPage.lunchTimePeriodFrom("12:30 AM")
        then: 'Verify the end of the Lunch time period under the column To can be modified.'
        dailyOverlayReportPage.lunchTimePeriodTo("1:30 AM")
        then: 'Verify the Lunch Post Meal Analysis duration is labeled “hours“.'
        patientReportSettingsPage.postMealAnalysisLunchHoursDisplayed("hours")
        then: 'Verify the Lunch Post Meal Analysis From setting can be modified.'
        dailyOverlayReportPage.postLunchMealAnalysisFrom("3")
        then: 'Verify the Lunch Post Meal Analysis To setting can be modified.'
        dailyOverlayReportPage.postLunchMealAnalysisTo("3")
        then: 'Verify the Low Limit for Before Dinner can be modified.'
        dailyOverlayReportPage.enterDinnerBeforeLow("32")
        then: 'Verify the High Limit for Before Dinner can be modified.'
        dailyOverlayReportPage.enterDinnerBeforeHigh("123")
        then: 'Verify the Low Limit for After Dinner can be modified.'
        dailyOverlayReportPage.enterDinnerAfterLow("22")
        then: 'Verify the High Limit for After Dinner can be modified'
        dailyOverlayReportPage.enterDinnerAfterHigh("32")
        then: 'Verify the start of the Dinner time period under the column From can be modified.'
        dailyOverlayReportPage.dinnerTimePeriodFrom("1:00 AM")
        then: 'Verify the end of the Dinner time period under the column To can be modifed.'
        dailyOverlayReportPage.dinnerTimePeriodTo("2:30 AM")
        then: 'Verify the Dinner Post Meal Analysis duration is labeled “hours“.'
        patientReportSettingsPage.postMealAnalysisDinnerHoursDisplayed("hours")
        then: 'Verify the Dinner Post Meal Analysis From setting can be modified.'
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("1")
        then: 'Verify the Dinner Post Meal Analysis To setting can be modified.'
        dailyOverlayReportPage.postDinnerMealAnalysisTo("5")
        then: 'Verify the Low Limit for Before Lunch can be modified.'
        dailyOverlayReportPage.enterLunchBeforeHigh("32")
        then: 'Verify the Low Limit for Evening can be modified.'
        dailyOverlayReportPage.enterEveningBeforeLow("21")
        then: 'Verify the High Limit for Evening can be modified.'
        dailyOverlayReportPage.enterEveningBeforeHigh("42")
        then: 'Verify the start of the Evening time period under the column From can be modified.'
        dailyOverlayReportPage.eveningTimePeriodFrom("3:00 AM")
        then: 'Verify the end of the Evening time period under the column To can be modified.'
        dailyOverlayReportPage.eveningTimePeriodTo("12:00 PM")
        then: 'Verify the Low Limit for Sleeping can be modified.'
        dailyOverlayReportPage.enterSleepingBeforeLow("323")
        then: 'Verify the High Limit for Sleeping can be modified.'
        dailyOverlayReportPage.enterSleepingBeforeHigh("44")
        then: 'Verify the start of the Sleeping time period under the column From can not be modified.'
        dailyOverlayReportPage.sleepingTimeDisabled()
        then: 'Verify the end of the Sleeping time period under the column To can be modified.'
        dailyOverlayReportPage.sleepingPeriodTo("6:00 AM")
        then: 'Verify the start of the Fasting time period under the column From can be modified.'
        dailyOverlayReportPage.fastingPeriodFrom("9:00 AM")
        then: 'Verify the end of the Fasting time period under the column To can be modified.'
        dailyOverlayReportPage.fastingPeriodTo("9:00 AM")
        patientReportSettingsPage.restoreDefaultsclick()

    }

}
