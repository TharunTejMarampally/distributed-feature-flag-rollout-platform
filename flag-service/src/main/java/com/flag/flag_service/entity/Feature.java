package com.flag.flag_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="feature")
public class Feature extends AbstractEntity
{

    @Column(name = "flag_id")
    private UUID flagId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "flag_info")
    private Map<String, Object> flagInfo;
}
