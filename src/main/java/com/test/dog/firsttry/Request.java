//Модель таблицы "заявок"

package com.test.dog.firsttry;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "vdg_request", catalog = "test_dog", schema = "dbo")
public class Request {

    private Long id;
    private String firstName;
    private String lastName;
    private String description;
    private String address;
    private int daysToComplete;
    private double workCost;
    private boolean prePayment;
    private LocalDateTime dateRequest;

    private Request() {}

    public Request(String firstName, String lastName, String description, String address,
                   int daysToComplete, double workCost, boolean prePayment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.address = address;
        this.daysToComplete = daysToComplete;
        this.workCost = workCost;
        this.prePayment = prePayment;
        this.dateRequest = LocalDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "days_to_complete", nullable = false)
    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    @Column(name = "work_cost", nullable = false)
    public double getWorkCost() {
        return workCost;
    }

    public void setWorkCost(double workCost) {
        this.workCost = workCost;
    }

    @Column(name = "pre_payment", nullable = false)
    public boolean isPrePayment() {
        return prePayment;
    }

    //public String getPrePaymentTxt() {return this.isPrePayment()? "Да" : "Нет";}

    public void setPrePayment(boolean prePayment) {
        this.prePayment = prePayment;
    }

    @Column(name = "date_request", nullable = false)
    public LocalDateTime getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDateTime dateRequest) {
        this.dateRequest = dateRequest;
    }

    @Override
    public String toString() {

        return "Request{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", daysToComplete='" + daysToComplete + '\'' +
                ", workCost='" + workCost + '\'' +
                ", prePayment='" + prePayment + '\'' +
                ", dateRequest='" + dateRequest + '\'' +
                '}';

    }
}
