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

@Stepwise
@Slf4j
@RegressionTest
@Title('SPAK820 – Save Get App Data')
class SPAK820Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static final admin = Calendar.getInstance().format('MMddHHmmss').toString()
    static final admin1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final email = Calendar.getInstance().format('MMMddHHmmss').toString() + "1@medtronic.com"
    static final email2 = Calendar.getInstance().format('MMMddHHmmss').toString() + "2@medtronic.com"
    static String pass = "Test1234@"

    def 'Create Clinic UI'() {
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
        when: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin, pass)
        homePage = browsers.at(HomePage)
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 1 "() {
        when: 'Save app data'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/patient",
                ApiPropertySpak.recorderSn1, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
        println(ApiPropertySpak.patientID1)
    }

    def "Save app data 2 - 401 "() {
        when: 'Save app data'
        then: 'Check response code = 401'
        def json = new JsonBuilder()
        json recorderSn: 'NDU2fj497cJ', data: ApiPropertySpak.data
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(401, '/study/appdata', "", "", json)
    }

    def "Save app data 6 - 204 "() {
        when: 'Save app data'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, data: ''
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(204, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def "Save app data 3- 400 "() {
        when: 'Save app data'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, data: ApiPropertySpak.data + '1' // 1001 symbols
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(400, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def "Save app data 4 - 400 "() {
        when: 'Save app data'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ''
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(400, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def "Save app data 5 - 400 "() {
        when: 'Save app data'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: '', data: "NDU2fj497cJ"
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(400, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def "Save app data 1 - 204 "() {
        when: 'Save app data'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, data: ApiPropertySpak.data
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(204, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def "Save app data 7- 400 "() {
        when: 'Save app data'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, data: 'NDU2#$%fj497cJ'
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(400, '/study/appdata', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, json)
    }

    def 'Create Clinic 2'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        then:
        'Click on the Register Clinic link. Create a new clinic and record the info:\n' +
                'Clinic name'
        and: 'Create a new administrative user and record the info:'
        precondition.registerClinic(admin1, LanguagePage.setCountry(), email2, pass)
        println('Username: ' + admin1)
        println('Password: ' + pass)
    }

    def 'Sign in to created Clinic'() {
        when: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin1, pass)
        homePage = browsers.at(HomePage)
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 2 "() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2 "() {
        when: 'Save app data'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/patient",
                ApiPropertySpak.recorderSn2, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId2, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Save app data 1 , Clinic 2 - 204 "() {
        when: 'Save app data'
        then: 'Check response code = 204'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn2, data: ApiPropertySpak.data
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(204, '/study/appdata', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, json)
    }

    def "Save app data 8 , Clinic 2 - 400 "() {
        when: 'Save app data'
        then: 'Check response code = 400'
        def json = new JsonBuilder()
        json recorderSn: ApiPropertySpak.recorderSn1, data: 'NDU2fj497cJ'
        println(json.toPrettyString())
        ApiRequestsSpak.saveAppData(400, '/study/appdata', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, json)
    }

    def "Get app data 1, Clinic 2- 200 "() {
        when: 'Get app data '
        then: 'Check response code = 200'
        ApiRequestsSpak.getAppData(200, "/study/appdata/$ApiPropertySpak.recorderSn2",
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.recorderSn2)
    }

    def "Get app data 2, Clinic 2- 400 "() {
        when: 'Get app data '
        then: 'Check response code = 400'
        ApiRequestsSpak.getAppData(400, "/study/appdata/0009984725",
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.recorderSn2)
    }

    def "Get app data 3, Clinic 2- 401 "() {
        when: 'Get app data '
        then: 'Check response code = 401'
        ApiRequestsSpak.getAppData(401, "/study/appdata/$ApiPropertySpak.recorderSn2",
                "", "", ApiPropertySpak.recorderSn2)
    }

    def "Get app data 4, Clinic 2- 400 "() {
        when: 'Get app data'
        then: 'Check response code = 400'
        ApiRequestsSpak.getAppData(400, "/study/appdata/$ApiPropertySpak.recorderSn1",
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.recorderSn2)
    }
}