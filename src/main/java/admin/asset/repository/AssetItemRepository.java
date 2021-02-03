package admin.asset.repository;

import admin.asset.entity.AssetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetItemRepository extends JpaRepository<AssetItem, Long> {
}
