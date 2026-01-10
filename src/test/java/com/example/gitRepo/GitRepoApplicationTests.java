package com.example.gitRepo;

import com.example.gitRepo.model.RepoToDisplay;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GitRepoApplicationTests {

	@LocalServerPort
	int port;
	private RestTemplate restTemplate = new RestTemplate();

	@Test
	public void wrongUsernameTest(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Accept","application/vnd.github+json");
		httpHeaders.set("X-GitHub-Api-Version","2022-11-28");
		HttpEntity httpEntity = new HttpEntity(httpHeaders);
		try {
			ResponseEntity<ExceptionResponse[]> responseEntity = restTemplate.exchange("http://localhost:"+port+"/repos?username=zse@#%ffa^^", HttpMethod.GET, httpEntity, ExceptionResponse[].class);

		}catch (HttpClientErrorException e){
			assertEquals(e.getStatusCode().value(), 404);
			assertTrue(e.getResponseBodyAsString().contains("doesn't exist"));
		}

	}

	@Test
	public void correctUsernameTest(){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.ACCEPT,"application/vnd.github+json");
		httpHeaders.set("X-GitHub-Api-Version","2022-11-28");
		HttpEntity httpEntity = new HttpEntity(httpHeaders);
		ResponseEntity<RepoToDisplay[]> responseEntity = restTemplate.exchange("http://localhost:"+port+"/repos?username=Mmati1996", HttpMethod.GET, httpEntity, RepoToDisplay[].class);

		assertEquals( responseEntity.getStatusCode().value(),200);
		assertNotNull(responseEntity.getBody());
		for (RepoToDisplay repo : responseEntity.getBody()){
			assertNotNull(repo);
		}

	}


}
