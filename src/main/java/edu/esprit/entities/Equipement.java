package edu.esprit.entities;

import java.util.Objects;

public class Equipement {
    private int idEq;
    private String nomEq;
    private String descEq;
    private String docEq;
    private String imageEq;
    private String categEq;
    private int noteEq;

    public Equipement (){}

    public Equipement(String nomEq, String descEq, String docEq, String imageEq, String categEq, int noteEq) {
        this.nomEq = nomEq;
        this.descEq = descEq;
        this.docEq = docEq;
        this.imageEq = imageEq;
        this.categEq = categEq;
        this.noteEq = noteEq;
    }

    public Equipement(int idEq, String nomEq, String descEq, String docEq, String imageEq, String categEq, int noteEq) {
        this.idEq = idEq;
        this.nomEq = nomEq;
        this.descEq = descEq;
        this.docEq = docEq;
        this.imageEq = imageEq;
        this.categEq = categEq;
        this.noteEq = noteEq;
    }

    public int getIdEq() {
        return idEq;
    }

    public void setIdEq(int idEq) {
        this.idEq = idEq;
    }

    public String getNomEq() {
        return nomEq;
    }

    public void setNomEq(String nomEq) {
        this.nomEq = nomEq;
    }

    public String getDescEq() {
        return descEq;
    }

    public void setDescEq(String descEq) {
        this.descEq = descEq;
    }

    public String getDocEq() {
        return docEq;
    }

    public void setDocEq(String docEq) {
        this.docEq = docEq;
    }

    public String getImageEq() {
        return imageEq;
    }

    public void setImageEq(String imageEq) {
        this.imageEq = imageEq;
    }

    public String getCategEq() {
        return categEq;
    }

    public void setCategEq(String categEq) {
        this.categEq = categEq;
    }

    public int getNoteEq() {
        return noteEq;
    }

    public void setNoteEq(int noteEq) {
        this.noteEq = noteEq;
    }

    @Override
    public String toString() {
        return "Equipement{" +
                "nomEq='" + nomEq + '\'' +
                ", descEq='" + descEq + '\'' +
                ", docEq='" + docEq + '\'' +
                ", imageEq='" + imageEq + '\'' +
                ", categEq='" + categEq + '\'' +
                ", noteEq=" + noteEq +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipement that = (Equipement) o;
        return idEq == that.idEq && noteEq == that.noteEq && Objects.equals(nomEq, that.nomEq) && Objects.equals(descEq, that.descEq) && Objects.equals(docEq, that.docEq) && Objects.equals(imageEq, that.imageEq) && Objects.equals(categEq, that.categEq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEq, nomEq, descEq, docEq, imageEq, categEq, noteEq);
    }
}
