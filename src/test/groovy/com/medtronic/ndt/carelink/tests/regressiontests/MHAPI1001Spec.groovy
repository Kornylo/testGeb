package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertyMH
import com.medtronic.ndt.carelink.data.api.ApiRequestsMH
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.HomePage
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
@Title('MHAPI 1001 - Patient Creation - Basic Authorization')
class MHAPI1001Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static final admin = Calendar.getInstance().format('MMddHHmmss').toString().toLowerCase()
    static final email = admin + '@ap1.com'
    def pass = "Test1234@"
    def uid = ApiPropertyMH.uid

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
        then: 'Check response code = 200'
        def payload = new JsonBuilder(username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1)
        println(payload.toPrettyString())
        ApiRequestsMH.accountCreation(200, '/auth/create', payload)
    }

    def "Patient creation 1 with no content"() {
        when: 'Create Patient'
        then: 'Check response code = 400'
        def payload = new JsonBuilder()
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(400, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient 2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 204'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "2015-03-21",
                    diabetesType: "OTHER",
                    therapyType : ["ORAL", "BOLUS"],
                    email       : "test_dryrun1@example.com",
                    physician   : "Susan"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(204, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def 'SQL Queries'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created:\n' +
                'Check the columns in the “Query Result” window: there is a record with GET_ACCOUNT value in the SUB_TYPE column'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertyMH.accountUserName1, 'GET_ACCOUNT')
    }

    def "Create Patient 3 with invalid username"() {
        when: 'Create Patient'
        then: 'Check response code = 401'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "2015-03-21",
                    diabetesType: "OTHER",
                    therapyType : ["ORAL", "BOLUS"],
                    email       : "test_dryrun1@example.com",
                    physician   : "Susan"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(401, '/patient/create', 'salala', ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient 4 with invalid password"() {
        when: 'Create Patient'
        then: 'Check response code = 401'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "2015-03-21",
                    diabetesType: "OTHER",
                    therapyType : ["ORAL", "BOLUS"],
                    email       : "test_dryrun1@example.com",
                    physician   : "Susan"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(401, '/patient/create', ApiPropertyMH.accountUserName1, 'salalaj', payload)
    }
}
