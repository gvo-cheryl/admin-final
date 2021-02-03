package admin.asset.sevice;

import admin.asset.entity.Admin;
import admin.asset.entity.AssetDetail;
import admin.asset.entity.AssetItem;
import admin.asset.entity.enumclass.AssetName;
import admin.asset.entity.enumclass.AssetType;
import admin.asset.repository.AdminRepository;
import admin.asset.repository.AssetDetailRepository;
import admin.asset.repository.AssetItemRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final AdminRepository adminRepository;
    private final AssetDetailRepository assetDetailRepository;
    private final AssetItemRepository assetItemRepository;

    // asset detail 등록
    public void registerAssetDetail(Admin admin){

        AssetDetail assetDetail = AssetDetail.builder()
                .admin(admin)
                .assetItems(new ArrayList<>())
                .build();
        AssetDetail newAssetDetail = assetDetailRepository.save(assetDetail);
        newAssetDetail.setAdmin(admin);

        registerAssetItems(newAssetDetail);
    }

    // asset Item 등록
    public void registerAssetItems(AssetDetail newAssetDetail){
        AssetItem assetItem = AssetItem.builder().assetName(AssetName.CHARGER).count(1).brand("logitech").assetType(AssetType.COMPANY).build();
        AssetItem newAssetItem = assetItemRepository.save(assetItem);
        newAssetItem.setAssetDetail(newAssetDetail);
        newAssetDetail.getAssetItems().add(assetItem);
    }

    // asset List 조회
    @Transactional
    public List getAssetDetailList(Long adminId){
        Admin findAdmin = adminRepository.findById(adminId).get();
        AssetDetail assetDetail = findAdmin.getAssetDetail();

        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        List list = new ArrayList();
        JSONObject adminDetail = new JSONObject();
        adminDetail.put("admin_id", findAdmin.getId());
        adminDetail.put("assetDetail_id", findAdmin.getAssetDetail().getId());

        JSONArray assetList = new JSONArray();
        for(AssetItem item : assetDetail.getAssetItems()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getId());
            jsonObject.put("name", item.getAssetName());
            jsonObject.put("brand", item.getBrand());
            jsonObject.put("count", item.getCount());
            jsonObject.put("type", item.getAssetType());
            assetList.add(jsonObject);
        }

        adminDetail.put("assetList", assetList);
        //array.add(memberDetail);
        list.add(adminDetail);


        result.put("assets", array);
        return list;
    }


}
