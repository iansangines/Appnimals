package com.example.iansangines.appnimals;

/**
 * Created by ian on 13/05/2016.
 */
public class Event {
    private String name;
    private String date;
    private String eventType;
    private String hour;
    private String petChip;
    private String eventLocation;
    private String eventDescription;


    public Event(){
        name = date = eventType = hour = petChip = "";
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getEventType() {return eventType;}

    public void setEventType(String eventType) {this.eventType = eventType;}

    public String getHour() {return hour;}

    public void setHour(String hour) {this.hour = hour;}

    public String getPetChip() {return petChip;}

    public void setPetChip(String petChip) {this.petChip = petChip;}

    public String getEventLocation() {return eventLocation;}

    public void setEventLocation(String eventLocation) {this.eventLocation = eventLocation;}

    public String getEventDescription() {return eventDescription;}

    public void setEventDescription(String eventDescription) {this.eventDescription = eventDescription;}
}

