package com.example.userService.Services;

import com.example.userService.Entity.UserInfo;
import com.example.userService.Entity.UserInfoDTO;
import com.example.userService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserInfoDTO createOrUpdate(UserInfoDTO userInfoDTO){

        if (userInfoDTO.getUserId() == null || userInfoDTO.getUserId().isBlank()) {
            userInfoDTO.setUserId(generateUserId());
        }

        UnaryOperator<UserInfo> updatingUser = user->{
            return userRepository.save(userInfoDTO.transformToUserInfo());
        };

        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDTO.transformToUserInfo());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDTO.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);

        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }


    public UserInfoDTO getUser(String id) throws Exception{
        Optional<UserInfo> userInfoopt = userRepository.findByUserId(id);
        if(userInfoopt.isEmpty()){
            throw new RuntimeException("User not found");
        }

        UserInfo userInfo = userInfoopt.get();
        return new UserInfoDTO(
                userInfo.getUserId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getPhoneNumber(),
                userInfo.getEmail(),
                userInfo.getProfilePic()
        );
    }
}
