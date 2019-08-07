package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.reports.OverlayByMealReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.SwitchingUtil
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('RPRT396 - Daily Overlay Report ')
@Screenshot
class RADE396Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static LoginPage loginPage
    static OverlayByMealReportPage overlayByMealReportPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static HelpPage helpPage
    static SwitchingUtil switchingUtil
    static DailyOverlayReportPage dailyOverlayReportPage

    def 'Daily Overlay Report' (){
        when: 'Open the MMT-7340 application(CareLink software).'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address'
        log.info(driver.getCurrentUrl())
        when: 'Click on Register Clinic link. Create a new clinic and record the info: Clinic Name.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Click continue on clinic registration'
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
        then: 'Create an administrative user and record the info: Username and password'

        when: 'At the login page'
        loginPage = browsers.at LoginPage
        then: 'Sign into the MMT-7340 application using the above credentials'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        then: 'Select the sample patient from list and click on open button.'
        signInPage.selectedFirstPatient()
        sleep(3000)
        log.info('Selected first patient')
        signInPage.openPatient()
        sleep(3000)
        log.info('Opened first patient')
        when: 'Click on daily overlay report link.'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:'Verify that the Daily Overlay is displayed.'
        dailyOverlayReportPage.clickDailyOverlayReport()
        sleep(3000)
        when: 'Observe the daily overlay report.'
        overlayByMealReportPage = browsers.at OverlayByMealReportPage
        overlayByMealReportPage.backToMainPage()
        then: 'Verify that sensor data summary table immediately follows the sensor graph and no title line.'
        then: 'Verify that the table reports for each day.'
        then: 'Verify the table is labeled with row names: #sensor values, highest, lowest, average, standard dev, MAD%, correlation, #valid calibrations and designation.'
        then: 'Verify that mean absolute difference is labeled as MAD%.'
        then: 'Verify that correlation for all readings in a day is labeled as correlation.'
        then: 'Verify the total number of calibrations is labeled as #valid calibrations.'
        then: 'Verify that there is excursion summary table.'
        then: 'Navigate to daily overlay graph section.'
        then: 'Verify that the graphs have a division at 2.2 mmol/L labelled <=2.2'
        then: 'Verify that the scale range is from 0 to 22.2 mmol/L labelled >=22.2'
        then: 'Verify that major divisions and 2.2 mmol/L division have a light gray, broken grid line running the width of the graph.'
        then: 'Verify that the plotted line is thicker when sensor data value is 2.2 or 22.2'
        then: 'Navigate to patient record screen through edit option.'
        when: 'Change the Glucose units to mg/dL.'
        then: 'After the pop-up displays click OK button and click save button. After the pop-up displays click on YES button.'
        when: 'When click on daily overlay report link.'
        then: 'Navigate to Overlay By Meal graph section.'
        then: 'Verify that the graphs have a division at 40mg/dL labelled <=40'
        then: 'Verify that the scale range is from 0 to 400 mg/dL labelled >=400'
        then: 'Verify that major divisions and 40mg/dL division have a light gray, broken grid line running the width of the graph.'
        then: 'Verify that the plotted line is thicker when sensor data value is 40 or 400'
    }

}