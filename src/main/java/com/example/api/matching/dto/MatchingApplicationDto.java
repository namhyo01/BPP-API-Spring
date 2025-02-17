package com.example.api.matching.dto;


import com.example.api.common.type.ApplicationStateEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MatchingApplicationDto {
    @NotNull
    @Min(1)
    private Long userId;
    
    @NotNull
    @Min(1)
    private Long matchingId;
    
    @NotNull
    private ApplicationStateEnum state;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @NotNull
    private Boolean isActive;
}