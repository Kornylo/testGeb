package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Title('SPAK 805 - Get patient')
class SPAK805Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static final admin1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final admin2 = '2' + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final emailAddress1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final emailAddress2 = '2' + Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final password = 'Test1234@'

    def 'Account Creation 1'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'Create Patient 2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Patient Login 2'() {
        when: "Patient Login 2"
        then: 'Verify that the API provided a method for the client to verify an account\'s credentials and determined if the mobile device in use has changed for the account\'s active study.'
        ApiRequestsSpak.patientLogin(200, '/auth/login/patient', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.patientID1, ApiPropertySpak.clinicId1)
    }

    def 'SQL Queries 1'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is GET_PATIENTS value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_PATIENTS')
    }

    def 'Account Creation 1-2'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 2-2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId2, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def 'Get Patient 1'() {
        when: 'Get Patient 1'
        then: 'Check response code = 200'
        ApiRequestsSpak.getPatient(200, '/patient', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.patientID2, ApiPropertySpak.clinicId2)
    }

    def 'SQL Queries 1-2'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is GET_PATIENTS value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2, 'GET_PATIENTS')
    }

    def 'Get Patient 2'() {
        when: 'Get Patient 2'
        then: 'Check response code = 401'
        ApiRequestsSpak.getPatient(401, '/patient', '', '', ApiPropertySpak.patientID2, ApiPropertySpak.clinicId2)
    }

    def 'Account Creation 1-3'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }

    def 'Get Patient 3'() {
        when: 'Get Patient 3'
        then: 'Check response code = 404'
        ApiRequestsSpak.getPatient(404, '/patient', ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3, '', '')
    }

    def 'Create Patient 2-3'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId2, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName3)
    }

    def 'Get Patient 4'() {
        when: 'Get Patient 4'
        then: 'Check response code = 403'
        ApiRequestsSpak.getPatient(403, '/patient', ApiPropertySpak.hcpAccountUserName2, ApiPropertySpak.hcpAccountPassword2, '', '')
    }

    def 'Create clinic 1 on UI and login'() {
        when: ''
        precondition = browsers.to(Precondition)
        homePage = browsers.at(HomePage)
        then: 'Create first clinic'
        precondition.registerClinic(admin1, LanguagePage.setCountry(), emailAddress1, password)
        println('Login: ' + admin1 + ' Password: ' + password)
        precondition.signInAsClinicAdmin(admin1, password)
        waitFor { homePage.clinicId.displayed }
    }

    String accountUserName = 'user4' + ApiPropertySpak.accountUserName1
    String recorderSn = ApiPropertySpak.recorderSn1.replace('11', '44')
    String patientId = ApiPropertySpak.patientID2 + '2'
    String deviceId = ApiPropertySpak.deviceId1.replace('11', '44')

    def 'Account Creation 1-4'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', accountUserName, ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 2-4'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', recorderSn, clinicIdUi, deviceId, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, patientId, accountUserName)
    }

    def 'Recorder Data Upload 1'() {
        when: "Recorder Data Upload 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, accountUserName, ApiPropertySpak.accountPassword2, deviceId,
                recorderSn, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    void 'Sign out'() {
        when: ''
        homePage = browsers.at(HomePage)
        then: ''
        homePage.clickSignOutButton()
        waitFor { browser.getCurrentUrl().contains('login') }
    }

    def 'Create clinic 2 on UI and login'() {
        when: ''
        precondition = browsers.to(Precondition)
        homePage = browsers.at(HomePage)
        then: 'Create second clinic'
        precondition.registerClinic(admin2, LanguagePage.setCountry(), emailAddress2, password)
        println('Login: ' + admin2 + ' Password: ' + password)
        precondition.signInAsClinicAdmin(admin2, password)
        waitFor { homePage.clinicId.displayed }
    }

    def 'Create Patient 2-5'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', recorderSn, clinicIdUi, deviceId, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, patientId, accountUserName)
    }

    def 'Recorder Data Upload 1 - 2'() {
        when: "Recorder Data Upload 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, accountUserName, ApiPropertySpak.accountPassword2, deviceId,
                recorderSn, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2, '2012-08-28T01:01:00.567Z', "/snapshot")
    }

    def 'Get Patient 1-2'() {
        when: 'Get Patient 1'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.getPatient(200, '/patient', accountUserName, ApiPropertySpak.accountPassword2, patientId, clinicIdUi)
    }

    def 'SQL Queries 1-3'() {
        when: 'Run the statement.'
        then:
        'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.\n' +
                'There is GET_PATIENTS value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(accountUserName, 'GET_PATIENTS')
    }
}
