package admin.asset.sevice;

import admin.asset.config.token.JwtTokenProvider;
import admin.asset.entity.AuthToken;
import admin.asset.entity.Member;
import admin.asset.repository.AuthTokenRepository;
import io.jsonwebtoken.JwtBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final AuthTokenRepository authTokenRepository;
    //private final static long AUTH_TOKEN_EXPIRED_DATE = 4 * 7 * 24 * 3600;
    //private final JwtTokenProvider jwtTokenProvider;

    private static String generateAuthToken(){
        return UUID.randomUUID() + LocalDateTime.now().toString();
    }

    public AuthToken insertAuthToken(Member member){
        AuthToken authToken =  AuthToken.builder()
                .token(generateAuthToken())
                .createdAt(LocalDateTime.now())
                //.expiresAt()
                .member(member)
                .build();
        authTokenRepository.save(authToken);
        return authToken;
    }

//    public JwtBuilder getAccessToken(String email, String role){
//        return jwtTokenProvider.createToken(email, role);
//    }

}
