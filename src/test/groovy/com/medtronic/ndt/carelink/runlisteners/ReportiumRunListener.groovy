package com.medtronic.ndt.carelink.runlisteners

import com.perfecto.reportium.client.ReportiumClient
import com.perfecto.reportium.test.TestContext
import com.perfecto.reportium.test.result.TestResultFactory
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.IterationInfo
import org.spockframework.runtime.model.SpecInfo

class ReportiumRunListener extends AbstractRunListener {
    /**
     * The Perfecto ReportiumClient
     */
    private ReportiumClient reportiumClient
    /**
     * Boolean flag for whether to ignore reporting the test completion, because the test completion has already
     * been reported as a failure in error()
     */
    private boolean didFail

    ReportiumRunListener(ReportiumClient reportiumClient) {
        this.reportiumClient = reportiumClient
        didFail = false

//        SpecInfo info = new SpecInfo()
//        String title = info.name
//        reportiumClient?.testStart(title, new TestContext())
    }

    @Override
    void error(ErrorInfo error) {
        reportiumClient?.testStop(TestResultFactory.createFailure(error.exception))
        didFail = true
    }

    @Override
    void beforeIteration(IterationInfo iteration) {
        if (iteration.feature.isParameterized()) {
            reportiumClient?.testStart(iteration.name, new TestContext())
            didFail = false
        }
    }

    @Override
    void afterIteration(IterationInfo iteration) {
        if (iteration.feature.isParameterized() && !didFail) {
            reportiumClient?.testStop(TestResultFactory.createSuccess())
        }
    }

    @Override
    void beforeFeature(FeatureInfo feature) {
        if (!feature.isParameterized()) {
            reportiumClient?.testStart(feature.spec.name, new TestContext())
        println("Before feature executed")
            didFail = false
        }
    }

    @Override
    void afterFeature(FeatureInfo feature) {
        if (!feature.isParameterized() && !didFail) {
            reportiumClient?.testStop(TestResultFactory.createSuccess())
        }
        println("After feature executed")
    }
//    @Override
//    void beforeSpec(SpecInfo info){
//    //    reportiumClient?.testStart(info.name, new TestContext())
//        println("Before spec executed")
//    }
//
//    @Override
//    void afterSpec(SpecInfo info){
//        if (!didFail) {
//            reportiumClient?.testStop(TestResultFactory.createSuccess())
//            println("After spec executed")
//        }
//    }
}