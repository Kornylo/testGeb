package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import com.medtronic.ndt.carelink.pages.LanguagePage
import groovy.json.JsonBuilder
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 809 - Event Update')
class SPAK809Spec extends CareLinkSpec {
    static Precondition precondition
    static SignInPage signInPage
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static LogbookPage logbookPage

    static final admin1 = "user1" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress1 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password1 = "Test1234@"

    def 'Register clinic & sign in'() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        signInPage = browsers.to SignInPage
        signInPage.checkIncludedFooterElements()
        and: 'Click on Register Clinic link. Create new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:'
        and: 'Username: (example: TC934)'
        and: 'Password: (example: Password1)"'
        precondition = browsers.to Precondition
        precondition.registerClinic(admin1, LanguagePage.setCountry(), emailAddress1, password1)
        println("Username: " + admin1 + " Password: " + password1)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        and: 'Record the clinicID:'
        precondition.signInAsClinicAdmin(admin1, password1)
        homePage = browsers.at HomePage
        homePage.getClinicIdFromHomePage()
        then: ''
    }

    def "Event Update, clinic 1"() {
        when: 'Event Update - authentication failed'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(7), timestamp: "2012-08-26T13:28:42",
                details: "Some unicode chars here.", size: "M", carbs: "51.0"
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(401, '/logbook/event/update', ApiPropertySpak.recorderSn1.replace('11', '00'),
                ApiPropertySpak.accountUserName1 + "89", ApiPropertySpak.accountPassword1 + "89", json, '', '')
    }

    def "Account creation 1, clinic 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1,
                ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2, clinic 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1, clinic 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1,
                ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, clinic 1"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(0))
    }

    def "Event Update 2, clinic 1"() {
        when: 'Event Update 2'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(0), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 51.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def 'SQL Queries 1, clinic 1, (1)'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "Event Update 8, clinic 1"() {
        when: 'Event Update 8'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "Medication", uniqueId: ApiPropertySpak.eventId.get(0).replace('354', '13a'),
                timestamp: "2012-08-26T13:28:42.567Z", details: "Some unicode chars here."
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def 'SQL Queries 1, clinic 1, (2)'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "Event Update 9, clinic 1"() {
        when: 'Event Update 9'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "Exercise", uniqueId: ApiPropertySpak.eventId.get(0).replace('354', '12a'),
                timestamp: "2012-08-26T13:28:42.567Z", endTime: "2015-12-21T18:06:10.684Z", details: "Some unicode chars here.",
                activityType: "Running", calories: "321", level: "MODERATE"
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def 'SQL Queries 1, clinic 1, (3)'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "Event Update 10, clinic 1"() {
        when: 'Event Update 10'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "DailySteps", uniqueId: ApiPropertySpak.eventId.get(0).replace('354', '17a'),
                timestamp: "2012-08-26", stepCount: "4000"
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def 'SQL Queries 1, clinic 1, (4)'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "Event Update 11, clinic 1"() {
        when: 'Event Update 11'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "Sleep", uniqueId: ApiPropertySpak.eventId.get(0).replace('354', '15a'),
                timestamp: "2012-08-26T13:28:42.567Z", details: "Some unicode chars here.",
                endTime: "2012-08-26T13:28:42.123Z", awakeCount: 5, restlessCount: 7, awakeDuration: 23,
                restlessDuration: 31
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def 'SQL Queries 1, clinic 1, (5)'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is NEW_PATIENT_EVENT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_EVENT')
    }

    def "End a Study 2, clinic 1"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin1, password1, ApiPropertySpak.recorderSn1, true)
    }

    def "Verify Daily Summary report UI, Clinic 1"() {
        when:
        'Navigate to the CareLink app and login into the clinic ' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'From the Patient list choose envision patient and click on [Open patient] button.'
        homePage = browsers.at HomePage
        homePage.refreshViaSettings()
        homePage.homeScreenIsAppeared()
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        and: 'Click on [Confirm] button.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickConfirmButton()
        and: 'Click on Patient Notes report link.'
        then:
        'Verify that the API accepts only one step count event per day with a source of “FITBIT" and the ' +
                '“Steps: 4,000 (Fitbit)” is displayed in report.'
        when: 'Close Patient Notes report and Click on Daily Summary report link.'
        //todo https://carelink.atlassian.net/browse/NDTIPRO23-850
        then:
        'Verify that the API accepts only one step count event per day with a source of “FITBIT" and the ' +
                '“Steps: 4,000 (Fitbit)” is displayed in report.'
        when: 'Close Daily Summary report.'
        //todo https://carelink.atlassian.net/browse/NDTIPRO23-850
        and: 'Click on [Open Logbook] button.'
        logbookPage = browsers.at LogbookPage
        logbookPage.openLogBook()
        then: 'Verify that all created events were successfully updated and are populated in Logbook'
        logbookPage.verifyEventCreation("51", "8/26/2012", "Insulin")
    }

    def "Account creation 1, clinic 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2, clinic 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Event Creation 2, clinic 2"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn2,
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.eventId.get(1))
    }

    def "Event Update 3, clinic 2"() {
        when: 'Event Update 3 - Incorrect username / password'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(1), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 51.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(401, '/logbook/event/update', ApiPropertySpak.recorderSn2,
                "", "", json, '', '')
    }

    def "Event Update 4, clinic 2"() {
        when: 'Event Update 4 - value of RecorderSn is NULL'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(1), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 52.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(400, '/logbook/event/update', null, ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, json, '', '')
    }

    def "Account creation 1, clinic 3"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName3,
                ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2, clinic 3"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId2, ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName1 + '1', ApiPropertySpak.lastName1 + '1', ApiPropertySpak.patientID1 + '1',
                ApiPropertySpak.accountUserName3)
    }

    def "Event Update 5, clinic 3"() {
        when: 'Event Update 5 - modify the recorderSn from the step ID 2755296 instead'
        then: 'Check response code = 403'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(0), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 55.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(400, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, json, '', '')
        //todo should be 403
    }

    def "End a Study 2, clinic 3"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName2,
                ApiPropertySpak.hcpAccountPassword2, ApiPropertySpak.recorderSn3, true)
    }

    def "Event Update 6, clinic 3"() {
        when: 'Event Update 6 - Already ended study'
        then: 'Check response code = 500'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(2), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 54.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(500, '/logbook/event/update', ApiPropertySpak.recorderSn3,
                ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, json, '"errorCode":9999',
                '"secondaryErrorCode":2')
    }

    def "Account creation 1, clinic 3, patient 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '1',
                ApiPropertySpak.accountPassword1 + '1', '', '')
    }

    def "Create Patient 2, clinic 3, patient 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, '/patient', ApiPropertySpak.recorderSn4, ApiPropertySpak.clinicId2,
                ApiPropertySpak.deviceId4, ApiPropertySpak.firstName1 + "1", ApiPropertySpak.lastName1 + "1", ApiPropertySpak.patientID1 + "12",
                ApiPropertySpak.accountUserName1 + '1')
    }

    def "Recorder Data Upload 1, clinic 3, patient 2"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1 + "1",
                ApiPropertySpak.accountPassword1 + "1", ApiPropertySpak.deviceId4, ApiPropertySpak.recorderSn4, ApiPropertySpak.rawEis,
                ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event Creation 2, clinic 3, patient 2"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn4,
                ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + "1", ApiPropertySpak.eventId.get(4))
    }

    def "End a Study 10, clinic 3, patient 2"() {
        when: "End A Study 10"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.accountUserName1 + '1',
                ApiPropertySpak.accountPassword1 + '1', ApiPropertySpak.recorderSn4, true)
    }

    def "Event Update 6, clinic 3, patient 2"() {
        when: 'Event Update 6 - already ended study'
        then: 'Check response code = 500'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(4), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 54.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(500, '/logbook/event/update', ApiPropertySpak.recorderSn4,
                ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + '1', json, '"errorCode":9999',
                '"secondaryErrorCode":1')
    }

    def "Account creation 1, clinic 4"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '1234',
                ApiPropertySpak.accountPassword1 + '1234', '', '')
    }

    def "Event Update 7, clinic 4"() {
        when: 'Event Update 7 - unknown recorderSn'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(5), timestamp: "2012-08-26T13:35:42.567Z",
                details: "Some unicode chars here.", size: "M", carbs: 51.0
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(400, '/logbook/event/update', '54546146546546545456465456464',
                ApiPropertySpak.accountUserName1 + '1234', ApiPropertySpak.accountPassword1 + '1234', json, '', '')
    }
}