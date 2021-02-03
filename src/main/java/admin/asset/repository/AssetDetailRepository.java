package admin.asset.repository;

import admin.asset.entity.AssetDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetDetailRepository extends JpaRepository<AssetDetail, Long> {
}
