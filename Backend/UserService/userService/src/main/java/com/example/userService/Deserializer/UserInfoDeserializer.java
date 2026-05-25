package com.example.userService.Deserializer;

import com.example.userService.Entity.UserInfoDTO;
import org.apache.kafka.common.serialization.Deserializer;
import tools.jackson.databind.ObjectMapper;
import java.util.Map;

public class UserInfoDeserializer implements Deserializer<UserInfoDTO> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserInfoDTO deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        UserInfoDTO userInfoDTO = null;
        try{
            userInfoDTO = objectMapper.readValue(bytes,UserInfoDTO.class);
        }catch(Exception e){
           e.printStackTrace();
        }
        return userInfoDTO;
    }

    @Override
    public void close() {

    }
}
