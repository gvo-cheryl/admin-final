package admin.asset.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "admin_seq_gen",
        sequenceName = "admin_seq",
        initialValue = 1000,
        allocationSize = 1)
public class Admin {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "admin_seq_gen")
    @Column(name = "admin_id")
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "admin")
    private AssetDetail assetDetail;
}
