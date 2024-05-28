package com.example.reportservice.domain.adapter.outbox;

import com.example.reportservice.domain.entity.outbox.OutBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoxRepository extends JpaRepository<OutBox, Long> {
}
