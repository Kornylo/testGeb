package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.LogbookEventType
import com.medtronic.ndt.carelink.data.enums.MealSize

class MealEvent extends BaseEvent {
    MealSize mealSize
    int carbs

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.MEAL
    }
}
