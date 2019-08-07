package com.medtronic.ndt.carelink.pages.logbookevents

import com.medtronic.ndt.carelink.data.logbookevents.BaseEvent

interface DefaultAbstractLogbookEventPage {

    /**
     * Logs the specified event. Null fields are ignored.
     * @param event the event to log
     */
    public abstract<T extends BaseEvent> void logEvent(T event)
}
