package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FeedbackConverter {
    public Feedback dtoToEntity(FeedbackDto feedbackDto){
        if(feedbackDto==null)
            return null;
        ModelMapper mapper = new ModelMapper();
        log.debug("feedbackDto to convert {}",feedbackDto);
        Feedback feedback = mapper.map(feedbackDto, Feedback.class);
        log.debug("feedback after conversion {}",feedback);
        return feedback;
    }

    public FeedbackDto entityToDto(Feedback feedback){
        if(feedback==null)
            return null;
        ModelMapper mapper = new ModelMapper();
        log.debug("Feedback to convert {}",feedback);
        FeedbackDto feedbackDto = mapper.map(feedback, FeedbackDto.class);
        log.debug("feedbackDto after conversion {}",feedbackDto);
        return feedbackDto;
    }

    public List<Feedback> dtoToEntity(List<FeedbackDto> feedbackDtos){
        if(feedbackDtos==null)
            return Collections.EMPTY_LIST;
        log.debug("Converting List<FeedbackDto>");
        return feedbackDtos.stream().map(this::dtoToEntity).toList();
    }

    public List<FeedbackDto> entityToDto(List<Feedback> feedbacks){
        if(feedbacks==null)
            return Collections.EMPTY_LIST;
        log.debug("Converting List<Feedback>");
        return feedbacks.stream().map(this::entityToDto).toList();
    }
}
