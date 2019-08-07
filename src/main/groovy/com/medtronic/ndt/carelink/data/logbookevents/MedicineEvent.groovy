package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.LogbookEventType

class MedicineEvent extends BaseEvent{

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.MEDICINE
    }
}
