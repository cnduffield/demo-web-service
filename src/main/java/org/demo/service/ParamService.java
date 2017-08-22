package org.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class ParamService {
	@Autowired
	private Environment env;
	@Cacheable("params")
	public String getParam(String name) {
		return env.getProperty(name);
	}
}
