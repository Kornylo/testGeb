package com.medtronic.ndt.carelink.context

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * Marks a test as a regression test.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface RegressionTest {

}