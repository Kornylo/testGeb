package com.medtronic.ndt.carelink.data

import com.medtronic.ndt.carelink.data.enums.DiabetesType
import com.medtronic.ndt.carelink.data.enums.TherapyType
import java.time.LocalDate

class Patient {
    String username
    String firstName
    String lastName
    String email
    String password
    LocalDate birthday
    DiabetesType diabetesType
    List<TherapyType> therapies
    String id
    String physicianName
    String activeSn
    String activeModel
    int utcOffset
    String deviceId
    String clinicId

    @Override
    String toString() {
        this.properties.findAll {it.key != 'class'}
    }
}
