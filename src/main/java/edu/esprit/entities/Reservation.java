package edu.esprit.entities;

import java.util.Objects;

public class Reservation {
private int IdReservation;
private Seance seance;
private User user;
//private ServiceSeance seanceService;

    public Reservation()
    {
    }

    public Reservation(int idReservation, Seance seance,User user) {
        IdReservation = idReservation;
        this.seance = seance;
        this.user=user;

    }

    public Reservation(Seance seance,User user) {
        this.seance = seance;

        this.user=user;
    }


    public int getIdReservation() {
        return IdReservation;
    }

    public Seance getSeance() {
        return seance;
    }

    public User getUser(){return user; }
    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public  void setUser(User user){this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return IdReservation == that.IdReservation && Objects.equals(seance, that.seance) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(IdReservation, seance);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "IdReservation=" + IdReservation +
                ", seance=" + seance +

                ",user="+user+
                '}';
    }
}
