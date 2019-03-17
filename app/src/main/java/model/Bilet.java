package model;

import repository.HasId;

import java.util.Objects;

public class Bilet implements HasId<Integer> {
    private Integer biletId;
    private String numeCumparator;
    private Spectacol spectacol;
    private Integer nrLocDorite;

    public Bilet(Integer biletId, String numeCumparator, Spectacol spectacol, Integer nrLocDorite) {
        this.biletId = biletId;
        this.numeCumparator = numeCumparator;
        this.spectacol = spectacol;
        this.nrLocDorite = nrLocDorite;
    }

    public Integer getBiletId() {
        return biletId;
    }

    public String getNumeCumparator() {
        return numeCumparator;
    }

    public void setNumeCumparator(String numeCumparator) {
        this.numeCumparator = numeCumparator;
    }

    public Spectacol getSpectacol() {
        return spectacol;
    }

    public void setSpectacol(Spectacol spectacol) {
        this.spectacol = spectacol;
    }

    public Integer getNrLocDorite() {
        return nrLocDorite;
    }

    public void setNrLocDorite(Integer nrLocDorite) {
        this.nrLocDorite = nrLocDorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bilet bilet = (Bilet) o;
        return Objects.equals(biletId, bilet.biletId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biletId);
    }

    @Override
    public String toString() {
        return "Bilet{" +
                "biletId=" + biletId +
                ", numeCumparator='" + numeCumparator + '\'' +
                ", spectacol=" + spectacol +
                ", nrLocDorite=" + nrLocDorite +
                '}';
    }

    @Override
    public Integer getId() {
        return biletId;
    }

    @Override
    public void setId(Integer integer) {
        biletId = integer;
    }
}
