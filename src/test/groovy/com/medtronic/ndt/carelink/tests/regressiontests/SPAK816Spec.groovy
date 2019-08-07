package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 816 - HCP Login ')
class SPAK816Spec  extends Specification {
    def 'HCP Login 1'() {
        when: "HCP Login 1"
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 111
        String method = 'POST'
        String url = '/auth/login/hcp'
        String authentication = "$ApiPropertySpak.hcpAccountUserName1:$ApiPropertySpak.hcpAccountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.setRequestProperty("Authorization", basicAuth)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
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
            assert sb.contains('clinicId":' + '"' + ApiPropertySpak.clinicId1 + '"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def 'HCP Login 2'() {
        when: "HCP Login 2"
        then: 'Check response code = 401'
        //Setup request
        def expectedCode = 401
        String method = 'POST'
        String url = '/auth/login/hcp'
        String authentication = "$ApiPropertySpak.hcpAccountUserName1:'eNp71DDHP0+hJCNVwSWzOBnMSM9PKVZISU3MKVEoTi1LLUrNqVQozyzJUEgEymYWlxTrPWqYqwAAgboWVQ=='"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.setRequestProperty("Authorization", basicAuth)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
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
    def 'HCP Login 3'() {
        when: "HCP Login 3"
        then: 'Check response code = 401'
        //Setup request
        def expectedCode = 401
        String method = 'POST'
        String url = '/auth/login/hcp'
        String authentication = "'lalala':$ApiPropertySpak.hcpAccountPassword1"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.setRequestProperty("Authorization", basicAuth)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
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
}
