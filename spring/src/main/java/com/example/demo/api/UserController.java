package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "user")
    public ResponseEntity<Map<String, Object>> insertUser(@Valid @NonNull @RequestBody User user) {

        int res = this.userService.addUser(user);

        if (res == 0) {
            return ResponseEntity.status(200).body(Map.of(
                    "message", "Please enter an email",
                    "status", 400
            ));
        }

        if (res == 3) {
            return ResponseEntity.status(200).body(Map.of(
                    "message", "Please enter a valid email",
                    "status", 400
            ));
        }

        int totalUsers = this.userService.getTotalNumberOfUsers();

        return ResponseEntity.status(200).body(Map.of(
                "message", "Successfully added user",
                "status", 200,
                "totalNumberOfUsers", totalUsers
        ));
    }

    @GetMapping(path = "users/filter")
    public ResponseEntity<Map<String, Object>> getUsersByEmail(@RequestParam(defaultValue = "") String email) {
        List<User> users = this.userService.filterByEmail(email);
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "numberOfUsers", users.size(),
                "users", users
        ));
    }

    @GetMapping(path = "users")
    public ResponseEntity<Map<String, Object>> paginate(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "10") int limit) {

        List<User> users = this.userService.paginate(offset, limit);
        int totalNumberOfUsers = this.userService.getTotalNumberOfUsers();
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "users", users,
                "offset", offset,
                "limit", limit,
                "totalNumberOfUsers", totalNumberOfUsers
        ));
    }

    @GetMapping("users/all")
    public ResponseEntity<Map<String, Object>> allUsers() {
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "emails", userService.allUsers()
        ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "Invalid query parameters");
        response.put("status", "400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
