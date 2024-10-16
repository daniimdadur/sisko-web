package practice.web.majors.service;

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
import practice.web.majors.model.MajorsReq;
import practice.web.majors.model.MajorsRes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MajorsServiceImpl implements MajorsService {
    private final RestTemplate restTemplate;
    private final BackEndUrl backEndUrl;
    private final ObjectMapper objectMapper;

    @Override
    public List<MajorsRes> get() {
        try {
            var url = backEndUrl.majorsUrl();
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return (List<MajorsRes>) response.getBody().getData();
            }
        } catch (RestClientException e) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<MajorsRes> getById(String id) {
        try {
            var url = Strings.concat(backEndUrl.majorsUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.getForEntity(url, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                MajorsRes result = objectMapper.readValue(json, MajorsRes.class);

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
    public Optional<MajorsRes> save(MajorsReq request) {
        try {
            var url = backEndUrl.majorsUrl();
            HttpEntity<MajorsReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.postForEntity(url, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                MajorsRes result = objectMapper.readValue(json, MajorsRes.class);

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
    public Optional<MajorsRes> update(MajorsReq request, String id) {
        try {
            var url = Strings.concat(backEndUrl.majorsUrl(), "/" + id);
            HttpEntity<MajorsReq> requestEntity = new HttpEntity<>(request);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                MajorsRes result = objectMapper.readValue(json, MajorsRes.class);

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
    public Optional<MajorsRes> delete(String id) {
        try {
            var url = Strings.concat(backEndUrl.majorsUrl(), "/" + id);
            ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Response.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                byte[] json = objectMapper.writeValueAsBytes(Objects.requireNonNull(response.getBody().getData()));
                MajorsRes result = objectMapper.readValue(json, MajorsRes.class);

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
