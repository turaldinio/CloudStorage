package com.guluev.cloudstorage;

import com.guluev.cloudstorage.entity.User;
import com.guluev.cloudstorage.model.FileResponse;
import com.guluev.cloudstorage.repository.UserRepository;
import com.guluev.cloudstorage.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private String port;
    private static URI uri;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;


    /**
     * Попытка доступа с несуществующим токеном
     * ожидаемый ответ 401
     */
    @Test
    void accessWithANonExistentToken() throws URISyntaxException {
        uri = new URI(String.format("http://localhost:%s/list", port));

        var header = new HttpHeaders();
        header.add("auth-token", "asf3wga3tahe");

        var result = restTemplate.exchange(uri,
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<FileResponse>() {
                }).getStatusCode().value();


        Assertions.assertEquals(401, result);

    }

    /**
     * попытка доступа с действующим токеном
     * ожидаем, что доступ будет разрешен
     */
    @Test
    public void accessWithAValidToken() throws URISyntaxException {


        var user = createJwtTokenAndSavePersonInDb();

        String token = user.getUserToken();

        uri = new URI(String.format("http://localhost:%s/list", port));

        var header = new HttpHeaders();
        header.add("auth-token", "Bearer " + token);

        var result = restTemplate.exchange(uri,
                HttpMethod.GET,
                new HttpEntity<>(header),
                new ParameterizedTypeReference<List<FileResponse>>() {
                }).getBody();

        userRepository.delete(user);

        Assertions.assertNotNull(result);

    }


    public User createJwtTokenAndSavePersonInDb() {
        var user = User.builder().email("admin").password(passwordEncoder.encode("user")).build();

        var jwtToken = jwtService.generateToken(user);

        user.setUserToken(jwtToken);

        userRepository.save(user);
        return user;
    }

}
