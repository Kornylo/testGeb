import com.kornylo.test.geb.context.SmokeTest

/**
 * Spock configuration file for running carelink smoke tests
 */
runner {
    if (EnvUtil.SMOKE_TESTS.toBoolean()) {
        include SmokeTest
    }

    optimizeRunOrder true
}

report {
    enabled true
    logFileDir 'build\\resources\\test\\CLSpockReport'
    logFileName 'spock-report.json'
    logFileSuffix ''
}
