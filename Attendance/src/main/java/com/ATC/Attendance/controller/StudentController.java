package com.ATC.Attendance.controller;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import com.ATC.Attendance.service.StudentService;

import lombok.RequiredArgsConstructor;

import com.ATC.Attendance.dto.*;
import com.ATC.Attendance.entities.UserEntity;
import com.ATC.Attendance.service.StudentService;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.List;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/v1/student")
@RequiredArgsConstructor    
public class StudentController {
    private final StudentService studentService;
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @PostMapping("/join-session")
    public ResponseEntity<Boolean> joinSession(@RequestBody JoinSessionRequest joinSessionRequest) {
        String studentCode = SecurityContextHolder.getContext().getAuthentication().getName();
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        

        // headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        // body.add("file", file.getResource());

        // HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        byte[] originalBytes = joinSessionRequest.getStudentImageUrl().getBytes(StandardCharsets.UTF_8);
        String encodedString = Base64.getEncoder().encodeToString(originalBytes);

        // Prepare the request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("image", encodedString);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            "http://localhost:5000/join", HttpMethod.POST, requestEntity, String.class);

        System.out.println("Response code: " + response.getStatusCode());   
        int intValue = Integer.parseInt(response.getBody().trim());
    
        if(role.contains("STUDENT")){

            return ResponseEntity.status(HttpStatus.OK).body(studentService.joinSession(joinSessionRequest.getSessionId(),studentCode,intValue));
        }
        throw new IllegalStateException("Only students can join sessions");}
    
    @GetMapping(path = "/sessions")
    public ResponseEntity<List<SessionResponse>> getSessions(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = (UserEntity) auth.getPrincipal();
        List<SessionResponse> response = this.studentService.getSessions(user.getUserCode());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
