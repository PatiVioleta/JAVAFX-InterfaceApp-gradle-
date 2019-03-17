package model;

import repository.HasId;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Spectacol implements HasId<Integer> {
    private Integer spectacolId;
    private String location;
    private String date;
    private String ora;
    private Integer nrLocDisp;
    private Integer nrLocVand;
    private Artist artist;
    private String numeArtist;

    public Spectacol(Integer spectacolId, String location, String date,String ora, Integer nrLocDisp, Integer nrLocVand, Artist artist) {
        this.spectacolId = spectacolId;
        this.location = location;
        this.date = date;
        this.ora = ora;
        this.nrLocDisp = nrLocDisp;
        this.nrLocVand = nrLocVand;
        this.artist = artist;
        this.numeArtist = artist.getName();
    }

    public Integer getSpectacolId() {
        return spectacolId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNrLocDisp() {
        return nrLocDisp;
    }

    public void setNrLocDisp(Integer nrLocDisp) {
        this.nrLocDisp = nrLocDisp;
    }

    public Integer getNrLocVand() {
        return nrLocVand;
    }

    public void setNrLocVand(Integer nrLocVand) {
        this.nrLocVand = nrLocVand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacol spectacol = (Spectacol) o;
        return Objects.equals(spectacolId, spectacol.spectacolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spectacolId);
    }

    @Override
    public Integer getId() {
        return spectacolId;
    }

    @Override
    public void setId(Integer integer) {
        spectacolId = integer;
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "spectacolId=" + spectacolId +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", nrLocDisp=" + nrLocDisp +
                ", nrLocVand=" + nrLocVand +
                ", artist=" + artist +
                '}';
    }

    public void setSpectacolId(Integer spectacolId) {
        this.spectacolId = spectacolId;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        this.numeArtist = artist.getName();
    }

    public String getNumeArtist() {
        return numeArtist;
    }

    public void setNumeArtist(String numeArtist) {
        this.numeArtist = numeArtist;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }
}
