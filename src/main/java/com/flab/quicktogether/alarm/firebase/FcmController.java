package com.flab.quicktogether.alarm.firebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FcmController {

    @GetMapping("/fcmtest")
    public String fcmtest() throws IOException {
        log.info("FcmController /fcmtest start");
        return "fcm/fcmtest";
    }


}