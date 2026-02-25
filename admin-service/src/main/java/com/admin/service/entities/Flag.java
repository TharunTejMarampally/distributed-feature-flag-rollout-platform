package com.admin.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

import java.util.Map;


@Setter
@Getter
@Entity
@Table(name="flag")
@Audited
public class Flag extends AbstractEntity {

    @Column(name = "flag_name")
    private String flagName;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "roll_out_percentage")
    private double rollOutPercentage;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "flag_configurations")
    private Map<String, Object> flagConfigurations;
}
