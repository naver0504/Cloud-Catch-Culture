package com.example.reportservice.repository.outbox;

import com.example.reportservice.entity.outbox.OutBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoxRepository extends JpaRepository<OutBox, Long> {
}
