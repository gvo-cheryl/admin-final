package admin.asset.entity;

import admin.asset.entity.enumclass.AssetName;
import admin.asset.entity.enumclass.AssetType;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "aitem_seq_gen",
        sequenceName = "aitem_seq",
        initialValue = 1,
        allocationSize = 1)
public class AssetItem {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "aitem_seq_gen")
    @Column(name = "asset_id")
    private Long id;
    private String brand;
    private int count;

    @Enumerated(value = EnumType.STRING)
    private AssetType assetType;

    @Enumerated(value = EnumType.STRING)
    private AssetName assetName;

    @ManyToOne
    @JoinColumn(name = "assetdetail_id")
    private AssetDetail assetDetail;

}
