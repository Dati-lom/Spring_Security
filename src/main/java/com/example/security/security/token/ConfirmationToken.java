package com.example.security.security.token;

import com.example.security.appuser.modules.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_sequence",
            sequenceName = "confirmation_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_sequence"
    )
    @Column(nullable = false)
    private long id;
    @Column(nullable = false)
    private String token;
    private LocalDateTime created;
    private LocalDateTime expired;
    @ManyToOne
    @JoinColumn(nullable = false,name = "app_user_id")
    private AppUser appUser;

    private LocalDateTime confirmedAt;


    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expired,AppUser appUser) {
        this.token = token;
        this.created = created;
        this.expired = expired;

        this.appUser = appUser;
    }
}
