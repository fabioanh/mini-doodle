package com.doodle.configuration;

import com.doodle.minidoodle.scheduling.Calendar;
import com.doodle.minidoodle.scheduling.annotations.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackageClasses = {Calendar.class},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})})
public class DomainConfiguration {
}
