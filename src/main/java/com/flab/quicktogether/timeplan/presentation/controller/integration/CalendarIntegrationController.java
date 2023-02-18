package com.flab.quicktogether.timeplan.presentation.controller.integration;

import com.flab.quicktogether.calendar_integration.GoogleCalendarIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class CalendarIntegrationController {

    private final GoogleCalendarIntegrationService googleCalendarIntegrationService;

    @RequestMapping(path = "/integration/oauth/google", method = RequestMethod.GET)
    public void callBackCode(String code) {
        googleCalendarIntegrationService.integrate(code);
    }
}
