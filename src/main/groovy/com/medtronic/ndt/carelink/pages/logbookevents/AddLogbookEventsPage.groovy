package com.medtronic.ndt.carelink.pages.logbookevents

import com.medtronic.ndt.carelink.data.logbookevents.BaseEvent
import geb.Page
import com.medtronic.ndt.carelink.data.enums.TherapyType
import com.medtronic.ndt.carelink.data.logbookevents.InsulinEvent

class AddLogbookEventsPage extends Page implements DefaultAbstractLogbookEventPage{
    static at = {
        // TODO: share !loadingDialog.displayed with other page objects for more thorough at checks
        !loadingDialog.displayed && !addLogbookEventOverlay.displayed && addLogBook.displayed
    }

    static content = {
        loadingDialog (required: false, wait: 5) {$('#loading')}
        addLogbookEventOverlay (required: false, wait: 2) {$('.ui-dialog-overlay')}
        addLogBook (required:false){$('button', name: 'log_add_btn')}
        newRecordPopup (required: false) {$('.logbook_add_dialog').module(AddLogbookEventDialogModule)}
        addTimeHour(required:false){$'input', id:'add_time_hour'}
        addTimeMinute (required:false){$'input', id:'add_time_minute'}
        insulinUnits (required: false){$'input', id:'insulin-units'}
        getInsulinEventRadioButton(required: false){$'input', id:'insulinType-premix'}
        enterLogbook(required:false){$'input', id:'add_enter'}
    }

    AddLogbookEventDialogModule clickAddLogbook() {
        addLogBook.click()
        newRecordPopup as AddLogbookEventDialogModule
    }

    void addHour(){
        addTimeHour.value('8')
    }
    void addMinute() {
        addTimeMinute.value('8')
    }
    void clickEnterLogbook() {
        enterLogbook.click()
    }
    @Override
    <T extends BaseEvent> void logEvent(T event) {
        if (event instanceof InsulinEvent) {
            if (event.units) {
                insulinUnits.("${event.units}")
            }
            if (event.insulinType && [TherapyType.BOLUS, TherapyType.BASAL, TherapyType.PREMIX].find{event.insulinType}) {
                getInsulinEventRadioButton(event.insulinType).getSelfReference().setChecked(true)
            }
        }

    }
}
