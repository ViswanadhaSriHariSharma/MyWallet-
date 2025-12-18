package Banking.MyApplication.security;

import Banking.MyApplication.CustomUserDetailsService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws java.io.IOException, jakarta.servlet.ServletException {

        String path = request.getServletPath();

        // ---------------- DEBUG LOGGING ----------------


        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Request path: " + path);

        // ---------------- Print Cookies ----------------
        if (request.getCookies() != null) {
            System.out.println("Cookies present:");
            for (Cookie cookie : request.getCookies()) {
                System.out.println(" - " + cookie.getName() + ": " + cookie.getValue());
            }
        } else {
            System.out.println("No cookies found in request.");
        }

        System.out.println("Request path: " + path);
        if (request.getCookies() != null) {
            System.out.println("Cookies:");
            for (Cookie cookie : request.getCookies()) {
                System.out.println(" - " + cookie.getName() + ": " + cookie.getValue());
            }
        } else {
            System.out.println("No cookies found");
        }
        // ------------------------------------------------

        // Allow all /auth/** endpoints without checking token
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;

        // ------------ Read JWT from cookie ------------
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // ------------ Fallback: Authorization Bearer ------------
        if (token == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }

        String username = null;

        // ------------ Extract username safely ------------
        if (token != null) {
            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                // Token invalid or expired → skip auth
                filterChain.doFilter(request, response);
                return;
            }
        }

        // ------------ Validate token and load user ------------
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails =
                    customUserDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
