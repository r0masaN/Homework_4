package romasan.homework_4.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import romasan.homework_4.model.User;
import romasan.homework_4.repository.UserRepository;
import romasan.homework_4.service.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository repository;

    public JwtAuthenticationFilter(final JwtService jwtService, final UserRepository repository) {
        this.jwtService = jwtService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring("Bearer ".length());
        UUID userId;
        try {
            userId = this.jwtService.validateTokenAndGetUserId(token);
        } catch (final Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        final Optional<User> user = this.repository.findById(userId);
        if (user.isPresent()) {
            final UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.get(), null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
