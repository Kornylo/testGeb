package com.medtronic.ndt.carelink.data.api

import groovy.json.JsonSlurper

class ApiRequestsSpak {
    static void accountCreation(Integer expectedCode, String envisionAccount, String envisionPass, String url, String accountUserName, String accountPass, String errorCode, String message) {
        String method = 'POST'
        String authentication = "$envisionAccount:$envisionPass"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.accountCreation(accountUserName, accountPass, "NONE"))
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            assert sb.contains(message)
            assert sb.contains("""errorCode":${errorCode}""")
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void createPatient(Integer expectedCode, String envisionAccount, String envisionPass, String url, String recorder, String clinic, String deviceId, String firstName, String lastName, String patientId, String accountUserName) {
        String method = 'POST'
        String authentication = "$envisionAccount:$envisionPass"
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
        wr.writeBytes(ApiBodySpak.createPatient2(recorder, clinic, deviceId, firstName, lastName, patientId, accountUserName))
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
    }

    static void createPatientGeneral(Integer expectedCode, String envisionAccount, String envisionPass, String url, Object body) {
        String method = 'POST'
        String authentication = "$envisionAccount:$envisionPass"
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
        wr.writeBytes(body.toString())
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
    }

    static void recorderDataUpload(Integer expectedCode, String accountUserName, String accountPassword, String deviceId, String recorder, String rawEis, String rawIsig1, String rawIsig2, String date, String url) {
        //Setup request
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
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
        wr.writeBytes(ApiBodySpak.recorderDataUpload(deviceId, recorder, rawEis, rawIsig1, rawIsig2, date))
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
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void recorderDataUploadGeneral(Integer expectedCode, String accountUserName, String accountPassword, String url, Object body) {
        //Setup request
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
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
        wr.writeBytes(body.toString())
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
            if (request.responseCode == 500) {
                def json = new JsonSlurper()
                def response = json.parseText(request.getErrorStream().text)
                println(response)
                assert response.errorCode == 9999
            }
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void eventCreation(Integer expectedCode, String url, String recorder, String accountUserName, String accountPassword, String eventId) {
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.eventCreation(recorder, eventId))
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
        }
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void endAStudy(Integer expectedCode, String url, String hcpAccountUserName, String hcpAccountPassword, String recorder, Boolean ignoreGaps) {
        String method = 'POST'
        String authentication = "${hcpAccountUserName}:${hcpAccountPassword}"
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
        wr.writeBytes(ApiBodySpak.endAStudy2(recorder, ignoreGaps))
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
            assert request.getResponseCode() == expectedCode
        }
    }

