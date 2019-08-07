package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.InsulinType
import com.medtronic.ndt.carelink.data.enums.LogbookEventType

class InsulinEvent extends BaseEvent{
    // Valid values: Bolus, Basal, and Premix
    InsulinType insulinType
    int units

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.INSULIN
    }
}
