package com.example.userService.Consumer;

import com.example.userService.Entity.UserInfo;
import com.example.userService.Entity.UserInfoDTO;
import com.example.userService.Repository.UserRepository;
import com.example.userService.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceConsumer {
    private UserService userService;

    @Autowired
    AuthServiceConsumer(UserService userService){
        this.userService = userService;
    }


    @KafkaListener(topics = "${spring.kafka.topic-json.name}" , groupId = "${spring.kafka.consumer.group-id}")
    public void listen(UserInfoDTO eventData){
        try{
            userService.createOrUpdate(eventData);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("AuthServiceConsumer: Exception is thrown while consuming kafka event");
        }
    }
}
