package com.medtronic.ndt.carelink.util

import com.medtronic.ndt.carelink.util.enums.Environment

class CarelinkUtil {

    /**
     * Gets the url of a specified CareLink environment
     * @param environment the environment to get the url of
     * @return the CareLink url for the specified CareLink environment
     */
    static String getUrlFor(Environment environment) {
        switch (environment) {
            case Environment.PROD:
                return 'https://carelink.minimed.com/ipro/hcp/login.jsf?bhcp=1'
            case Environment.STAGING:
                return 'https://carelink-test.minimed.com/ipro/hcp/login.jsf?bhcp=1'
            case Environment.DEV:
                return 'https://build16.ols.minimed.com/ipro/hcp/login.jsf?bhcp=1'
            case Environment.QA:
                return 'https://carelink-qa.ols.minimed.com/ipro/hcp/login.jsf?bhcp=1'
        }
    }
}
