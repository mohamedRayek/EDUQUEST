package com.platform.platform.entity.dto;

import com.platform.platform.entity.File;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class FileDTOMapper implements Function<File, FileDTO> {
    @Override
    public FileDTO apply(File file) {
        return new FileDTO(
            file.getId(),
            file.getUrl(),
            file.getName()
        );
    }
}