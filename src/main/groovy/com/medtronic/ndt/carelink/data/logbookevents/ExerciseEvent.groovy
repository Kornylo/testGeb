package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.ExerciseIntensity
import com.medtronic.ndt.carelink.data.enums.LogbookEventType
import java.time.Duration

class ExerciseEvent extends BaseEvent{
    Duration duration
    ExerciseIntensity intensity

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.EXERCISE
    }
}
