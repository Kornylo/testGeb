package com.medtronic.ndt.carelink.data.logbookevents

import com.medtronic.ndt.carelink.data.enums.LogbookEventType

class NotesEvent extends BaseEvent{
    String notes

    @Override
    LogbookEventType getEventType() {
        LogbookEventType.NOTES
    }
}
