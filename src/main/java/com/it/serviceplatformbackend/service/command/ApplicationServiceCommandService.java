package com.it.serviceplatformbackend.service.command;

import com.it.serviceplatformbackend.repository.ApplicationServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationServiceCommandService {
    private final ApplicationServiceRepository applicationServiceRepository;

    public boolean deleteApplicationServiceById(Long id) {
        try {
            applicationServiceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
