package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 812 - Get Recorder Data Windows')
class SPAK812Spec extends CareLinkSpec {
    static Precondition precondition
    static SignInPage signInPage
    static HomePage homePage

    static final admin1 = "user1" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final admin2 = "user2" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final admin3 = "user3" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress1 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress2 = "email2" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress3 = "email3" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password1 = "Test1234@"
    static final password2 = "Test12345@"
    static final password3 = "Test123456@"

    def 'Register clinic & sign in, clinic 1'() {
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

    def "Account creation 1, patient 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1,
                ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2, patient 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1, patient 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,
                '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Get Recorder Data Windows, patient 1"(){
        when: 'Get Recorder Data Windows'
        then: 'Check response code = 200'
        ApiRequestsSpak.getRecorderDataWindows(200, '/window/' + ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Get Recorder Data Windows 1, patient 1"(){
        when: 'Get Recorder Data Windows 1'
        then: 'Check response code = 200'
        ApiRequestsSpak.getRecorderDataWindows(200, '/window/' + ApiPropertySpak.recorderSn1, admin1, password1, '', '')
    }

    def "Get Recorder Data Windows 2, patient 1"(){
        when: 'Get Recorder Data Windows 2 - without credentials'
        then: 'Check response code = 401'
        ApiRequestsSpak.getRecorderDataWindows(401, '/window/' + ApiPropertySpak.recorderSn1, '', '', '', '')
    }

    def "Get Recorder Data Windows 3, patient 1"(){
        when: 'Get Recorder Data Windows 3 - incorrect username'
        then: 'Check response code = 401'
        ApiRequestsSpak.getRecorderDataWindows(401, '/window/' + ApiPropertySpak.recorderSn1,
                'username', ApiPropertySpak.accountPassword1, '', '')
    }

    def "Get Recorder Data Windows 4, patient 1"(){
        when: 'Get Recorder Data Windows 4 - no recorder exists with the given recorder serial number'
        then: 'Check response code = 400'
        ApiRequestsSpak.getRecorderDataWindows(400, '/window/' + "011546474588",
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "End a Study 2, patient 1"() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin1 ,password1, ApiPropertySpak.recorderSn1, true)
    }

    def "Get Recorder Data Windows 5, patient 1"(){
        when: 'Get Recorder Data Windows 5 - already ended study'
        then: 'Check response code = 500'
        ApiRequestsSpak.getRecorderDataWindows(500, '/window/' + ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '"errorCode":9999', '')
    }

    def "Account creation 1, patient 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2, patient 2"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Recorder Data Upload 1, patient 2"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2,
                ApiPropertySpak.deviceId1, ApiPropertySpak.recorderSn2, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1,
                ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "End a Study 10, patient 2"() {
        when: "End A Study 10"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", ApiPropertySpak.accountUserName2,
                ApiPropertySpak.accountPassword2, ApiPropertySpak.recorderSn2, true)
    }

    def "Get Recorder Data Windows 5, patient 2"(){
        when: 'Get Recorder Data Windows 5 - already ended study'
        then: 'Check response code = 500'
        ApiRequestsSpak.getRecorderDataWindows(500, '/window/' + ApiPropertySpak.recorderSn2,
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '"errorCode":9999', '"secondaryErrorCode":1')
    }

    def 'Register new clinic & sign in, clinic 2'() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        homePage.clickSignOutButton()
        sleep(1500)
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        and: 'Click on Register Clinic link. Create new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:'
        and: 'Username: (example: TC934)'
        and: 'Password: (example: Password1)"'
        precondition = browsers.to Precondition
        precondition.registerClinic(admin2, LanguagePage.setCountry(), emailAddress2, password2)
        println("Username: " + admin2 + " Password: " + password2)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        and: 'Record the clinicID:'
        precondition.signInAsClinicAdmin(admin2, password2)
        homePage = browsers.at HomePage
        homePage.getClinicIdFromHomePage()
        then: ''
    }

    def "Account creation 1, clinic 2, patient 1"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName3,
                ApiPropertySpak.accountPassword3, '', '')
    }

    def "Create Patient 2, clinic 2, patient 1"() {
        when: 'Create Patient 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId3,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1 + "1", ApiPropertySpak.accountUserName3)
    }

    def "Recorder Data Upload 1,clinic 2, patient 1"() {
        when: 'Recorder Data Upload 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204, ApiPropertySpak.accountUserName3, ApiPropertySpak.accountPassword3,
                ApiPropertySpak.deviceId3, ApiPropertySpak.recorderSn3, ApiPropertySpak.rawEis, ApiPropertySpak.rawIsig1,
                ApiPropertySpak.rawIsig2, '2012-08-26T01:01:00.567Z', "/snapshot")
    }

    def "Account creation 1, clinic 2, patient 2"() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName,
                ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1 + "1",
                ApiPropertySpak.accountPassword1 + "1", '', '')
    }

    def "Get Recorder Data Windows 6, clinic 2, patient 2"(){
        when: 'Get Recorder Data Windows 6 - ' +
                'authenticated patient account is not associated with the recorder serial number in the request'
        then: 'Check response code = 400'
        ApiRequestsSpak.getRecorderDataWindows(400, '/window/' + ApiPropertySpak.recorderSn3,
                ApiPropertySpak.accountUserName1 + "1", ApiPropertySpak.accountPassword1 + "1", '', '')
    }

    def 'Register new clinic, clinic 3'() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        sleep(1500)
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        and: 'Click on Register Clinic link. Create new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:'
        and: 'Username: (example: TC934)'
        and: 'Password: (example: Password1)"'
        precondition = browsers.to Precondition
        precondition.registerClinic(admin3, LanguagePage.setCountry(), emailAddress3, password3)
        println("Username: " + admin3 + " Password: " + password3)
        then: ''
    }

    def "Get Recorder Data Windows 7, clinic 3, patient 1"(){
        when: 'Get Recorder Data Windows 7 - HCP account does not have access to the patient'
        then: 'Check response code = 400'
        ApiRequestsSpak.getRecorderDataWindows(400, '/window/' + ApiPropertySpak.recorderSn4, admin3, password3, '', '')
    }
}