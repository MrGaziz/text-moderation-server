package com.example.textmoderate.version3.repository;

import com.example.textmoderate.version3.model.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadWordRepository extends JpaRepository<BadWord, Long> {
    boolean existsByWord(String word);
}
