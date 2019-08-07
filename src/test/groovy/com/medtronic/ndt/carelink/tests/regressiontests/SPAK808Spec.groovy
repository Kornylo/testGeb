package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.reports.PatientNotesPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 808 - Event Creation')
class SPAK808Spec extends CareLinkSpec {
    static Precondition precondition
    static SignInPage signInPage
    static HomePage homePage
    static LogbookPage logbookPage
    static PatientStudyPage patientStudyPage
    static PatientNotesPage patientNotesPage

    def "Event Creation, Patient 1"() {
        when: 'Event Creation'
        then: 'Check response code = 401'
        ApiRequestsSpak.eventCreation(401, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(0))
    }

    def "Account creation 1, Patient 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1,
                ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2, Patient 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1, Patient 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, Patient 1"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(0))
    }

    def "SQL Queries 1, NEW PATIENT EVENT value"() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "Event Creation 20, Patient 1"() {
        when: 'Event Creation 20'
        then: 'Verify that the API accepts only one step count event per day with a source of “FITBIT"'
        then: 'Check response code = 400'
        ApiRequestsSpak.eventCreation(400, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(1))
    }

    def "End a Study 2, Patient 1"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName1,
                ApiPropertySpak.hcpAccountPassword1, ApiPropertySpak.recorderSn1, true)
    }

    def "Verify logbook on UI, Patient 1"() {
        when:
        'Navigate to the CareLink app and login into the clinic ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition = browsers.to Precondition
        precondition.signInAsClinicAdmin(ApiPropertySpak.hcpAccountUserName1, ApiPropertySpak.hcpAccountPassword1)
        and: 'From the Patient list choose envision patient and click on [Open patient] button.'
        homePage = browsers.at HomePage
        homePage.searchInput(ApiPropertySpak.patientID1)
        sleep(2000)
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        and: 'Click on [Confirm] button.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickConfirmButton()
        and: 'Click on [Open Logbook] button.'
        logbookPage = browsers.at LogbookPage
        logbookPage.openLogBook()
        then: 'Verify that if the source of Insulin event is not provided or not recognized, the source is set to MOBILE.'
        then: 'Verify the if the source of Meal, Medication, Sleep, Notes events are not provided or not recognized, the source is set to MOBILE.'
        then: 'Verify the value is set as indicated by the data from Event_Creation_2.json.'
        logbookPage.verifyEventCreation("Mobile", "8/28/2012", "Insulin")
        logbookPage.clickSignOut()

    }

    def "Account creation 1, Patient 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2, Patient 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2,
                ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Recorder Data Upload 1, Patient 2"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2,
                ApiPropertySpak.deviceId2, ApiPropertySpak.recorderSn2, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 3, Patient 2"() {
        when: 'Event Creation 3 - incorrect username'
        then: 'Check response code = 401'
        ApiRequestsSpak.eventCreation(401, '/logbook', ApiPropertySpak.recorderSn2,
                "incorrectUsername", ApiPropertySpak.accountPassword2, ApiPropertySpak.eventId.get(1))
    }

    def "Event Creation 4, Patient 2"() {
        when: 'Event Creation 4 - incorrect password'
        then: 'Check response code = 401'
        ApiRequestsSpak.eventCreation(401, '/logbook', ApiPropertySpak.recorderSn2,
                ApiPropertySpak.accountUserName2, "incorrectPassword", ApiPropertySpak.eventId.get(1))
    }

    def "Event Creation 5, Patient 2"() {
        when: 'Event Creation 5'
        then: 'Check response code = 204'
        String body = ApiBodySpak.eventCreation21(ApiPropertySpak.recorderSn2, ApiPropertySpak.eventId.get(1), '1000')
        ApiRequestsSpak.eventCreationGeneral(204, '/logbook', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, body)
    }

    def "SQL Queries 1, UPDATE STUDY value"() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is UPDATE_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2, 'UPDATE_STUDY')
    }

    def "Event Creation 21, Patient 2"() {
        when: 'Event Creation 21 - Multiple step count events per day with a source of “MOBILE'
        then: 'Check response code = 204'
        String body = ApiBodySpak.eventCreation21(ApiPropertySpak.recorderSn2, ApiPropertySpak.eventId.get(2), '1500')
        ApiRequestsSpak.eventCreationGeneral(204, '/logbook', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, body)
    }

    def "End a Study 2, Patient 2"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName1,
                ApiPropertySpak.hcpAccountPassword1, ApiPropertySpak.recorderSn2, true)
    }

    def "Verify Patient Notes, Patient 2"() {
        when:
        'Navigate to the CareLink app and login into the clinic ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition = browsers.to Precondition
        precondition.signInAsClinicAdmin(ApiPropertySpak.hcpAccountUserName1, ApiPropertySpak.hcpAccountPassword1)
        and: 'From the Patient list choose envision patient and click on [Open patient] button.'
        homePage = browsers.at HomePage
        sleep(2000)
        homePage.searchInput(ApiPropertySpak.patientID2)
        sleep(2000)
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        and: 'Click on [Confirm] button.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickConfirmButton()
        and: 'Click on Patient Notes report link.'
        patientNotesPage = browsers.at PatientNotesPage
        then: 'Verify the sum of step counts is displayed in report as follows “Steps: 2,500 (Step Counter)”.'
        then: 'Close Patient Notes report and Click on Daily Summary report link.'
        patientNotesPage.verifyPatientNotes("Patient Notes", "Steps: 2,500")
    }

    def "Account creation 1, Patient 3"() {
        when: 'Account creation 1 '
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName3,
                ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2, Patient 3"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId3, ApiPropertySpak.firstName1 + '1',
                ApiPropertySpak.lastName1 + '1', ApiPropertySpak.patientID1 + '1', ApiPropertySpak.accountUserName3)
    }

    def "Event Creation 6, Patient 3"() {
        when: 'Event Creation 6 - Mistake in logbookEvents > Type'
        then: 'Check response code = 400'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn3, ApiPropertySpak.eventId.get(2), ApiPropertySpak.eventId.get(8),
                "Meals", "2012-08-27T13:28:42.567Z", "50.0", "Some unicode chars here.", "Metformin", "Running")
        ApiRequestsSpak.eventCreationGeneral(400, '/logbook', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, body)
    }

    def "Event Creation 7, Patient 3"() {
        when: 'Event Creation 7 - Incorrect format of the date'
        then: 'Check response code = 400'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn3, ApiPropertySpak.eventId.get(2), ApiPropertySpak.eventId.get(8),
                "Meal", "26/8/2012", "50.0", "Some unicode chars here.", "Metformin", "Running")
        ApiRequestsSpak.eventCreationGeneral(400, '/logbook', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, body)
    }

    def "Event Creation 8, Patient 3"() {
        when: 'Event Creation 8 - UniqueId is the same as per Event_Creation_2'
        then: 'Check response code = 400'
        ApiRequestsSpak.eventCreation(400, '/logbook', ApiPropertySpak.recorderSn3,
                ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, ApiPropertySpak.eventId.get(0))
    }

    def "Account creation 1, Patient 4"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '1',
                ApiPropertySpak.accountPassword1 + '1', '', '')
    }

    def "Event Creation 9, Patient 4"() {
        when: 'Event Creation 8 - Unknown recorderSn'
        then: 'Check response code = 400'
        ApiRequestsSpak.eventCreation(400, '/logbook', "232436524242",
                ApiPropertySpak.accountUserName1 + '1', ApiPropertySpak.accountPassword1 + '1', ApiPropertySpak.eventId.get(2))
    }

    def "Create Patient 2, Patient 4"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1.replace('11', '45'), ApiPropertySpak.clinicId1,
                ApiPropertySpak.deviceId4, ApiPropertySpak.firstName1 + '2', ApiPropertySpak.lastName1 + '2',
                ApiPropertySpak.patientID1 + '2', ApiPropertySpak.accountUserName1 + "1")
    }

    def "Event Creation 10, Patient 4"() {
        when: 'Event Creation 10 - already used recorderSn'
        then: 'Check response code = 403'
        ApiRequestsSpak.eventCreation(403, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1 + '1', ApiPropertySpak.accountPassword1 + '1', ApiPropertySpak.eventId.get(6))
    }

    def "End a Study 2, Patient 4"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName1,
                ApiPropertySpak.hcpAccountPassword1, ApiPropertySpak.recorderSn1.replace('11', '45'), true)
    }

    def "Event Creation 11, Patient 4"() {
        when: 'Event Creation 11 - already ended study'
        then: 'Check response code = 500'
        ApiRequestsSpak.eventCreation(500, '/logbook', ApiPropertySpak.recorderSn1.replace('11', '45'),
                ApiPropertySpak.accountUserName1 + '1', ApiPropertySpak.accountPassword1 + '1', ApiPropertySpak.eventId.get(7))
    }

    def "Account creation 1, Patient 5"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '2',
                ApiPropertySpak.accountPassword1 + '2', '', '')
    }

    def "Create Patient 2, Patient 5"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn4, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1.replace('85', '32'),
                ApiPropertySpak.firstName1 + '3', ApiPropertySpak.lastName1 + '3', ApiPropertySpak.patientID1 + '3', ApiPropertySpak.accountUserName1 + "2")
    }

    def "Recorder Data Upload 1, Patient 5"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1 + "2", ApiPropertySpak.accountPassword1 + "2",
                ApiPropertySpak.deviceId1.replace('85', '32'), ApiPropertySpak.recorderSn4, ApiPropertySpak.rawEis,
                ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, Patient 5"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn4,
                ApiPropertySpak.accountUserName1 + "2", ApiPropertySpak.accountPassword1 + "2", ApiPropertySpak.eventId.get(4))
    }

    def "End a Study 10, Patient 5"() {
        when: "End A Study 10"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.accountUserName1 + '2',
                ApiPropertySpak.accountPassword1 + '2', ApiPropertySpak.recorderSn4, true)
    }

    def "Event Creation 11, Patient 5"() {
        when: 'Event Creation 11 - already ended study'
        then: 'Check response code = 500'
        ApiRequestsSpak.eventCreation(500, '/logbook', ApiPropertySpak.recorderSn4,
                ApiPropertySpak.accountUserName1 + '2', ApiPropertySpak.accountPassword1 + '2', ApiPropertySpak.eventId.get(5))
    }

    def "Account creation 1, Patient 6"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', '', '')
    }

    def "Create Patient 2, Patient 6"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1.replace('11', '35'), ApiPropertySpak.clinicId1,
                ApiPropertySpak.deviceId1.replace('85', '36'), ApiPropertySpak.firstName1 + '4',
                ApiPropertySpak.lastName1 + '4', ApiPropertySpak.patientID1 + '4', ApiPropertySpak.accountUserName1 + "3")
    }

    def "Event Creation 12, Patient 6"() {
        when: 'Event Creation 12 - uniqueId is an empty string'
        then: 'Check response code = 400'
        ApiRequestsSpak.eventCreation(400, '/logbook', ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.accountUserName1 + '3', ApiPropertySpak.accountPassword1 + '3', "")
    }

    def "Event Creation 13, Patient 6"() {
        when: 'Event Creation 13 - carbs property has non-numeric value'
        then: 'Check response code = 400'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.eventId.get(2), ApiPropertySpak.eventId.get(6), "Meal", "2012-08-27T13:28:42.567Z",
                "\"Some unicode chars here.\"", "Some unicode chars here.", "Metformin", "Running")
        ApiRequestsSpak.eventCreationGeneral(400, '/logbook', ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', body)
    }

    def "Event Creation 15, Patient 6"() {
        when: 'Event Creation 15 - In any two event types place the same unique ID.'
        then: 'Check response code = 400'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.eventId.get(5), ApiPropertySpak.eventId.get(5), "Meal", "2012-08-27T13:28:42.567Z",
                "50.0", "Some unicode chars here.", "Metformin", "Running")
        ApiRequestsSpak.eventCreationGeneral(400, '/logbook', ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', body)
    }

    def "Event Creation 17, Patient 6"() {
        when: 'Event Creation 17 - 301 characters in the detail type property'
        then: 'Check response code = 500'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.eventId.get(8), ApiPropertySpak.eventId.get(4), "Meal", "2012-08-27T13:28:42.567Z",
                "50.0", RandomStringUtils.randomAlphanumeric(301), "Metformin", "Running")
        ApiRequestsSpak.eventCreationGeneral(500, '/logbook', ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', body)
    }

    def "Event Creation 18, Patient 6"() {
        when: 'Event Creation 18 - 81 characters in the name type property.'
        then: 'Check response code = 500'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.eventId.get(8), ApiPropertySpak.eventId.get(4), "Meal", "2012-08-27T13:28:42.567Z",
                "50.0", "Some unicode chars here.", RandomStringUtils.randomAlphanumeric(81), "Running")
        ApiRequestsSpak.eventCreationGeneral(500, '/logbook', ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', body)
    }

    def "Event Creation 19, Patient 6"() {
        when: 'Event Creation 19 - 141 characters in the activity type property.'
        then: 'Check response code = 500'
        String body = ApiBodySpak.eventCreationGeneral(ApiPropertySpak.recorderSn1.replace('11', '35'),
                ApiPropertySpak.eventId.get(8), ApiPropertySpak.eventId.get(4), "Meal", "2012-08-27T13:28:42.567Z",
                "50.0", "Some unicode chars here.", "Metformin", RandomStringUtils.randomAlphanumeric(141))
        ApiRequestsSpak.eventCreationGeneral(500, '/logbook', ApiPropertySpak.accountUserName1 + '3',
                ApiPropertySpak.accountPassword1 + '3', body)
    }
}