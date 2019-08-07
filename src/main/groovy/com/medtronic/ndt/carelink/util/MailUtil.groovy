package com.medtronic.ndt.carelink.util

import com.medtronic.ndt.carelink.pages.dashboard.Mail

class MailUtil extends Mail {
    static void checkEmailBoxForgotUsername(String email, String adminUser) {
        sleep(5000)
        while (getAllMessages(email) == null) {
            sleep(5000)
        }
        def lastLetter = getAllMessages(email).last
//        def tempPasswordText = lastLetter.body.split('\n').getAt(3).replace('\r', '')
//        println lastLetter
//        println tempPasswordText
        assert lastLetter.fromFull == 'donotreply@medtronic.com'
        assert lastLetter.subject == 'CareLink Username Reminder'
        assert lastLetter.body.split('\n').getAt(2).replace('\r', '') == 'Your username is ' + adminUser.toLowerCase()
        assert lastLetter.body.split('\n').getAt(7).replace('\r', '') == 'The temporary password will expire 24 hours after it was issued, so please use it to sign in to the system at your earliest opportunity.  If it expires before you have a chance to use it, you can request a new temporary password from the CareLink home page.'
        assert lastLetter.body.split('\n').getAt(9).replace('\r', '') == 'This is an automated message; please do not reply.  If you need further assistance, please call customer support at 1-800-646-4633.'
        assert lastLetter.body.split('\n').getAt(12).replace('\r', '') == 'Thank you,'
        assert lastLetter.body.split('\n').getAt(14).replace('\r', '') == 'Medtronic Diabetes'
        assert lastLetter.body.split('\n').getAt(15).replace('\r', '') == '[CONFIDENTIALITY AND PRIVACY NOTICE] Information transmitted by this email is proprietary to Medtronic and is intended for use only by the individual or entity to which it is addressed, and may contain information that is private, privileged, confidential or exempt from disclosure under applicable law. If you are not the intended recipient or it appears that this mail has been forwarded to you without proper authority, you are notified that any use or dissemination of this information in any manner is strictly prohibited. In such cases, please delete this mail from your records. To view this notice in other languages you can either select the following link or manually copy and paste the link into the address bar of a web browser: http://emaildisclaimer.medtronic.com'
    }
    static void waitForLetter(String email, Integer amount){
        while (Mail.getAllMessagesWithoutWait(email).size() != amount){
            sleep(5000)
        }
    }

    static void checkEmailBoxMFAVerifyCode(String email) {
        sleep(5000)
        while (getAllMessages(email) == null) {
            sleep(5000)
        }
        def lastLetter = getAllMessages(email).last
        assert lastLetter.fromFull == 'donotreply@medtronic.com'
        assert lastLetter.subject == 'CareLink Verification Code'
    }
}
