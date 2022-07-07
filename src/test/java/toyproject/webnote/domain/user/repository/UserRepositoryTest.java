package toyproject.webnote.domain.user.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.webnote.domain.user.domain.User;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void insertTest() {
        User user = User.builder()
                .password("password")
                .email("test@email.com")
                .name("test")
                .build();
        userRepository.save(user);

        User findUser = userRepository.findByName("test");

        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());
    }
}