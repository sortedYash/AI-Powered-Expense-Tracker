package org.example.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.Entities.UserInfo;
import org.example.EventProducer.UserInfoEvent;
import org.example.EventProducer.UserInfoProducer;
import org.example.Model.UserInfoDTO;
import org.example.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final UserInfoProducer userInfoProducer;

    private static final Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        UserInfo user =  userRepository.findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("Could not find the user..");
        }
        log.info("User Authenticated Successfully..!!!");
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfoDTO){
        return userRepository.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean signupUser(UserInfoDTO userInfoDTO){
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        UserInfo userInfo = new UserInfo(userId, userInfoDTO.getUsername(), userInfoDTO.getPassword(), new HashSet<>());
        userRepository.save(userInfo);

        //pushEventToQueue (means send userdetails from authentication service to userService)
        userInfoProducer.sendEventToKafka(userInfoEventToPublish(userInfoDTO,userId));
        return true;
    }

    public String getUserByUsername(String userName){
        return Optional.of(userRepository.findByUsername(userName)).map(UserInfo::getUserId).orElse(null);
    }

    private UserInfoEvent userInfoEventToPublish(UserInfoDTO userInfoDTO , String userID){
        return UserInfoEvent.builder()
                .firstName(userInfoDTO.getFirstName())
                .lastName(userInfoDTO.getLastName())
                .email(userInfoDTO.getEmail())
                .userId(userID)
                .phoneNumber(userInfoDTO.getPhoneNumber()).build();
    }

}
