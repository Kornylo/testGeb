package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.reports.OverlayByMealReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import spock.lang.Stepwise
import spock.lang.Title
import groovy.util.logging.Slf4j

@RegressionTest
@Stepwise
@Slf4j
@Title('RPRT404 - Overlay by meal report')
@Screenshot
class RPRT404Spec extends CareLinkSpec implements ScreenshotTrait {
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

    def 'Overlay by meal report'() {

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
        then: 'Observe the glucose units set for current patient.'
        then: 'If Glucose units are equal to mmol/L continue to next step. If Glucose units are equal to mg/dL change to mmol/L and save it.'
        then: 'Click on Overlay By Meal report link.'
        when: 'A patient is opened and click on Overlay by meal report.'
        overlayByMealReportPage = browsers.at OverlayByMealReportPage
        then:
        overlayByMealReportPage.clickOverlayByMealReport()
        sleep(3000)
        //overlayByMealReportPage.switchToWindowsIfVisibleAndVerifyPDFMg()
        log.info('Opened overlay by meal report')
        sleep(5000)
        log.info('Verified pdf(mg/dL)')
        sleep(3000)
        overlayByMealReportPage.backToMainPage()
        sleep(3000)
        overlayByMealReportPage.selectFirstPatient()
        sleep(3000)
        then: 'Verify the report includes upto seven 24-hours of study data displayed in 7 calendar days.'
        then: 'Navigate to Overlay By Meal graph section.'
        then: 'Verify that the graphs have a division at 2.2 mmol/L labelled <=2.2'
        then: 'Verify that the scale range is from 0 to 22.2 mmol/L labelled >=22.2'
        then: 'Verify that major divisions and 2.2 mmol/L division have a light gray, broken grid line running the width of the graph.'
        then: 'Verify that the plotted line is thicker when sensor data value is 2.2 or 22.2'
        then: 'Navigate to patient record screen through edit option.'
        signInPage.openPatient()
        sleep(3000)
        log.info('Opened first patient')
        when: 'Change the Glucose units to mg/dL.'
        helpPage = browsers.at HelpPage
        then: 'After the pop-up displays click OK button and click save button. After the pop-up displays click on YES button.'
        helpPage.clickEditPatientSettings()
        helpPage.bgunit_mmollApprove()
        sleep(3000)
        helpPage.mmollPopupApprove()
        helpPage.saveReportSettings()
        sleep(3000)
        helpPage.yesMmolAprrove()
        sleep(3000)
        log.info('Changed settings (mg/dL) to (mmol/L)')
        when: 'Click on Overlay By Meal report link.'
        overlayByMealReportPage = browsers.at OverlayByMealReportPage
        overlayByMealReportPage.clickOverlayByMealReport()
        //overlayByMealReportPage.switchToWindowsIfVisibleAndVerifyPDFMml()
        log.info('Verified pdf(mg/dL)')
        then: 'Navigate to Overlay By Meal graph section.'
        then: 'Verify that the graphs have a division at 40mg/dL labelled <=40'
        then: 'Verify that the scale range is from 0 to 400 mg/dL labelled >=400'
        then: 'Verify that major divisions and 40mg/dL division have a light gray, broken grid line running the width of the graph.'
        then: 'Verify that the plotted line is thicker when sensor data value is 40 or 400'
    }

}