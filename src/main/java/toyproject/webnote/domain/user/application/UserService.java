package toyproject.webnote.domain.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import toyproject.webnote.domain.user.domain.User;
import toyproject.webnote.domain.user.dto.UserCreateRequest;
import toyproject.webnote.domain.user.repository.UserRepository;
import toyproject.webnote.global.exception.ApiErrorException;
import toyproject.webnote.global.security.filter.jwt.JwtAuthenticationProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public void createUser(UserCreateRequest request) {
        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .email(request.getEmail())
                        .build()
        );
    }

    public User login(String email, String password, HttpServletResponse response) {

        User member = userRepository.findByEmail(email).orElseThrow(() -> new ApiErrorException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(member.getEmail());
        response.setHeader("ACCESS-TOKEN", token);

        Cookie cookie = new Cookie("ACCESS-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        return member;
    }
}
