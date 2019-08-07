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
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('Smoke test - Register a clinic test')
@Screenshot
@SmokeTest
@Stepwise
class SCUI296Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static ClinicSettingsPage clinicSettingsPage
    static HelpPage helpPage
    static CreatePatientPage createPatientPage
    static DailyOverlayReportPage dailyOverlayReportPage

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
        log.info('User is successful login in the system') ;
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
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: 'User should be able to successfully log into Carelink envision pro'
        dailyOverlayReportPage.highValue()
        log.info('Glucose target range High value')
        dailyOverlayReportPage.lowValue()
        log.info('Glucose target range low value')
        dailyOverlayReportPage.glucoseTargetXtremeHigh()
        log.info('Glucose target xtreme high ')
        dailyOverlayReportPage.glucoseTargetXtremeLow()
        log.info('Glucose target xtreme low')
        dailyOverlayReportPage.beforeBreakfastGlucoseTargetLow()
        log.info('Before break fast glucose target Low')
        dailyOverlayReportPage.beforeBreakfastGlucoseTargetHigh()
        log.info('Before break fast glucose tTarget High')
        dailyOverlayReportPage.eveningGlucoseTargetLow()
        log.info('Evening glucose target Low')
        dailyOverlayReportPage.eveningGlucoseTargetHigh()
        log.info('Evening glucose target High')
        dailyOverlayReportPage.sleepingGlucoseTargetLow()
        log.info('Sleeping glucose target Low')
        dailyOverlayReportPage.sleepingGlucoseTargetHigh()
        log.info('Sleeping glucose target High')
        dailyOverlayReportPage.backToMainPage()
    }

    def 'Create new patient'() {
        when: 'A user is logged into Carelink application'
        signInPage = browsers.at SignInPage
        then: 'User should be able to create a new patient'
        signInPage.clickNewPatient()
    }

    def 'Enter new patient info'() {
        when:
        createPatientPage = browsers.at CreatePatientPage
        then:
        createPatientPage.createPatient()
        driver.findElement(By.name("patientDiabetesType")).click()
        driver.findElement(By.id("patient_details:otherMedicationIndCheckbox")).click()
        createPatientPage.clickSaveBtn()
        screenshot('Patient', 'New Patient')
        sleep(500)

    }

    def ' Navigate to Patient Report Settings screen by option Edit'() {
        when: 'Patient Report Settings opend'
        helpPage = browsers.at HelpPage
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then: 'Verify that the patient report settings default values match the Clinic Settings values at the time of construction.'
        dailyOverlayReportPage.clickEditPatientSettings()
        dailyOverlayReportPage.bgunit_mgdl()
        log.info('mg/dl and 12 hour time display')
        dailyOverlayReportPage.glucoseTargetLowDefault()
        log.info('Glucose target range High value 180')
        dailyOverlayReportPage.glucoseTargetHighDefault()
        log.info('Glucose target range low value 70')
        then: ' Glucose Units are set to mg/dl and 12 hour time display.'
        dailyOverlayReportPage.timedisplay_hr12()
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("breakfast.time.period.from"), dailyOverlayReportPage.breakfasrCss())
        log.info('Breakfast from time is 6:00 AM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("breakfast.time.period.to"), dailyOverlayReportPage.breakfasrToXpath())
        log.info('Breakfast from time is 11:00 AM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("breakfast.post.meal.analysis.to"), dailyOverlayReportPage.breakfastMealTo())
        log.info('Breakfast post meal analysis to time is 3 hours.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.time.period.from"), dailyOverlayReportPage.lunchTimeFrom())
        log.info('Lunch time period from time is 11:00 AM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.time.period.to"), dailyOverlayReportPage.lunchTimeTo())
        log.info('Lunch time period to time is 4:00 PM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.post.meal.analysis.from"), dailyOverlayReportPage.LunchPostAnalysisTo())
        log.info('Lunch post meal analysis from time is 1 hour')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("lunch.post.meal.analysis.to"), dailyOverlayReportPage.LunchPostAnalysisFrom())
        log.info('Lunch post meal analysis to time is 3 hours.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.time.period.from"), dailyOverlayReportPage.dinnerTimeFrom())
        log.info('Dinner time period from time is 4:00 PM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.time.period.to"), dailyOverlayReportPage.dinnerTimeTo())
        log.info('Dinner time period to time is 8:00 PM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.post.meal.analysis.from"), dailyOverlayReportPage.dinnerPostAnalysisTo())
        log.info('Lunch post meal analysis from time is 1 hour')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dinner.post.meal.analysis.to"), dailyOverlayReportPage.dinnerPostAnalysisFrom())
        log.info('Lunch post meal analysis to time is 3 hours.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("evening.time.period.from"), dailyOverlayReportPage.eveningTimePeriodFrom())
        log.info('Evening time period from time is 8:00 PM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("evening.time.period.to"), dailyOverlayReportPage.eveningTimePeriodTo())
        log.info('Evening time period to time is 12:00 AM')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("sleeping.time.period.from"), dailyOverlayReportPage.sleepingTimePeriodTrom())
        log.info('Sleeping time period from time is 12:00 AM')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("sleeping.time.period.to"), dailyOverlayReportPage.sleepingTimePeriodTo())
        log.info('Sleeping time period to time is 6:00 AM.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dawn.time.period.from"), dailyOverlayReportPage.dawnTimePeriodFrom())
        log.info('Verify that Dawn time period from time is 04:00 AM. ')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("dawn.time.period.to"), dailyOverlayReportPage.dawnTimePeriodTo())
        log.info('Verify that Dawn time period to time is 06:00 AM. ')
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.glucoseUnitsMmolUprrove()
        then: ' Change the glucose units to mmol/L and the time display to 24 hour.'
        dailyOverlayReportPage.selectTimeDisplay24()
        log.info('Glucose units to mmol/L and the time display to 24 hour is сhanged.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Save in Patient Report Settings ')
        dailyOverlayReportPage.clickEditPatientSettings()
        dailyOverlayReportPage.glucoseTargetHighMmolDefault()
        log.info('Verify that Glucose target high range value is 10.0 mmol/L.')
        dailyOverlayReportPage.glucoseTargetLowMmolDefaul()
        log.info('Verify that Glucose target low range value is 3.9 mmol/L.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("24hour.breakfast.time.period.from"), dailyOverlayReportPage.hour24BreakfasrCss())
        log.info('Breakfast from time is 6:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.breakfast.time.period.to"), dailyOverlayReportPage.hour24BreakfasrToXpath())
        log.info('Breakfast from time is 11:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.lunch.time.period.from"), dailyOverlayReportPage.hour24LunchTimeFrom())
        log.info('Lunch time period from time is 11:00 ')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.lunch.time.period.to"), dailyOverlayReportPage.hour24LunchTimeTo())
        log.info('Lunch time period to time is 16:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.dinner.time.period.from"), dailyOverlayReportPage.hour24DinnerTimeFrom())
        log.info('Dinner t ime period from time is 16:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.dinner.time.period.to"), dailyOverlayReportPage.hour24DinnerTimeTo())
        log.info('Dinner time period to time is 20:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.bedtime.time.period.from"), dailyOverlayReportPage.hour24BedtimeTimeFrom())
        log.info('Evening time period from time is 20:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.bedtime.time.period.to"), dailyOverlayReportPage.hour24BedtimeTimeTo())
        log.info('Evening time period to time is 0:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.evening.time.period.from"), dailyOverlayReportPage.hour24EveningTimePeriodFrom())
        log.info('Evening time period from time is 00:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.evening.time.period.to"), dailyOverlayReportPage.hour24EveningTimePeriodTo())
        log.info('Evening time period to time is 4:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.sleeping.time.period.from"), dailyOverlayReportPage.hour24SleepingTimePeriodTrom())
        log.info('Sleeping time period from time is 4:00')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("24hour.sleeping.time.period.to"), dailyOverlayReportPage.hour24SleepingTimePeriodTo())
        log.info('Sleeping time period to time is 6:00')
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 3.5 mmol/L Verify error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("7.9")
        dailyOverlayReportPage.enterGlucoseTargetLow("3.5")
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button. ')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.low"), dailyOverlayReportPage.errorGlucoseLow2())
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.error.lowest"), dailyOverlayReportPage.errorGlucoseLowest())
        log.info('Verify that system gives the error for glucose low value is not in the acceptable target range.')
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 7.9 mmol/L Verify error'
        dailyOverlayReportPage.enterGlucoseTargetLow("7.9")
        log.info('Glucos e target range low value to 7.9 mmol/L.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button. ')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.low.2.8"), dailyOverlayReportPage.errorGlucoseLow2())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 3.6 mmol/L  System accepts and navigates to the Patient Record Screen  '
        dailyOverlayReportPage.enterGlucoseTargetLow("3.6")
        log.info('Change the Glucose target range low value to 3.6 mmol/L.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("3.6  mmol/L – 7.9  mmol/L")
        log.info('Verify that system accepts the value and navigates to the Patient Record Screen by saving it.')
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 7.8 mmol/L System gives the errorSystem gives the errorSystem gives the error'
        dailyOverlayReportPage.enterGlucoseTargetLow("7.8")
        log.info('Change the Glucose target range  value to  7.8 mmol/L')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("7.8  mmol/L – 7.9  mmol/L")
        log.info('Verify 7.8  mmol/L – 7.9  mmol/L')
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 3.5 mmol/L System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetLow("3.5")
        log.info('Change the Glucose target range  value to  3.5 mmol/L')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.low.3.5"), dailyOverlayReportPage.errorGlucoseLow2())
        then: ' Glucose Low in mmol/L verify high 7.9 mmol/L low 3.6 mmol/L System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetLow("3.6")
        log.info('Change the Glucose target range  value to  3.6 mmol/L')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("3.6  mmol/L – 7.9  mmol/L")
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mmol/L verify high 16.7 mmol/L low 3.6 mmol/L System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("16.7")
        dailyOverlayReportPage.enterGlucoseTargetLow("3.6")
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.high"), dailyOverlayReportPage.errorGlucoseHigh())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mmol/L verify high 4.3 mmol/L low 3.6 mmol/L System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("4.3")
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.error.high.21.1"), dailyOverlayReportPage.errorGlucoseHigh())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mmol/L verify high 4.4 mmol/L low 3.6 mmol/L System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetHigh("4.4")
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("3.6  mmol/L – 4.4  mmol/L")
        log.info('3.6  mmol/L – 4.4  mmol/L')
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mmol/L verify high 12.8 mmol/L low 3.6 mmol/L System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetHigh("12.8")
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("3.6  mmol/L – 12.8  mmol/L")
        log.info('3.6  mmol/L – 12.8  mmol/L')
        dailyOverlayReportPage.clickEditPatientSettings()
        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.glucoseUnitsMmolUprrove()
        then: ' Glucose Low in mg/dL verify high 142 mg/dL low 54 mg/dL System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("142")
        log.info('Change the Glucose target range  value to 142 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("54")
        log.info('Change the Glucose target range  value to 54 mg/dL.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.verify.39.mg"), dailyOverlayReportPage.errorGlucoseHigh())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mg/dL verify high 142 mg/dL low 63 mg/dL System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetLow("63")
        log.info('Change the Glucose target range  value to 63 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.verify.39.mg"), dailyOverlayReportPage.errorGlucoseHigh())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mg/dL verify high 142 mg/dL low 64 mg/dL System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetLow("64")
        log.info('Change the Glucose target range  value to 64 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        dailyOverlayReportPage.systemAcceptsValue("64  mg/dL – 142  mg/dL")
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mg/dL verify high 142 mg/dL low 140 mg/dL System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetLow("140")
        log.info('Change the Glucose target range  value to 140 mg/dL') ;
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        log.info('System gives the error for glucose low value is not in the acceptable target range.')
        dailyOverlayReportPage.systemAcceptsValue("140  mg/dL – 142  mg/dL")
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mg/dL verify high 100 mg/dL low 100 mg/dL System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("100")
        log.info('Change the Glucose target range  value to 100 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("100")
        log.info('Change the Glucose target range  value to 100 mg/dL.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedXpath(ReadProperties.getProperties().get("glucose.target.range.accepts.low.must.be.form.50"), dailyOverlayReportPage.errorGlucoseLowfrom50())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mg/dL verify high 100 mg/dL low 98 mg/dL System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetLow("98")
        log.info('Change the Glucose target range  value to 98 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("98  mg/dL – 100  mg/dL")
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mg/dL verify high 301 mg/dL low 64 mg/dL System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("301")
        log.info('Change the Glucose target range  value to 301 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("64")
        log.info('Change the Glucose target range  value to 64 mg/dL.')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.accepts.high.must.be.form.80"), dailyOverlayReportPage.errorGlucoseHigh())
        then: ' Glucose Low in mg/dL verify high 301 mg/dL low 66 mg/dL System gives the error'
        dailyOverlayReportPage.enterGlucoseTargetHigh("66")
        log.info('Change the Glucose target range  value to 66 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.isTextGlucoseRangesDisplayedCssSelector(ReadProperties.getProperties().get("glucose.target.range.accepts.high.must.be.to.380"), dailyOverlayReportPage.errorGlucoseHigh())
        log.info('System gives the error for glucose low value is not in the acceptable target range.  ')
        then: ' Glucose Low in mg/dL verify high 230 mg/dL low 66 mg/dL System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetHigh("230")
        log.info('Change the Glucose target range  value to 230 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        log.info('Press the save button.')
        dailyOverlayReportPage.systemAcceptsValue("64  mg/dL – 230  mg/dL")
        dailyOverlayReportPage.clickEditPatientSettings()
        then: ' Glucose Low in mg/dL verify high 80 mg/dL low 66 mg/dL System accepts and navigates to the Patient Record Screen'
        dailyOverlayReportPage.enterGlucoseTargetHigh("80")
        log.info('Change the Glucose target range  value to 80 mg/dL')
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.systemAcceptsValue("64  mg/dL – 80  mg/dL")
        log.info('Press the save button.') 
        dailyOverlayReportPage.clickEditPatientSettings()
        and: 'Verify that Breakfast post meal analysis from time is 1 hour.'
        dailyOverlayReportPage.breakfastPostMealAnalysis()
        and: 'Change the Glucose target range high value to 10 mmol/L.'
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.glucoseUnitsMmolUprrove()
        dailyOverlayReportPage.enterGlucoseTargetHigh("10")
    }

}

