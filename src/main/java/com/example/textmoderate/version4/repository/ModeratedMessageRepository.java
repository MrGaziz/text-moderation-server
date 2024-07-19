package com.example.textmoderate.version4.repository;

import com.example.textmoderate.version4.model.ModeratedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModeratedMessageRepository extends JpaRepository<ModeratedMessage, UUID> {
}
