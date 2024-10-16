package practice.web.course.service;

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
import practice.web.course.model.CourseReq;
import practice.web.course.model.CourseRes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final RestTemplate restTemplate;
    private final BackEndUrl backEndUrl;
    private final ObjectMapper objectMapper;

    @Override
    public List<CourseRes> get() {
        try {
            var url = backEndUrl.courseUrl();
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<CourseRes>) response.getBody().getData();
            }
        } catch (RestClientException e) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<CourseRes> getById(String id) {
        try {
            var url = Strings.concat(backEndUrl.courseUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                CourseRes result = objectMapper.readValue(json, CourseRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CourseRes> save(CourseReq request) {
        try {
            var url = backEndUrl.courseUrl();
            HttpEntity<CourseReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.postForEntity(url, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                CourseRes result = objectMapper.readValue(json, CourseRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CourseRes> update(CourseReq request, String id) {
        try {
            var url = Strings.concat(backEndUrl.courseUrl(), "/" + id);
            HttpEntity<CourseReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                CourseRes result = objectMapper.readValue(json, CourseRes.class);

                return Optional.of(result);
            }
        } catch (RestClientException e) {
            return Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<CourseRes> delete(String id) {
        try {
            var url = Strings.concat(backEndUrl.courseUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(response.getBody().getData());
                CourseRes result = objectMapper.readValue(json, CourseRes.class);

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
