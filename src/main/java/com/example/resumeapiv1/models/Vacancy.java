package com.example.resumeapiv1.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vacancy")
public class Vacancy {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany
    @Column(name = "person_id")
    private List<Person> responded;

    public Vacancy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getResponded() {
        return responded;
    }

    public void setResponded(List<Person> responded) {
        this.responded = responded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
