package com.tothandras.talkabotexam.todo.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Setter
public class DateFilterCriteria {

    String fieldName;

    LocalDateTime startDate;

    LocalDateTime endDate;

}
