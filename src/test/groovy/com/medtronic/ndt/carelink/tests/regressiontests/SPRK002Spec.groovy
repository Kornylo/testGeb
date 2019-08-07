package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPRK 002 - Log File Upload (status codes check)')
class SPRK002Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage

    static final admin = Calendar.getInstance().format('MMddHHmmss').toString()
    def email = Calendar.getInstance().format('MMMddHHmmss').toString() + "1@medtronic.com"
    def pass = "Test1234@"

    def 'Create Clinic'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        then:
        'Click on the Register Clinic link. Create a new clinic and record the info:\n' +
                'Clinic name'
        and: 'Create a new administrative user and record the info:'
        precondition.registerClinic(admin, LanguagePage.setCountry(), email, pass)
        println('Username: ' + admin)
        println('Password: ' + pass)
    }

    def 'Sign in the MMT-7340'() {
        when: 'Sign in the MMT-7340'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin, pass)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, clinicIdUi, ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder_Data_Upload 1"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.deviceId1,
                ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Event creation 2"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn1, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(1))
    }

    def 'End A Study 2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin, pass, ApiPropertySpak.recorderSn1, true)
    }

    def 'Log File Upload 1'() {
        when: 'Log File Upload 1'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(401, '/support/log', ApiPropertySpak.accountUserName1, '45TYykm !#$%&', json)
    }

    def 'Log File Upload 2'() {
        when: 'Log File Upload 2'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(204, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 3'() {
        when: 'Log File Upload 3'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json logData: '!'
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(204, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 4'() {
        when: 'Log File Upload 4'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, logData: ''
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 5'() {
        when: 'Log File Upload 5'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: '4567878', logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 6'() {
        when: 'Log File Upload 6'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 7'() {
        when: 'Log File Upload 7'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: '', logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 8'() {
        when: 'Log File Upload 8'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json recorderSn: null, logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(204, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 9'() {
        when: 'Log File Upload 9'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(401, '/support/log', '', '', json)
    }

    def 'Log File Upload 10'() {
        when: 'Log File Upload 10'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, logData: null
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Log File Upload 11'() {
        when: 'Log File Upload 11'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: 'abc', logData: ApiPropertySpak.logData
        println(json.toPrettyString())
        ApiRequestsSpak.logFileUpload(400, '/support/log', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Verify created patient on UI'() {
        when: 'Select newly created patient and click on [Open patient] button.'
        patientStudyPage = browsers.at(PatientStudyPage)
        homePage.refreshViaSettings()
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        homePage.selectPatientFromList()
        homePage.openPatient()
        patientStudyPage.patientNameInformationDisplayed()
        patientStudyPage.patientFirstName(ApiPropertySpak.firstName1)
        then: 'Click on [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
        def patientName = patientStudyPage.patientName.text()
        assert patientName.contains(ApiPropertySpak.firstName1) && patientName.contains(ApiPropertySpak.lastName1) && patientName.contains(ApiPropertySpak.patientID1)
    }
}



