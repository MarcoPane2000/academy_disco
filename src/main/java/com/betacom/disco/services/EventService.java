package com.betacom.disco.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.disco.dtos.EventFilter;
import com.betacom.disco.entities.Event;
import com.betacom.disco.entities.EventImage;
import com.betacom.disco.repositories.EventRepository;
import com.betacom.disco.specifications.EventSpecification;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Value("${app.images.root-folder}")
    private String rootUploadFolder;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    //
    public Page<Event> findAll(EventFilter filters, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Event> specifications = EventSpecification.fromFilter(filters);
        return eventRepository.findAll(specifications, pageable);
    }

    public Optional<Event> findByid(Long id) {
        return eventRepository.findById(id);
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public void saveEventImages(Long id, MultipartFile[] images) throws Exception {
        if (images == null || images.length == 0) {
            throw new IllegalArgumentException("No files uploaded");
        }

        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new IllegalArgumentException("No event found with id: " + id);
        }

        //

        Event foundEvent = event.get();

        if (foundEvent.getImagesPath() == null || foundEvent.getImagesPath().isBlank()) {
            String eventFolderName = "event_" + foundEvent.getTitle().replace(" ", "_") + "_" + id;
            foundEvent.setImagesPath(eventFolderName);
        }

        String finalUploadPath = rootUploadFolder + foundEvent.getImagesPath() + "/";

        File directory = new File(finalUploadPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                continue;
            }

            String originalFileName = image.getOriginalFilename();
            String extension =
                originalFileName != null && originalFileName.contains(".")
                ? originalFileName.substring(originalFileName.lastIndexOf("."))
                : ".jpg";

            String uniqueFilename = UUID.randomUUID().toString() + extension;

            Path path = Paths.get(finalUploadPath + uniqueFilename);
            Files.write(path, image.getBytes());

            EventImage eventImage = new EventImage();
            eventImage.setFileName(uniqueFilename);
            eventImage.setEvent(foundEvent);

            foundEvent.getImages().add(eventImage);
        }

        eventRepository.save(foundEvent);
    }
}
