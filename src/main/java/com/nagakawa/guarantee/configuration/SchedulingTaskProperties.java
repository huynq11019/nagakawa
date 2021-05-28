package com.nagakawa.guarantee.configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulingTaskProperties {

    //@Value("${scheduling.doctor-appointment.enable}")
    private Boolean doctorAppointmentEnable = false;

    //@Value("${scheduling.doctor-appointment-reminder-notification.enable}")
    private Boolean doctorAppointmentReminderNotificationEnable = false;

    //@Value("${scheduling.notification-reminder.enable}")
    private Boolean notificationReminderEnable = false;

    //@Value("${scheduling.medication-reminder.enable}")
    private Boolean medicationReminderEnable = false;

    //@Value("${scheduling.feedback-reminder.enable}")
    private Boolean feedbackReminderEnable = false;

    //@Value("${scheduling.appointment-cancel-log.check-blocked.enable}")
    private Boolean appointmentCancelLogCheckBlockedEnable = false;

    //@Value("${scheduling.appointment-cancel-log.refresh-daily.enable}")
    private Boolean appointmentCancelLogRefreshDailyEnable = false;

   // @Value("${scheduling.appointment-cancel-log.refresh-weekly.enable}")
    private Boolean appointmentCancelLogRefreshWeeklyEnable = false;

    //@Value("${scheduling.doctor-appointment-active-pending-config.enable}")
    private Boolean appointmentActivePendingConfigEnable = false;

    public Boolean getMedicationReminderEnable() {
        return medicationReminderEnable;
    }

    public void setMedicationReminderEnable(Boolean medicationReminderEnable) {
        this.medicationReminderEnable = medicationReminderEnable;
    }

    public Boolean getNotificationReminderEnable() {
        return notificationReminderEnable;
    }

    public void setNotificationReminderEnable(Boolean notificationReminderEnable) {
        this.notificationReminderEnable = notificationReminderEnable;
    }

    public Boolean getDoctorAppointmentEnable() {
        return doctorAppointmentEnable;
    }

    public void setDoctorAppointmentEnable(Boolean doctorAppointmentEnable) {
        this.doctorAppointmentEnable = doctorAppointmentEnable;
    }

    public Boolean getFeedbackReminderEnable() {
        return feedbackReminderEnable;
    }

    public void setFeedbackReminderEnable(Boolean feedbackReminderEnable) {
        this.feedbackReminderEnable = feedbackReminderEnable;
    }

    public Boolean getAppointmentCancelLogCheckBlockedEnable() {
        return appointmentCancelLogCheckBlockedEnable;
    }

    public Boolean getAppointmentCancelLogRefreshDailyEnable() {
        return appointmentCancelLogRefreshDailyEnable;
    }

    public Boolean getAppointmentCancelLogRefreshWeeklyEnable() {
        return appointmentCancelLogRefreshWeeklyEnable;
    }

    public Boolean getDoctorAppointmentReminderNotificationEnable() {
        return doctorAppointmentReminderNotificationEnable;
    }

    public void setDoctorAppointmentReminderNotificationEnable(Boolean doctorAppointmentReminderNotificationEnable) {
        this.doctorAppointmentReminderNotificationEnable = doctorAppointmentReminderNotificationEnable;
    }

    public Boolean getAppointmentActivePendingConfigEnable() {
        return appointmentActivePendingConfigEnable;
    }

    public void setAppointmentActivePendingConfigEnable(Boolean appointmentActivePendingConfigEnable) {
        this.appointmentActivePendingConfigEnable = appointmentActivePendingConfigEnable;
    }
}
