package com.betacom.disco.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "table_events")
public class TableEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;

    @Column(name = "drink_included_limit")
    private Integer drinkIncludedlimit;
    private String state;

    @ManyToOne
    @JoinColumn(name="id_event")
    private Event event;

    @ManyToOne
    @JoinColumn(name="id_table")
    private TableEntity table;

    @OneToMany(mappedBy="tableEvent")
    private List<TableReservation> tableReservations;
}
