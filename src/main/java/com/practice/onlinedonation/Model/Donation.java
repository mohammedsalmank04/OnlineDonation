package com.practice.onlinedonation.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donationId;
    private String description;
    private Long donationAmount;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Donation(String description, Long donationAmount, User user) {
        this.description = description;
        this.donationAmount = donationAmount;
        this.user =user;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donationId=" + donationId +
                ", description='" + description + '\'' +
                ", donationAmount=" + donationAmount +
                ", user=" + user.getLastName() +
                '}';
    }
}
