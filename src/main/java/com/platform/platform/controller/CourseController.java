package com.platform.platform.controller;

import com.platform.platform.Services.CourseService;
import com.platform.platform.Services.FileUploadUtil;
import com.platform.platform.config.CustomUserDetails;
import com.platform.platform.Services.RatingService;
import com.platform.platform.config.CustomUserDetails;
import com.platform.platform.config.CustomUserDetails;
import com.platform.platform.entity.*;
import com.platform.platform.entity.dto.CourseDTO;
import com.platform.platform.repo.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.EncoderException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    @Autowired
    private CourseService courseService;

    private final RatingService ratingService;

    private final CourseRepository courseRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable int id) {
        Optional<CourseDTO> course = courseService.getCourseById(id);
        return course.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> createCourse(
            @RequestParam(required = false) String title,
            @RequestParam String subTitle,
            @RequestParam String description,
            @RequestParam float price,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam(value = "videos", required = false) MultipartFile[] videos,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {

        try {
            // Validate and create upload directory if needed
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                if (!uploadDirectory.mkdirs()) {
                    throw new IOException("Failed to create upload directory: " + uploadDir);
                }
            }

            Course course = new Course();
            course.setTitle(title);
            course.setSubTitle(subTitle);
            course.setDescription(description);
            course.setPrice(price);

            // Process files
            if (files != null && files.length > 0) {
                List<com.platform.platform.entity.File> fileList = new ArrayList<>();
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String savedFileName = FileUploadUtil.saveFile(uploadDir, file);

                        com.platform.platform.entity.File fileEntity = new com.platform.platform.entity.File();
                        fileEntity.setName(file.getOriginalFilename());
                        fileEntity.setUrl("/uploads/" + savedFileName);
                        fileEntity.setCourse(course);
                        fileList.add(fileEntity);
                    }
                }
                course.setFiles(fileList);
            }

            // Process videos
            double totalDuration = 0;
            if (videos != null && videos.length > 0) {
                List<Video> videoList = new ArrayList<>();
                for (MultipartFile video : videos) {
                    if (!video.isEmpty()) {
                        // First save the video file
                        String fileName = FileUploadUtil.saveFile(uploadDir, video);
                        File savedVideo = new File(uploadDir + File.separator + fileName);

                        // Calculate duration from the saved file
                        try {
                            MultimediaObject multimediaObject = new MultimediaObject(savedVideo);
                            MultimediaInfo info = multimediaObject.getInfo();
                            long durationMillis = info.getDuration();
                            totalDuration += durationMillis / 1000.0; // Convert to seconds
                        } catch (EncoderException e) {
                            // Log error but continue processing
                            System.err.println("Error calculating duration for video: " + fileName);
                            e.printStackTrace();
                            // You might want to set a default duration here if needed
                            totalDuration += 0; // or some default value
                        }

                        // Create video entity
                        Video videoEntity = new Video();
                        videoEntity.setTitle(video.getOriginalFilename());
                        videoEntity.setUrl("/uploads/" + fileName);
                        videoEntity.setCourse(course);
                        videoList.add(videoEntity);
                    }
                }
                course.setVideos(videoList);
                course.setDuration(totalDuration);
            }

// Process images
            if (images != null && images.length > 0) {
                List<Image> imageList = new ArrayList<>();
                for (MultipartFile image : images) {
                    if (!image.isEmpty()) {
                        // Changed this line to use the correct saveFile method signature
                        String fileName = FileUploadUtil.saveFile(uploadDir, image);

                        Image imageEntity = new Image();
                        imageEntity.setName(image.getOriginalFilename());
                        imageEntity.setUrl("/uploads/" + fileName);
                        imageEntity.setCourse(course);
                        imageList.add(imageEntity);
                    }
                }
                course.setImages(imageList);
            }

            Course savedCourse = courseService.saveCourse(course);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course courseDetails) {
        Course updatedCourse = courseService.updateCourse(id, courseDetails);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search courses by subject (limit 5 and not owned by current teacher)
    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCoursesBySubject(
            @RequestParam String subject,
            Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Integer currentTeacherId = userDetails.getUserId();

        Pageable limit = PageRequest.of(0, 5);
        List<Course> courses = courseRepository
                .findBySubjectContainingIgnoreCaseAndTeacherIdNot(subject, currentTeacherId, limit)
                .getContent();

        return ResponseEntity.ok(courses);
    }



    @PostMapping("/ratings/{courseId}/rate")
    public ResponseEntity<Rating> rateCourse(
            @PathVariable Integer courseId,
            @RequestParam Integer studentId,
            @RequestParam Integer stars) {
        Rating rating = ratingService.addRating(courseId, studentId, stars);
        return ResponseEntity.ok(rating);
    }

    @GetMapping("/ratings/{courseId}/distribution")
    public ResponseEntity<RatingDistribution> getRatingDistribution(@PathVariable Integer courseId) {
        RatingDistribution distribution = ratingService.getRatingDistribution(courseId);
        return ResponseEntity.ok(distribution);
    }





}
