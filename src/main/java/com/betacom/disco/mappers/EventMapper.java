package com.betacom.disco.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betacom.disco.dtos.EventResponseDto;
import com.betacom.disco.entities.Event;


@Component
public class EventMapper {

    @Value("${app.images.base-url}")
    private String imagesBasePath;

    public EventResponseDto toDto(Event event) {

        return new EventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getMusicGenre() != null ? event.getMusicGenre().name() : null,
                event.getMinimunAge(),
                event.getBasePrice(),
                event.getVipPrice(),
                event.getDate(),
                event.getImages() != null
                ? event.getImages()
                        .stream()
                        .map(eventImage -> imagesBasePath + event.getImagesPath() + "/" + eventImage.getFileName())
                        .toList()
                : List.of()
        );
    }
}
