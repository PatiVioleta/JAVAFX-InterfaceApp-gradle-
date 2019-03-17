package model;

import repository.HasId;

import java.util.Objects;

public class Artist implements HasId<Integer> {
    private Integer artistId;
    private String name;

    public Artist(Integer artistId, String name) {
        this.artistId = artistId;
        this.name = name;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(artistId, artist.artistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistId);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return artistId;
    }

    @Override
    public void setId(Integer integer) {
        artistId = integer;
    }
}
