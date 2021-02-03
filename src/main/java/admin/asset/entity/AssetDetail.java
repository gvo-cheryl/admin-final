package admin.asset.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "adetail_seq_gen",
        sequenceName = "adetail_seq",
        initialValue = 1,
        allocationSize = 1)
public class AssetDetail {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "adetail_seq_gen")
    @Column(name = "assetdetail_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @OneToMany(mappedBy = "assetDetail")
    private List<AssetItem> assetItems = new ArrayList<>();

}
