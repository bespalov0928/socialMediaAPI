package com.example.socialmediaapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class SocialMediaApiApplicationTests {

    @Test
    void contextLoads() {
    }

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
