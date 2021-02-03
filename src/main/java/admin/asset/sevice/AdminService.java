package admin.asset.sevice;

import admin.asset.entity.Admin;
import admin.asset.entity.Member;
import admin.asset.entity.Team;
import admin.asset.entity.enumclass.MemberType;
import admin.asset.repository.AdminRepository;
import admin.asset.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final TeamService teamService;

    @Transactional
    public Admin insertAdmin(Member member, Team team){
        Member newMember = memberRepository.findByEmail(member.getEmail());
        newMember.setMemberType(MemberType.ADMIN);
        Admin admin = adminRepository.save(Admin.builder().build());
        admin.setMember(newMember);
        newMember.setAdmin(admin);
        Team newTeam = teamService.insertTeam(admin, team);
        admin.setTeam(newTeam);
        return admin;
    }



}
