package com.nagakawa.guarantee.schedule;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
public class SchedulingTaskProperties {

    //@Value("${scheduling.doctor-appointment.enable}")
    private Boolean doctorAppointmentEnable = false;
}
