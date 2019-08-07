package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Title('SPAK 804 - Patient Login')
class SPAK804Spec extends Specification {
    def 'Unsuccessful login'() {
        when: "Incorrect username is passed"
        then: 'Verify that the logging was NOT successful for the account’s active study and the returned status code is 401.'
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId1))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 401
    }

    def 'Account Creation 1'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        String envisionAccountUserNamePassword = "$ApiPropertySpak.envisionAccountUserName:$ApiPropertySpak.envisionAccountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((envisionAccountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/create')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.accountCreation(ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, "NONE"))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 204
    }

    def 'Create Patient 2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String envisionAccountUserNamePassword = "$ApiPropertySpak.envisionAccountUserName:$ApiPropertySpak.envisionAccountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((envisionAccountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.createPatient2(ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1))
        wr.flush()
        wr.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
        StringBuilder sb = new StringBuilder()
        String line
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n")
        }
        br.close()
        println(sb.toString())
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert sb.contains('studyHash')
        assert request.getResponseCode() == 200
    }

    def 'Patient Login 2'() {
        when: "Patient Login 2"
        then: 'Verify that the API provided a method for the client to verify an account\'s credentials and determined if the mobile device in use has changed for the account\'s active study.'
        and: 'Check response code = 200'
        String accountUserNamePassword = "$ApiPropertySpak.accountUserName1:$ApiPropertySpak.accountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((accountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId1))
        wr.flush()
        wr.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
        StringBuilder sb = new StringBuilder()
        String line
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n")
        }
        br.close()
        println(sb.toString())
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert sb.contains('recorderSn":' + '"' + ApiPropertySpak.recorderSn1 + '"')
        assert sb.contains('firstName":' + '"' + ApiPropertySpak.firstName1 + '"')
        assert sb.contains('lastName":' + '"' + ApiPropertySpak.lastName1 + '"')
        assert sb.contains('patientId":' + '"' + ApiPropertySpak.patientID1 + '"')
        assert sb.contains('clinicId":' + '"' + ApiPropertySpak.clinicId1 + '"')
        assert sb.contains('"secondaryEulaStatus":"NONE"')
        assert sb.contains('recorderSn":' + '"' + ApiPropertySpak.recorderSn1 + '"')
        assert sb.contains('deviceChanged":false')
        assert sb.contains('MMT-7781')
        assert request.getResponseCode() == 200
    }

    def 'Patient Login 3'() {
        when: "Patient Login 3"
        then: 'Verify that the API provided a method for the client to verify an account\'s credentials and determined if the mobile device in use has changed for the account\'s active study and returned status code is 400.'
        String accountUserNamePassword = "$ApiPropertySpak.accountUserName1:$ApiPropertySpak.accountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((accountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.emptyBody)
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 400
    }

    def 'Patient Login 4'() {
        when: "Patient Login 4"
        then: 'Verify that the logging was NOT successful for the account’s active study and the returned status code is 401.'
        String accountUserNamePassword = "'lala':$ApiPropertySpak.accountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((accountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId1))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 401
    }

    def 'Patient Login 5'() {
        when: "Patient Login 5"
        then: 'Verify that the logging was NOT successful for the account’s active study and the returned status code is 401.'
        String accountUserNamePassword = "$ApiPropertySpak.accountUserName1:'dobre'"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((accountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId1))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 401
    }

    def 'Patient Login 6'() {
        when: "Patient Login 6"
        then: 'Verify that the API provided a method for the client to verify an account\'s credentials and determined if the mobile device in use has changed for the account\'s active study and returned status code is 400.'
        String accountUserNamePassword = "$ApiPropertySpak.accountUserName1:$ApiPropertySpak.accountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((accountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/login/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientNullDevice)
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 400
    }

    def 'End A Study 2'() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        String credentials = "$ApiPropertySpak.hcpAccountUserName1:$ApiPropertySpak.hcpAccountPassword1"
        String url = '/study/end'
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((credentials).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.endAStudy2(ApiPropertySpak.recorderSn1, true))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 200
    }

    def 'Patient Login 8'() {
        when: "Patient Login 6"
        then: 'Verify that the API provided a method and the returned values for recorderSn1, studyHash properties are null values and deviceChanged is value of false and returned status code is 200.'
        String credentials = "$ApiPropertySpak.accountUserName1:$ApiPropertySpak.accountPassword1"
        String url = '/auth/login/patient'
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((credentials).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId1))
        wr.flush()
        wr.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
        StringBuilder sb = new StringBuilder()
        String line
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n")
        }
        br.close()
        println(sb.toString())
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert sb.contains('"secondaryEulaStatus":"NONE"')
        assert sb.contains('deviceChanged":false')
        assert request.getResponseCode() == 200
    }

    def 'Account Creation 1-2'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        String envisionAccountUserNamePassword = "$ApiPropertySpak.envisionAccountUserName:$ApiPropertySpak.envisionAccountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((envisionAccountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/auth/create')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.accountCreation(ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword1, "ACCEPTED"))
        wr.flush()
        wr.close()
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert request.getResponseCode() == 204
    }

    def 'Create Patient 2-2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String envisionAccountUserNamePassword = "$ApiPropertySpak.envisionAccountUserName:$ApiPropertySpak.envisionAccountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((envisionAccountUserNamePassword).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + '/patient')
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.createPatient2(ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2))
        wr.flush()
        wr.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
        StringBuilder sb = new StringBuilder()
        String line
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n")
        }
        br.close()
        println(sb.toString())
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert sb.contains('studyHash')
        assert request.getResponseCode() == 200
    }

    def 'Patient Login 9'() {
        when: "Patient Login 6"
        then: 'Verify that this method include in its response an indication of whether the owner of the account has accepted an agreement for secondary use of their data and the returned status code is 200.'
        String credentials = "$ApiPropertySpak.accountUserName2:$ApiPropertySpak.accountPassword1"
        String url = '/auth/login/patient'
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((credentials).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("POST")
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(ApiPropertySpak.deviceId2))
        wr.flush()
        wr.close()
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
        StringBuilder sb = new StringBuilder()
        String line
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n")
        }
        br.close()
        println(sb.toString())
        println(request.getResponseMessage())
        println(request.getResponseCode())
        assert sb.contains('"secondaryEulaStatus":"ACCEPTED"')
        assert sb.contains('recorderSn":' + '"' + ApiPropertySpak.recorderSn2 + '"')
        assert sb.contains('firstName":' + '"' + ApiPropertySpak.firstName2 + '"')
        assert sb.contains('lastName":' + '"' + ApiPropertySpak.lastName2 + '"')
        assert sb.contains('patientId":' + '"' + ApiPropertySpak.patientID2 + '"')
        assert sb.contains('clinicId":' + '"' + ApiPropertySpak.clinicId1 + '"')
        assert sb.contains('deviceChanged":false')
        assert sb.contains('MMT-7781')
        assert sb.contains('studyHash')
        assert request.getResponseCode() == 200
    }
}
