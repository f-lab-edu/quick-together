package com.flab.quicktogether.meeting.application;

import com.flab.quicktogether.meeting.presentation.MeetingSearchCondition;
import com.flab.quicktogether.meeting.presentation.MeetingCatalogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingCatalogService {

    private final MeetingCatalogRepository meetingCatalogRepository;
    public List<MeetingCatalogDto> getMeetings(Long memberId, MeetingSearchCondition meetingSearchCondition) {


        return null;
    }
}
