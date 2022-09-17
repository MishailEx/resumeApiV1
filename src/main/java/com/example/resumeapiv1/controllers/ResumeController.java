package com.example.resumeapiv1.controllers;

import com.example.resumeapiv1.dto.ResumeDto;
import com.example.resumeapiv1.models.Resume;
import com.example.resumeapiv1.services.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public ResponseEntity<List<ResumeDto>> findAll() {
        return new ResponseEntity<>(
                resumeService.findAll().stream()
                        .map(this::toResumeDto)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                toResumeDto(resumeService.findById(id)),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<ResumeDto> create(@RequestBody ResumeDto resumeDto) {
        Resume save = resumeService.create(resumeDto);
        return new ResponseEntity<>(
                toResumeDto(save),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeDto> update(@PathVariable int id, @RequestBody ResumeDto resumeDto) {
        return new ResponseEntity<>(
                toResumeDto(resumeService.update(id, resumeDto)),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
       resumeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ResumeDto toResumeDto(Resume resume) {
        return new ResumeDto(resume.getDescription());
    }
}
