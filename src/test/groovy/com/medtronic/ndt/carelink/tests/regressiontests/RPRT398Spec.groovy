package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.reports.PatientNotesPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Screenshot
@Title('RPRT398 - Patient Notes Report')
class RPRT398Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static LoginPage loginPage
    static PatientNotesPage patientNotesPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'Patient notes report'() {

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

        then: 'Open logbook and enter three different records, click the enter button and save changes.'
        when: 'Click on patient notes report link.'
        patientNotesPage = browsers.at PatientNotesPage
        patientNotesPage.clickPatientNotesReport()
        sleep(10000)
        patientNotesPage.backToMainPage()
        then: 'Verify the system provides the patient notes report.'
        then: 'Observe patient notes report.'
        then: 'Verify the report title is Patient Notes.'
        then: 'Verify that multiple pages of the patient notes report displays the date range for data currently displayed.'
        then: 'Verify that each row in the report represents a unique timestamp, and all displays all events recorded.'
        then: 'Verify that only timestamps that have at least one event element displayed in the report. '
        then: 'Verify the events column includes for meal event'
        then: 'Verify the events column includes for medication event'
        then: 'Verify the events column includes for insulin event'
        then: 'Verify the events column includes for exercise event'
        then: 'Verify the events column includes for notes event'
        then: 'Verify the events column includes for BG event'
        then: 'Verify the events column includes for BG calibration event'
        then: 'Verify if a row contains multiple events with patient notes, each events notes text begin on a new line.'


    }

}