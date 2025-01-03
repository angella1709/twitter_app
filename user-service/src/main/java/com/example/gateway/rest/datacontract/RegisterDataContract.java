package com.example.gateway.rest.datacontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDataContract {
    private String username;

    private String email;

    private String password;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("last_login_time")
    private LocalDateTime lastLoginTime;

    @JsonProperty("is_hot_user")
    private Boolean isHotUser;

}
