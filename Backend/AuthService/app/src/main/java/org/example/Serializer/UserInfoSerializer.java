package org.example.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.example.EventProducer.UserInfoEvent;
import org.example.Model.UserInfoDTO;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UserInfoSerializer implements Serializer<UserInfoEvent>{


    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public byte[] serialize(String s, UserInfoEvent userInfoEvent) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            retVal = objectMapper.writeValueAsString(userInfoEvent).getBytes();

        }catch (Exception e){
           e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {
    }
}
