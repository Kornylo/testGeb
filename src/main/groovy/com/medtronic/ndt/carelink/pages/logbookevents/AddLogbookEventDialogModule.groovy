package com.medtronic.ndt.carelink.pages.logbookevents

import com.medtronic.ndt.carelink.data.enums.ExerciseIntensity
import com.medtronic.ndt.carelink.data.enums.InsulinType
import com.medtronic.ndt.carelink.data.enums.MealSize
import com.medtronic.ndt.carelink.data.logbookevents.*
import com.medtronic.ndt.carelink.pages.components.RadioButton
import geb.Module
import groovy.util.logging.Slf4j

import java.time.LocalDate
import java.time.LocalTime

@Slf4j
class AddLogbookEventDialogModule extends Module {

    static content = {
        closeButton { $('a', class: 'ui-dialog-titlebar-close') }
        eventHour { $('#add_time_hour') }
        eventMinute { $('#add_time_minute') }
        bgValueInput { $('#add_bg') }
        carbValueInput { $('#carb-amount') }
        insulinValueInput { $('#insulin-units') }
        exerciseDurationHoursInput { $('#exercise-duration-hh') }
        exerciseDurationMinutesInput { $('#exercise-duration-mm') }
        sleepHoursInput { $('#sleep_wake_hour') }
        sleepMinutesInput { $('#sleep_wake_minute') }
        mealSizeCheckbox { MealSize mealSize -> $("#mealSize-${mealSize.toString().toLowerCase()}").module(RadioButton) }
        insulinTypeCheckbox { InsulinType insulinType -> $("#insulinType-${insulinType.toString().toLowerCase()}").module(RadioButton) }
        medicationCheckbox { $('#add_medication').module(RadioButton) }
        exerciseLevelCheckbox { ExerciseIntensity exerciseIntensity -> $("#exerciseLevel-${exerciseIntensity.toString().toLowerCase()}").module(RadioButton) }
        wakeupCheckbox { $('#did_sleep').module(RadioButton) }
        notesCheckbox { $('#add_notes').module(RadioButton) }
        notesField { $('#notes') }
        enterButton { $('#add_enter') }
    }

    public <T extends BaseEvent> void logEvent(T event, boolean save = true, boolean close = true) {
        setEventTime(event.eventTime.toLocalTime())

        if (event.notes) {
            setNotes(event.notes)
        }

        switch (event.class) {
            case ExerciseEvent:
                if ((event as ExerciseEvent).duration) {
                    setExercise((event as ExerciseEvent).intensity, Math.floor((event as ExerciseEvent).duration.toMinutes() / 60).toInteger(), (event as ExerciseEvent).duration.toMinutes() % 60)
                } else {
                    setExercise((event as ExerciseEvent).intensity)
                }
                break
            case InsulinEvent:
                setInsulin((event as InsulinEvent).insulinType, (event as InsulinEvent).units)
                break
            case MealEvent:
                setCarbs((event as MealEvent).mealSize, (event as MealEvent).carbs)
                break
            case MedicineEvent:
                // Medicine events in Carelink only have a true/false checkbox
                setMeds(true)
                break
            case SleepEvent:
                setSleep((event as SleepEvent).sleepTime.toLocalTime(), (event as SleepEvent).wakeTime.toLocalTime())
                break
        }

        !save ?: enterButton.click()
        !close ?: closeButton.click()
    }

    void setEventTime(LocalTime eventTime) {
        log.info "Setting event time to $eventTime"
        // TODO: time formatting (am/pm vs 24-hour time)
        eventHour.value("${eventTime.hour % 13}")
        eventMinute.value("${eventTime.minute}")
    }

    void setEventDate(LocalDate eventDate) {
        // TODO
    }

    void setBG(int bg) {
        log.info "Setting bg value to $bg"
        bgValueInput.value("$bg")
    }

    void setCarbs(MealSize mealSize, int carbs) {
        log.info "Setting meal size to $mealSize and carbs to $carbs"
        carbValueInput.value("$carbs")
        mealSizeCheckbox(mealSize).setSelected(true)
    }

    void setInsulin(InsulinType insulinType, int units = 0) {
        if (units > 0) {
            log.info "Setting insulin type to $insulinType with $units units"
            insulinValueInput.value("$units")
        }

        insulinTypeCheckbox(insulinType).setSelected(true)
    }

    void setMeds(boolean medsUsed) {
        medicationCheckbox.setSelected(medsUsed)
    }

    void setExercise(ExerciseIntensity exerciseIntensity, int hours = -1, int minutes = -1) {
        if (hours >= 0 && minutes >= 0) {
            log.info "Setting exercise intensity to $exerciseIntensity with a duration of $hours hours and $minutes minutes"
            exerciseDurationHoursInput.value("$hours")
            exerciseDurationMinutesInput.value("$minutes")
        }

        exerciseLevelCheckbox(exerciseIntensity).setSelected(true)
    }

    void setSleep(LocalTime sleepTime, LocalTime wakeTime) {
        log.info "Setting sleep time to $sleepTime and wake time to $wakeTime"
        wakeupCheckbox.setSelected(false)
        sleepHoursInput.value("$sleepTime.hour")
        sleepMinutesInput.value("$sleepTime.minute")

        wakeupCheckbox.setSelected(true)
        sleepHoursInput.value("$wakeTime.hour")
        sleepMinutesInput.value("$wakeTime.minute")
    }

    void setNotes(String notes) {
        log.info "Adding notes $notes"
        notesCheckbox.setSelected(true)
        notesField.value("$notes")
    }
}
