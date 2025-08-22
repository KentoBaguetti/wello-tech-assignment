package com.example.demo.api;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("api/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> insertUser(@Valid @NonNull @RequestBody User user) {

        int res = this.userService.addUser(user);

        if (res == 0) {
            return ResponseEntity.status(400).body("Please enter an email");
        }

        if (res == 2) {
            return ResponseEntity.status(400).body("Email already exists");
        }

        if (res == 3) {
            return ResponseEntity.status(400).body("Please enter a valid email address");
        }

        return ResponseEntity.status(200).body("Successfully added user to the DB");
    }

    @GetMapping(path = "email/{keyword}")
    public ResponseEntity<Map<String, Object>> getUsersByEmail(@PathVariable("keyword") String keyword) {
        List<String> emails = this.userService.filterByEmail(keyword);
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "emails", emails
        ));
    }

    @GetMapping(path = "page/{index}")
    public ResponseEntity<Map<String, Object>> getUsersByPage(@PathVariable("index") Integer index) {

        if (index == null) {
            return ResponseEntity.status(400).body(Map.of(
                    "message", "Please give an index",
                    "status", 400
            ));
        }

        boolean inRangeFlag = userService.checkInRange(index);

        if (!inRangeFlag) {
            return ResponseEntity.status(200).body(Map.of(
                    "message", "Page does not exist",
                    "status", 200
            ));
        }

        List<String> emails = this.userService.filterByPage(index);
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "currentPage", index,
                "emails", emails
        ));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> allUsers() {
        return ResponseEntity.status(200).body(Map.of(
                "message", "Success",
                "status", 200,
                "emails", userService.allUsers()
        ));
    }

}
