package com.flag.flag_service.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserValidationRequestDTO {
    private String flagId;
    private String userId;
    private Map<String, Object> userInfo;
}
