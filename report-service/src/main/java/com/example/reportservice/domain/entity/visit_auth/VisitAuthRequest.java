package com.example.reportservice.domain.entity.visit_auth;

import com.example.reportservice.common.converter.StoredFileUrlConverter;
import com.example.reportservice.domain.entity.BaseEntity;
import com.example.reportservice.kafka.message.BaseMessage;
import com.example.reportservice.kafka.message.VisitAuthMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VisitAuthRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StoredFileUrlConverter.class)
    private List<String> storedFileUrl;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private int culturalEventId;


    private boolean isAuthenticated;

    @Override
    public BaseMessage toBaseMessage() {
        return new VisitAuthMessage(this.userId, this.culturalEventId);
    }

    public void authenticate() {
        this.isAuthenticated = true;
    }

    public void unAuthenticate() {
        this.isAuthenticated = false;
    }
}
