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
@Title('SPAK 813 - Recorder Data Upload')
class SPAK813Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static final admin = Calendar.getInstance().format('MMddHHmmss').toString().toLowerCase()
    static final email = admin + '@ap1.com'
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

    def 'Sign in the Application'() {
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
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : "$ApiPropertySpak.deviceId1",
                            status       : 30,
                            recorderSn   : "$ApiPropertySpak.recorderSn1",
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, "/snapshot", payload)
    }

    def 'SQL Queries'() {
        when: 'Run the statement.'
        then: '''In the “Query Result” window ensure that AuditEvent is created:
Check the columns in the “Query Result” window.
Also in the “DETAIL” column for the "DEVICE_SNAPSHOT_PART" SUB_TYPE there is comment “Creating snapshot part for recorder model MMT-7781, serial number xyz” (where xyz is replaced with "recorderSn" from Recorder_Data_Upload_1.json).'''
        DataBaseRequestsUtil.deviceSnapshotPart(ApiPropertySpak.accountUserName1, ApiPropertySpak.recorderSn1)
    }

    def "Recorder_Data_Upload 2 - Wrong Credentials"() {
        when: 'Data Uploading'
        then: 'Check response code = 401'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId1,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn1,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(401, 'abc', 'def', "/snapshot", payload)
    }

    def "Recorder_Data_Upload 3 - Missing snapshots property"() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId1,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn1,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 4 - Missing rawIsig property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId1,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn1,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, "/snapshot", payload)
    }

    def "Account creation 1.2 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2.2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId2, ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Recorder_Data_Upload 5 - Missing rawEis property."() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn2,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 6 - Missing startOffset property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig  : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis   : ApiPropertySpak.rawEis,
                             endOffset: 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn2,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 7 - Missing endOffset property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn2,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 8 - Missing metadata property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def payload = new JsonBuilder(snapshots: [listSnapshots])
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 9 - Missing recorderSn property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 10 - Missing recorderModel property."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion: '1.0',
                            deviceId  : ApiPropertySpak.deviceId2,
                            status    : 30,
                            recorderSn: ApiPropertySpak.recorderSn2,
                            startTime : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Recorder_Data_Upload 11 - Invalid recorderSn."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderSn   : '12616841651561165156',
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def 'End A Study 2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName2, ApiPropertySpak.hcpAccountPassword2, ApiPropertySpak.recorderSn2, true)
    }

    def "Recorder_Data_Upload 12 - Already ended study."() {
        when: 'Data Uploading'
        then: 'Check response code = 500'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId2,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn2,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(500, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Account creation 1.3 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2.3 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId3, ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName2 + '3', ApiPropertySpak.lastName2 + '3', ApiPropertySpak.patientID2 + '3', ApiPropertySpak.accountUserName3)
    }

    def "Recorder_Data_Upload 1 - Missing rawEis property."() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId3,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn3,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, "/snapshot", payload)
    }

    def 'End A Study 2.2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.hcpAccountUserName3, ApiPropertySpak.hcpAccountPassword3, ApiPropertySpak.recorderSn3, true)
    }

    def "Recorder_Data_Upload 12.2 - Already ended study."() {
        when: 'Data Uploading'
        then: 'Check response code = 500'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId3,
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn3,
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(500, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, "/snapshot", payload)
    }

    def "Account creation 1.4 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3 + '4', ApiPropertySpak.accountPassword3 + '4', '', '')
    }

    def "Create Patient 2.4 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3 + '4', ApiPropertySpak.clinicId3, ApiPropertySpak.deviceId4,
                ApiPropertySpak.firstName2 + '4', ApiPropertySpak.lastName2 + '4', ApiPropertySpak.patientID2 + '4', ApiPropertySpak.accountUserName3 + '4')
    }

    def "Account creation 1.5 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3 + '5', ApiPropertySpak.accountPassword3 + '5', '', '')
    }

    def "Create Patient 2.5 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3 + '5', ApiPropertySpak.clinicId3, ApiPropertySpak.deviceId4.replace('44', '55'),
                ApiPropertySpak.firstName2 + '5', ApiPropertySpak.lastName2 + '5', ApiPropertySpak.patientID2 + '5', ApiPropertySpak.accountUserName3 + '5')
    }

    def "Recorder_Data_Upload 13 - Authenticated patient account is not associated with the recorder serial number in the request."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId4 + '1',
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn3 + '4',
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, ApiPropertySpak.accountUserName3 + '4', ApiPropertySpak.accountPassword3 + '4', "/snapshot", payload)
    }

    def "Account creation 1.6 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3 + '6', ApiPropertySpak.accountPassword3 + '6', '', '')
    }

    def "Create Patient 2.6 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3 + '6', clinicIdUi, ApiPropertySpak.deviceId4.replace('44', '66'),
                ApiPropertySpak.firstName2 + '6', ApiPropertySpak.lastName2 + '6', ApiPropertySpak.patientID2 + '6', ApiPropertySpak.accountUserName3 + '6')
    }

    def "Recorder_Data_Upload 14 - Valid HCP account for the clinic the patient belongs to."() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId4.replace('44', '66'),
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn3 + '6',
                            recorderModel: 'MMT-7781',
                            startTime    : '2016-08-03T02:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, admin, pass, "/snapshot", payload)
    }

    def 'SQL Queries 2'() {
        when: 'Run the statement.'
        then: '''In the “Query Result” window ensure that AuditEvent is created:
Check the columns in the “Query Result” window.
Also in the “DETAIL” column for the "DEVICE_SNAPSHOT_PART" SUB_TYPE there is comment “Creating snapshot part for recorder model MMT-7781, serial number xyz” (where xyz is replaced with "recorderSn" from Recorder_Data_Upload_1.json).'''
        DataBaseRequestsUtil.deviceSnapshotPart(admin, ApiPropertySpak.recorderSn3 + '6')
    }

    def 'Create Clinic 2'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        homePage.clickSignOutButton()
        waitFor { browser.getCurrentUrl().contains('login') }
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        then:
        'Click on the Register Clinic link. Create a new clinic and record the info:\n' +
                'Clinic name'
        and: 'Create a new administrative user and record the info:'
        precondition.registerClinic(admin+'2', LanguagePage.setCountry(), '2'+email, pass+'2')
        println('Username: ' + admin+'2')
        println('Password: ' + pass+'2')
    }

    def 'Sign in the Application 2'() {
        when: 'Sign in the MMT-7340'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin+'2', pass+'2')
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1.7 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName3 + '7', ApiPropertySpak.accountPassword3 + '7', '', '')
    }

    def "Create Patient 2.7 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3 + '7', clinicIdUi, ApiPropertySpak.deviceId4.replace('44', '77'),
                ApiPropertySpak.firstName2 + '7', ApiPropertySpak.lastName2 + '7', ApiPropertySpak.patientID2 + '7', ApiPropertySpak.accountUserName3 + '7')
    }

    def "Recorder Data Upload 15 - Authenticated HCP account does not have access to the patient."() {
        when: 'Data Uploading'
        then: 'Check response code = 400'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : ApiPropertySpak.deviceId4.replace('44', '77'),
                            status       : 30,
                            recorderSn   : ApiPropertySpak.recorderSn1,
                            recorderModel: 'MMT-7781',
                            startTime    : '2016-08-03T02:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(400, admin+'2', pass+'2', "/snapshot", payload)
    }
}
