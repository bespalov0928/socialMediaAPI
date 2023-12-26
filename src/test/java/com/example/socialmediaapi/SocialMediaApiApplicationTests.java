package com.example.socialmediaapi;

import com.example.socialmediaapi.model.User;
import com.example.socialmediaapi.repositoty.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;


@SpringBootTest
class SocialMediaApiApplicationTests {

//    public static void main(String[] args) {
//        SpringApplication.run(SocialMediaApiApplicationTests.class, args);
//    }

//    @Bean
//    public CommandLineRunner run(UserRepository userRepository) throws Exception {
//        return (String[] args) -> {
//            User user1 = new User("user", "user");
//            User user2 = new User("admin", "user");
//            userRepository.save(user1);
//            userRepository.save(user2);
//            userRepository.findAll().forEach(System.out::println);
//        };
//    }


//    @Test
//    public void givenBidirectionRelation_whenUsingJsonIdentityInfo_thenCorrect()
//            throws JsonProcessingException {
//
//        User user = new User();
//        user.setId(1);
//        user.setEmail("User");
//
//        Friend friend = new Friend();
//        friend.setId(1);
//        friend.setAppruv(true);
//        friend.setUser(user);
//
//        String result = new ObjectMapper().writeValueAsString(friend);
//
//        assertThat(result, containsString("book"));
//        assertThat(result, containsString("John"));
//        assertThat(result, containsString("userItems"));
//    }

}
