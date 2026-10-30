package com.example.demo.service;

import com.example.demo.entity.Lesson;
import com.example.demo.exception.InvalidLessonFileException;
import com.example.demo.exception.LessonNotFoundException;
import com.example.demo.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson uploadFile (MultipartFile multipartFile) throws IOException {
        validateFile(multipartFile);
        byte [] bytes = multipartFile.getBytes();
        String content =  new String(bytes, StandardCharsets.UTF_8);
        String title = extractTitle(content);
        Lesson lesson = new Lesson();

        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setPublished(false);

        return lessonRepository.save(lesson);
    }

    private void validateFile(MultipartFile file) {
        String fileNme = file.getOriginalFilename();
        String lowerName = fileNme.toLowerCase();
        if (file.isEmpty()) {
            throw new InvalidLessonFileException("Файл пустой");
        }
        if (fileNme == null){
            throw new InvalidLessonFileException("У файла нет имени");
        }
        if (!lowerName.endsWith(".md") && !lowerName.endsWith(".txt")){
            throw new InvalidLessonFileException("Можно загружать только .md и .txt");
        }
    }

    // метод находит строку которая начинается с #  в .md  файле
    private String extractTitle(String content) {
        String [] line = content.split("\n");
        for (String res : line){
            if (res.startsWith("# ")) return res.substring(2).trim();
        }
        return null;
    }

    public Lesson publish(Long id) {
        Lesson lesson1 = lessonRepository.findById(id).
                orElseThrow(() -> new RuntimeException("С таким id уже есть "));

        lesson1.setPublished(true);
        return lessonRepository.save(lesson1);
        }

        public List<Lesson> getPublished () {

        return lessonRepository.findByPublishedTrue();

    }

    public Lesson getById (Long id) {
       return lessonRepository.findById(id).orElseThrow(()-> new LessonNotFoundException("Урок с id " + id + " не найден"));
    }
}