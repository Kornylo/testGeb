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
@Title('MHAPI 1002 - Patient Creation - Valid and Invalid clinic info and UID')
class MHAPI1002Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static final admin = Calendar.getInstance().format('MMddHHmmss').toString().toLowerCase()
    static final email = admin + '@ap1.com'
    def pass = "Test1234@"
    def uid = ApiPropertyMH.uid
    def clinicIdUi = ApiPropertyMH.clinicIdUi

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

    def "Create Patient A1 - Successfully created patient"() {
        when: 'Create Patient A1'
        then: 'Check response code = 204'
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(204, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def 'SQL Queries'() {
        when: 'Run the statement. Check_Patient_Created.sql'
        then: 'Verify that a patient record exists in the database containing the same information from the JSON file.'
        def body = ['Joe', 'Grant', '1986-08-07 00:00:00.0', 'Test909@medtronic.com', 'Charles', 'TYPE2']
        def therapyType = ['Non-insulin Injectable', 'Diet and Exercise']
        DataBaseRequestsUtil.checkPatientCreated(ApiPropertyMH.patientID1, body, therapyType)
    }

    def "Create Patient A2 - Invalid UID"() {
        when: 'Create Patient A2'
        then: 'Check response code = 400'
        def body = [uid         : '8eba627b-c64f-4265-bee4-c4191aad-c63a@',
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(400, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient A3 - UID is null"() {
        when: 'Create Patient A3'
        then: 'Check response code = 400'
        def body = [uid         : null,
                    clinicId    : clinicIdUi,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(400, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient A4 - Invalid clinic id"() {
        when: 'Create Patient A4'
        then: 'Check response code = 500'
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : '@2456!er',
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(500, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient A5 - Clinic id is null"() {
        when: 'Create Patient A5'
        then: 'Check response code = 400'
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : null,
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(400, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }

    def "Create Patient A6 - Clinic id does not exist"() {
        when: 'Create Patient A6'
        then: 'Check response code = 500'
        def body = [uid         : uid.replace('"', ''),
                    clinicId    : 'KA56-9090',
                    firstName   : ApiPropertyMH.firstName1,
                    lastName    : ApiPropertyMH.lastName1,
                    patientId   : ApiPropertyMH.patientID1,
                    dob         : "1986-08-07",
                    diabetesType: "TYPE_2",
                    therapyType : ["DIET_EXERCISE", "INJECTABLE"],
                    email       : "Test909@medtronic.com",
                    physician   : "Charles"]
        def payload = new JsonBuilder(body)
        println(payload.toPrettyString())
        ApiRequestsMH.patientCreationGeneral(500, '/patient/create', ApiPropertyMH.accountUserName1, ApiPropertyMH.accountPassword1, payload)
    }
}
