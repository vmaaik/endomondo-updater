package com.gebarowski.endomondoupdater.model;

import javax.persistence.*;
import java.util.Objects;
//TODO replace with lombok
@Entity
@Table(name = "user_result")
public class UserResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long endomondoProfileId;
    private Double distance;
    private Integer rank;

    public UserResult() {
    }

    public UserResult(Long id, Long endomondoProfileId, Double distance, Integer rank) {
        this.id = id;
        this.endomondoProfileId = endomondoProfileId;
        this.distance = distance;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", endomondo_profile_id=" + endomondoProfileId +
                ", distance=" + distance +
                ", rank=" + rank +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEndomondoProfileId() {
        return endomondoProfileId;
    }

    public void setEndomondoProfileId(Long endomondoProfileId) {
        this.endomondoProfileId = endomondoProfileId;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResult userResult = (UserResult) o;
        return Objects.equals(id, userResult.id) &&
                Objects.equals(endomondoProfileId, userResult.endomondoProfileId) &&
                Objects.equals(distance, userResult.distance) &&
                Objects.equals(rank, userResult.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, endomondoProfileId, distance, rank);
    }
}
