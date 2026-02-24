package agenda.seguridad;

import agenda.entidades.Rol;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static agenda.seguridad.Constans.SUPER_SECRET_KEY;
import static agenda.seguridad.Constans.TOKEN_EXPIRATION_TIME;
import static agenda.seguridad.Constans.getSigningKey;

@Configuration
public class JWTAuthenticationConfig {

    public String getJWTToken(String username, Rol rol) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_" + rol.toString());

        String token = Jwts
                .builder()
                .setId("espinozaajgeJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        return "Bearer " + token;
    }
}
