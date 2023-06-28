package com.fastcamp.programming.DMaker.repository;

import com.fastcamp.programming.DMaker.entity.Developer;
import com.fastcamp.programming.DMaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDeveloperRepository
        extends JpaRepository<RetiredDeveloper, Long> {

}
