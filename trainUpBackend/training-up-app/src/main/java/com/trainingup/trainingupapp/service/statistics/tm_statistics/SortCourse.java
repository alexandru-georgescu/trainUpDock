package com.trainingup.trainingupapp.service.statistics.tm_statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class SortCourse {
    private String name;
    private int nr;
}
