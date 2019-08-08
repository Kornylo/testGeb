import com.kornylo.test.geb.context.RegressionTest


/**
 * Spock configuration file for running carelink smoke tests
 */
runner {
    if (EnvUtil.REGRESSION_TESTS.toBoolean()) {
        include RegressionTest
    }

    optimizeRunOrder true
}

report {
    enabled true
    logFileDir 'build\\resources\\test\\CLSpockReport'
    logFileName 'spock-report.json'
    logFileSuffix ''
}

