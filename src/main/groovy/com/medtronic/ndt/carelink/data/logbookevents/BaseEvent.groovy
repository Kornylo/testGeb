package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.LogbookEventType
import java.time.LocalDateTime

abstract class BaseEvent {
    LocalDateTime eventTime
    String notes

    abstract LogbookEventType getEventType()
}
