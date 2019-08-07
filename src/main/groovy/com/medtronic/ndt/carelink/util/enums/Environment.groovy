package com.medtronic.ndt.carelink.util.enums

enum Environment {
    DEV(['DEV']),
    QA(['QA']),
    STAGING(['STAGING']),
    PROD(['PROD'])

    final List<String> searchStrings

    Environment(List<String> searchStrings) {
        this.searchStrings = searchStrings
    }
}
