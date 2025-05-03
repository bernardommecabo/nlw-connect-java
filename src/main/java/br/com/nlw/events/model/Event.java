package br.com.nlw.events.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer id;

    @Column(name = "title",length = 255, nullable = false)
    private String title;

    @Column(name = "pretty_name",length = 50, nullable = false, unique = true)
    private String prettyName;

    @Column(name = "location",length = 255, nullable = false)
    private String location;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "start_date")
    private LocalDate starDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    public Event(Integer id, String title, String prettyName, String location, double price, LocalDate starDate,
            LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.title = title;
        this.prettyName = prettyName;
        this.location = location;
        this.price = price;
        this.starDate = starDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPrettyName() {
        return prettyName;
    }
    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public LocalDate getStarDate() {
        return starDate;
    }
    public void setStarDate(LocalDate starDate) {
        this.starDate = starDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", prettyName=" + prettyName + ", location=" + location
                + ", price=" + price + ", starDate=" + starDate + ", endDate=" + endDate + ", startTime=" + startTime
                + ", endTime=" + endTime + "]";
    }
}
