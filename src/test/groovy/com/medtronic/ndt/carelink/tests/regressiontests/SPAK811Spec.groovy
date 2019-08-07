package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Slf4j
@Title('SPAK 811 - Forgot Password')
class SPAK811Spec extends Specification {
    def 'Forgot Password Account Creation'() {
        when: "Forgot Password Account Creation"
        then: 'Check response code = 204'
        //Setup request
        def expectedCode = 204
        String method = 'POST'
        String url = '/auth/create'
        String authentication = "$ApiPropertySpak.envisionAccountUserName:$ApiPropertySpak.envisionAccountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.forgotPasswordAccountCreation(ApiPropertySpak.accountUserName1, 'Password1'))
        wr.flush()
        wr.close()
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        } else {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            InputStream stream = request.getInputStream()
            println(stream.text)
            //Assertions
            assert request.getResponseCode() == expectedCode
        }
    }

    def 'Forgot Password 1'() {
        when: "Forgot Password 1"
        then: 'Check response code = 204'
        //Setup request
        def expectedCode = 204
        String method = 'POST'
        String url = '/auth/forgotPassword'
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.forgotPassword('username', ApiPropertySpak.accountUserName1))
        wr.flush()
        wr.close()
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        } else {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            //Assertions
            assert request.getResponseCode() == expectedCode
        }
    }

    def 'Forgot Password 2'() {
        when: "Forgot Password 2"
        then: 'Check response code = 400'
        //Setup request
        def expectedCode = 400
        String method = 'POST'
        String url = '/auth/forgotPassword'
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.forgotPassword('usernames', ApiPropertySpak.accountUserName1))
        wr.flush()
        wr.close()
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        } else {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            InputStream stream = request.getErrorStream()
            println(stream.text)
            //Assertions
            assert request.getResponseCode() == expectedCode
        }
    }

}
