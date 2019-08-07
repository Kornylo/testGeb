package com.medtronic.ndt.carelink.data.api

import groovy.json.JsonSlurper

class ApiRequestsMH {
    static def accountCreation(Integer expectedCode, String url, Object payload) {
        String method = 'POST'
        URL serverUrl = new URL(ApiPropertyMH.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(payload.toString())
        wr.flush()
        wr.close()
        if (request.getResponseMessage() == null) {
            sleep(3000)
        }
        //catch error
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            false
        }
        //positive and negative flow
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
            true
        }
        if (request.responseCode <= 399) {
            assert request.getResponseCode() == expectedCode
            println(request.getResponseMessage())
            println(request.getResponseCode())
            ApiPropertyMH.uid = request.getInputStream().text.replaceAll('.*:|}', '')
            println('UID = ' + ApiPropertyMH.uid)
            true
        }
    }

    static void patientCreationGeneral(Integer expectedCode, String url, String account, String pass, Object payload) {
        String method = 'POST'
        String authentication = "$account:$pass"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertyMH.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(payload.toString())
        wr.flush()
        wr.close()
        //for slow request
        if (request.getResponseMessage() == null) {
            sleep(3000)
        }
        //catch error
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        }
        //positive and negative flow
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getErrorStream().text)
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void getClinic(Integer expectedCode, String url, Object body) {
        String method = 'GET'
        String authentication = "$body.username:$body.password"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        //for slow request
        if (request.getResponseMessage() == null) {
            sleep(3000)
        }
        //catch error
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        }
        //positive and negative flow
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getErrorStream().text)
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            def response = new JsonSlurper().parseText(request.getInputStream().text)
            println(response)
            assert response.clinicId == body.clinic
            assert response.name == body.clinicName
        }
    }

    static void accountLogin(Integer expectedCode, Object data) {
        String method = 'POST'
        String authentication = "$data.account:$data.password"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(data.url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        //for slow request
        if (request.getResponseMessage() == null) {
            sleep(3000)
        }
        //catch error
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        }
        //positive and negative flow
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getErrorStream().text)
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            def response = new JsonSlurper().parseText(request.getInputStream().text)
            println(response)
            assert response.uid == data.uid.replace('"', '')
        }
    }
}
