package com.medtronic.ndt.carelink.data.api

import org.apache.commons.lang3.RandomStringUtils


class ApiBodySpak {
    public static String emptyBody = """{}"""
    public static String patientNullDevice = """{"deviceId" : null}"""

    static def accountCreation(String username, String password, String eulaStatus) {
        String body = """{
                "username": "${username}",
                "password": "${password}",
                "secondaryEulaStatus": "${eulaStatus}"
                }"""
        println(body)
        return body
    }

    static def createPatient2(String recorder, String clinic, String device, String firstName1, String lastname1, String patient, String accountUserName) {
        String body = """{
    "activeModel": "MMT-7781",
    "activeSn": "${recorder}",
    "clinicId": "${clinic}",
    "deviceId": "${device}",
    "diabetesType": "OTHER",
    "dob": "2015-03-10",
    "email": "ja@ty.sk",
    "firstName": "${firstName1}",
    "lastName": "${lastname1}",
    "patientId": "${patient}",
    "physician": "Dr.House",
    "therapies": [
        {
            "therapyType": "DIET_EXERCISE"
        },
        {
            "brandName": "Glyset",
            "dosage": [
                "50"
            ],
            "genericName": "Miglitol",
            "medicationId": 4,
            "quantity": "1",
            "therapyType": "ORAL",
            "timeTaken": "BREAKFAST"
        },
        {
            "brandName": "Victoza",
            "dosage": [
                "0.6"
            ],
            "genericName": "Liraglutide",
            "medicationId": 91,
            "therapyType": "INJECTABLE",
            "timeTaken": "LUNCH"
        },
        {
            "therapyType": "PREMIX",
            "timeTaken": "AM"
        },
        {
            "therapyType": "BASAL",
            "timeTaken": "PM"
        },
        {
            "therapyType": "BOLUS"
        },
        {
            "therapyType": "PUMP"
        },
        {
            "therapyType": "OTHER"
        }
    ],
    "therapyType": [
        "ORAL"
    ],
    "username": "${accountUserName}",
    "utcOffset": "60"
}"""
        println(body)
        return body
    }

    static def patientLogin(String device) {
        String body = """{"deviceId": "${device}"}"""
        println(body)
        return body
    }

    static def endAStudy2(String recorder, Boolean ignore) {
        String body = """{"ignoreGaps": ${ignore},"recorderSn": "${recorder}"}"""
        println(body)
        return body
    }

    static def recorderDataUpload(String device, String recorder, String rawEis, String rawIsig1, String rawIsig2, String date) {
        String body = """{
    "metadata": {
        "appVersion": "1.0",
        "deviceId": "${device}",
        "recorderModel": "MMT-7781",
        "recorderSn": "${recorder}",
        "startTime": "${date}",
        "status": 30
    },
    "snapshots": [
        {
            "endOffset": 10050,
            "rawEis": "${rawEis}",
            "rawIsig": "${rawIsig1}${rawIsig2}",
            "startOffset": 1
        }
    ]
}"""
        println(body)
        return body
    }

    static def forgotPasswordAccountCreation(String email, String pass) {
        String body = """{"password": "${pass}","username": "${email}"}"""
        println(body)
        return body
    }

    static def forgotPassword(String username, String accountUserName) {
        String body = """{
                "${username}": "${accountUserName}"
                }"""
        println(body)
        return body
    }

    static def configurationSupportCheck(String appVersion, String deviceModel, String os, String osVersion) {
        String body = """{
                "appVersion": ${appVersion},
                "deviceModel": ${deviceModel},
                "os": ${os},
                "osVersion": ${osVersion}
                }"""
        println(body)
        return body
    }

    static def eventCreation(String record, String eventId) {
        String body = """{
    "logbookEvents": [
        {
            "type": "Meal",
            "uniqueId": "${eventId}",
            "timestamp": "2012-08-21T13:28:42.567Z",
            "details": "Some unicode chars here.",
            "size": "M",
            "carbs": 50.0
        },
       {  
         "type":"Insulin",
         "source":"INVALID",
         "uniqueId": "${eventId.replace('354', '11a')}",
         "timestamp":"2012-08-28T13:28:42.567Z",
         "details":"Some unicode chars here.",
         "units":5.4,
         "insulinType":"BASAL"
      },  
      {  
         "type":"Exercise",
         "source":"Fitbit",
         "uniqueId":"${eventId.replace('354', '12a')}",
         "timestamp":"2012-09-04T13:28:42.567Z",
         "endTime":"2012-09-11T18:06:10.684Z",
         "details":"F0B56u1 41SYdEX Xn6A5d0r WZ9kVVk7u GW42P90j I1UE8I7 58dxmW CN9iYz 19w8U9N6Tc1 z He cjgku6ar RMJfI50i Pc6esRA oqzXJXs1o dDYSCHjv u8cQ53w esWg5I857 dkX3vEw4LyB g921AZ3JX8 2nB xwOQ0t3NF6 zNtbw 4604c P6p6sk yu9nAe4H4 qTG7x4C 9ronDBEz7D i8x Mi57Gk 18Yk6jUvJ I9Pz6U Ig35wDCzI eSywo7H 20vLN3Y WOcdJe ile2UWt",
         "activityType":"Running",
         "calories":3310,
         "level":"MODERATE"
      },
      {  
         "type":"Medication",
         "name":"Metformin",
         "uniqueId":"${eventId.replace('354', '13a')}",
         "timestamp":"2012-09-18T13:28:42.567Z",
         "details":"Some unicode chars here."
      },
      {  
         "type":"Medication",
         "name":"Miglitol",
         "uniqueId":"${eventId.replace('354', '14a')}",
         "timestamp":"2012-09-25T13:28:42.567Z",
         "details":"Some unicode chars here."
      },
      {  
         "type":"Sleep",
         "uniqueId":"${eventId.replace('354', '15a')}",
         "timestamp":"2012-10-02T13:28:42.567Z",
         "details":"Some unicode chars here.",
         "endTime":"2012-10-09T13:28:42.123Z",
         "awakeCount":3,
         "restlessCount":6,
         "awakeDuration":24,
         "restlessDuration":30
      },
      {  
         "type":"Notes",
         "uniqueId":"${eventId.replace('354', '16a')}",
         "timestamp":"2012-10-16T13:28:42.567Z",
         "details":"Take a medicine!"
      },
      {  
         "type":"DailySteps",
         "source":"Fitbit",
         "uniqueId":"${eventId.replace('354', '17a')}",
         "timestamp":"2012-10-23",
         "stepCount":"2000"
      }
    ],
    "recorderSn": "${record}"
}"""
        println(body)
        return body
    }

