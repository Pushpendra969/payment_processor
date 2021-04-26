package com.luminor.pay_process.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModel {

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}