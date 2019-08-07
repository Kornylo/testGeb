package com.medtronic.ndt.carelink.util

import geb.Page
import groovy.sql.Sql
import java.sql.SQLException

class DataBaseRequestsUtil extends Page {
    static def connection() {
        //do not forget -Dsql='3.218.56.222' or -Dsql='build15'
        if (EnvUtil.getBaseSQLInstance().contains('3.218.56.222')) {
            String dbUrl = 'jdbc:oracle:thin:@ipro-db-1.cubik4o1i2vi.us-east-1.rds.amazonaws.com:1521:IPROD'
            String dbUser = 'ipro_update'
            String dbPassword = 'G0D1abetes$'
            def db = [
                    url     : dbUrl,
                    user    : dbUser,
                    password: dbPassword,
                    driver  : 'oracle.jdbc.OracleDriver']
            def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
            return sql
        }
        if (EnvUtil.getBaseSQLInstance().contains('build15')) {
            String dbUrl = 'jdbc:oracle:thin:@oraint03.ols.minimed.com:1521:ipro2i'
            String dbUser = 'BUILD15NB'
            String dbPassword = 'BUILD15NB'
            def db = [
                    url     : dbUrl,
                    user    : dbUser,
                    password: dbPassword,
                    driver  : 'oracle.jdbc.OracleDriver']
            def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
            return sql
        }
    }

    static void changeExpiredPasswordTime(String userName) {
        final String date1 = connection().rows("select password_ts from ipro_account  where user_name = $userName").PASSWORD_TS
        try {
            connection().rows("update ipro_account set password_ts = password_ts-1 where user_name = $userName")
        } catch (SQLException) {
        }
        final String date2 = connection().rows("select password_ts from ipro_account  where user_name = $userName").PASSWORD_TS
        connection().close()

        final dateStr1 = date1.replace('[', '')
        final dateStr2 = date2.replace('[', '')
        println('Today ' + dateStr1)
        println('Today-1 ' + dateStr2)
        def pattern = "yyyy-mm-dd HH:mm:ss"
        def today = new Date().parse(pattern, dateStr1)
        def yesterday = new Date().parse(pattern, dateStr2)
        assert today > yesterday, 'Execute date is earlier'
    }

    static void qa_Queries_1(String email, String eventType) {
        def sub_type = connection().rows("select iae.SUB_TYPE from ipro_audit_event iae INNER JOIN ipro_account ia ON ia.ID=iae.ACCOUNT_ID where ia.USER_NAME = $email").SUB_TYPE
        if (sub_type == null || sub_type == '') {
            sleep(10000)
        }
        assert sub_type.contains(eventType)
        connection().close()
    }

    static void deviceSnapshotPart(String email, String details) {
        def detail = connection().rows("select iae.DETAIL from ipro_audit_event iae INNER JOIN ipro_account ia ON ia.ID=iae.USER_ACCOUNT_ID where ia.USER_NAME = $email and SUB_TYPE = 'DEVICE_SNAPSHOT_PART'").DETAIL
        if (detail == null || detail == '') {
            sleep(10000)
        }
        def clobTest = [(oracle.sql.CLOB) detail[0], (oracle.sql.CLOB) detail[1]]
        def bodyText = clobTest?.asciiStream.text
        println bodyText.get(0)
        println bodyText.get(1)
        assert bodyText.get(0).contains(details)
        assert bodyText.get(1).contains(details)
        connection().close()
    }

    static void qaQueries1CheckMissedType(String email, String type) {
        def sub_type = connection().rows("select iae.SUB_TYPE from ipro_audit_event iae INNER JOIN ipro_account ia ON ia.ID=iae.ACCOUNT_ID where ia.USER_NAME = $email").SUB_TYPE
        if (sub_type == null || sub_type == '') {
            sleep(2000)
        }
        assert !sub_type.contains(type)
        connection().close()
    }

    static void passwordExpiration(String userName) {
        def beforeReset = connection().rows("Select password_ts from ipro_account where user_name = $userName").toString()
        println(beforeReset)
        try {
            connection().rows("update ipro_account set password_ts = password_ts-90 where user_name = $userName")
        } catch (SQLException) {
        }
        final String afterReset = connection().rows("Select password_ts from ipro_account where user_name = $userName").toString()
        println(afterReset)
        assert beforeReset > afterReset
        connection().close()
    }

    static void qa_Queries_2(String patientId, String clinicId) {
        def patient_id = connection().rows("select p.id from ipro_patient p join ipro_clinic c on p.clinic_id = c.id where p.external_id = $patientId and c.clinic_identifier = $clinicId").toString().replace("[[ID:", "").replace("]]", "")
        println(patient_id)
        try {
            connection().rows("insert into ipro_patient_therapy(ID, OPT_LOCK, TS, SYSTEM_VERSION, PATIENT_ID, THERAPY_TYPE_ID) values (ipro_patient_therapy_seq.nextval, 1, sysdate, '1', $patient_id, 2)")
        } catch (SQLException) {
        }
        connection().close()
    }

    static void qa_Queries_3(String patientId, String clinicId) {
        def patient_id = connection().rows("select p.id from ipro_patient p join ipro_clinic c on p.clinic_id = c.id where p.external_id = $patientId and c.clinic_identifier = $clinicId").toString().replace("[[ID:", "").replace("]]", "")
        println(patient_id)
        try {
            connection().rows("delete from ipro_patient_therapy where PATIENT_ID = $patient_id")
        } catch (SQLException) {
        }
        connection().close()
        try {
            connection().rows("insert into ipro_patient_therapy(ID, OPT_LOCK, TS, SYSTEM_VERSION, PATIENT_ID, THERAPY_TYPE_ID) values(ipro_patient_therapy_seq.nextval, 1, sysdate, '1', $patient_id, 1)")
        } catch (SQLException) {
        }
        connection().close()
    }

    static void checkPatientCreated(String patientId, Object body, Object therapy) {
        def detail = connection().rows("select FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, EMAIL_ADDRESS, PHYSICIAN, DIABETES_TYPE from ipro_patient where EXTERNAL_ID = $patientId")
        def therapyType = connection().rows("select itt.DESCRIPTION from IPRO_PATIENT_THERAPY ipt inner join IPRO_THERAPY_TYPE  itt on itt.ID=ipt.THERAPY_TYPE_ID inner join ipro_patient ip on ip.ID=ipt.PATIENT_ID where ip.EXTERNAL_ID = $patientId order by itt.DESCRIPTION desc ").DESCRIPTION
        if (therapyType == null || therapyType == '' || detail == null || detail == '') {
            sleep(10000)
        }
        def i = 0
        body.each {
            assert detail.collect { it.values() }.get(0).getAt(i).toString() == body.getAt(i)
            i++
        }
        assert therapyType == therapy
        connection().close()
    }
}