package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPRK 001 - Get Logbook events')
class SPRK001Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage

    static final admin = Calendar.getInstance().format('MMddHHmmss').toString()
    def email = Calendar.getInstance().format('MMMddHHmmss').toString() + "1@medtronic.com"
    def pass = "Test1234@"

    def 'Register new Clinic'() {
        when: 'Open the browser as specified in the ETP.'
        precondition = browsers.to(Precondition)
        and: 'Open the MMT-7340 application (CareLink Software).'
        and: 'Record the server URL address.'
        then: 'Click on the Register Clinic link.'
        and: 'Create a new clinic and record the info: - Clinic name'
        and:
        'Create a new administrative user and record the info:' +
                '- Username:' +
                '- Password'
        precondition.registerClinic(admin, LanguagePage.setCountry(), email, pass)
        println('Username: ' + admin)
        println('Password: ' + pass)
    }

    def 'Sign in to application'() {
        when: 'Sign into the MMT-7340 application using the credentials from step above.'
        precondition.signInAsClinicAdmin(admin, pass)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        and: 'If 2FA Instruction Screen titled Two-Step Verification appears, click on Postpone link.'
        then: 'Record the clinic Id'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Get LogBook 1"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'SQL Queries 1'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created:\n' +
                'Check the columns in the “Query Result” window: there is a record with GET_PATIENT_EVENTS value in the SUB_TYPE column '
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_PATIENT_EVENTS')
    }

    def "Event Creation 1"() {
        when: 'Event Creation'
        then: 'Check response code = 204'
        def json = [type     : 'Meal',
                    uniqueId : ApiPropertySpak.eventId.get(0),
                    timestamp: '2012-08-26T13:28:42',
                    details  : 'Some unicode chars here.',
                    size     : 'M',
                    carbs    : 50.0]
        def payload = new JsonBuilder(logbookEvents: [json], recorderSn: ApiPropertySpak.recorderSn1)

        println(payload.toPrettyString())
        ApiRequestsSpak.eventCreationGeneral(204, '/logbook', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, payload)
    }

    def "Get LogBook 1.2"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(0), details: 'Some unicode chars here.', size: 'M', carbs: 50.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 0)
    }

    def "Event Update, Clinic 1"() {
        when: 'Event Update - authentication failed'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json type: "Meal", uniqueId: ApiPropertySpak.eventId.get(0), timestamp: "2012-08-26T13:28:42", details: "Updated event", size: "L", carbs: "51.0"
        println(json.toPrettyString())
        ApiRequestsSpak.eventUpdate(204, '/logbook/event/update', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json, '', '')
    }

    def "Get LogBook 1.3"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(0), details: 'Updated event', size: 'L', carbs: 51.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 0)
    }

    def "Event Creation 1.2"() {
        when: 'Event Creation'
        then: 'Check response code = 204'
        def json = [type     : 'Meal',
                    uniqueId : ApiPropertySpak.eventId.get(1),
                    timestamp: '2012-08-28T13:28:42',
                    details  : 'Some unicode chars here.',
                    size     : 'M',
                    carbs    : 50.0]
        def payload = new JsonBuilder(logbookEvents: [json], recorderSn: ApiPropertySpak.recorderSn1)
        println(payload.toPrettyString())
        ApiRequestsSpak.eventCreationGeneral(204, '/logbook', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, payload)
    }

    def "Get LogBook 1.4"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(1), details: 'Some unicode chars here.', size: 'M', carbs: 50.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 1)
    }

    def "Event Deletion, Clinic 1"() {
        when: 'Event Deletion'
        then: 'Check response code = 204'
        String eventId = ApiPropertySpak.eventId.get(1)
        println("Deleted event Id: " + eventId)
        ApiRequestsSpak.eventDeletion(204, '/logbook/' + ApiPropertySpak.recorderSn1 + '/event/' + eventId + '/delete',
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Get LogBook 1.5"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(0), details: 'Updated event', size: 'L', carbs: 51.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 0)
    }

    def 'End A Study 2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin, pass, ApiPropertySpak.recorderSn1, true)
    }

    def "Get LogBook 1.6"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(0), details: 'Updated event', size: 'L', carbs: 51.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 0)
    }

    def "Get LogBook 2"() {
        when: 'Get LogBook 2'
        then: 'Check response code = 401'
        ApiRequestsSpak.getLogBook(401, "/logbook/${ApiPropertySpak.recorderSn1}", '', '', '', '')
    }

    def "Get LogBook 3"() {
        when: 'Get LogBook 3'
        then: 'Check response code = 401'
        ApiRequestsSpak.getLogBook(401, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Account creation 1.2 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Get LogBook 4"() {
        when: 'Get LogBook 4'
        then: 'Check response code = 400'
        ApiRequestsSpak.getLogBook(400, "/logbook/${ApiPropertySpak.recorderSn2}", ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Account creation 1.3 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2.2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, '3' + ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName3)
    }

    def "Event Creation 1.3"() {
        when: 'Event Creation'
        then: 'Check response code = 204'
        def json = [type     : 'Meal',
                    uniqueId : ApiPropertySpak.eventId.get(2),
                    timestamp: '2012-08-28T13:28:42',
                    details  : 'Event created from API',
                    size     : 'L',
                    carbs    : 55.0]
        def payload = new JsonBuilder(logbookEvents: [json], recorderSn: ApiPropertySpak.recorderSn3)
        println(payload.toPrettyString())
        ApiRequestsSpak.eventCreationGeneral(204, '/logbook', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, payload)
    }

    def "Get LogBook 1.7"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', uniqueId: ApiPropertySpak.eventId.get(2), details: 'Event created from API', size: 'L', carbs: 55.0]
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn3}", ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, dataMap, 0)
    }
}
