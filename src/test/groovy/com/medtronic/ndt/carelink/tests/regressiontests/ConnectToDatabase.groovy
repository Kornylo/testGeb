package com.medtronic.ndt.carelink.tests.regressiontests

import groovy.sql.Sql
import spock.lang.Specification

@GrabConfig(systemClassLoader = true)
class ConnectToDatabase extends Specification{

    def "Check connection to database"() {

        when: ' '

        then:
        def cl = Class.forName('oracle.jdbc.OracleDriver')
        println(cl)
        def db = [
                url     : 'jdbc:oracle:thin:@oraint03.ols.minimed.com:1521:ipro2i',
                user    : 'BUILD15NB',
                password: 'BUILD15NB',
                driver  : 'oracle.jdbc.OracleDriver']
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        def results = sql.rows("SELECT FIRST_NAME FROM IPRO_PATIENT WHERE CLINIC_ID=2866")
        println(results)

        sql.close()
    }

}

