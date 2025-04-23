package com.platform.platform.entity.dto;

import com.platform.platform.entity.Image;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
public class ImageDTOMapper implements Function<Image, ImageDTO> {
    @Override
    public ImageDTO apply(Image image) {
        return new ImageDTO(
            image.getId(),
            image.getUrl()
        );
    }
}