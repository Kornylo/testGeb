package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertyMH
import com.medtronic.ndt.carelink.data.api.ApiRequestsMH
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('MHAPI 1006 - Get Clinic')
class MHAPI1006Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static final admin = Calendar.getInstance().format('MMddHHmmss').toString().toLowerCase()
    static final email = admin + '@ap1.com'
    def pass = "Test1234@"
    def clinicIdUi = ApiPropertyMH.clinicIdUi
    def clinicName = ApiPropertyMH.clinicName
    def url = ApiPropertyMH.baseAPI + '/clinic/' + clinicIdUi

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
        homePage.getClinicNameFromHomePage()
    }

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 200'
        def payload = new JsonBuilder(username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1)
        println(payload.toPrettyString())
        ApiRequestsMH.accountCreation(200, '/auth/create', payload)
    }

    def 'Get Clinic 1'() {
        when: 'Get Clinic 1'
        then: 'Check response code = 200'
        def data = [username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, clinicName: clinicName, clinic: clinicIdUi]
        ApiRequestsMH.getClinic(200, url, data)
    }

    def 'Get Clinic 2 - Username not found'() {
        when: 'Get Clinic 2'
        then: 'Check response code = 401'
        def data = [username: 'NoSucUserName@mail.com', password: ApiPropertyMH.accountPassword1, clinicName: clinicName, clinic: clinicIdUi]
        ApiRequestsMH.getClinic(401, url, data)
    }

    def 'Get Clinic 3 - Invalid clinicID'() {
        when: 'Get Clinic 3'
        then: 'Check response code = 404'
        def data = [username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, clinicName: clinicName, clinic: 'NoSuchClinicId']
        ApiRequestsMH.getClinic(404, url.replace('-', ''), data)
    }

    def 'Get Clinic 4 - Unsecured HTTP Connection'() {
        when: 'Get Clinic 4'
        then: 'Check response code = 404'
        def data = [username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, clinicName: clinicName, clinic: clinicIdUi]
        ApiRequestsMH.getClinic(404, url.replace('https', 'http'), data)
    }

    def 'Get Clinic 5 - Clinic ID is in valid format but not found in DB'() {
        when: 'Get Clinic 5'
        then: 'Check response code = 404'
        def data = [username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, clinicName: clinicName, clinic: clinicIdUi]
        ApiRequestsMH.getClinic(404, url.replace('US', 'UK'), data)
    }
}