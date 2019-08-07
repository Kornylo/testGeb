package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('SCUI198 Patient clinic settings screen - error message priority')
@Screenshot
@RegressionTest
@Stepwise
class SCUI198Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static LoginPage loginPage
    static HelpPage helpPage
    static DailyOverlayReportPage dailyOverlayReportPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'User Login'() {
        given: 'Carelink is navigated to using the browser using an unsupported browser version'
        signInPage = browsers.to SignInPage
        and: 'The browser warning shall be displayed'
        println 'Verifying the signin button is displayed'
        assert signInPage.signInButtonDisplayed()
    }

    def 'Register clinic'() {
        when: 'At the new clinic registration page'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        then: 'Click on register clinic and click continue'
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
        when: 'At the locale page'
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Select the locale under which clinic will be created'
        clinicLocalePage.selectLocale()
        when: 'At the EULA page'
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Accept the EULA'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        when: 'At the Clinic Info page'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Enter all the clinic info'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        when: 'At the Clinic admin info page'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Enter all the user details'
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
        when: 'A user supplies valid carelink credentials'
        loginPage = browsers.at LoginPage
        then: 'User should be able to successfully log into Carelink envision pro'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        log.info('User is successful login in the system')
    }


    def ' Navigate to Report Settings'() {
        when: 'Patient Report Settings opend'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        dailyOverlayReportPage.clinicSettingsTab()
        then: 'Verify that the patient report settings default values '
        dailyOverlayReportPage.bgunit_mgdl()
        log.info('mg/dl and 12 hour time display')
/*        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.convertingSavedBtn()*/\
        then: 'Set the “Low” value to be: 39 mg/dL or 2.2 mmol/L'
        dailyOverlayReportPage.enterBreakfastBeforeLow("39")
        then:'Set the “High” value to be: 301 mg/dL or 16.6 mmol/L'
        dailyOverlayReportPage.enterBreakfastBeforeHigh("301")
        then:'Set each “Post Meal Analysis” “To” field as: 2 hours'
        dailyOverlayReportPage.postBreakfastMealAnalysisTo("4")
        then:'Set each “Post Meal Analysis” “From” field as: 3 hours'
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom("6")
        then:'In the “Breakfast From” time entry field, enter: 5:00 AM'
        dailyOverlayReportPage.breakfastTimePeriodFrom("5:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(300)
        then:'VERIF6698 Verify the software outputs the following error messages'
        dailyOverlayReportPage.breakfastSectionError("Time Period cannot overlap with Sleeping Time Period.")
/*        dailyOverlayReportPage.breakfastSectionError("Low must be from 50 to 140.", "High must be from 80 to Highest Limit - 20.", "Post Meal Analysis must be at least 0.5 hours long.")*/
        log.info('Verified breakfast section error')
        then:'In the “Breakfast From” time entry field, enter: 6:30 AM'
        dailyOverlayReportPage.breakfastTimePeriodFrom("6:30 AM")
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom("6")
        dailyOverlayReportPage.postBreakfastMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify the software displays an error message for the Glucose Range error and Post Meal Analysis error. '
        dailyOverlayReportPage.breakfastSectionError("Low must be from 50 to 140.", "High must be from 80 to Highest Limit - 20.", "Post Meal Analysis must be at least 0.5 hours long.")
        log.info('Verified breakfast section error')
        then:'In the “Breakfast To” time entry field, enter: 6:00 AM'
        dailyOverlayReportPage.breakfastTimePeriodTo("6:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify the software only outputs 1 error message'
        sleep(300)
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        then:'In the “Breakfast To” time entry field, enter: 11:30 AM'
        dailyOverlayReportPage.breakfastTimePeriodTo("11:30 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify the software only outputs 1 error message'
        sleep(400)
        dailyOverlayReportPage.breakfastSectionError("Time Period cannot overlap with Lunch Time Period.")
        then:'In the “Breakfast To” time entry field, enter: 10:00 AM'
        dailyOverlayReportPage.breakfastTimePeriodTo("10:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'In the “Lunch From” time entry field, enter: 9:00 AM'
        dailyOverlayReportPage.lunchTimePeriodFrom("9:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(40)
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Breakfast Time Period.")
        then:'In the “Lunch From” time entry field, enter: 4:00 PM'
        dailyOverlayReportPage.lunchTimePeriodFrom("4:00 PM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        then:'In the “Lunch From” time entry field, enter: 11:00 AM'
        dailyOverlayReportPage.lunchTimePeriodFrom("11:00 AM")
        then:'In the “Lunch To” time entry field, enter: 10:30 AM'
        dailyOverlayReportPage.lunchTimePeriodTo("10:30 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        then:'In the “Lunch To” time entry field, enter: 06:30 PM'
        dailyOverlayReportPage.lunchTimePeriodTo("10:30 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        then: 'Verified dinner section'
        dailyOverlayReportPage.enterDinnerBeforeLow("40")
        log.info('Change the Glucose target range  value to 40 mg/dL')
        dailyOverlayReportPage.enterDinnerBeforeHigh("300")
        log.info('Change the Glucose target range  value to 300 mg/dL.')
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        then:'Enter in a time that is earlier than the Lunch To time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodTo("4:00 PM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.'
        dailyOverlayReportPage.dinnerSectionError("Time Period 'From' must be before 'To'.")
        log.info('Verified dinner section error')
        then:'Enter in an appropriate selection for Dinner From time.'
        dailyOverlayReportPage.dinnerTimePeriodTo("8:00 PM")
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(40)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors. '
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        then:'Enter in a time that is earlier than or equal to the Dinner From time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodFrom("6:00 PM")
        dailyOverlayReportPage.dinnerTimePeriodTo("6:00 PM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors. '
        dailyOverlayReportPage.dinnerSectionError("Time Period must be at least 0.5 hours long.")
        then:'Enter in a time that is earlier than the Dinner To time and press the Save button.'
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        dailyOverlayReportPage.dinnerTimePeriodFrom("4:00 PM")
        dailyOverlayReportPage.dinnerTimePeriodTo("11:00 PM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors. '
        sleep(400)
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        log.info('Verified lunch section error')
        then: 'Verified evening section'
        dailyOverlayReportPage.enterEveningBeforeLow("40")
        log.info('Change the Glucose target range  value to 40 mg/dL')
        dailyOverlayReportPage.enterEveningBeforeHigh("300")
        log.info('Change the Glucose target range  value to 300 mg/dL.')
        then:'Enter in a time that is earlier than or equal to the Evening From time and press the Save button.'
        dailyOverlayReportPage.eveningTimePeriodTo("4:00 PM")
        sleep(200)
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        sleep(4000)
        dailyOverlayReportPage.overnightSectionError("Time Period 'From' must be before 'To'.", "Time Period 'From' must be before 'To'.")
        log.info('Verified overnight section error')
        sleep(2000)
        then:''
        dailyOverlayReportPage.eveningTimePeriodTo("12:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then: 'Verified sleepsing section'
        dailyOverlayReportPage.enterSleepingBeforeLow("40")
        log.info('Change the Glucose target range  value to 40 mg/dL')
        dailyOverlayReportPage.enterSleepingBeforeHigh("300")
        log.info('Change the Glucose target range  value to 300 mg/dL.')
        then:'Enter in a time that is earlier than or equal to the Sleeping From time and press the Save button.'
        dailyOverlayReportPage.sleepingPeriodTo("7:00 AM")
        then:'Click on the “Save” button.'
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(500)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        sleep(30)
        dailyOverlayReportPage.overnightSectionError("Time Period cannot overlap with Breakfast Time Period.")
        log.info('Verified overnight section error')

    }


}

