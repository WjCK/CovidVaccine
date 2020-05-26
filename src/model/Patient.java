package model;

import java.util.Date;

import util.Column;
import util.Table;

/**
 * Entity class wich represent a Patient
 */
@Table(tableName = "patientform")
public class Patient {

    @Column(columnName = "id")
    private int id;
    @Column(columnName = "PatientName")
    private String patientName;
    @Column(columnName = "Age")
    private String sex;
    @Column(columnName = "Sex")
    private int age;
    @Column(columnName = "Weight")
    private double weight;
    @Column(columnName = "VaccineDate")
    private Date vaccineDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(Date vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

}