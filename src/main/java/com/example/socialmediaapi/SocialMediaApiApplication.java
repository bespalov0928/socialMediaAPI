package com.example.socialmediaapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialMediaApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
////                    .firstname("Admin")
////                    .lastname("Admin")
//                    .email("admin1@mail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequest.builder()
////                    .firstname("Admin")
////                    .lastname("Admin")
//                    .email("user1@mail.com")
//                    .password("password")
//                    .role(USER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
//    }


//    @Bean
//    public GroupedOpenApi publicUserApi() {
//        return GroupedOpenApi.builder()
//                .group("Users")
//                .pathsToMatch("/users/**")
//                .build();
//    }
//
//    @Bean
//    public OpenAPI customOpenApi(@Value("${application-description}")String appDescription,
//                                 @Value("${application-version}")String appVersion) {
//        return new OpenAPI().info(new Info().title("Application API")
//                        .version(appVersion)
//                        .description(appDescription)
//                        .license(new License().name("Apache 2.0")
//                                .url("http://springdoc.org"))
//                        .contact(new Contact().name("username")
//                                .email("test@gmail.com")))
//                .servers(List.of(new Server().url("http://localhost:8080")
//                                .description("Dev service"),
//                        new Server().url("http://localhost:8082")
//                                .description("Beta service")));
//    }

}
