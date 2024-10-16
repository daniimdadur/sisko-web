package practice.web.student.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import practice.web.base.Response;
import practice.web.constant.BackEndUrl;
import practice.web.student.model.StudentReq;
import practice.web.student.model.StudentRes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final RestTemplate restTemplate;
    private final BackEndUrl backEndUrl;
    private final ObjectMapper objectMapper;

    @Override
    public List<StudentRes> get() {
        try {
            var url = backEndUrl.studentUrl();
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<StudentRes>) response.getBody().getData();
            }
        } catch (RestClientException e) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<StudentRes> getById(String id) {
        try {
            var url = Strings.concat(backEndUrl.studentUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                StudentRes result = objectMapper.readValue(json, StudentRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return Optional.empty();
    }

    @Override
    public Optional<StudentRes> save(StudentReq request) {
        try {
            var url = backEndUrl.studentUrl();
            HttpEntity<StudentReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.postForEntity(url, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                StudentRes result = objectMapper.readValue(json, StudentRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return Optional.empty();
    }

    @Override
    public Optional<StudentRes> update(StudentReq request, String id) {
        try {
            var url = Strings.concat(backEndUrl.studentUrl(), "/" + id);
            HttpEntity<StudentReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                StudentRes result = objectMapper.readValue(json, StudentRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return Optional.empty();
    }

    @Override
    public Optional<StudentRes> delete(String id) {
        try {
            var url = Strings.concat(backEndUrl.studentUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                StudentRes result = objectMapper.readValue(json, StudentRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
