package com.inoastrum.pharmaquestionservice.repositories;

import com.inoastrum.pharmaquestionservice.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findAllByPharmacyId(UUID pharmacyId);

}
