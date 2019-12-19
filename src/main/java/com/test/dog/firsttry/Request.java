package com.test.dog.firsttry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Request {

    private @Id
    @GeneratedValue Long id;
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
                   int daysToComplete, double workCost, boolean prePayment, LocalDateTime dateRequest) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.address = address;
        this.daysToComplete = daysToComplete;
        this.workCost = workCost;
        this.prePayment = prePayment;
        this.dateRequest = dateRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request employee = (Request) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(description, employee.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, address, dateRequest, workCost, daysToComplete);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }

    public double getWorkCost() {
        return workCost;
    }

    public void setWorkCost(double workCost) {
        this.workCost = workCost;
    }

    public boolean isPrePayment() {
        return prePayment;
    }

    public void setPrePayment(boolean prePayment) {
        this.prePayment = prePayment;
    }

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
