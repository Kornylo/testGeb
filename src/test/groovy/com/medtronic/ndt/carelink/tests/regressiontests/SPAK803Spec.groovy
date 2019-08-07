package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@RegressionTest
@Title('SPAK 803 - Diagnostics Upload')
class SPAK803Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static final admin1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final emailAddress1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final password = 'Test1234@'

    def 'Register Clinic'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        println(browser.getCurrentUrl())
        and: 'Click on the Register Clinic link. Create a new clinic and record the info:'
        and: 'Clinic Name:'
        precondition.registerClinic(admin1, LanguagePage.setCountry(), emailAddress1, password)
        then:
        'Create a new administrative user and record the info below:' +
                'Username:\n' +
                'Password:'
        println('Username: ' + admin1 + ' Password: ' + password)
    }

    def 'Sign into created Clinic'() {
        when: 'Sign into the MMT-7340 application using the credentials from step ID 3195634.'
        precondition.signInAsClinicAdmin(admin1, password)
        homePage = browsers.at(HomePage)
        homePage.listLoaded()
        then:
        'Record clinic ID:\n' +
                'Clinic ID:'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        println('Clinic ID: ' + clinicIdUi)
    }

    def 'Diagnostics Upload 1'() {
        when: "Diagnostics Upload 1"
        then: 'Check response code = 401'
        String body = ApiBodySpak.emptyBody
        ApiRequestsSpak.diagnosticsUpload(401, '/study/diagnostics', '', '', body)
    }

    def 'Account Creation 1'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'Create Patient 2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, clinicIdUi, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Diagnostics Upload 2'() {
        when: "Diagnostics Upload 2"
        then: 'Check response code = 204'
        String body = ApiBodySpak.diagnosticsUpload(ApiPropertySpak.recorderSn1, 'c2VtcGVyLnViaS5zdWIudWJp')
        ApiRequestsSpak.diagnosticsUpload(204, '/study/diagnostics', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, body)
    }

    def 'Diagnostics Upload 3'() {
        when: "Diagnostics Upload 3"
        then: 'Check response code = 204'
        String body = ApiBodySpak.diagnosticsUpload(ApiPropertySpak.recorderSn1, null)
        ApiRequestsSpak.diagnosticsUpload(204, '/study/diagnostics', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, body)
    }

    def 'Diagnostics Upload 4'() {
        when: "Diagnostics Upload 4"
        then: 'Check response code = 401'
        String body = ApiBodySpak.diagnosticsUpload(ApiPropertySpak.recorderSn1, 'c2VtcGVyLnViaS5zdWIudWJp')
        ApiRequestsSpak.diagnosticsUpload(401, '/study/diagnostics', 'darthvader', ApiPropertySpak.accountPassword1, body)
    }

    def 'Diagnostics Upload 5'() {
        when: "Diagnostics Upload 5"
        then: 'Check response code = 401'
        String body = ApiBodySpak.diagnosticsUpload(ApiPropertySpak.recorderSn1, 'c2VtcGVyLnViaS5zdWIudWJp')
        ApiRequestsSpak.diagnosticsUpload(401, '/study/diagnostics', ApiPropertySpak.accountUserName1, 'lukeskywalker', body)
    }

    def 'Diagnostics Upload 6'() {
        when: "Diagnostics Upload 6"
        then: 'Check response code = 400'
        String body = ApiBodySpak.diagnosticsUpload('01154644-80085', 'c2VtcGVyLnViaS5zdWIudWJp')
        ApiRequestsSpak.diagnosticsUpload(400, '/study/diagnostics', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, body)
    }

    def 'Diagnostics Upload 7'() {
        when: "Diagnostics Upload 7"
        then: 'Check response code = 400'
        String body = ApiBodySpak.diagnosticsUpload(null, 'c2VtcGVyLnViaS5zdWIudWJp')
        ApiRequestsSpak.diagnosticsUpload(400, '/study/diagnostics', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, body)
    }
}
