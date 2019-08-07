package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.LogbookEventType

import java.time.LocalDateTime

class SleepEvent extends BaseEvent {
    LocalDateTime sleepTime
    LocalDateTime wakeTime

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.SLEEP
    }
}
