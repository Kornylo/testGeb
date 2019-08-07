package com.medtronic.ndt.carelink.tests

import com.perfecto.reportium.client.ReportiumClient
import com.perfecto.reportium.test.TestContext
import geb.Browser
import geb.spock.GebReportingSpec
import com.perfecto.reportium.model.PerfectoExecutionContext
import com.perfecto.reportium.client.ReportiumClientFactory
import com.perfecto.reportium.model.Job
import com.medtronic.ndt.carelink.runlisteners.ReportiumRunListener
import org.openqa.selenium.WebDriver
import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo
import spock.lang.Shared

class CareLinkSpec extends GebReportingSpec implements IGlobalExtension{

    @Shared
    Browser browsers
    @Shared
    ReportiumClient reportiumClient
    @Shared
    WebDriver dr

    Browser getBrowser() {
        if (browsers == null) {
            browsers = new Browser()
        }
        browsers
    }

    // Same as @BeforeClass from JUnit
    def setupSpec() {
        if (System.getProperty("geb.env")=="ie"||System.getProperty("geb.env").equals("firefox")||System.getProperty("geb.env").equals("chrome")){
            println "Test will run locally"
        }else {
            dr = browser.driver
            PerfectoExecutionContext perfecto_Execution_Context = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
                    .withContextTags("Regression")
                    .withWebDriver(dr)
                    .withJob(new Job("Test", 9))
            //        .withJob(new Job(System.getProperty("JOB_NAME"),Integer.parseInt(System.getProperty("BUILD_NUMBER"))
                    .build()
            reportiumClient = new ReportiumClientFactory().createPerfectoReportiumClient(perfecto_Execution_Context)
            setupRunListeners()
        }
    }

    @Override
    void visitSpec(SpecInfo specInfo)
    {
        reportiumClient?.testStart(specInfo.name, new TestContext())
        specInfo.addListener(new ReportiumRunListener(reportiumClient))
    }

    void start() {}
    void stop() {}
    private void setupRunListeners(){
        specificationContext.currentSpec.addListener(new ReportiumRunListener(reportiumClient))
    }

    def cleanup() {
        // Delete any PDF reports we downloaded during the test so the next test doesn't download as "file (1).pdf"
        File f = new File("${System.properties.'user.dir'}/build/file.pdf")
        if (f.exists()) {
            f.delete()
        }
    }
}