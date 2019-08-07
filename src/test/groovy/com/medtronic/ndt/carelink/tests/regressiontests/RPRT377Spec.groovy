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
import org.openqa.selenium.By
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Title('RPRT377 – Patient Report Settings ')
@Screenshot
@RegressionTest
@Stepwise
class RPRT377Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static HelpPage helpPage
    static DailyOverlayReportPage dailyOverlayReportPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static CreatePatientPage createPatientPage
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

    def 'Create new patient'() {
        when: 'A user is logged into Carelink application'
        signInPage = browsers.at SignInPage
        then: 'User should be able to create a new patient'
        signInPage.clickNewPatient()
    }

    def 'Enter new patient info'() {
        when:
        createPatientPage = browsers.at CreatePatientPage
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:
        createPatientPage.createPatient()
        dailyOverlayReportPage.selectPatientDiabetesType()
        dailyOverlayReportPage.selectPatient_details()
        createPatientPage.clickSaveBtn()
        sleep(500)
    }

    def ' Navigate to Report Settings'() {
        when: 'Patient Report Settings opend'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        $(By.cssSelector("p.change_settings > a")).click()
        dailyOverlayReportPage.bgunit_mgdlSelect()
        dailyOverlayReportPage.enterGlucoseTargetHigh("150")
        log.info('Change the Glucose target range  value to 150 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("70")
        log.info('Change the Glucose target range  value to 70 mg/dL.')
        dailyOverlayReportPage.bgunit_mgdl()
        then: 'Verify that Observe default Glucose Units settings.'
        dailyOverlayReportPage.bgunit_mgdl()
        then:'Verify that the default Glucose Target High value is set to 150. '
        dailyOverlayReportPage.glucoseTargetLowDefault()
        then:'Verify that the default Glucose Target Low value is set to 70. '
        dailyOverlayReportPage.glucoseTargetHighDefault()
        then:'Verify that the default Extreme High value is set to 250.'
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("250")
        then:'Verify that the default Extreme Low value is set to 50.'
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("50")
        log.info(' Verify that default  High value is  250 and Low value 50')
    }

    def 'Verify glucose units is set default mmol/L'() {
        when: 'Glucose units is set to mmol/L'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:'Set Glucose Units to mmol/L. Click [OK] button on Convert units. Press button [Save]. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        then:'Verify that the Glucose units is set to mmol/L. '
        dailyOverlayReportPage.bgunitMmollValue()
        then: 'Verify that for the default Glucose Target High value is set to 8.3. '
        dailyOverlayReportPage.glucoseTargetHighValue("8.3")
        then:'Verify that for the default Glucose Target Low value is set to 3.9. '
        dailyOverlayReportPage.glucoseTargetLowValue("3.9")
        then:'Verify that default Extreme High value is set to 13.9. '
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("13.9")
        then:'Verify that default Extreme Low value is set to 2.8. '
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("2.8")
        log.info('Verify that Glucose Target value is set')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        dailyOverlayReportPage.clickEditNewPatientSettings()
    }

    def 'Verify glucose units is set default mg/dL'() {
        when: 'Glucose units is set to mg/dL'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:'Set Glucose Units to mg/dL. Click [OK] button on Convert units. Press button [Save]. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        then: 'Set following ranges of values. Glucose Target Range: High to 381 mg/dL Low to 141 mg/dL Extreme Glucose Range: High to 410 mg/dL Low to 131 mg/dL Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("381")
        log.info('Change the Glucose target range  value to 381 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("141")
        log.info('Change the Glucose target range  value to 141 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("410")
        log.info('Change the Glucose target  Xtreme High value to 410 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("100")
        log.info('Change the Glucose target  Xtreme Low value to 131 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then: 'Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to 380.” “Low must be from 50 to 140.” “Extreme High must be from 100 to 400.” “Extreme Low must be from 40 to 130.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to 380.", "Highest Limit must be from 100 to 400.", "Low must be from 50 to 140.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        and:'Verify glucose units is set to 175 mg/dl 73 mg/dl 165 mg/dl 71 mg/dl'
        log.info(' Glucose units is set to mg/d ')
        dailyOverlayReportPage.enterGlucoseTargetHigh("175")
        log.info('Change the Glucose target range  value to 175 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("73")
        log.info('Change the Glucose target range  value to 73 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("165")
        log.info('Change the Glucose target  Xtreme High value to 165 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("71")
        log.info('Change the Glucose target  Xtreme Low value to 71 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:' Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to Extreme High - 20.” “Low must be from Extreme Low + 10 to High - 2.” “Extreme High must be from High + 20 to 400.” “Extreme Low must be from 40 to Low - 10.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to Highest Limit - 20.", "Highest Limit must be from High + 20 to 400.", "Low must be from Lowest Limit + 10 to High - 2.")
        then:' Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to Extreme High - 20.” “Low must be from Extreme Low + 10 to High - 2.” “Extreme High must be from High + 20 to 400.” “Extreme Low must be from 40 to Low - 10.”'
        dailyOverlayReportPage.breakfastSectionError("Low must be from Lowest Limit + 10 to High - 2.", "High must be from 80 to Highest Limit - 20.", "Low must be from Lowest Limit + 10 to High - 2.")
        then:' Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to Extreme High - 20.” “Low must be from Extreme Low + 10 to High - 2.” “Extreme High must be from High + 20 to 400.” “Extreme Low must be from 40 to Low - 10.”'
        dailyOverlayReportPage.lunchSectionError("Low must be from Lowest Limit + 10 to High - 2.", "High must be from 80 to Highest Limit - 20.", "Low must be from Lowest Limit + 10 to High - 2.")
        then:' Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to Extreme High - 20.” “Low must be from Extreme Low + 10 to High - 2.” “Extreme High must be from High + 20 to 400.” “Extreme Low must be from 40 to Low - 10.”'
        dailyOverlayReportPage.dinnerSectionError("Low must be from Lowest Limit + 10 to High - 2.", "High must be from 80 to Highest Limit - 20.", "Low must be from Lowest Limit + 10 to High - 2.")
        then:' Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to Extreme High - 20.” “Low must be from Extreme Low + 10 to High - 2.” “Extreme High must be from High + 20 to 400.” “Extreme Low must be from 40 to Low - 10.”'
        dailyOverlayReportPage.overnightSectionError("Low must be from Lowest Limit + 10 to High - 2.", "High must be from 80 to Highest Limit - 20.", "Low must be from Lowest Limit + 10 to High - 2.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        then:'Verify glucose units is set to 130 mg/dl 128 mg/dl 250 mg/dl 60 mg/dl'
        dailyOverlayReportPage.enterGlucoseTargetHigh("130")
        log.info('Change the Glucose target range  value to 130 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("128")
        log.info('Change the Glucose target range  value to 128 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("250")
        log.info('Change the Glucose target  Xtreme High value to 250 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("60")
        log.info('Change the Glucose target  Xtreme Low value to 60 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Set following ranges of values. Glucose Target Range')
        and: 'Verify if error are missing'
        dailyOverlayReportPage.glucoseTargetErrorMissing()
        dailyOverlayReportPage.mealsectionErrorMissing()
        dailyOverlayReportPage.breakfastsectionErrorMissing()
        dailyOverlayReportPage.dinnersectionErrorMissing()
        dailyOverlayReportPage.overnightsectionErrorMissing()
        log.info('Verify that the following error are not set.')
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then:'Set following ranges of values. Glucose Target Range: High to 379 mg/dL Low to 139 mg/dL Extreme Glucose Range: High to 399 mg/dL Low to 60 mg/dL Click [Save] button to confirm changes. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("379")
        log.info('Change the Glucose target range  value to 379 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("139")
        log.info('Change the Glucose target range  value to 139 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("399")
        log.info('Change the Glucose target  Xtreme High value to 399 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("60")
        log.info('Change the Glucose target  Xtreme Low value to 60 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then:'Verify that Glucose Target Range High value is set to 379 mg/dL, Glucose Target Low value is set to 139 mg/dL, Extreme Glucose Range Extreme High value is set to 399 mg/dL, and Extreme Glucose Range Extreme Low value is set to 60 mg/dL.'
        dailyOverlayReportPage.glucoseTargetHighValue("379")
        dailyOverlayReportPage.glucoseTargetLowValue("139")
        log.info('Verify that for the default Glucose Target High value is set to 379 and Low value 139')
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("399")
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("60")
        log.info('Verify that Glucose Target  value is set')
        then:'Set following ranges of values. Glucose Target Range: High to 1.2 mg/dL Low to 1.1 mg/dL Extreme Glucose Range: High to 11.2 mg/dL Low to 10.9 mg/dL Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("1.2")
        log.info('Change the Glucose target range  value to 1.2 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("1.1")
        log.info('Change the Glucose target range  value to 1.1 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("11.2")
        log.info('Change the Glucose target  Xtreme High value to 11.2 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("10.9")
        log.info('Change the Glucose target  Xtreme Low value to 10.9 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:'Verify that error messages are shown and value is not set in the General Report Settings section of the Patient Report Settings Page: “High must be a number in xxx format”. “Low must be a number in xxx format.” ”Extreme High must be a number in xxx format.” “Extreme Low must be a number in xxx format.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be a number in xxx format.", "Highest Limit must be a number in xxx format.", "Low must be a number in xxx format.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        then:'Set following ranges of values. Glucose Target Range: High to 0 mg/dL Low to 0 mg/dL Extreme Glucose Range: High to 0 mg/dL Low to 0 mg/dL Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("0")
        log.info('Change the Glucose target range  value to 0 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow("0")
        log.info('Change the Glucose target range  value to 0 mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("0")
        log.info('Change the Glucose target  Xtreme High value to 0 mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("0")
        log.info('Change the Glucose target  Xtreme Low value to 0 mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:'Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 80 to 380.” “Low must be from 50 to 140.” “Extreme High must be from 100 to 400.” “Extreme Low must be from 40 to 130.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 80 to 380.", "Highest Limit must be from 100 to 400.", "Low must be from 50 to 140.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        then:'Set following ranges of values. Glucose Target Range: High value to blank Low value to blank Extreme Glucose Range: High value to blank Low value to blank Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh(" ")
        log.info('Change the Glucose target range  value to N/A mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetLow(" ")
        log.info('Change the Glucose target range  value to N/A mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh(" ")
        log.info('Change the Glucose target  Xtreme High value to N/A mg/dL')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow(" ")
        log.info('Change the Glucose target  Xtreme Low value to N/A mg/dL.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:'Verify that error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be a number in xxx format”. “Low must be a number in xxx format.” ”Extreme High must be a number in xxx format.” “Extreme Low must be a number in xxx format.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be a number in xxx format.", "Highest Limit must be a number in xxx format.", "Low must be a number in xxx format.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
    }

    def 'Verify glucose units is set to mmol/L'() {
        when: 'Glucose units is set to mmol/L.'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:'Set Glucose Units to mmol/L. Click [OK] button on Convert units. Press button [Save]. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        then:' Verify that the Glucose units is set to mmol/L. '
        dailyOverlayReportPage.bgunitMmollValue()
        then:'Set following ranges of values. Glucose Target Range: High to 22.1 mmol/L Low to 7.9 mmol/L Extreme Glucose Range: High to 22.7 mmol/L Low to 7.3 mmol/L Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("22.1")
        log.info('Change the Glucose target range  value to 22.1 mmol/L.')
        dailyOverlayReportPage.enterGlucoseTargetLow("7.9")
        log.info('Change the Glucose target range  value to 7.9 mmol/L.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("22.7 ")
        log.info('Change the Glucose target  Xtreme High value to 22.7 mmol/L.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("7.3")
        log.info('Change the Glucose target  Xtreme Low value to 7.3 mmol/L.')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        then:'Verify that the following error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to 21.1.” “Low must be from 2.8 to 7.8.” “Extreme High must be from 5.6 to 22.2.” “Extreme Low must be from 2.2 to 7.2.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 4.4 to 21.1.", "Highest Limit must be from 5.6 to 22.2.", "Low must be from 2.8 to 7.8.")
        sleep(1500)
        log.info('Click on the [Patient] tab.')
        then:'Set following ranges of values. Glucose Target Range: High to 9.7 mmol/L Low to 4.0 mmol/L Extreme Glucose Range: High to 9.1 mmol/L Low to 3.9 mmol/L Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("9.7")
        log.info('Change the Glucose target range  value to 9.7 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow("4.0")
        log.info('Change the Glucose target range  value to 4.0 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("9.1")
        log.info('Change the Glucose target  Xtreme High value to 9.1 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("3.9")
        log.info('Change the Glucose target  Xtreme Low value to 3.9 mmol/L')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        log.info('Set following ranges of values. Glucose Target Range')
        then:' Verify that the following error messages are shown and entries are not set. in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to Extreme High - 1.1.” “Low must be from Extreme Low + 0.6 to High - 0.1.” “Extreme High must be from High + 1.1 to 22.2.” “Extreme Low must be from 2.2 to Low - 0.6.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be from 4.4 to Highest Limit - 1.1.", "Highest Limit must be from High + 1.1 to 22.2.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:' Verify that the following error messages are shown and entries are not set. in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to Extreme High - 1.1.” “Low must be from Extreme Low + 0.6 to High - 0.1.” “Extreme High must be from High + 1.1 to 22.2.” “Extreme Low must be from 2.2 to Low - 0.6.”'
        dailyOverlayReportPage.breakfastSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:' Verify that the following error messages are shown and entries are not set. in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to Extreme High - 1.1.” “Low must be from Extreme Low + 0.6 to High - 0.1.” “Extreme High must be from High + 1.1 to 22.2.” “Extreme Low must be from 2.2 to Low - 0.6.”'
        dailyOverlayReportPage.lunchSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:' Verify that the following error messages are shown and entries are not set. in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to Extreme High - 1.1.” “Low must be from Extreme Low + 0.6 to High - 0.1.” “Extreme High must be from High + 1.1 to 22.2.” “Extreme Low must be from 2.2 to Low - 0.6.”'
        dailyOverlayReportPage.dinnerSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:' Verify that the following error messages are shown and entries are not set. in the General Report Settings section of the Patient Report Settings Page: “High must be from 4.4 to Extreme High - 1.1.” “Low must be from Extreme Low + 0.6 to High - 0.1.” “Extreme High must be from High + 1.1 to 22.2.” “Extreme Low must be from 2.2 to Low - 0.6.”'
        dailyOverlayReportPage.overnightSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        log.info('Verify that the following error messages are shown and entries are not set in the Analysis Reports Settings section of the Clinic Settings Page: Breakfast Section,Lunch Section,Dinner Section,Overnight Section')
        then:'Set following ranges of values. Glucose Target Range: High to 7.2 mmol/L Low to 7.1 mmol/L Extreme Glucose Range: High to 8.3 mmol/L Low to 5.6 mmol/L Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("7.2")
        log.info('Change the Glucose target range  value to 7.2 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow("7.1")
        log.info('Change the Glucose target range  value to 7.1 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("8.3")
        log.info('Change the Glucose target  Xtreme High value to 8.3 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("5.6")
        log.info('Change the Glucose target  Xtreme Low value to 5.6 mmol/L')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        log.info('Set following ranges of values. Glucose Target Range')
        dailyOverlayReportPage.glucoseTargetErrorMissing()
        then:'Verify that the following error messages are shown and entries are not set in the Analysis Report Settings section of the Patient Report Settings Page: Breakfast Section: “Before Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.” “After Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.'
        dailyOverlayReportPage.breakfastSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:'Verify that the following error messages are shown and entries are not set in the Analysis Report Settings section of the Patient Report Settings Page: Breakfast Section: “Before Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.” “After Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.'
        dailyOverlayReportPage.lunchSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:'Verify that the following error messages are shown and entries are not set in the Analysis Report Settings section of the Patient Report Settings Page: Breakfast Section: “Before Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.” “After Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.'
        dailyOverlayReportPage.dinnerSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        then:'Verify that the following error messages are shown and entries are not set in the Analysis Report Settings section of the Patient Report Settings Page: Breakfast Section: “Before Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.” “After Breakfast Low must be from Extreme Low + 0.6 to High - 0.1.” “High must be from 4.4 to Extreme High - 1.1.'
        dailyOverlayReportPage.overnightSectionError("Low must be from Lowest Limit + 0.6 to High - 0.1.", "High must be from 4.4 to Highest Limit - 1.1.", "Low must be from Lowest Limit + 0.6 to High - 0.1.")
        log.info('Verify that the following error messages are shown and entries are not set in the Analysis Reports Settings section of the Clinic Settings Page: Breakfast Section,Lunch Section,Dinner Section,Overnight Section')
        then:'Set following ranges of values. Glucose Target Range: High to 8.3 mmol/L Low to 3.9 mmol/L Extreme Glucose Range: High to 13.9 mmol/L Low to 2.8 mmol/L Click [Save] button to confirm changes. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("8.3")
        log.info('Change the Glucose target range  value to 8.3 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow("3.9")
        log.info('Change the Glucose target range  value to 3.9 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("13.9")
        log.info('Change the Glucose target  Xtreme High value to 13.9 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("2.8")
        log.info('Change the Glucose target  Xtreme Low value to 2.8 mmol/L')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        log.info('Set following ranges of values. Glucose Target Range')
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then:'Verify that Glucose Target Range High value is set to 8.3 mmol/L, Glucose Target Low value is set to 3.9 mmol/L, Extreme Glucose Range Extreme High value is set to 13.9 mmol/L, Extreme Glucose Range Extreme Low value is set to 2.8 mmol/L.'
        dailyOverlayReportPage.glucoseTargetHighValue("8.3")
        dailyOverlayReportPage.glucoseTargetLowValue("3.9")
        log.info('Verify that for the default Glucose Target High value is set to 8.3 and Low value 3.9')
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("13.9")
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("2.8")
        log.info(' Verify that default Extreme High value is set to 13.9 and Extreme Low value 2.8 ')
        log.info('Verify that Glucose Target value is set')
        and:'Set following ranges of values. Glucose Target Range: High to 5.33 mmol/L Low to 2.64 mmol/L Extreme Glucose Range: High to 7.77 mmol/L Low to 2.33 mmol/L Click [Save] button to confirm changes.'
        dailyOverlayReportPage.enterGlucoseTargetHigh("5.33")
        log.info('Change the Glucose target range  value to 5.33 mol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow("2.64")
        log.info('Change the Glucose target range  value to 2.64 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("7.77")
        log.info('Change the Glucose target  Xtreme High value to 7.77 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("2.33")
        log.info('Change the Glucose target  Xtreme Low value to 2.33 mmol/L')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:'Verify that error messages are shown and entries are not set in the General Report Settings section of the Patient Report Settings Page: “High must be a number in xx.x format”. “Low must be a number in xx.x format.” ”Extreme High must be a number in xx.x format.” “Extreme Low must be a number in xx.x format.”'
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be a number in xx.x format.", "Highest Limit must be a number in xx.x format.", "Low must be a number in xx.x format.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        then:'Verify glucose units is set to 8.33 mmol/L 3.93 mmol/L 9.99 mmol/L 2.83 mmol/L'
        dailyOverlayReportPage.enterGlucoseTargetHigh("8.33")
        log.info('Change the Glucose target range  value to 8.33 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow("3.93")
        log.info('Change the Glucose target range  value to 3.93 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("9.99")
        log.info('Change the Glucose target  Xtreme High value to 9.99 mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("2.83")
        log.info('Change the Glucose target  Xtreme Low value to 2.83 mmol/L')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        log.info('Set following ranges of values. Glucose Target Range')
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be a number in xx.x format.", "Highest Limit must be a number in xx.x format.", "Low must be a number in xx.x format.")
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        log.info('Click on the [Patient] tab.')
        and:'Verify glucose units is set to  N/A mmol/L  N/A mmol/L  N/A mmol/L  N/A mmol/L'
        dailyOverlayReportPage.enterGlucoseTargetHigh(" ")
        log.info('Change the Glucose target range  value to  N/A mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetLow(" ")
        log.info('Change the Glucose target range  value to  N/A mg/dL.')
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh(" ")
        log.info('Change the Glucose target  Xtreme High value to  N/A mmol/L')
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow(" ")
        log.info('Change the Glucose target  Xtreme Low value to  N/A mmol/L.')
        log.info('Set following ranges of values. Glucose Target Range')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        dailyOverlayReportPage.targetRangeSectionSectionError("High must be a number in xx.x format.", "Highest Limit must be a number in xx.x format.", "Low must be a number in xx.x format.")
        log.info('Verify that the following error messages are shown General Report Settings.')
        dailyOverlayReportPage.selectGlucoseUnitsMg()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        dailyOverlayReportPage.bgunitMgdlValue()
        log.info( ' Verify glucose units is set to mg/dL and mmol/L ')
        then:'Set following ranges of values. Glucose Target Range: High to 220 mg/dL Low to 80 mg/dL Extreme Glucose Range: High to 260 mg/dL Low to 60 mg/dL Click [Save] button to confirm changes. Click Edit'
        dailyOverlayReportPage.enterGlucoseTargetHigh("220")
        dailyOverlayReportPage.enterGlucoseTargetLow("80")
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("260")
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("60")
        log.info('Set following ranges of values. Glucose Target Range')
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then:'Verify that Glucose Target Range High value is set to 220 mg/dL, Glucose Target Low value is set to 80 mg/dL, Extreme Glucose Range Extreme High value is set 260 mg/dL, and Extreme Glucose Range Extreme Low value is set to 60 mg/dL.'
        dailyOverlayReportPage.glucoseTargetHighValue("220")
        dailyOverlayReportPage.glucoseTargetLowValue("80")
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("260")
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("60")
        log.info('Verify that Glucose Target  value is set')
        dailyOverlayReportPage.selectGlucoseUnitsMmol()
        dailyOverlayReportPage.convertingPatientSavedBtn()
        dailyOverlayReportPage.bgunitMmollValue()
        then:'Click on the [Patient] tab. Click Edit link to open Patient Report Settings.'
        dailyOverlayReportPage.clinicPatientTab()
        dailyOverlayReportPage.clickEditNewPatientSettings()
        and:'Verify glucose units is set to 12.2 mmol/L 4.4 mmol/L 14.4 mmol/L 3.3 mmol/L'
        dailyOverlayReportPage.enterGlucoseTargetHigh("12.2")
        dailyOverlayReportPage.enterGlucoseTargetLow("4.4")
        dailyOverlayReportPage.enterGlucoseTargetXtremeHigh("14.4")
        dailyOverlayReportPage.enterGlucoseTargetXtremeLow("3.3")
        dailyOverlayReportPage.reportPreferenceSavePrefs()
        sleep(1000)
        then:' Verify that Glucose Target Range High value is set to 12.2 mmol/L, Glucose Target Range Low value is set to 4.4 mmol/L, Extreme Glucose Range Extreme High value is set to 14.4 mmol/L,'
        dailyOverlayReportPage.glucoseTargetHighValue("12.2")
        dailyOverlayReportPage.glucoseTargetLowValue("4.4")
        dailyOverlayReportPage.glucoseTargetXtremeHighValue("14.4")
        dailyOverlayReportPage.glucoseTargetXtremeLowValue("3.3")
        log.info('Verify that Glucose Target value is set')
    }


}

