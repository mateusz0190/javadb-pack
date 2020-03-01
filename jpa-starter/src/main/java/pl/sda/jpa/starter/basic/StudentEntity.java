package pl.sda.jpa.starter.basic;

import javax.persistence.*;
import java.sql.Date;

@Entity

@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String fullName;
    @Column(name = "year")
    private int yearOfStudy;

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", yearOfStudy=" + yearOfStudy +
                '}';
    }

    StudentEntity() {
    }

    public StudentEntity(String fullName, int yearOfStudy) {
        this.fullName = fullName;
        this.yearOfStudy = yearOfStudy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String name) {
        this.fullName = name;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }


}
