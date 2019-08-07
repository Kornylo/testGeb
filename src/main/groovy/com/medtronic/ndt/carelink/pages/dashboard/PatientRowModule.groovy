package com.medtronic.ndt.carelink.pages.dashboard

import geb.Module

/**
 * Module for the row of patient info which appears on the hcp dashboard (landing page after login)
 */
class PatientRowModule extends Module {

    Map<DashboardPatientInfo, String> getPatientInfo() {
        Map<DashboardPatientInfo, String> patientInfoMap = [:]
        $('p').collect {it.text()}.eachWithIndex { it, i ->
            patientInfoMap.put(DashboardPatientInfo.values()[i], it)
        }

        patientInfoMap
    }
}
