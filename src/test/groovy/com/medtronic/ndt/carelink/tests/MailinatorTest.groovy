package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.Message
import spock.lang.Specification

class MailinatorTest extends Specification{

    def getInboxTest() {
        given: ' '
        when: ' '
        then: ' '
        println("getInboxMessages")
        String apikey = ""
        String emailAddress = "test@medtronictest.mailinator.com"
        List<Message> result = Mail.getInboxMessages(emailAddress)
        println(result)

    }
    def getEmailTest() {
        given: ' '
        when: ' '
        then:  ' '
        println("getEmail")
        String emailId = "email905fOM3@medtronictest.mailinator.com" //should be unique
        List<Message> result = Mail.getAllMessages(emailId)
        println(result)
    }

    def removeLetter(){
        given: ' '
        when: ' '
        then:  ' '
        println('remove Letter')
        String emailId = "email605Dutl@medtronictest.mailinator.com"
        List<Message> result = Mail.removeAllMessages(emailId)
        println(result)
    }
}
