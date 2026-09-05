package com.climbmetrics.backend.service;

import com.climbmetrics.backend.dto.ClimbsResponse;
import com.climbmetrics.backend.dto.LogClimbRequest;
import com.climbmetrics.backend.dto.StatsResponse;
import com.climbmetrics.backend.dto.UserProfileResponse;
import com.climbmetrics.backend.entity.Climb;
import com.climbmetrics.backend.exception.NoSuchUserException;
import com.climbmetrics.backend.repository.ClimbRepository;
import com.climbmetrics.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClimbService {

    @Autowired
    ClimbRepository climbRepository;
    @Autowired
    UserRepository userRepository;

    public ClimbService(ClimbRepository climbRepository,  UserRepository userRepository) {
        this.climbRepository = climbRepository;
        this.userRepository = userRepository;
    };

    public List<ClimbsResponse> getClimbs(Long userId) {
        List<Climb> climbs = climbRepository.findAllByUserId(userId);
        return climbs.stream()
                .map(climb -> new ClimbsResponse(
                        climb.getId(),
                        climb.getUserId(),
                        climb.getDate(),
                        climb.getGrade(),
                        climb.getStyle(),
                        climb.getAttempts(),
                        climb.isCompleted(),
                        climb.getNotes(),
                        climb.getTimestamp()
                ))
                .toList();
    };

    public void logClimb(Long userID, LogClimbRequest logClimbRequest) {
        Climb climb = new Climb();

        climb.setUserId(userID);
        climb.setGrade(logClimbRequest.grade());
        climb.setDate(logClimbRequest.date());
        climb.setCompleted(logClimbRequest.completed());
        climb.setStyle(logClimbRequest.style());
        climb.setAttempts(logClimbRequest.attempts());


        climbRepository.save(climb);
    }

    private int getVGradeNumber(String grade) {
        return Integer.parseInt(grade.substring(1));
    }

    public StatsResponse getStatistics(Long userId) {
        List<Climb> climbs = climbRepository.findAllByUserId(userId);


        int total = climbs.size();

        int completed =(int) climbs.stream()
                .filter(Climb::isCompleted)
                .count();

        double completionRate =
                total == 0 ? 0 : (double) completed / total * 100;

        int totalAttempts = climbs.stream()
                .mapToInt(Climb::getAttempts)
                .sum();

        double averageAttempts =
                total == 0 ? 0 : (double) totalAttempts / total;

        String highestGrade = climbs.stream()
                .map(Climb::getGrade)
                .max(Comparator.comparingInt(this::getVGradeNumber))
                .orElse(null);

        Map<String, Integer> climbsByGrade = climbs.stream()
                .collect(Collectors.groupingBy(
                        Climb::getGrade,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));

        Map<String, Double> completionRateByGrade = climbs.stream()
                .collect(Collectors.groupingBy(
                        Climb::getGrade,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                gradeClimbs -> gradeClimbs.stream()
                                        .filter(Climb::isCompleted)
                                        .count() * 100.0 / gradeClimbs.size()
                        )
                ));

        Map<String, Double> averageAttemptsByGrade = climbs.stream()
                .collect(Collectors.groupingBy(
                        Climb::getGrade,
                        Collectors.averagingInt(Climb::getAttempts)
                ));

        return  new StatsResponse(
                total,
                completed,
                completionRate,
                totalAttempts,
                averageAttempts,
                highestGrade,
                climbsByGrade,
                completionRateByGrade,
                averageAttemptsByGrade
        );
    }
}
