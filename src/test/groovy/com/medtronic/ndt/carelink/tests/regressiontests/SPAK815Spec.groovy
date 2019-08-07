package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 815 - Get Active Studies per test plan')
class SPAK815Spec extends CareLinkSpec {
    static Precondition precondition
    static SignInPage signInPage
    static HomePage homePage

    static final admin1 = "user1" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress1 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password1 = "Test1234@"

    def "Account creation 1, clinic 1, patient 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1,
                ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2, clinic 1, patient 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1, clinic 1, patient 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Get Active Studies 1, clinic 1"(){
        when: 'Get Active Studies 1'
        then: 'Check response code = 200'
        ApiRequestsSpak.getActiveStudies(200, "/study", ApiPropertySpak.hcpAccountUserName1, ApiPropertySpak.hcpAccountPassword1)
    }

    def "SQL Queries 1, verify GET STUDY value, clinic 1, patient 1"() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is GET_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_STUDY')
    }

    def "Get Active Studies 2, clinic 1"(){
        when: 'Get Active Studies 2 - authentication failed'
        then: 'Check response code = 401'
        ApiRequestsSpak.getActiveStudies(401, "/study", "username", "password")
    }

    def "Register clinic & sign in"() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
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

    def "Get Active Studies 4, clinic 2"(){
        when: 'Get Active Studies 4 - user with no active studies at all'
        then: 'Check response code = 200'
        ApiRequestsSpak.getActiveStudies(200, "/study", admin1, password1)
    }

    def "SQL Queries 1, verify GET STUDY is missed, clinic 2"() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is NOT created: Check the columns in the “Query Result” window.' +
                'There no is GET_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qaQueries1CheckMissedType(ApiPropertySpak.accountUserName1, 'GET STUDY')
    }

    def "Account creation 1, clinic 2, patient 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2, clinic 2, patient 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "End a Study 2, clinic 2, patient 2"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin1, password1, ApiPropertySpak.recorderSn2, true)
    }

    def "Get Active Studies 3, clinic 2"(){
        when: 'Get Active Studies 3 - user with no active studies.'
        then: 'Check response code = 200'
        ApiRequestsSpak.getActiveStudies(200, "/study", admin1, password1)
    }

    def "SQL Queries 1, verify GET STUDY is missed, clinic 2, patient 2"() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is NOT created: Check the columns in the “Query Result” window.' +
                'There no is GET_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qaQueries1CheckMissedType(ApiPropertySpak.accountUserName2, 'GET STUDY')
    }

    def "Account creation 1, clinic 2, patient 3"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName3,
                ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2, clinic 2, patient 3"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName1 + "1", ApiPropertySpak.lastName1 + "1", ApiPropertySpak.patientID1 + "1", ApiPropertySpak.accountUserName3)
    }

    def "Recorder Data Upload 1, clinic 2, patient 3"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3,
                ApiPropertySpak.deviceId3, ApiPropertySpak.recorderSn3, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Create Patient 2, clinic 2, patient 3 "() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn4, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName1 + "1", ApiPropertySpak.lastName1 + "1", ApiPropertySpak.patientID1 + "1", ApiPropertySpak.accountUserName3)
    }

    def "Recorder Data Upload 1, clinic 2, patient 3 "() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3,
                ApiPropertySpak.deviceId3, ApiPropertySpak.recorderSn4, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-27T01:01:00.567Z', "/snapshot")
    }

    def "Get Active Studies 1, clinic 2"(){
        when: 'Get Active Studies 1'
        then: 'Check response code = 200'
        ApiRequestsSpak.getActiveStudies(200, "/study", admin1, password1)
    }

    def "SQL Queries 1, verify GET STUDY value, clinic 2 "() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window.' +
                'There is GET_STUDY value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName3, 'GET_STUDY')
    }

    def "Account creation 1, clinic 2, patient 4"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1 + "2", ApiPropertySpak.accountPassword1 + "2", '', '')
    }

    def "Get Active Studies 5, clinic 2"(){
        when: 'Get Active Studies 5 - authenticated account was not an HCP Admin or Staff use.'
        then: 'Check response code = 403'
        ApiRequestsSpak.getActiveStudies(403, "/study", ApiPropertySpak.accountUserName1 + "2",  ApiPropertySpak.accountPassword1 + "2")
    }
}