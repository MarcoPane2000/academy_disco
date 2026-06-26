package com.betacom.disco.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="table_reservations")
public class TableReservation {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @ManyToOne
    @JoinColumn(name="id_customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="id_table_event")
    private TableEvent tableEvent;
}
