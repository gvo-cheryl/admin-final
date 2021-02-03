package admin.asset.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "token_seq_gen",
        sequenceName = "token_seq",
        initialValue = 1,
        allocationSize = 1)
public class AuthToken {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "token_seq_gen")
    @Column(name = "auth_token_id")
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "member_id")
    private Member member;


}
