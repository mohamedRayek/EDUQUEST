package com.platform.platform.entity.dto;

import com.platform.platform.entity.StringListConverter;
import com.platform.platform.entity.Video;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;

import java.util.List;

public record CourseDTO(

        Integer id,
        String title,
        String subTitle,
        String subject,
        String description,
        float price,
        float discount,
        Double duration,
        Integer lessonNumber,
        Integer level,
        Integer studentNumber,
        float rate,
        @Column(name = "goals", length = 2000)
        @Convert(converter = StringListConverter.class)
        List<String> goals,
        List<VideoDTO> videos,
        List<ImageDTO> images,
        List<FileDTO> files


) {



}
