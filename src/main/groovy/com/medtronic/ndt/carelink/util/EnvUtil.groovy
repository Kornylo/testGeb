package com.medtronic.ndt.carelink.util

import com.medtronic.ndt.carelink.util.enums.Browsers
import com.medtronic.ndt.carelink.util.CarelinkUtil
import com.medtronic.ndt.carelink.util.enums.Environment

class EnvUtil {
    /**
     * The base URL for carelink DEV environment
     */
    static final String CL_DEV_URL = System.getProperty('carelinkDevUrl', 'https://build16.ols.minimed.com/ipro/hcp/login.jsf?bhcp=1')
    /**
     * The base URL for carelink QA environment
     */
    static final String CL_QA_URL = System.getProperty('carelinkQaUrl', "https://carelink-qa.ols.minimed.com/ipro/hcp/login.jsf?bhcp=1")
    /**
     * The base URL for carelink STAGING environment
     */
    static final String CL_STAGING_URL = System.getProperty('carelinkStagingUrl', "https://carelink-test.minimed.eu/ipro/hcp/login.jsf?bhcp=1")
    /**
     * The browser name this is used for getting the browser on which the tests needs to run
     */
    static final String ENVIRONMENTS = System.getProperty('geb.env', 'ie')
    /**
     * Boolean input from the user whether to run just the smoke tests, default true (all tests will run)
     */
    static final String SMOKE_TESTS = System.getProperty('Smoke', 'true')
    /**
     * Boolean input from the user whether to run just the regression tests, default false (all tests will run)
     */
    static final String REGRESSION_TESTS = System.getProperty('Regression', 'true')
    /**
     * The language to use for the test
     */
    static final String LANGUAGE = System.getProperty('language', 'en')
    /**
     * The locale to use for the test (ex: Portuguese differs between Brazil and Portugal, so the locale would impact which Strings are used and the device settings)
     */
    static final String LOCALE = System.getProperty('locale')
    /**
     * The country to use for the test
     */
    static final String COUNTRY = System.getProperty('country', 'US')
    /**
     * URL for API Spak -DbaseAPIUrl= "https://build15.ols.minimed.com/ipro/sparkApi/v1"
     */
    final static String baseAPIUrl = System.getProperty('baseAPIUrl', 'http://3.218.56.222:7201/ipro/sparkApi/v1')
    /**
     * SQL instance -Dsql='3.218.56.222' or -Dsql='build15'
     */
    final static String baseSQLInstance = System.getProperty('sql', 'build15')
    /**
     * URL for API MedHelp -DbaseAPIMHUrl= "https://build15.ols.minimed.com/ipro/iPro2DiaryApi/v1"
     */
    final static String baseAPIMHUrl = System.getProperty('baseAPIMHUrl', 'https://build15.ols.minimed.com/ipro/iPro2DiaryApi/v1')
}
