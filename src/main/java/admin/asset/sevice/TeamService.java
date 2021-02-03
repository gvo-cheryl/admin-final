package admin.asset.sevice;

import admin.asset.entity.Admin;
import admin.asset.entity.Team;
import admin.asset.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final AssetService assetService;

    public Team generateTeam() {
        Team newTeam = teamRepository.save(
                Team.builder().position("Junior").admins(new ArrayList<>()).build());
        return newTeam;
    }

    public Team insertTeam(Admin admin, Team team){
        team.getAdmins().add(admin);
        assetService.registerAssetDetail(admin);
        return team;
    }
}
