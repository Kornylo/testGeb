package com.medtronic.ndt.carelink.util.enums

enum Browsers {
    FIREFOX,
    CHROME,
    IE,
    EDGE
    final List<String> searchStrings

    Browsers(List<String> searchStrings) {
        this.searchStrings = searchStrings
    }
}
