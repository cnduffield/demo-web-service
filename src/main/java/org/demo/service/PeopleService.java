package org.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PeopleService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ParamService params;
	private static final String URL = "people.url";
	public List<Person> retrievePeopleByName(String firstName, String lastName) {
		Person[] people = null;
		if (!StringUtils.isBlank(firstName) && !StringUtils.isBlank(lastName)) {
			people = restTemplate.getForObject(
					params.getParam(URL) + "people?firstName=" + firstName + "&lastName=" + lastName,
					Person[].class);
		} else if (!StringUtils.isBlank(firstName)) {
			people = restTemplate.getForObject(params.getParam(URL) + "people?firstName=" + firstName,
					Person[].class);
		} else if (!StringUtils.isBlank(lastName)) {
			people = restTemplate.getForObject(params.getParam(URL) + "people?lastName=" + lastName,
					Person[].class);
		}
		if (people != null) { return Arrays.asList(people); }
		return Collections.emptyList();
	}
	public Person savePerson(Person person) {
		return restTemplate.postForObject(params.getParam(URL) + "people", person, Person.class);
	}
	public Person retrievePersonById(String id) {
		if (!StringUtils.isBlank(
				id)) { return restTemplate.getForObject(params.getParam(URL) + "people/" + id, Person.class); }
		return null;
	}
	public Person retrievePersonByGovernmentId(String governmentId) {
		if (!StringUtils.isBlank(governmentId)) { return restTemplate.getForObject(
				params.getParam(URL) + "people/search/findByGovernmentId?governmentId=" + governmentId,
				Person.class); }
		return null;
	}
	public List<Person> retrieveAllPeople() {
		return new ArrayList<>(restTemplate.exchange(params.getParam(URL) + "people", HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Person>>() {}).getBody().getContent());
	}
	public void deletePersonById(String id) {
		restTemplate.exchange(params.getParam(URL) + "people/" + id, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<PagedResources<Person>>() {});
	}
}
