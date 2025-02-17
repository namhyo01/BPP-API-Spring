package com.example.api.preference.service;

import com.example.api.preference.adapter.out.persistence.PreferenceEntity;
import com.example.api.preference.adapter.out.persistence.PreferenceMapperInterface;
import com.example.api.preference.application.port.in.ComparePreferenceUsecase;
import com.example.api.preference.application.port.in.FindPreferenceUsecase;
import com.example.api.preference.application.port.in.SavePreferenceUsecase;
import com.example.api.preference.application.port.out.ComparePreferencePort;
import com.example.api.preference.application.port.out.FindPreferencePort;
import com.example.api.preference.application.port.out.SavePreferencePort;
import com.example.api.preference.domain.Preference;
import com.example.api.preference.dto.ComparePreferenceDto;
import com.example.api.preference.dto.FindPreferenceDto;
import com.example.api.preference.dto.SavePreferenceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreferenceService implements SavePreferenceUsecase, FindPreferenceUsecase, ComparePreferenceUsecase {
    private final PreferenceMapperInterface preferenceMapper;
    private final SavePreferencePort savePreferencePort;
    private final FindPreferencePort findPreferencePort;
    private final ComparePreferencePort comparePreferencePort;
    
    // CRUD
    
    @Override
    @Transactional
    public FindPreferenceDto createPreference(SavePreferenceDto preferenceDto) {
        Preference preference = savePreferencePort.createPreference(preferenceMapper.toDomain(preferenceDto));
        return preferenceMapper.toDto(preference);
    }
    
    @Override
    public Optional<ComparePreferenceDto> getPreferenceByPreferenceId(Long preferenceId) {
        return findPreferencePort.getPreferenceByPreferenceId(preferenceId)
                .map(PreferenceEntity::toCompareDto);
    }
    
    @Override
    @Transactional
    public FindPreferenceDto updatePreference(Long preferenceId, SavePreferenceDto preferenceDto) {
        Preference preference = savePreferencePort.updatePreference(preferenceId, preferenceMapper.toDomain(preferenceDto));
        return preferenceMapper.toDto(preference);
    }
    
    // ComparePreference
    
    @Override
    public ComparePreferenceDto getUserPreference(Long userId) {
        Long preferenceId = comparePreferencePort.getUserPreferenceId(userId);
        return this.getPreferenceByPreferenceId(preferenceId).orElseThrow();
    }
    
    @Override
    public ComparePreferenceDto getMatchingPreference(Long matchingId) {
        Long preferenceId = comparePreferencePort.getMatchingPreferenceId(matchingId);
        return this.getPreferenceByPreferenceId(preferenceId).orElseThrow();
    }
    
    @Override
    public Integer getMatchingScore(Long userId, Long matchingId) {
        ComparePreferenceDto userPreference = this.getUserPreference(userId);
        ComparePreferenceDto matchingPreference = this.getMatchingPreference(matchingId);
        Integer score = 0;
        score += Math.abs(userPreference.getAlcoholAmount() - matchingPreference.getAlcoholAmount());
        score += Math.abs(userPreference.getMateAllowedAlcohol() - matchingPreference.getMateAllowedAlcohol());
        score += Math.abs(userPreference.getAllowedMoveTime() - matchingPreference.getAllowedMoveTime());
        score += Math.abs(userPreference.getAllowedPeople() - matchingPreference.getAllowedPeople());
        score += userPreference.getSmoke() == matchingPreference.getSmoke() ? 0 : 1;
        score += Math.abs(userPreference.getSlang() - matchingPreference.getSlang());
        return score;
    }
}