    static void patientLogin(Integer expectedCode, String url, String accountUserName, String accountPassword, String deviceId, String recorderSn, String patientId, String clinicId) {
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.patientLogin(deviceId))
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
            assert request.responseCode == expectedCode
        }
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            assert sb.contains("""recorderSn":"${recorderSn}""")
            assert sb.contains("""patientId":"${patientId}""")
            assert sb.contains("""clinicId":"${clinicId}""")
            assert sb.contains('"secondaryEulaStatus":"NONE"')
            assert sb.contains('deviceChanged":false')
            assert sb.contains('MMT-7781')
        }
    }

    static void getPatient(Integer expectedCode, String url, String accountUserName, String accountPassword, String patientId, String clinicId) {
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod("GET")
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
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode


        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            assert sb.contains(""""patientId":"${patientId}""")
            assert sb.contains(""""clinicId":"${clinicId}""")
            assert request.getResponseCode() == expectedCode
        }
    }

    static void createPatientEmpty(Integer expectedCode, String url) {
        String method = 'POST'
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.emptyBody)
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
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
    }

    static void eventUpdate(Integer expectedCode, String url, String recorder, String accountUserName, String accountPassword, Object json, String errorCode, String secondaryError) {
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(ApiBodySpak.eventUpdate(recorder, json.toString()))
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
        }
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            assert sb.toString().contains(errorCode)
            assert sb.toString().contains(secondaryError)
            br.close()
            println(sb.toString())
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void eventDeletion(Integer expectedCode, String url, String accountUserName, String accountPassword, String errorCode, String secondaryError) {
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())

        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")

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
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            assert sb.toString().contains(secondaryError)
            assert sb.toString().contains(errorCode)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void eventCreationGeneral(Integer expectedCode, String url, String accountUserName, String accountPassword, Object body) {
        //Setup request
        String method = 'POST'
        String authentication = "${accountUserName}:${accountPassword}"
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
        wr.writeBytes(body.toString())
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
            assert request.responseCode == expectedCode
        }
        //positive and negative flow
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void getRecorderDataWindows(Integer expectedCode, String url, String accountUserName, String accountPassword, String errorCode, String secondaryError) {
        String method = 'GET'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")

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
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
            assert sb.toString().contains(errorCode)
            assert sb.toString().contains(secondaryError)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void logFileUpload(Integer expectedCode, String url, String accountUserName, String accountPass, Object json) {
        String method = 'POST'
        String authentication = "$accountUserName:$accountPass"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(json.toString())
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
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void getActiveStudies(Integer expectedCode, String url, String hcpUserName, String hcpAccountPassword) {
        String method = 'GET'
        String authentication = "${hcpUserName}:${hcpAccountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())

        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")

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
        if (request.responseCode >= 400) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getErrorStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()))
            StringBuilder sb = new StringBuilder()
            String line
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n")
            }
            br.close()
            println(sb.toString())
        }
    }

    static void getLogBook(Integer expectedCode, String url, String accountUserName, String accountPass, Object dataMap, Object index) {
        String method = 'GET'
        String authentication = "$accountUserName:$accountPass"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        println(serverUrl)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
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
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            def json = new JsonSlurper()
            def response = json.parseText(request.getInputStream().text)
            println(response)
            if (response.logbookEvents.toString() != '[]') {
                def newMapKey = response.logbookEvents.getAt(index.toInteger())*.key - (response.logbookEvents.getAt(index.toInteger())*.key - dataMap*.key)
                def newMapValue = response.logbookEvents.getAt(index.toInteger())*.value - (response.logbookEvents.getAt(index.toInteger())*.value - dataMap*.value)
                def newMap = [newMapKey, newMapValue].transpose().inject([:]) { a, b -> a[b[0]] = b[1]; a }
                assert newMap == dataMap
            }
        }
    }

    static void diagnosticsUpload(Integer expectedCode, String url, String accountUserName, String accountPass, String body) {
        String method = 'POST'
        String authentication = "$accountUserName:$accountPass"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(body)
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
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void saveAppData(Integer expectedCode, String url, String accountUserName, String accountPassword, Object json) {
        String method = 'POST'
        String authentication = "$accountUserName:$accountPassword"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        request.addRequestProperty("Content-type", "application/json")
        DataOutputStream wr = new DataOutputStream(request.getOutputStream())
        wr.writeBytes(json.toString())
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
            println(request.getErrorStream().text)
            assert request.getResponseCode() == expectedCode
        }
        if (request.responseCode <= 399) {
            println(request.getResponseMessage())
            println(request.getResponseCode())
            assert request.getResponseCode() == expectedCode
            println(request.getInputStream().text)
        }
    }

    static void getAppData(Integer expectedCode, String url, String accountUserName, String accountPassword, String recorderSn) {
        String method = 'GET'
        String authentication = "${accountUserName}:${accountPassword}"
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((authentication).getBytes())
        URL serverUrl = new URL(ApiPropertySpak.baseAPI + url)
        HttpURLConnection request = (HttpURLConnection) serverUrl.openConnection()
        request.setDoOutput(true)
        request.setRequestProperty("Authorization", basicAuth)
        request.setRequestMethod(method)
        request.addRequestProperty("Accept-Language", "en_US")
        def json = new JsonSlurper()
        println(request.getResponseMessage().toString())
        println(request.getResponseCode().toString())
        if (request.responseCode != expectedCode) {
            InputStream stream = request.getErrorStream()
            if (stream == null) {
                stream = request.getInputStream()
            }
            println('ERROR! ' + stream.text)
            assert request.responseCode == expectedCode
        }
        if (request.responseCode >= 400) {
            assert request.getResponseCode() == expectedCode
            if (request.responseCode == 401) {
                assert request.getErrorStream().text.contains('<TITLE>Error 401--Unauthorized</TITLE>')
            } else {
                def response = json.parseText(request.getErrorStream().text)
                println(response)
                assert response.message.contains('An unexpected error occurred')
            }
        }
        if (request.responseCode <= 399) {
            assert request.getResponseCode() == expectedCode
            def response = json.parseText(request.getInputStream().text)
            println(response)
            assert response.recorderSn == "${recorderSn}"
            assert response.data == ApiPropertySpak.data
        }
    }

    static void endAStudy2(Integer expectedCode, String url, String hcpAccountUserName, String hcpAccountPassword, Object json ) {
        String method = 'POST'
        String authentication = "${hcpAccountUserName}:${hcpAccountPassword}"
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
        wr.writeBytes(json.toString())
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
        }
        if (request.responseCode <= 399) {
            assert request.getResponseCode() == expectedCode
            def json1 = new JsonSlurper()
            def response = json1.parseText(request.getInputStream().text)
            println(response)
            if (  response.studyEnded == true){
                assert response.studyEnded == true
            }
            if (response.windows == [[endOffset:10100, startOffset:10051], [endOffset:20200, startOffset:20051]]){
                assert response.windows == [[endOffset:10100, startOffset:10051], [endOffset:20200, startOffset:20051]]
                println(response)
            }
            if (response.windows == [[endOffset:10100, startOffset:10051], [startOffset:20051]]){
                assert response.windows ==  [[endOffset:10100, startOffset:10051], [startOffset:20051]]
                println(response)
            }
        }
    }

}
