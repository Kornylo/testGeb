package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 810 - Event Deletion')
class SPAK810Spec extends CareLinkSpec {
    static Precondition precondition
    static SignInPage signInPage
    static HomePage homePage
    static LogbookPage logbookPage
    static PatientStudyPage patientStudyPage

    def "Account creation 1, Clinic 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1,
                ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2, Clinic 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1, Clinic 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, Clinic 1"() {
        when: 'Event Creation 2'
        and: 'Note: Change the uniqueId of the event in Event_Deletion.json as per Event_Creation_2.json.'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(0))
    }

    def "Event Deletion, Clinic 1"() {
        when: 'Event Deletion'
        then: 'Check response code = 204'
        String eventId = ApiPropertySpak.eventId.get(0)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(204, '/logbook/' + ApiPropertySpak.recorderSn1 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "SQL Queries 1"() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is DELETE_PATIENT_EVENT value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'DELETE_PATIENT_EVENT')
    }

    def "End a Study 2, Clinic 1"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName1,
                ApiPropertySpak.hcpAccountPassword1, ApiPropertySpak.recorderSn1, true)
    }

    def "Verify events on UI, Clinic 1"() {
        when: 'Navigate to the CareLink app and login into the clinic ' +
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
        then: 'Verify if all events were successfully deleted and are not populated in Logbook.'
        logbookPage.verifyEventsDeleted()
    }

    def "Event Deletion 1, Clinic 1"() {
        when: 'Event Deletion 1 - incorrect username'
        then: 'Check response code = 401'
        String eventId = ApiPropertySpak.eventId.get(0)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(401, '/logbook/' + ApiPropertySpak.recorderSn1 + '/event/' + eventId + '/delete',
                'rahatLokum', ApiPropertySpak.accountPassword1, '', '')
    }

    def "Event Deletion 2, Clinic 1"() {
        when: 'Event Deletion 2 - incorrect password'
        then: 'Check response code = 401'
        String eventId = ApiPropertySpak.eventId.get(0)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(401, '/logbook/' + ApiPropertySpak.recorderSn1 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1, "incorrectPass", '', '')
    }

    def "Account creation 1, Clinic 1 "() {
        when: 'Account creation 1 '
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2, Clinic 1 "() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2,
                ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Event Deletion 3, Clinic 1 "() {
        when: 'Event Deletion 3 - incorrect recorderSn'
        then: 'Check response code = 400'
        String eventId = ApiPropertySpak.eventId.get(0)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(400, '/logbook/' + '17786uigjk' + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "End a Study 2, Clinic 1 "() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName1,
                ApiPropertySpak.hcpAccountPassword1, ApiPropertySpak.recorderSn2, true)
    }

    def "Event Deletion 4, Clinic 1 "() {
        when: 'Event Deletion 4 - study completed by hcp account'
        then: 'Check response code = 500'
        String eventId = ApiPropertySpak.eventId.get(1)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(500, '/logbook/' + ApiPropertySpak.recorderSn2 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2,'"errorCode":9999', '"secondaryErrorCode":2')
    }

    def "Account creation 1, Clinic 2"() {
        when: 'Account creation 1 '
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2, Clinic 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId2, ApiPropertySpak.deviceId3, ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName3)
    }

    def "Recorder Data Upload 1, Clinic 2"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3,
                ApiPropertySpak.deviceId3, ApiPropertySpak.recorderSn3, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, Clinic 2"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn3,
                ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, ApiPropertySpak.eventId.get(1))
    }

    def "End a Study 10, Clinic 2"() {
        when: "End A Study 10"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.accountUserName3,
                ApiPropertySpak.accountPassword3, ApiPropertySpak.recorderSn3, true)
    }

    def "Event Deletion 4, Clinic 2"() {
        when: 'Event Deletion 4 - study completed by patient account'
        then: 'Check response code = 500'
        String eventId = ApiPropertySpak.eventId.get(1)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(500, '/logbook/' + ApiPropertySpak.recorderSn3 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '"errorCode":9999', '"secondaryErrorCode":1')
    }

    def "Account creation 1, Clinic 3"() {
        when: 'Account creation 1 '
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + "1", '', '')
    }

    def "Create Patient 2, Clinic 3"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn4, ApiPropertySpak.clinicId3, ApiPropertySpak.deviceId4, ApiPropertySpak.firstName2,
                ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1 + "1")
    }

    def "Event Creation 2, Clinic 3"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn4,
                ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + "1", ApiPropertySpak.eventId.get(2))
    }

    def "Event Deletion 5, Clinic 3"() {
        when: 'Event Deletion 5 - uniqueID does not exist'
        then: 'Check response code = 400'
        String eventId = '4d089354-7f4c-4c52-851e-418ca0fd0500'
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(400, '/logbook/' + ApiPropertySpak.recorderSn4 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + "1", '', '')
    }

    def "Account creation 1, Clinic 4"() {
        when: 'Account creation 1 '
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1 + "2", ApiPropertySpak.accountPassword1 + "2", '', '')
    }

    def "Event Deletion 6, Clinic 4"() {
        when: 'Event Deletion 6 - Unknown recorderSn'
        then: 'Check response code = 400'
        String eventId = ApiPropertySpak.eventId.get(2)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(400, '/logbook/' + '45rfdfdd44556' + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1 + "2", ApiPropertySpak.accountPassword1 + "2", '', '')
    }
}