package com.fastcamp.programming.DMaker.dto;

import com.fastcamp.programming.DMaker.code.StatusCode;
import com.fastcamp.programming.DMaker.entity.Developer;
import com.fastcamp.programming.DMaker.type.DeveloperLevel;
import com.fastcamp.programming.DMaker.type.DeveloperSkillType;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeveloperDetailDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private StatusCode statusCode;
    private String name;
    private Integer age;

    public static DeveloperDetailDto fromEntity(Developer developer) {
       return DeveloperDetailDto.builder()
               .developerLevel(developer.getDeveloperLevel())
               .developerSkillType(developer.getDeveloperSkillType())
               .experienceYears(developer.getExperienceYears())
               .memberId(developer.getMemberId())
               .statusCode(developer.getStatusCode())
               .name(developer.getName())
               .age(developer.getAge())
               .build();
    }

}
