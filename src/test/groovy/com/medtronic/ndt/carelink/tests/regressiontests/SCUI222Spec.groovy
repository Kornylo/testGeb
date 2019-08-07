package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('ScUI222 - Clinic Report Settings Screen Functionality')
@Screenshot
@RegressionTest
@Stepwise

class SCUI222Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static ClinicSettingsPage clinicSettingsPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static DailyOverlayReportPage dailyOverlayReportPage

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
        sleep(2000)
        signInPage.postponeMFA()
        println("Username: " + adminUs + " Password: " + loginPassword)
        when: 'Navigate to Clinic Report Settings Screen.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then:''
        sleep(2000)
        clinicSettingsPage.clinicSettingsClick()
        when: 'Set the General Report Settings as follows: Glucose Target Range, High 180, Low 100'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        dailyOverlayReportPage.enterGlucoseTargetHigh("180")
        dailyOverlayReportPage.enterGlucoseTargetLow("100")
        then: 'Verify that the “Save” button is active.'
        dailyOverlayReportPage.savePrefsIsActive()
        when: 'Set the Glucose units to mg/dL. Note: This depends on locale selected, if default is mmol/L, change units to mg/dL.'
        dailyOverlayReportPage.mgdlSelect()
        and: 'Set the time display to 12 hours. Note: This depends on locale selected, if default is 24hrs, change time to 12hr.'
        dailyOverlayReportPage.selectTimehr12displayed()
        and: 'Set the Analysis Report Settings as follows:' +
                ' Glucose Target Range Time Period, ' +
                ' Post Meal Analysis;' +
                ' Low High, From To From To;' +
                ' Breakfast: Before Meal, 80 160, 7AM 9AM, 2 - 4,' + ' After Meal, 110 170; ' +
                ' Lunch: Before Meal, 80 140, 10AM 2PM, 2- 4, ' + ' After Meal, 110 170;' +
                ' Dinner: Before Meal, 80 140, 4PM 7PM, 2-4,' + ' After Meal, 110 160;' +
                ' Evening: 110 160, 10PM 2AM; ' +
                ' Sleeping: 110 160, 2AM 5AM;' +
                ' Fasting 6AM 8AM'
        dailyOverlayReportPage.enterBreakfastAfterLow("80")
        dailyOverlayReportPage.enterBreakfastAfterHigh("160")
        dailyOverlayReportPage.breakfastTimePeriodFrom("7:00 AM")
        dailyOverlayReportPage.breakfastTimePeriodTo("9:00 AM")
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom("4")
        dailyOverlayReportPage.postBreakfastMealAnalysisTo("8")
        dailyOverlayReportPage.enterBreakfastBeforeLow("110")
        dailyOverlayReportPage.enterBreakfastBeforeHigh("170")
        dailyOverlayReportPage.enterLunchAfterLow("80")
        dailyOverlayReportPage.lunchTimePeriodFrom("7:00 AM")
        dailyOverlayReportPage.lunchTimePeriodTo("9:00 AM")
        dailyOverlayReportPage.postLunchMealAnalysisFrom("4")
        dailyOverlayReportPage.postLunchMealAnalysisTo("8")
        dailyOverlayReportPage.enterLunchBeforeLow("110")
        dailyOverlayReportPage.enterLunchBeforeHigh("170")
        dailyOverlayReportPage.enterDinnerAfterLow("80")
        dailyOverlayReportPage.enterDinnerAfterHigh("160")
        dailyOverlayReportPage.dinnerTimePeriodFrom("7:00 AM")
        dailyOverlayReportPage.dinnerTimePeriodTo("9:00 AM")
        dailyOverlayReportPage.postDinnerMealAnalysisFrom("4")
        dailyOverlayReportPage.postDinnerMealAnalysisTo("8")
        dailyOverlayReportPage.enterDinnerBeforeLow("110")
        dailyOverlayReportPage.enterDinnerBeforeHigh("170")
        dailyOverlayReportPage.enterEveningBeforeLow('110')
        dailyOverlayReportPage.enterEveningBeforeHigh('160')
        dailyOverlayReportPage.eveningTimePeriodFrom('10:00 AM')
        dailyOverlayReportPage.eveningTimePeriodTo('2:00 AM')
        dailyOverlayReportPage.enterSleepingBeforeLow('110')
        dailyOverlayReportPage.enterSleepingBeforeHigh('160')
        dailyOverlayReportPage.sleepingPeriodTo('5:30 AM')
        dailyOverlayReportPage.fastingPeriodFrom('10:00 AM')
        dailyOverlayReportPage.fastingPeriodTo('2:00 AM')
        then: 'Verify that the values could be modified.'
        then: 'Verify that all the settings in the Clinic Report Settings Screen are in 12 hour format.'
        dailyOverlayReportPage.мerifyСonverted12hour()
        then: 'Verify that [Clinic_Settings_Breakfast_BG_Low_Before] can be modified.'
        dailyOverlayReportPage.enterBreakfastBeforeLow("79")
        then: 'Verify that [Clinic_Settings_Breakfast_BG_Low_After] can be modified.'
        dailyOverlayReportPage.enterBreakfastAfterLow("79")
        then: 'Verify that [Clinic_Settings_Breakfast_BG_High_Before] can be modified.'
        dailyOverlayReportPage.enterBreakfastBeforeHigh('141')
        then: 'Verify that [Clinic_Settings_Breakfast_BG_High_After] can be modified.'
        dailyOverlayReportPage.enterBreakfastAfterHigh('141')
        then: 'Verify that [Clinic_Settings_Lunch_BG_Low_Before] can be modified.'
        dailyOverlayReportPage.enterLunchBeforeLow('79')
        then: 'Verify that [Clinic_Settings_Lunch_BG_Low_After] can be modified.'
        dailyOverlayReportPage.enterLunchAfterLow('79')
        then: 'Verify that [Clinic_Settings_Lunch_BG_High_Before] can be modified.'
        dailyOverlayReportPage.enterLunchBeforeHigh('141')
        then: 'Verify that [Clinic_Settings_Lunch_BG_High_After] can be modified.'
        dailyOverlayReportPage.enterLunchGlucoseTargetAfterHigh('141')
        then: 'Verify that [Clinic_Settings_Diner_BG_Low_Before] can be modified.'
        dailyOverlayReportPage.enterDinnerBeforeLow('79')
        then: 'Verify that [Clinic_Settings_Diner_BG_Low_After] can be modified.'
        dailyOverlayReportPage.enterDinnerAfterLow('79')
        then: 'Verify that [Clinic_Settings_Diner_BG_High_Before] can be modified.'
        dailyOverlayReportPage.enterDinnerBeforeHigh('141')
        then: 'Verify that [Clinic_Settings_Diner_BG_High_After] can be modified.'
        dailyOverlayReportPage.enterDinnerAfterHigh('141')
        then: 'Verify that [Clinic_Settings_Evenig_BG_Low] can be modified.'
        dailyOverlayReportPage.enterEveningBeforeLow('79')
        then: 'Verify that [Clinic_Settings_Evening_BG_High] can be modified.'
        dailyOverlayReportPage.enterEveningBeforeHigh('141')
        then: 'Verify that [Clinic_Settings_Sleeping_BG_Low] can be modified.'
        dailyOverlayReportPage.enterSleepingBeforeLow('79')
        then: 'Verify that [Clinic_Settings_Sleeping_BG_High] can be modified.'
        dailyOverlayReportPage.enterSleepingBeforeHigh('141')
        then: 'Verify that the following values could be modified:'
        then: 'Verify that [Clinic_Settings_Breakfast_From] can be modified.'
        dailyOverlayReportPage.breakfastTimePeriodFrom('8:00 AM')
        then: 'Verify that [Clinic_Settings_Breakfast_To] can be modified.'
        dailyOverlayReportPage.breakfastTimePeriodTo('8:30 AM')
        then: 'Verify that [Clinic_Settings_Lunch_From] can be modified.'
        dailyOverlayReportPage.lunchTimePeriodFrom('1:00 PM')
        then: 'Verify that [Clinic_Settings_Lunch_To] can be modified.'
        dailyOverlayReportPage.lunchTimePeriodTo('5:00 PM')
        then: 'Verify that [Clinic_Settings_Dinner_From] can be modified.'
        dailyOverlayReportPage.dinnerTimePeriodFrom('5:00 PM')
        then: 'Verify that [Clinic_Settings_Dinner_To] can be modified.'
        dailyOverlayReportPage.dinnerTimePeriodTo('9:00 PM')
        then: 'Verify that [Clinic_Settings_Evening_From] can be modified.'
        dailyOverlayReportPage.eveningTimePeriodFrom('9:00 PM')
        then: 'Verify that [Clinic_Settings_Evening_To] can be modified.'
        dailyOverlayReportPage.eveningTimePeriodTo('12:30 AM')
        then: 'Verify that [Clinic_Settings_Sleeping_From] can not be modified.'
        then: 'Verify that [Clinic_Settings_Sleeping_To] can be modified.'
        dailyOverlayReportPage.sleepingPeriodTo('5:30 AM')
        then: 'Verify that [Clinic_Settings_Fasting_From] can be modified.'
        dailyOverlayReportPage.fastingPeriodFrom('5:30 AM')
        then: 'Verify that [Clinic_Settings_Fasting_To] can be modified.'
        dailyOverlayReportPage.fastingPeriodTo('9:00 AM')
        then: 'Verify that the following values could be modified:'
        then: 'Verify that [Clinic_Settings_Breakfast_Analysis_From] can be modified.'
        dailyOverlayReportPage.postBreakfastMealAnalysisFrom('1')
        then: 'Verify that [Clinic_Settings_Breakfast_Analysis_To] can be modified.'
        dailyOverlayReportPage.postBreakfastMealAnalysisTo('6')
        then: 'Verify that [Clinic_Settings_Lunch_Analysis_From] can be modified.'
        dailyOverlayReportPage.postLunchMealAnalysisFrom('1')
        then: 'Verify that [Clinic_Settings_Lunch_Analysis_To] can be modified.'
        dailyOverlayReportPage.postLunchMealAnalysisTo('6')
        then: 'Verify that [Clinic_Settings_Dinner_Analysis_From] can be modified.'
        dailyOverlayReportPage.postDinnerMealAnalysisFrom('1')
        then: 'Verify that [Clinic_Settings_Dinner_Analysis_To] can be modified.'
        dailyOverlayReportPage.postDinnerMealAnalysisTo('6')
        then: 'Verify that the “Save” button is active.'
        when: 'Click on the save button.'
        dailyOverlayReportPage.saveReportSettings()
        then: 'Verify the Save Confirmation Window is displayed with the title “Saved” and the following text: “SUCCESS! Your changes have been saved." '
        dailyOverlayReportPage.successPopupAppear("SUCCESS!")
        then: 'Verify there is an OK Button, labeled as “OK” on the message box.'
        dailyOverlayReportPage.successPopupSavedOkBtnvalue('OK')
        when:
        dailyOverlayReportPage.successPopupSavedOkBtn()
        then: 'Verify the software returns to the Clinic Report Settings Screen.'
        sleep(2000)
        dailyOverlayReportPage.selectTimehr12displayed()
        then: 'Verify that upon pressing the save button the system stores the user’s clinic settings.'
        dailyOverlayReportPage.enterBreakfastBeforeLowVerify("79")
        dailyOverlayReportPage.enterBreakfastAfterLowVerify("79")
        dailyOverlayReportPage.enterBreakfastBeforeHighVerify('141')
        dailyOverlayReportPage.enterBreakfastAfterHighVerify('141')
        dailyOverlayReportPage.enterLunchBeforeLowVerify('79')
        dailyOverlayReportPage.enterLunchAfterLowVerify('79')
        dailyOverlayReportPage.enterLunchGlucoseTargetAfterHighVerify('141')
        dailyOverlayReportPage.enterDinnerBeforeLowVerify('79')
        dailyOverlayReportPage.enterDinnerAfterLowVerify('79')
        dailyOverlayReportPage.enterDinnerBeforeHighVerify('141')
        dailyOverlayReportPage.enterDinnerAfterHighVerify('141')
        dailyOverlayReportPage.enterEveningBeforeLowVerify('79')
        dailyOverlayReportPage.enterEveningBeforeHighVerify('141')
        dailyOverlayReportPage.enterSleepingBeforeLowVerify('79')
        dailyOverlayReportPage.enterSleepingBeforeHighVerify('141')
        dailyOverlayReportPage.breakfastTimePeriodFromVerify('8:00 AM')
        dailyOverlayReportPage.breakfastTimePeriodToVerify('8:30 AM')
        dailyOverlayReportPage.lunchTimePeriodFromVerify('1:00 PM')
        dailyOverlayReportPage.lunchTimePeriodToVerify('5:00 PM')
        dailyOverlayReportPage.dinnerTimePeriodFromVerify('5:00 PM')
        dailyOverlayReportPage.dinnerTimePeriodToVerify('9:00 PM')
        dailyOverlayReportPage.eveningTimePeriodFromVerify('9:00 PM')
        dailyOverlayReportPage.eveningTimePeriodToVerify('12:30 AM')
        dailyOverlayReportPage.sleepingPeriodToVerify('5:30 AM')
        dailyOverlayReportPage.postBreakfastMealAnalysisFromVerify('1')
        dailyOverlayReportPage.postBreakfastMealAnalysisToVerify('6')
        dailyOverlayReportPage.postLunchMealAnalysisFromVerify('1')
        dailyOverlayReportPage.postLunchMealAnalysisToVerify('6')
        dailyOverlayReportPage.postDinnerMealAnalysisFromVerify('1')
        dailyOverlayReportPage.postDinnerMealAnalysisTo('6')
        when: 'Select the mmol/L for Glucose units. Click OK on Convert Units Confirmation Window.'
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.buttonSaveSettings()
        and:  'Select the 24 hour time display setting. Click SAVE button then Click OK button.'
        dailyOverlayReportPage.selectTimeDisplay24()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.successPopupAppear("SUCCESS! ")
        dailyOverlayReportPage.successPopupSavedOkBtnvalue('OK')
        dailyOverlayReportPage.successPopupSavedOkBtn()
        then: 'Verify that all the settings in the Clinic Report Settings Screen are converted to 24 hour format.'
        sleep(2000)
        dailyOverlayReportPage.verifyСonverted24hour()
        then: 'Verify that all the settings in the Clinic Report Settings Screen are converted to mmol/L. Note: 1 mmol/L = 18.016 mg/dL'
        dailyOverlayReportPage.enterBreakfastBeforeLowVerify("4.4")
        dailyOverlayReportPage.enterBreakfastAfterLowVerify("4.4")
        dailyOverlayReportPage.enterBreakfastBeforeHighVerify('7.8')
        dailyOverlayReportPage.enterBreakfastAfterHighVerify('7.8')
        dailyOverlayReportPage.enterLunchBeforeLowVerify('4.4')
        dailyOverlayReportPage.enterLunchAfterLowVerify('4.4')
        dailyOverlayReportPage.enterLunchGlucoseTargetAfterHighVerify('7.8')
        dailyOverlayReportPage.enterDinnerBeforeLowVerify('4.4')
        dailyOverlayReportPage.enterDinnerAfterLowVerify('4.4')
        dailyOverlayReportPage.enterDinnerBeforeHighVerify('7.8')
        dailyOverlayReportPage.enterDinnerAfterHighVerify('7.8')
        dailyOverlayReportPage.enterEveningBeforeLowVerify('4.4')
        dailyOverlayReportPage.enterEveningBeforeHighVerify('7.8')
        dailyOverlayReportPage.enterSleepingBeforeLowVerify('4.4')
        dailyOverlayReportPage.enterSleepingBeforeHighVerify('7.8')
        and: 'End of test.'
    }
}