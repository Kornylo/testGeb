package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title
@Stepwise
@Slf4j
@Title('SPAK 801 - Configuration Support Check')
class SPAK801Spec extends Specification{

    def "Configuration Support Check 2 "() {
        when: 'Configuration Support Check 2'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','turbo','Android','5.0'))
        wr.flush()
        wr.close()
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
            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"WARN"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def "Configuration Support Check 3 "() {
        when: 'Configuration Support Check 3'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','turbo','Android','5.0'))
        wr.flush()
        wr.close()
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
            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"WARN"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def "Configuration Support Check 4 "() {
        when: 'Configuration Support Check 4'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','5S','iOS','8.0'))
        wr.flush()
        wr.close()
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
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"NO"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def "Configuration Support Check 5 "() {
        when: 'Configuration Support Check 5'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','Galaxy','Android','5.0'))
        wr.flush()
        wr.close()
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
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"WARN"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def "Configuration Support Check 6 "() {
        when: 'Configuration Support Check 5'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','Nokia','Blackberry','5'))
        wr.flush()
        wr.close()
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
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"NO"')
            assert request.getResponseCode() == expectedCode
        }
    }
    def "Configuration Support Check 7 "() {
        when: 'Configuration Support Check 5'
        then: 'Check response code = 200'
        //Setup request
        def expectedCode = 200
        String method = 'POST'
        String url = "/support"
        //
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.configurationSupportCheck('1.0','Nano','Android','5.0'))
        wr.flush()
        wr.close()
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
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"At least 3 of the 4 character classes must be included:","  • Uppercase letter","  • Lowercase letter","  • Number","  • Special character"')
//            assert sb.contains('requirements":["The password must be 8-20 characters in length."')
//            assert sb.contains('"reason":"The Envision Pro App is not fully compatible with your mobile device. You may continue to use the App, but information may display differently than intended."')
            assert sb.contains('"supported":"NO"')
            assert request.getResponseCode() == expectedCode
        }
    }
}
