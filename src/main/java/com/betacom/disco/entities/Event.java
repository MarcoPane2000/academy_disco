package com.betacom.disco.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "minimum_age")
    private Integer minimunAge;

    private String description;

    @Column(name = "images_path")
    private String imagesPath;

    @Column(name = "base_price")
    private Double basePrice;

    @Column(name = "vip_price")
    private Double vipPrice;

    @Column(name = "max_capacity")
    private Integer maxCapacity;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "music_genre")
    private EMusicGenre musicGenre;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "event")
    private List<TableEvent> tableEvents;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventImage> images;
}
