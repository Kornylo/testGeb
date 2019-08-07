package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.MoveEvaluationPage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SCUI189 - Move Patient study - upload confirmation')
@RegressionTest
@Stepwise
@Screenshot
@Slf4j
class SCUI189Spec extends CareLinkSpec implements ScreenshotTrait{

    static SignInPage signInPage
    static PatientStudyPage patientStudyPage
    static MoveEvaluationPage moveEvaluationPage

    def 'Move Patient Study' () {
        when: 'Open the MMT-7340 software under test'
        signInPage = browsers.to SignInPage
        then: 'Login using an existing username and password'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
        then: 'Record the username and password'
        log.info "Logging in as Akilaa and Test1234"

        when: 'At the home screen click on the new patient button'
        then: 'At the new patient record screen enter valid data for the user input fields'
        then: 'Ensure the patient first name, last name and patient ID contains upper-case letters'
        then: 'Click on the save button'
        then: 'Click on the upload ipro2 button'
        then: 'Perform the necessary steps to upload data with matching BG meter entries'

        when: 'Navigate back to the home screen click on the new patient button'
        then: 'At the new patient record screen enter valid data for the user input fields'
        then: 'Ensure the patient first name, last name and patient ID contains upper-case letters'
        then: 'Click on the save button'
        then: 'Click on the upload ipro2 button'
        then: 'Perform the necessary steps to upload data with matching BG meter entries'

        when: 'Open Patient1'
        then: 'Select move this evaluation option from the dropdown list'
        then:'Confirm that move evaluation window appears'
        then:'Enter the first and last name of patient2 and click on search'
        then: 'Confirm that Patient2 has been found'
        then: 'Select Patient2 and click'
        then: 'Click on move evaluation button'
        then: 'Click on the Home tab.'
        then: 'Now open Patient 1.'
        then: 'Verify that there is Upload iPro2 button in the patient.'
        then: 'Verify that there is no study in the patient.'
        then: 'Click on the Upload iPro2 button.'
        then: 'Verify that the software does not navigate to Upload confirmation screen asking to confirm one more upload for the day for the same patient.'
        then: 'Verify that the software navigates to the Upload Wizard Screen.'
        then: 'Now open patient 2.'
        then: 'Verify that the reports have been moved from Patient 1 to Patient 2.'
        then: 'Click on the Upload iPro2 button.'
        then: 'Verify that the software navigates to Upload confirmation screen asking to confirm one more upload for the day for the same patient.'

    }
    def 'Search Patient' () {
        when: 'A user is logged into Carelink application'

        then: 'User should be able to search for a patient'
        (browsers.page as SignInPage).searchForPatient('Testing3')
    }
    def 'Select Patient' () {
        when: 'A user is logged into Carelink application'
        then: 'User should be able to select a patient'
        signInPage.selectFirstPatient()
    }
    def 'Open Patient' () {
        when: 'A patient is selected'
        then: 'User should be able to open patient'
        signInPage.openPatient()
    }
    def 'Select move this evaluation from the drop down' () {
        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.isUploadiProDisplayed()
        patientStudyPage.moveEvaluation()
    }
    def 'Confirm Move Evaluation Test' (){
        when:
        moveEvaluationPage = browsers.at MoveEvaluationPage
        then:
        moveEvaluationPage.enterMoveStudyFirstName()
        moveEvaluationPage.enterMoveStudyLastName()
        moveEvaluationPage.enterMoveStudyPatientID()
        moveEvaluationPage.clickMoveStudySearch()
        moveEvaluationPage.clickSelectPatient()
        moveEvaluationPage.clickConfirmationDialog()
    }
    def 'Close Patient' () {
        when: 'A patient is opened'
        then: 'User should be able to close patient'
        signInPage.closePatient()
    }
    def 'User Logout' () {
        when:'A user is logged into carelink'

        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
    }
}
