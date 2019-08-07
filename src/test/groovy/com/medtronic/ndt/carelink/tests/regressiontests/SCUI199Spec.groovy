package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('SCUI199 Patient report settings screen - error message priority')
@Screenshot
@RegressionTest
@Stepwise
class SCUI199Spec extends CareLinkSpec implements ScreenshotTrait{
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
    static CreatePatientPage createPatientPage

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

    def 'Create new patient'() {
        when: 'A user is logged into Carelink application'
        signInPage = browsers.at SignInPage
        then: 'User should be able to create a new patient'
        signInPage.clickNewPatient()
    }

    def 'Enter new patient info'() {
        when: 'At the patient page'
        createPatientPage = browsers.at CreatePatientPage
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: 'Enter new patient information and create.'
        createPatientPage.createPatient()
        dailyOverlayReportPage.selectPatientDiabetesType()
        dailyOverlayReportPage.selectPatient_details()
        createPatientPage.clickSaveBtn()
        sleep(500)

    }
    def ' Navigate to Report Settings'() {
        when: 'Patient Report Settings opened'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: 'Verify that the patient report settings default values '
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then:'At the Patient Record Screen press the Edit link.'
        dailyOverlayReportPage.timedisplay_hr12()
        log.info('Change the time display to 12 hour.')
        then:'Change the Glucose target range value to 40 mg/dL'
        dailyOverlayReportPage.enterGlucoseTargetLow("40")
        then:'Change the Glucose target range value to 300 mg/dL'
        dailyOverlayReportPage.enterGlucoseTargetHigh("300")
        then:'Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.'
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom("6")
        dailyOverlayReportPage.postBreakfastMealAnalysisTo("4")
        then:'Enter in a time that is earlier than the Sleeping To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo("2:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.breakfastSectionError("Time Period 'From' must be before 'To'.")
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        then:'Enter in a time that is equal to or later than the Sleeping To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo("11:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the only error messages shown are for the Glucose Range error and Post Meal Analysis error .  '
        dailyOverlayReportPage.breakfastSectionError("Post Meal Analysis must be at least 0.5 hours long.")
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified breakfast section error')
        then:'Enter in a time that later than the Breakfast From time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodFrom("6:00 AM")
        then:'Enter in a time that is earlier than or equal to the Breakfast From time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodTo("6:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.'
        dailyOverlayReportPage.breakfastSectionError("Time Period must be at least 0.5 hours long.")
        then:'Enter in a time that later than the Breakfast To time and press the Save button.'
        dailyOverlayReportPage.breakfastTimePeriodFrom("6:00 AM")
        dailyOverlayReportPage.breakfastTimePeriodTo("11:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:' Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.   '
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        sleep(1000)
        then:'Enter in a time that is equal to or later than the Lunch To time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodTo("6:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.lunchSectionError("Time Period 'From' must be before 'To'.")
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified lunch section error')
        then:'Enter in a time that is earlier than or equal to the Lunch From time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodTo("4:00 PM")
        dailyOverlayReportPage.postLunchMealAnalysisFrom("6")
        dailyOverlayReportPage.postLunchMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.lunchSectionError("Post Meal Analysis must be at least 0.5 hours long.")
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        then:'Enter in a time that is later than the Dinner From time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodFrom("6:00 AM")
        dailyOverlayReportPage.lunchTimePeriodTo("6:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.lunchSectionError("Time Period must be at least 0.5 hours long.","Time Period cannot overlap with Breakfast Time Period.")
        log.info('Verified lunch section error')
        dailyOverlayReportPage.postLunchMealAnalysisFrom("2")
        dailyOverlayReportPage.postLunchMealAnalysisTo("6")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        then:'Enter in a time that is earlier than the Lunch To time and press the Save button.'
        dailyOverlayReportPage.lunchTimePeriodFrom("6:00 AM")
        dailyOverlayReportPage.lunchTimePeriodTo("11:00 AM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(600)
        then:' Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Breakfast Time Period.")
        then:'Verified dinner section'
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        then:'Enter in a time that is earlier than or equal to the Dinner From time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodTo("4:00 PM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.dinnerSectionError("Time Period 'From' must be before 'To'.")
/*        dailyOverlayReportPage.dinnerSectionError("Time Period must be at least 0.5 hours long.")*/
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified dinner section error')
        then:'Enter in a time that is later than the Evening From time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodTo("8:00 PM")
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        log.info('Set each Post Meal Analysis From field as 3 hours and each Post Meal Analysis To field as 2 hours.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(400)
        then:' Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified dinner section error')
        then:'Enter in a time that is earlier than the Dinner To time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodFrom("6:00 PM")
        dailyOverlayReportPage.dinnerTimePeriodTo("6:00 PM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.dinnerSectionError("Time Period must be at least 0.5 hours long.")
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("6")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("4")
        then:'Enter in a time that is earlier than or equal to the Evening From time and press the Save button.'
        dailyOverlayReportPage.dinnerTimePeriodFrom("4:00 PM")
        dailyOverlayReportPage.dinnerTimePeriodTo("11:00 PM")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.lunchSectionError("Time Period cannot overlap with Breakfast Time Period.")
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified dinner section error')
        then:'Enter in a time that is earlier than or equal to the Evening From time and press the Save button.'
        dailyOverlayReportPage.eveningTimePeriodTo("4:00 PM")
        sleep(2000)
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(2000)
        then:'Verify that the Time Period validation error message has a higher priority and requires a fix before displaying the Glucose Range errors.  '
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.","Highest Limit must be from High + 20 to 400.","Low must be from 50 to 140.")
        log.info('Verified overnight section error')
    }
}
