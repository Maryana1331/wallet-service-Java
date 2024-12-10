package com.example.wallet.config;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "log_entity")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    private String method;

    private Integer status;

    private String errorMessage;

    private String path;

    private String ipAddress;

    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }
}
