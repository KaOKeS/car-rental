package com.dawidpater.project.carrental.converter;

import com.dawidpater.project.carrental.dto.CarDto;
import com.dawidpater.project.carrental.dto.FeedbackDto;
import com.dawidpater.project.carrental.entity.Car;
import com.dawidpater.project.carrental.entity.Feedback;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackConverter {
    public Feedback dtoToEntity(FeedbackDto feedbackDto){
        ModelMapper mapper = new ModelMapper();
        Feedback feedback = mapper.map(feedbackDto, Feedback.class);
        return feedback;
    }

    public FeedbackDto entityToDto(Feedback feedback){
        ModelMapper mapper = new ModelMapper();
        FeedbackDto feedbackDto = mapper.map(feedback, FeedbackDto.class);
        return feedbackDto;
    }

    public List<Feedback> dtoToEntity(List<FeedbackDto> feedbackDtos){
        return feedbackDtos.stream().map(feedbackDto -> dtoToEntity(feedbackDto)).collect(Collectors.toList());
    }

    public List<FeedbackDto> entityToDto(List<Feedback> feedbacks){
        return feedbacks.stream().map(feedback -> entityToDto(feedback)).collect(Collectors.toList());
    }
}
