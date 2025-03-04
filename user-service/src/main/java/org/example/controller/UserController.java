package org.example.controller;

import org.example.entity.UserInfo;
import org.example.service.IUserService;
import org.example.util.JwtUtil;
import org.example.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public R<String> loginHandler(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        UserInfo userInfo = userService.login(username, password);
        if (userInfo == null) {
            return R.error(400, "Invalid username or password");
        }
        Map<String, Object> claims = new HashMap<>();
        String token = jwtUtil.generateToken(username, claims);
        return R.ok(token);
    }

    @GetMapping
    public R<List<UserInfo>> getAllUser() {
        List<UserInfo> userInfos = userService.getAllUser();
        if (CollectionUtils.isEmpty(userInfos)) {
            return R.error(404, "User not found");
        }
        return R.ok(userInfos);
    }

    @GetMapping("/{id}")
    public R<UserInfo> getUserById(@PathVariable Integer id) {
        UserInfo userInfo = userService.getUserById(id);
        if (userInfo == null) {
            return R.error(404, "User not found");
        }
        return R.ok(userInfo);
    }

    @PostMapping("/add")
    public R<String> addUser(@RequestBody UserInfo userInfo) {
        boolean result = userService.addUser(userInfo);
        if (!result) {
            return R.error(400, "Error adding user");
        }
        return R.ok("User added");
    }

    @PutMapping("/{id}")
    public R<String> updateUserById(@PathVariable Integer id, @RequestBody UserInfo userInfo) {
        boolean result = userService.updateUserById(id, userInfo);
        if (!result) {
            return R.error(400, "Error updating user");
        }
        return R.ok("User updated");
    }

    @DeleteMapping("/{id}")
    public R<String> deleteUserById(@PathVariable Integer id) {
        boolean result = userService.deleteUserById(id);
        if (!result) {
            return R.error(400, "Error deleting user");
        }
        return R.ok("User deleted");
    }
}
