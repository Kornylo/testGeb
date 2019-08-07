import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.SmokeTest

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