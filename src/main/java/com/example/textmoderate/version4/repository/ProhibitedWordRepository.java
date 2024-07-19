package com.example.textmoderate.version4.repository;

import com.example.textmoderate.version4.model.ProhibitedWord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProhibitedWordRepository extends JpaRepository<ProhibitedWord, UUID> {
}

