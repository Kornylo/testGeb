import com.kornylo.test.geb.context.RegressionTest

/**
 * Spock configuration file for running screenshot scripts
 */
runner {
    include RegressionTest
}

report {
    enabled true
    logFileDir 'build\\resources\\test\\CLSpockReport'
    logFileName 'spock-report.json'
    logFileSuffix ''
}