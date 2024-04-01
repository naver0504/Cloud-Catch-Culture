package com.example.eventservice.repository;

import com.example.eventservice.entity.event.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Long>{
}
