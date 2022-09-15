package com.example.resumeapiv1.controllers;

import com.example.resumeapiv1.dto.ResumeDto;
import com.example.resumeapiv1.models.Resume;
import com.example.resumeapiv1.services.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    public ResponseEntity<List<Resume>> findAll() {
        return new ResponseEntity<>(
                resumeService.findAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resume> findById(@PathVariable int id) {
        return new ResponseEntity<>(
                resumeService.findById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<Resume> create(@RequestBody ResumeDto resumeDto) {
        Resume save = resumeService.create(resumeDto);
        return new ResponseEntity<>(
                save,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resume> update(@PathVariable int id, @RequestBody ResumeDto resumeDto) {
        return new ResponseEntity<>(
                resumeService.update(id, resumeDto),
                HttpStatus.ACCEPTED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
       resumeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
