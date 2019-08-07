package com.medtronic.ndt.carelink.pages.dashboard

import groovy.transform.Canonical
import com.medtronic.ndt.carelink.util.Utils

@Canonical
class Message implements Comparable<Message>{
    String fromFull
    String subject
    String body
    String from
    String id
    Long time
    Long secondsAgo

    @Override
    int compareTo(Message o) {
        return (int) time - o.time
    }

    @Override
    String toString() {
        return  "\n Mail from - ${from},<br>\n Subject - ${subject},<br>\n Seconds Ago - ${secondsAgo},<br>\\n Time - ${new Date(time as Long)},<br>\n Body - ${Utils.data.getTextFromHTML(body)}"

    }
}
