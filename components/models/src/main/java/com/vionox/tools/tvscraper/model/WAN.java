package com.vionox.tools.tvscraper.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "wan_networks")
public class WAN
{
    @Id
    @Column(name = "id", updatable = false, unique = true, nullable = false, precision = 10)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "WAN name is required.")
    private String name;

    @Column(name = "enabled", nullable = false)
    @NotNull(message = "WAN must be enabled or disabled with true/false.")
    private boolean enabled = true;
}
