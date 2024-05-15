package com.example.backend.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cinema")
public class Cinema {
    @Id
    @Column(name = "cinemaid", nullable = false)
    private Integer id;

    @Column(name = "city", length = Integer.MAX_VALUE)
    private String city;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "country", length = Integer.MAX_VALUE)
    private String country;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
