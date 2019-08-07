package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.data.enums.InsulinType
import com.medtronic.ndt.carelink.data.enums.LogbookEventType
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.AddLogbookEventsPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.data.enums.MealSize
import com.medtronic.ndt.carelink.data.enums.ExerciseIntensity
import com.medtronic.ndt.carelink.data.logbookevents.*
import com.medtronic.ndt.carelink.pages.logbookevents.AddLogbookEventDialogModule
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title
import spock.lang.Unroll
import java.time.LocalDateTime

@Title('ScUI935 - Logbook common iPro')
@RegressionTest
@Unroll
@Screenshot
@Stepwise
@Slf4j
class SCUI935Spec extends CareLinkSpec implements ScreenshotTrait {

    static SignInPage signInPage
    static LoginPage loginPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def setupSpec() {
        go()
        (browser.at(SignInPage))
    }
    def 'Logbook Common iPro'() {
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
        then: 'Click on open logbook button to navigate into logbook.'
        then: 'Verify the logbook data screen is provided with logbook editor.'
        then: 'Click on add button.'
        then: 'Verify that logbook edit window displays the title add new record.'
        then: 'Verify that Date field is displayed with full year.'
        then: 'Select any day from date field.'
        then: 'Verify that time input field is displayed.'
        then: 'Enter value into time input field.'
        then: 'Verify there is a field named BG with corresponding BG icon.'
        then: 'Enter the value in for BG input field.'
        then: 'Verify there is a field named meal with corresponding meal icon.'
        then: 'Verify the meal field has entry radio buttons which allow user to select unknown, small, medium and large.'
        then: 'Click on one of the meal entry radio buttons to select meal size and enter value into carbs data field.'
        then: 'Verify that there is a field named carbs with carbs unit.'
        then: 'Verify there is a insulin field with corresponding insulin icon.'
        then: 'Verify that the logbook edit window has insulin entry radio buttons which allow user to select insulin type.'
        then: 'Select one of the insulin type and enter in a value.'
        then: 'Verify there is a Meds field with corresponding Meds icon.'
        then: 'Verify the user is able to select and unselect meds checkbox.'
        then: 'Verify there is a exercise field with corresponding exercise icon.'
        then: 'Verify that the exercise duration data field and allows user to enter hours and minutes of duration.'
        then: 'Verify that there is a field named sleep.'
        then: 'Verify that there is a field labeled wake up with corresponding wake up icon.'
        then: 'Verify there is a patient study notes field labeled as notes with corresponding notes icon'
        then: 'Start typing into notes field.'
        then: 'Verify that the checkbox is checked when user starts typing in notes field.'
        then: 'Uncheck the notes checkbox.'
        then: 'Verify the notes field gets cleared when they click on uncheck notes checkbox.'
        then: 'Click on enter button to save the record.'
        then: 'Add all the different events.'
        then: 'Verify the records are saved correctly to logbook table data.'
        then: 'Verify that the save button is labeled as done.'
        then: 'Verify that remove button is labeled as remove.'
        then: 'Verify remove button in remove entry confirmation window.'
        then: 'Click on done button.'

    }

    def 'Open Logbook'() {
        when: 'Open logbook'
        LogbookPage logbookPage = browser.at LogbookPage
        then: 'I am on logbook page'
        logbookPage.openLogBook()
        browser.at AddLogbookEventsPage
    }

    def 'Log #eventType event'() {
        when: 'Add logbook is clicked'
        AddLogbookEventDialogModule addLogbookEventPopupModule = (browser.page as AddLogbookEventsPage).clickAddLogbook()
        screenshot("Events", "LogbookEvents")
        then: 'An event can be logged'
        addLogbookEventPopupModule.logEvent(event)
        browser.at AddLogbookEventsPage
        where:
        event                                                                                                              | eventType
        new MealEvent(eventTime: LocalDateTime.now().minusHours(2).minusMinutes(15), mealSize: MealSize.MEDIUM, carbs: 15) | LogbookEventType.MEAL
        new ExerciseEvent(eventTime: LocalDateTime.now(), intensity: ExerciseIntensity.INTENSE)                            | LogbookEventType.EXERCISE
        new InsulinEvent(eventTime: LocalDateTime.now().minusMinutes(55), insulinType: InsulinType.BASAL, units: 3)        | LogbookEventType.INSULIN
        new MedicineEvent(eventTime: LocalDateTime.now().plusDays(1).plusMinutes(2))                                       | LogbookEventType.MEDICINE
        new NotesEvent(eventTime: LocalDateTime.now(), notes: "This is a notes event")                                     | LogbookEventType.NOTES
    }

}
