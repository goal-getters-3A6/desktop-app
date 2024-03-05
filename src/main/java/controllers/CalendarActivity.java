package controllers;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime date;
    private String eventName; // Modification: Remplacement de clientName par eventName
    private Integer eventId; // Modification: Remplacement de serviceNo par eventId

    public CalendarActivity(ZonedDateTime date, String eventName, Integer eventId) { // Modification: Changement des paramètres du constructeur
        this.date = date;
        this.eventName = eventName;
        this.eventId = eventId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "CalendarActivity{" + // Modification: Changement du nom de la classe dans la méthode toString()
                "date=" + date +
                ", eventName='" + eventName + '\'' +
                ", eventId=" + eventId +
                '}';
    }
}
