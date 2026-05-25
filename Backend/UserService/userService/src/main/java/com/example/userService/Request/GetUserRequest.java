package com.example.userService.Request;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUserRequest {
    private String id;
}
