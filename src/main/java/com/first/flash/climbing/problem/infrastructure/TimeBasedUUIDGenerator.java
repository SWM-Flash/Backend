package com.first.flash.climbing.problem.infrastructure;

import com.fasterxml.uuid.Generators;
import com.first.flash.climbing.problem.domain.UUIDGenerator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TimeBasedUUIDGenerator implements UUIDGenerator {

    @Override
    public UUID generate() {
        return Generators.timeBasedEpochGenerator().generate();
    }
}
