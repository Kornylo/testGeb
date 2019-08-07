package com.medtronic.ndt.carelink.util

import org.ccil.cowan.tagsoup.Parser

class DataUtils {

    static def slurper = new XmlSlurper(new Parser())

    String getTextFromHTML(String text) {
        def htmlParsed = slurper.parseText(text)
        String message = htmlParsed.'**'.find { it.text }
        message.substring(message.lastIndexOf("}") + 1)
    }

}
