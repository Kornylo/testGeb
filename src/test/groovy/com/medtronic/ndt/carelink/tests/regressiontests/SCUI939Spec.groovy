package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI939 - Spark_Flag is False')
class SCUI939Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static HomePage homePage
    static Precondition precondition

    static final admin = "Admin" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Spark_Flag is False'() {
        when: 'Execute ScUI 939 per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below:'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on the Register Clinic link. Create a new clinic (Country of clinic should have Spark_flag = False) and record the info:' +
                '- Clinic Name.'
        and: 'Create a new administrative user and record the info below: Username: (example: TC939) Password: (example: Password1)'
        precondition = browsers.to Precondition
        precondition.registerClinic(admin, "Canada", emailAddress, "Test1234@")
        println("Username: " + admin + " Password: Test1234@")
        and: 'Sign into the MMT-7340 application using the credentials above.'
        precondition.signInAsClinicAdmin(admin, "Test1234@")
        println "Logging in as " + admin
        homePage = browsers.at HomePage
        then: 'Verify the CareLink iPro Therapy Management Software for Diabetes brand logo is displayed in the upper right of the screen.'
        homePage.verifyLogo()
        then: 'Verify there is no column labeled as “Evaluation Start Date” that displays [Patient_Study_Date_Start].'
        then: 'Verify there is no column labeled as “Status” that displays [Patient_Study_Status].'
        homePage.verifyColumnForSparkFalse()
        then: 'End of the test.'
    }
}
