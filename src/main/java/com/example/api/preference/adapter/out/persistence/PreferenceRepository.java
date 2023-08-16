package com.example.api.preference.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long> {
    Optional<PreferenceEntity> getPreferenceByPreferenceId(Long preferenceId);
}