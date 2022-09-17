package com.example.resumeapiv1.services;

import com.example.resumeapiv1.dto.ResumeDto;
import com.example.resumeapiv1.models.Person;
import com.example.resumeapiv1.models.Resume;
import com.example.resumeapiv1.repositories.PersonRepository;
import com.example.resumeapiv1.repositories.ResumeRepository;
import com.example.resumeapiv1.utill.AuthPerson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResumeService {

    private ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    public Resume create(ResumeDto resumeDto) {
        Resume resume = new Resume(resumeDto.getDescription());
        Person person = AuthPerson.getAuthPerson();
        person.getResumes().add(resume);
        return resumeRepository.save(resume);
    }

    @Transactional
    public Resume update(int id, ResumeDto resumeDto) {
        Resume resumeRsl = resumeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "a resume not found"));
        resumeRsl.setDescription(resumeDto.getDescription());
        return resumeRepository.save(resumeRsl);
    }

    @Transactional
    public void deleteById(int id) {
        resumeRepository.deleteById(id);
    }

    public Resume findById(int id) {
        return resumeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "a resume not found"));
    }

    public List<Resume> findAll() {
        Person person = AuthPerson.getAuthPerson();
        return person.getResumes();
    }
}
