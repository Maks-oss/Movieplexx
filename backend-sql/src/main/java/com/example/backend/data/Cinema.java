package com.example.backend.data;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cinema")
public class Cinema {
    @Id @GeneratedValue
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

//    public Cinema(Integer id) {
//        this.id = id;
//    }

    public Cinema() {
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
        if (!(object instanceof Cinema cinema)) return false;
        return Objects.equals(getId(), cinema.getId()) && Objects.equals(getCity(), cinema.getCity()) && Objects.equals(getName(), cinema.getName()) && Objects.equals(getCountry(), cinema.getCountry()) && Objects.equals(getAddress(), cinema.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCity(), getName(), getCountry(), getAddress());
    }
}
