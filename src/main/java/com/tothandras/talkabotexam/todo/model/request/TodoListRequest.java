package com.tothandras.talkabotexam.todo.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoListRequest {

    List<FilterCriteria> filters;

    List<DateFilterCriteria> dateFilters;

    List<SortingCriteria> sorting;

    Integer offset;

    Integer limit;

}
