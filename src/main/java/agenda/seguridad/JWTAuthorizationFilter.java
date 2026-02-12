package agenda.seguridad;

import io.jsonwebtoken.*;
import jakarta.servlet.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            if (isJWTValid(request)) {
                Claims claims = getClaimsFromJWT(request);

                if (claims.get("authorities") != null) {
                    setUpSpringAuthentication(claims);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | io.jsonwebtoken.security.SignatureException | IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }

    private boolean isJWTValid(HttpServletRequest request) {
        String authHeader = request.getHeader(Constans.HEADER_AUTHORIZATION_KEY);
        return authHeader != null && authHeader.startsWith(Constans.TOKEN_BEARER_PREFIX);
    }

    private Claims getClaimsFromJWT(HttpServletRequest request) {
        String jwtToken = request.getHeader(Constans.HEADER_AUTHORIZATION_KEY)
                .replace(Constans.TOKEN_BEARER_PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(Constans.getSigningKey(Constans.SUPER_SECRET_KEY))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        var auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(), // username
                null, // password no la necesitamos aquí
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
