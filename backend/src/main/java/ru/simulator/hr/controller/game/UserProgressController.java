package ru.simulator.hr.controller.game;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simulator.hr.entity.UserProgress;
import ru.simulator.hr.interfaces.UserProgressService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserProgressController {

    private final UserProgressService userProgressService;

    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }
    @GetMapping("/{userTelId}/all-progress")
    public ResponseEntity<List<UserProgress>> getAllUserProgress(@PathVariable Long userTelId) {
        List<UserProgress> userProgressList = userProgressService.getAllUserProgress(userTelId);

        if (!userProgressList.isEmpty()) {
            return ResponseEntity.ok(userProgressList);
        } else {
            return null;
        }
    }
    @GetMapping("/{userTelId}/progress/{topicId}")
    public ResponseEntity<UserProgress> getUserProgressByTopicId(@PathVariable Long userTelId, @PathVariable Long topicId) {
        UserProgress userProgress = userProgressService.getUserProgressByTopicId(userTelId, topicId);

        if (userProgress != null) {
            return ResponseEntity.ok(userProgress);
        } else {
            return null;
        }
    }
    @GetMapping("/all-progress")
    public ResponseEntity<List<UserProgress>> getAllUserProgress() {
        List<UserProgress> allUserProgress = userProgressService.getAllUserProgress();

        if (!allUserProgress.isEmpty()) {
            return ResponseEntity.ok(allUserProgress);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/top-10-users")
    public ResponseEntity<List<UserProgress>> getTop10UsersByProgress() {
        List<UserProgress> top10Users = userProgressService.getTop10UsersByProgress();

        if (!top10Users.isEmpty()) {
            return ResponseEntity.ok(top10Users);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/{userTelId}/update-progress")
    public ResponseEntity<String> updateUserProgress(@PathVariable Long userTelId, @RequestBody UserProgress userProgress) {
        UserProgress updatedProgress = userProgressService.updateUserProgress(userTelId, userProgress);

        if (updatedProgress != null) {
            return ResponseEntity.ok("Статистика пользователя успешно обновлена");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}