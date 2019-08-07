package com.medtronic.ndt.carelink.pages.dashboard

import groovy.util.logging.Slf4j
import groovy.json.JsonSlurper
import groovy.transform.Canonical
import java.util.concurrent.TimeoutException
import com.medtronic.ndt.carelink.util.Utils

@Slf4j
class Mail {

    private static final Long TIMEOUT_MAIL = 12000L
    private static final Long FULL_TIMEOUT_MAIL = 60000L
    private static final int READ_TIMEOUT = 40000

    @Canonical
    private static class InboxMessage {
        String subject
        String from
        String id
        String to
        Long secondsAgo
    }

    private static final String TOKEN = "a0aa3bf213734bd18d9962fde9853fa8"
    private static final String MAILINATOR_API_ENDPOINT = "https://api.mailinator.com/"
    private static final String INBOX_TEMPLATE_URL = "api/inbox?to=%s&token=%s&private_domain=%b"
    private static final String EMAIL_TEMPLATE_URL = "api/email?token=%s&id=%s&private_domain=%b"
    private static final String DELETE_TEMPLATE_URL = "api/delete?id=%s&token=%s&private_domain=%b"
    private static final JsonSlurper jsonSlurper = new JsonSlurper()
    private static String id

    private static String getTextFromUrl(String uri) {
        return (MAILINATOR_API_ENDPOINT + uri).toURL().getText([connectTimeout: READ_TIMEOUT, readTimeout: READ_TIMEOUT])
    }

    private static List<InboxMessage> getInboxMessages(String emailAddress) {
        List<InboxMessage> inboxMessageList = []
        def object = jsonSlurper.parseText(getTextFromUrl(String.format(INBOX_TEMPLATE_URL, emailAddress.split("@")[0], TOKEN, true)))
        (object.messages as ArrayList).each {
            inboxMessageList << new InboxMessage(it.subject, it.from, it.id, it.to, (it.seconds_ago as Long))
        }

        inboxMessageList
    }

    private static Message getMessage(String id) {
        def object = jsonSlurper.parseText(getTextFromUrl(String.format(EMAIL_TEMPLATE_URL, TOKEN, id, true)))
        def message = new Message(object.data.fromfull, object.data.subject, object.data.parts.body[0], object.data.from, id, (object.data.time as Long), (object.data.seconds_ago as Long))
        // log.info("Message - " + message)
        message
    }

    private static void removeMessage(String id) {
        getTextFromUrl(String.format(DELETE_TEMPLATE_URL, id, TOKEN, true))
    }

    static Message getLastMessage(String emailAddress, String subject) throws TimeoutException {
        def startTime = System.currentTimeMillis()
        def timeout = FULL_TIMEOUT_MAIL
        Message lastMessage = null
        Thread.sleep(TIMEOUT_MAIL)
        while (true) {
            if (System.currentTimeMillis() - startTime > timeout) {
                emailsLog(getAllMessagesWithoutWait(emailAddress), emailAddress)
                throw new TimeoutException("The letter with the subject - '" + subject + "' was not received on " + emailAddress)
            } else {
                lastMessage = getAllMessagesWithoutWait(emailAddress).last()
                if (lastMessage.subject.equals(getLocaleSubject(subject))) {
                    log.info("\n\n************************************************************************************************ ")
                    log.info("********The email - ${emailAddress}********")
                    emailLog(lastMessage)
                    log.info("*******************************************\n")
                    def deliveryTime = System.currentTimeMillis() - startTime
                    log.info("The time of delivery - " + deliveryTime)
                    return lastMessage
                }
                Thread.sleep(TIMEOUT_MAIL)
            }

        }
    }

    static LinkedList<Message> getAllMessages(String emailAddress) {
        Thread.sleep(TIMEOUT_MAIL)
        LinkedList<Message> messages = getAllMessagesWithoutWait(emailAddress)
        emailsLog(messages, emailAddress)
        messages
    }
    private static LinkedList<Message> getAllMessagesWithoutWait(String emailAddress) {
        (getInboxMessages(emailAddress) as LinkedList).collect { getMessage(it.id) }
    }

    private static void emailsLog(LinkedList<Message> message, String emailAddress) {
        log.info("\n************************************************************************************************ ")
        log.info("********The email - ${emailAddress}********\n")
        message.each {
            emailLog(it)
        }
        log.info("*******************************************\n")
    }

    private static emailLog(Message it) {
        log.info("\n Mail from - ${it.from},\n Subject - ${it.subject},\n Seconds Ago - ${it.secondsAgo},\n Time - ${new Date(it.time as Long)},\\n Body - ${Utils.data.getTextFromHTML(it.body)}")
    }


    static void removeAllMessages(String emailAddress) {
        (getInboxMessages(emailAddress) as LinkedList).each { removeMessage(it.id) }
    }
}
