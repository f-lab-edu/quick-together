package com.flab.quicktogether.alarm;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FcmConfig.class})
public class AlarmConfig {
}