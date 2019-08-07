package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Slf4j
@RegressionTest
@Title('SPAK814 - End A Study')
class SPAK814Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage

    static final admin = "user1" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final email = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static String pass = "Test1234@"
    static final admin1 = "user2" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final email1 = "email2" + Calendar.getInstance().format('MMMddHHmmss').toString() + '@medtronictest.mailinator.com'
    static String pass1 = "Test1234@"

    def "End A Study"() {
        when: 'End A Study'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json ignoreGaps: true, recorderSn: ApiPropertySpak.recorderSn1
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(401,"/study/end",'','',json)
    }
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
    def "Account creation 1 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName1+'1', ApiPropertySpak.accountPassword1+'1', '', '')
    }
    def "Create Patient 1"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1+'1', clinicIdUi, ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1+'1')
    }
    def 'End A Study 2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        def json = new JsonBuilder()
        json ignoreGaps: true, recorderSn: ApiPropertySpak.recorderSn1+'1'
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(200, "/study/end", ApiPropertySpak.accountUserName1+'1', ApiPropertySpak.accountPassword1+'1', json)
    }
    def "SQL Queries 1, UPDATE STUDY value"() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is UPDATE_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1+'1', 'UPDATE_STUDY')
    }
    def 'End A Study 3'() {
        when: 'End A Study 3'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json ignoreGaps: true, recorderSn: ApiPropertySpak.recorderSn1+'2'
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(401, "/study/end", 'bad.username', pass, json)
    }
    def "Account creation 2 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2+'2', ApiPropertySpak.accountPassword2+2, '', '')
    }
    def "Create Patient 2"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        println(clinicIdUi)
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1+'3', clinicIdUi, ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2+'2')
    }
    def 'End A Study 4'() {
        when: 'End A Study 4'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json ignoreGaps: true, recorderSn: '4567878'
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(400, "/study/end", admin, pass, json)
    }
    def "Account creation 3 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3+'3', ApiPropertySpak.accountPassword3+'3', '', '')
    }
    def "Create Patient 3"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn4, clinicIdUi, ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName3+'3')
    }
    def 'End A Study 5'() {
        when: 'End A Study 5'
        then: 'Check response code = 400'
        def json = ApiBodySpak.emptyBody
        ApiRequestsSpak.endAStudy2(400, "/study/end", admin, pass, json)
    }
    def 'Create Clinic 2'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        then:
        'Click on the Register Clinic link. Create a new clinic and record the info:\n' +
                'Clinic name'
        and: 'Create a new administrative user and record the info:'
        precondition.registerClinic(admin1, LanguagePage.setCountry(), email1, pass1)
        println('Username: ' + admin1)
        println('Password: ' + pass1)
    }
    def 'Sign in the second time'() {
        when: 'Sign in the MMT-7340'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin1, pass1)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }
    def "Account creation 4 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }
    def "Create Patient 4"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        println(clinicIdUi)
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, clinicIdUi, ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName3)
    }
    def 'End A Study 6'() {
        when: 'End A Study 4'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json ignoreGaps: true, recorderSn: ApiPropertySpak.recorderSn3
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(400, "/study/end", admin, pass, json)
    }
    def "Account creation 5"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName4,
                ApiPropertySpak.accountPassword4, '', '')
    }
    def "Create Patient 5"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, clinicIdUi, ApiPropertySpak.deviceId4,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName4)
    }
    def "Recorder Data Upload 16.1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        def listSnapshots1 = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listSnapshots2 = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             startOffset: 10101,
                             endOffset  : 20050,
                             rawEis     : ApiPropertySpak.rawEis,]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : "$ApiPropertySpak.deviceId4",
                            status       : 30,
                            recorderSn   : "$ApiPropertySpak.recorderSn2",
                            recorderModel: 'MMT-7781',
                            startTime    : '2016-08-03T02:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots1,listSnapshots2], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName4, ApiPropertySpak.accountPassword4, "/snapshot", payload)
    }
    def 'End A Study 8'() {
        when: 'End A Study 8'
        then: 'Check response code = 200'
        def json = new JsonBuilder()
        json ignoreGaps: false, recorderSn: ApiPropertySpak.recorderSn2
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(200, "/study/end",
                admin1 ,pass1 , json)
    }
    def "Account creation 6"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + '6',
                ApiPropertySpak.accountPassword1 + '6', '', '')
    }
    def "Create Patient 6"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, clinicIdUi, ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1 + '6')
    }
    def "Recorder Data Upload 16.2"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots1 = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                              rawEis     : ApiPropertySpak.rawEis,
                              startOffset: 1,
                              endOffset  : 10050]
        def listSnapshots2 = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                              startOffset: 10101,
                              endOffset  : 20050,
                              rawEis     : ApiPropertySpak.rawEis,]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : "$ApiPropertySpak.deviceId1",
                            status       : 30,
                            recorderSn   : "$ApiPropertySpak.recorderSn1",
                            recorderModel: 'MMT-7781',
                            startTime    : '2016-08-03T02:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots1,listSnapshots2], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName1+'6', ApiPropertySpak.accountPassword1+'6', "/snapshot", payload)

    }
    def 'End A Study 9'() {
        when: 'End A Study 8'
        then: 'Check response code = 200'
        def json = new JsonBuilder()
        json ignoreGaps: false, recorderSn: ApiPropertySpak.recorderSn1,maxOffset:20200
        println(json.toPrettyString())
        ApiRequestsSpak.endAStudy2(200, "/study/end",
                ApiPropertySpak.accountUserName1+ "6", ApiPropertySpak.accountPassword1+ "6", json)

    }
}