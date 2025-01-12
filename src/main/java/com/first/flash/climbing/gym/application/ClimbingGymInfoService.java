package com.first.flash.climbing.gym.application;

import com.first.flash.climbing.gym.domian.ClimbingGymInfo;
import com.first.flash.climbing.gym.domian.ClimbingGymInfoRepository;
import com.first.flash.climbing.gym.exception.exceptions.ClimbingGymInfoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClimbingGymInfoService {

    private final ClimbingGymInfoRepository climbingGymInfoRepository;

    public ClimbingGymInfo findById(final Long id) {
        return climbingGymInfoRepository.findById(id)
                                        .orElseThrow(
                                            () -> new ClimbingGymInfoNotFoundException(id));
    }
}
