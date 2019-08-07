package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Slf4j
@Title('SPAK 806 - Get Clinic')
class SPAK806Spec extends Specification {

    def "Get clinic"() {
        when: "Get clinic with correct HCP credentials"
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'GET'
        String url = "/clinic/$ApiPropertySpak.clinicId1"
        String authentication = "$ApiPropertySpak.hcpAccountUserName1:$ApiPropertySpak.hcpAccountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        if (request.getResponseMessage() == null) {
            sleep(3000)
        }
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            //Assertions
            assert sb.contains('"clinicId":' + '"' + ApiPropertySpak.clinicId1 + '"')
            assert request.getResponseCode() == expectedCode
        }
    }

    def "Get clinic 1"() {
        when: "Get clinic 1"
        then: 'Check response code = 401'
        //Setup request
        def expectedCode = 401
        String method = 'GET'
        String url = "/clinic/$ApiPropertySpak.clinicId1"
        String authentication = "'':'Password'"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        //
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
            //Assertions
            assert stream.text.contains('Error 401--Unauthorized')
            assert request.getResponseCode() == expectedCode
        }
    }

    def "Get clinic 2"() {
        when: "Get clinic 2"
        then: 'Check response code = 404'
        //Setup request
        def expectedCode = 404
        String method = 'GET'
        String url = "/clinic/P3DH-KDXE"
        String authentication = "$ApiPropertySpak.hcpAccountUserName1:$ApiPropertySpak.hcpAccountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        //
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
            assert stream.text.contains('No clinic was found with id P3DH-KDXE')
            //Assertions
            assert request.getResponseCode() == expectedCode
        }
    }

    def "Get clinic 3"() {
        when: "Get clinic 3"
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'GET'
        String url = "/clinic/$ApiPropertySpak.clinicId1"
        String authentication = "userjun26172229@ap1.com:Test1234@1" //patient credentials
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        //
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
//            Assertions
            assert sb.contains('"clinicId":' + '"' + ApiPropertySpak.clinicId1 + '"')
            assert sb.contains('"phone":"777-345-6789"')
            assert request.getResponseCode() == expectedCode
        }
    }

}