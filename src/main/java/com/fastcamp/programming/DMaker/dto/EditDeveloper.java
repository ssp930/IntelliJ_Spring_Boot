package com.fastcamp.programming.DMaker.dto;

import com.fastcamp.programming.DMaker.entity.Developer;
import com.fastcamp.programming.DMaker.type.DeveloperLevel;
import com.fastcamp.programming.DMaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class EditDeveloper {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;
        @NotNull
        private DeveloperSkillType developerSkillType;
        @NotNull
        @Min(0)
        @Max(20)
        private Integer experienceYears;
    }




}


