package com.fastcamp.programming.DMaker.service;

import com.fastcamp.programming.DMaker.code.StatusCode;
import com.fastcamp.programming.DMaker.dto.CreateDeveloper;
import com.fastcamp.programming.DMaker.dto.DeveloperDetailDto;
import com.fastcamp.programming.DMaker.dto.DeveloperDto;
import com.fastcamp.programming.DMaker.dto.EditDeveloper;
import com.fastcamp.programming.DMaker.entity.Developer;
import com.fastcamp.programming.DMaker.entity.RetiredDeveloper;
import com.fastcamp.programming.DMaker.exception.DMakerException;
import com.fastcamp.programming.DMaker.repository.DeveloperRepository;
import com.fastcamp.programming.DMaker.repository.RetiredDeveloperRepository;
import com.fastcamp.programming.DMaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.fastcamp.programming.DMaker.exception.DMakerErrorCode.*;

@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    private static void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if(developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);

        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .name(request.getName())
                .age(request.getAge())
                .build();
        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        //business validation

        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                }));



    }

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll()
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));

    }

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        validateDeveloperLevel(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );
    }

    @Transactional
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                () -> new DMakerException(NO_DEVELOPER)
        );

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);

    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        // 1. EMPLOYED -> RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }
}





