    static def eventUpdate(String record, String json) {
        String body = """{
                    "recorderSn" : "${record}",
                    "logbookEvent" : 
                     ${json}                
                }"""
        println(body)
        return body
    }

    static def createPatientGeneral(String recorder, String device, String firstName1, String lastname1, String patient, String accountUserName, Object offset, String email, String dob, String diabetesType,
                                    String activeModel, String physician, String therapyType, String clinic) {
        String body = """{
    "clinicId"  : "${clinic}",
    "activeModel": "${activeModel}",
    "activeSn": "${recorder}",
    "deviceId": "${device}",
    "diabetesType": "${diabetesType}",
    "dob": "${dob}",
    "email": "${email}",
    "firstName": "${firstName1}",
    "lastName": "${lastname1}",
    "patientId": "${patient}",
    "physician": "${physician}",
    "therapyType": [
        "${therapyType}"
    ],
    "username": "${accountUserName}",
    "utcOffset": "${offset}"
}"""
        println(body)
        return body
    }

    static def diagnosticsUpload(Object recorder, Object diagnostics) {
        String body = """{
    "diagnostics": "${diagnostics}",
    "recorderSn": "${recorder}"
}"""
        println(body)
        return body
    }

    static def eventCreationGeneral(String record, String eventId, String eventId2, String type, String date,
                                    String carbs, String detail, String name, String activity) {
        String dynamicId = "4d089354-7f4c-4c52-851e-" + RandomStringUtils.randomNumeric(11)
        String body = """{
    "logbookEvents": [
        {
            "type": "${type}",
            "uniqueId": "${eventId}",
            "timestamp": "${date}",
            "details": "Some unicode chars here.",
            "size": "M",
            "carbs": ${carbs}
        },
       {  
         "type":"Insulin",
         "source":"INVALID",
         "uniqueId": "${eventId2}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"${detail}",
         "units":5.4,
         "insulinType":"BASAL"
      },
      {  
         "type":"Exercise",
         "source":"Fitbit",
         "uniqueId":"${dynamicId + "2"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "endTime":"2015-12-21T18:06:10.684Z",
         "details":"F0B56u1 41SYdEX Xn6A5d0r WZ9kVVk7u GW42P90j I1UE8I7 58dxmW CN9iYz 19w8U9N6Tc1 z He cjgku6ar RMJfI50i Pc6esRA oqzXJXs1o dDYSCHjv u8cQ53w esWg5I857 dkX3vEw4LyB g921AZ3JX8 2nB xwOQ0t3NF6 zNtbw 4604c P6p6sk yu9nAe4H4 qTG7x4C 9ronDBEz7D i8x Mi57Gk 18Yk6jUvJ I9Pz6U Ig35wDCzI eSywo7H 20vLN3Y WOcdJe ile2UWt",
         "activityType":"${activity}",
         "calories":3310,
         "level":"MODERATE"
      },
      {  
         "type":"Medication",
         "name":"Metformin",
         "uniqueId":"${dynamicId + "3"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"Some unicode chars here."
      },
      {  
         "type":"Medication",
         "name":"${name}",
         "uniqueId":"${dynamicId + "4"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"Some unicode chars here."
      },
      {  
         "type":"Sleep",
         "uniqueId":"${dynamicId + "5"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"Some unicode chars here.",
         "endTime":"2012-08-26T13:28:42.123Z",
         "awakeCount":3,
         "restlessCount":6,
         "awakeDuration":24,
         "restlessDuration":30
      },
      {  
         "type":"Notes",
         "uniqueId":"${dynamicId + "6"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"Info there!"
      },
      {  
         "type":"Notes",
         "uniqueId":"${dynamicId + "7"}",
         "timestamp":"2012-08-26T13:28:42.567Z",
         "details":"Take a medicine!"
      },
      {  
         "type":"DailySteps",
         "source":"Fitbit",
         "uniqueId":"${dynamicId + "8"}",
         "timestamp":"2012-08-26",
         "stepCount":"2000"
      }
    ],
    "recorderSn": "${record}"
}"""
        println(body)
        return body
    }

    static def eventCreation21(String record, String eventId, String steps) {
        String body = """{
  "logbookEvents": [{
      "type": "DailySteps",
      "source": "MOBILE",
      "uniqueId":  "${eventId}",
      "timestamp": "2012-08-26",
      "stepCount": "${steps}"
  }],
  "recorderSn": "${record}"
}"""
        println(body)
        return body
    }


}
