package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import org.openqa.selenium.By
import groovy.util.logging.Slf4j

@Slf4j
@Screenshot
@SmokeTest
class GlucoseRangeDefaultSpec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static ClinicSettingsPage clinicSettingsPage
    static HelpPage helpPage
    static CreatePatientPage createPatientPage

    def 'Browser Warning'() {
        given: 'Carelink is navigated to using the browser using an unsupported browser version'
        signInPage = browsers.to SignInPage
        and: 'The browser warning shall be displayed'
        println 'Verifying the signin button is displayed'
        assert signInPage.signInButtonDisplayed()
    }
    def "User able to see carelink banner image in login page"() {
        given: "User is at the carelink login page"
        loginPage = browsers.at LoginPage
//        when: 'User is hovering over an image'
        and: 'User is able to see banner image'
        assert loginPage.isBannerImageDisplayed()
    }
    def 'Inactivity warning popup, logout page'() {
        when: 'A user supplies valid carelink credentials'
        loginPage = browsers.at LoginPage
        then: 'User should be able to successfully log into Carelink envision pro'
        loginPage.enterUsername("kornylodmytro")
        loginPage.enterPassword("Maluy013+")
        loginPage.clickOnSignIn()
        log.info( 'User is successful login in the system');
    }
    def 'Click Clinic Settings'() {
        given: 'When user successfully logged into carelink application'
        when: 'User clicks on the clinic settings tab'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'User is able to view clinic settings and close it'
        sleep(1000)
        clinicSettingsPage.clickOnClinicSettings()
        }
    def '  page'() {
        when: 'A user supplies valid carelink credentials'
        helpPage = browsers.at HelpPage
        then: 'User should be able to successfully log into Carelink envision pro'
        helpPage.highValue()
        log.info( 'Glucose target range High value');
        helpPage.lowValue()
        log.info( 'Glucose target range low value');
        helpPage.glucoseTargetXtremeHigh()
        log.info( 'Glucose target xtreme high ');
        helpPage.glucoseTargetXtremeLow()
        log.info( 'Glucose target xtreme low');
        helpPage.beforeBreakfastGlucoseTargetLow()
        log.info( 'Before break fast glucose target Low');
        helpPage.beforeBreakfastGlucoseTargetHigh()
        log.info( 'Before break fast glucose tTarget High');
        helpPage.eveningGlucoseTargetLow()
        log.info( 'Evening glucose target Low');
        helpPage.eveningGlucoseTargetHigh()
        log.info( 'Evening glucose target High');
        helpPage.sleepingGlucoseTargetLow()
        log.info( 'Sleeping glucose target Low');
        helpPage.sleepingGlucoseTargetHigh()
        log.info( 'Sleeping glucose target High');
        helpPage.backToMainPage() ;
    }
    def 'Create new patient' () {
        when: 'A user is logged into Carelink application'
        signInPage = browsers.at SignInPage
        then: 'User should be able to create a new patient'
        signInPage.clickNewPatient()
    }
    def 'Enter new patient info' () {
        when:
        createPatientPage = browsers.at CreatePatientPage
        then:
        createPatientPage.createPatient()
        driver.findElement(By.name("patientDiabetesType")).click();
        driver.findElement(By.id("patient_details:otherMedicationIndCheckbox")).click();
        createPatientPage.clickSaveBtn()
        screenshot('Patient', 'New Patient')
        sleep(500)

    }
    def ' verify page'() {
        when: 'A user supplies valid carelink credentials'
        helpPage = browsers.at HelpPage
        then: 'User should be able to successfully log into Carelink envision pro'
        helpPage.clickEditPatientSettings()
        helpPage.highValue()
        log.info( 'Glucose target range High value');
        helpPage.lowValue()
        log.info( 'Glucose target range low value');
        helpPage.glucoseTargetXtremeHigh()
        log.info( 'Glucose target xtreme high ');
        helpPage.glucoseTargetXtremeLow()
        log.info( 'Glucose target xtreme low');
        helpPage.beforeBreakfastGlucoseTargetLow()
        log.info( 'Before break fast glucose target Low');
        helpPage.beforeBreakfastGlucoseTargetHigh()
        log.info( 'Before break fast glucose tTarget High');
        helpPage.eveningGlucoseTargetLow()
        log.info( 'Evening glucose target Low');
        helpPage.eveningGlucoseTargetHigh()
        log.info( 'Evening glucose target High');
        helpPage.sleepingGlucoseTargetLow()
        log.info( 'Sleeping glucose target Low');
        helpPage.sleepingGlucoseTargetHigh()
        log.info( 'Sleeping glucose target High');
        helpPage.bgunit_mgdl()
        helpPage.timedisplay_hr12()
        log.info( 'mg/dl and 12 hour time display');
        helpPage.highValue()
        log.info( 'Glucose target range High value 150');
        helpPage.lowValue()
        log.info( 'Glucose target range low value 70');
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("breakfast.time.period.from"), helpPage.breakfasrCss()) ; ;
        log.info( 'Breakfast from time is 6:00 AM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("breakfast.time.period.to"), helpPage.breakfasrToXpath()) ; ;
        log.info( 'Breakfast from time is 11:00 AM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("breakfast.post.meal.analysis.to"), helpPage.breakfastMealTo()) ; ;
        log.info( 'Breakfast post meal analysis to time is 3 hours.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.time.period.from"), helpPage.lunchTimeFrom()) ; ;
        log.info( 'Lunch time period from time is 11:00 AM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.time.period.to"), helpPage.lunchTimeTo()) ; ;
        log.info( 'Lunch time period to time is 4:00 PM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.post.meal.analysis.from"), helpPage.LunchPostAnalysisTo()) ; ;
        log.info( 'Lunch post meal analysis from time is 1 hour');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.post.meal.analysis.to"), helpPage.LunchPostAnalysisFrom()) ; ;
        log.info( 'Lunch post meal analysis to time is 3 hours.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.time.period.from"), helpPage.dinnerTimeFrom()) ; ;
        log.info( 'Dinner t ime period from time is 5:00 PM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.time.period.to"), helpPage.dinnerTimeTo()) ; ;
        log.info( 'Dinner time period to time is 8:00 PM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.post.meal.analysis.from"), helpPage.dinnerPostAnalysisTo()) ; ;
        log.info( 'Lunch post meal analysis from time is 1 hour');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.post.meal.analysis.to"), helpPage.dinnerPostAnalysisFrom()) ; ;
        log.info( 'Lunch post meal analysis to time is 3 hours.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("evening.time.period.from"), helpPage.eveningTimePeriodFrom()) ; ;
        log.info( 'Evening time period from time is 12:00 AM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("evening.time.period.to"), helpPage.eveningTimePeriodTo()) ; ;
        log.info( 'Evening time period to time is 4:00 AM');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("sleeping.time.period.from"), helpPage.sleepingTimePeriodTrom()) ; ;
        log.info( 'Sleeping time period from time is 4:00 AM.');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("sleeping.time.period.to"), helpPage.sleepingTimePeriodTo()) ; ;
        log.info( 'Sleeping time period to time is 6:00 AM.');
        helpPage.selectGlucoseUnitsMmol()
        helpPage.glucoseUnitsMmolUprrove()
        helpPage.selectTimeDisplay24()
        log.info( 'Glucose units to mmol/L and the time display to 24 hour is сhanged.');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.clickEditPatientSettings()
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("24hour.breakfast.time.period.from"), helpPage.hour24BreakfasrCss()) ; ;
        log.info( 'Breakfast from time is 6:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.breakfast.time.period.to"), helpPage.hour24BreakfasrToXpath()) ; ;
        log.info( 'Breakfast from time is 11:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.lunch.time.period.from"), helpPage.hour24LunchTimeFrom()) ; ;
        log.info( 'Lunch time period from time is 11:00 ');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.lunch.time.period.to"), helpPage.hour24LunchTimeTo()) ; ;
        log.info( 'Lunch time period to time is 16:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.dinner.time.period.from"), helpPage.hour24DinnerTimeFrom()) ; ;
        log.info( 'Dinner t ime period from time is 16:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.dinner.time.period.to"), helpPage.hour24DinnerTimeTo()) ; ;
        log.info( 'Dinner time period to time is 20:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.evening.time.period.from"), helpPage.hour24EveningTimePeriodFrom()) ; ;
        log.info( 'Evening time period from time is 00:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.evening.time.period.to"), helpPage.hour24EveningTimePeriodTo()) ; ;
        log.info( 'Evening time period to time is 4:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.sleeping.time.period.from"), helpPage.hour24SleepingTimePeriodTrom()) ; ;
        log.info( 'Sleeping time period from time is 4:00');
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.sleeping.time.period.to"), helpPage.hour24SleepingTimePeriodTo()) ; ;
        log.info( 'Sleeping time period to time is 6:00');
        helpPage.enterGlucoseTargetHigh("16.6")
        helpPage.enterGlucoseTargetLow("2.1")
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.high"), helpPage.errorGlucoseHigh()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.highest"), helpPage.errorGlucoseHighest()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.low"), helpPage.errorGlucoseLow()) ;;
        log.info( 'System gives the error for glucose low value is not in the acceptable target range.  ');
        helpPage.enterGlucoseTargetLow("16.6")
        log.info( 'Glucos e target range low value to 16.6 mmol/L.');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.high"), helpPage.errorGlucoseHigh()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.highest"), helpPage.errorGlucoseHighest()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.low"), helpPage.errorGlucoseLow()) ;;
        log.info( 'System gives the error for glucose low value is not in the acceptable target range.  ');
        helpPage.enterGlucoseTargetLow("7.8")
        log.info( 'Change the Glucose target range low value to 7.8  mmol/L.');
        helpPage.enterGlucoseTargetHigh("11.1")
        log.info( 'Change the Glucose target range high value to 11.1 mmol/L.');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.verifyMmolHomePage()
        helpPage.clickEditPatientSettings()
        helpPage.enterGlucoseTargetLow("10")
        log.info( 'Change the Glucose target range  value to  10 mmol/L');
        helpPage.enterGlucoseTargetHigh("10")
        log.info( 'Change the Glucose target range  value to  10 mmol/L');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.low"), helpPage.errorGlucoseHigh()) ;;
        helpPage.enterGlucoseTargetLow("7.8")
        log.info( 'Change the Glucose target range  value to  7.8 mmol/L');
        helpPage.saveReportSettings()
        helpPage.verifyMmolHomePageText()
        log.info( 'Verify 7.8  mmol/L – 10.0  mmol/L');
        log.info( 'Verify glucose mmol home page');
        helpPage.clickEditPatientSettings()
        helpPage.selectGlucoseUnitsMg()
        helpPage.glucoseUnitsMmolUprrove()
        helpPage.enterGlucoseTargetLow("39")
        log.info( 'Change the Glucose target range  value to 39 mg/dL.');
        helpPage.enterGlucoseTargetHigh("300")
        log.info( 'Change the Glucose target range  value to 300 mg/dL');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.highmg"), helpPage.errorGlucoseHigh()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.highestmg"), helpPage.errorGlucoseHighest()) ; ;
        helpPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.lowmg"), helpPage.errorGlucoseLow()) ;;
        log.info( 'System gives the error for glucose low value is not in the acceptable target range.  ');
        helpPage.enterGlucoseTargetLow("140")
        log.info( 'Change the Glucose target range  value to 140 mg/dL.');
        helpPage.enterGlucoseTargetHigh("200")
        log.info( 'Change the Glucose target range  value to 200 mg/dL');
        helpPage.saveReportSettings()
        log.info( 'Save in Patient Report Settings ');
        helpPage.verifyMgHomePage()
        log.info( 'Verify glucose mg home page');

    }



}



