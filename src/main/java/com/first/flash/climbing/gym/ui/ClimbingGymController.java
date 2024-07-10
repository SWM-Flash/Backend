package com.first.flash.climbing.gym.ui;

import com.first.flash.climbing.gym.application.ClimbingGymService;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateRequestDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymCreateResponseDto;
import com.first.flash.climbing.gym.application.dto.ClimbingGymResponseDto;
import com.first.flash.climbing.gym.domian.ClimbingGym;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gyms")
@RequiredArgsConstructor
public class ClimbingGymController {

    private final ClimbingGymService climbingGymService;

    @GetMapping
    public List<ClimbingGymResponseDto> getGyms() {
        return climbingGymService.findAllClimbingGyms().stream()
            .toList();
    }

    @PostMapping
    public ResponseEntity<ClimbingGymCreateResponseDto> createGym(
        @RequestBody final ClimbingGymCreateRequestDto gymCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(climbingGymService.save(gymCreateRequestDto));
    }

    @GetMapping("/{gymId}")
    public ClimbingGym getGymDetails(@PathVariable Long gymId) {
        return climbingGymService.findClimbingGymById(gymId);
    }
}
