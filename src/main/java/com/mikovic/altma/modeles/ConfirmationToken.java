package com.mikovic.altma.modeles;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "сonfirmation_tokens")
public class ConfirmationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date  expiryDate;;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id" ,referencedColumnName = "id")
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE,EXPIRATION );
        expiryDate = new Date(cal.getTime().getTime());;
        confirmationToken = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {
    }
    // getters and setters
}
