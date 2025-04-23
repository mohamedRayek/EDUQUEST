package com.platform.platform.entity.dto;

import com.platform.platform.entity.Course;
import com.platform.platform.entity.File;
import com.platform.platform.entity.Image;
import com.platform.platform.entity.Video;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseDTOMapper implements Function<Course, CourseDTO> {

    private final Function<Video, VideoDTO> videoDTOMapper;
    private final Function<Image, ImageDTO> imageDTOMapper;
    private final Function<File, FileDTO> fileDTOMapper;

    public CourseDTOMapper(
            Function<Video, VideoDTO> videoDTOMapper,
            Function<Image, ImageDTO> imageDTOMapper,
            Function<File, FileDTO> fileDTOMapper
            ) {
        this.videoDTOMapper = videoDTOMapper;
        this.imageDTOMapper = imageDTOMapper;
        this.fileDTOMapper = fileDTOMapper;
    }

    @Override
    public CourseDTO apply(Course Course) {
        return new CourseDTO (
                Course.getId(),
                Course.getTitle(),
                Course.getSubTitle(),
                Course.getSubject(),
                Course.getDescription(),
                Course.getPrice(),
                Course.getDiscount(),
                Course.getDuration(),
                Course.getLessonNumber(),
                Course.getLevel(),
                Course.getStudentNumber(),
                Course.getRate(),
                Course.getGoals(),
                Course.getVideos().stream().map(videoDTOMapper).collect(Collectors.toList()),
                Course.getImages().stream().map(imageDTOMapper).collect(Collectors.toList()),
                Course.getFiles().stream().map(fileDTOMapper).collect(Collectors.toList())


        );
    }
}
