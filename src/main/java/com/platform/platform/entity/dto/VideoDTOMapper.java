package com.platform.platform.entity.dto;

import com.platform.platform.entity.Video;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class VideoDTOMapper implements Function<Video, VideoDTO> {

    @Override
    public VideoDTO apply(Video video) {
        return new VideoDTO(
                video.getId(),
                video.getUrl(),
                video.getTitle()
        );
    }
}
