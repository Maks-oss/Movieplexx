package com.example.backend.data.nosql;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document("Cinema")
public class CinemaDocument {
    @Id
    private Integer id;

    private String city;

    private String name;

    private String country;

    private String address;

//    public Cinema(Integer id) {
//        this.id = id;
//    }

    public CinemaDocument() {
    }

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CinemaDocument cinema)) return false;
        return Objects.equals(getId(), cinema.getId()) && Objects.equals(getCity(), cinema.getCity()) && Objects.equals(getName(), cinema.getName()) && Objects.equals(getCountry(), cinema.getCountry()) && Objects.equals(getAddress(), cinema.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getName(), getCountry(), getAddress());
    }
}
