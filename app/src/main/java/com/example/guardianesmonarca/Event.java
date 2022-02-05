package com.example.guardianesmonarca;

public class Event {
    String name,place,date,description,link;

    public Event(){

    }
    public Event(String name,String place, String date, String description, String link){
        this.name=name;
        this.place=place;
        this.date=date;
        this.description=description;
        this.link=link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
