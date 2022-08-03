package toyproject.webnote.domain.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyproject.webnote.domain.user.application.UserService;
import toyproject.webnote.domain.user.domain.User;
import toyproject.webnote.domain.user.dto.UserCreateRequest;
import toyproject.webnote.domain.user.dto.UserLoginRequest;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<HttpStatus> create(@RequestBody UserCreateRequest request) {
        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/login")
    @ResponseBody
    public User login(@RequestBody UserLoginRequest request, HttpServletResponse response) {
        return userService.login(request.getEmail(), request.getPassword(), response);
    }

    @GetMapping("login-pass")
    @ResponseBody
    public String loginSuccess() {
        return "login success";
    }

    @GetMapping("confirm-duplication/{email}")
    @ResponseBody
    public ResponseEntity<Boolean> confirmDuplication(@PathVariable String email) {
        return new ResponseEntity<>(userService.isUniqueEmail(email), HttpStatus.OK);
    }

}
