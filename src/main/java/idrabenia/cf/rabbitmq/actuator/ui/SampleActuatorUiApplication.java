/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package idrabenia.cf.rabbitmq.actuator.ui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import groovy.text.GStringTemplateEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class SampleActuatorUiApplication {

	@RequestMapping("/")
	public String home(Map<String, Object> model) {
		model.put("message", "Hello World");
		model.put("title", "Hello Home");
		model.put("date", new Date());
		return "home";
	}

	@RequestMapping("/foo")
	public String foo() {
		throw new RuntimeException("Expected exception in controller");
	}

	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SampleActuatorUiApplication.class, args);

        String message = new GStringTemplateEngine()
                .createTemplate(new InputStreamReader(SampleActuatorUiApplication
                    .class
                    .getResourceAsStream("/gstring/templates/hello-template.template")))
                .make(new HashMap() {{
                    put("firstname", "Ilya");
                    put("lastname", "Drabenia");
                    put("city", "Helsinki");
                    put("month", "October");
                    put("signed", "Mr. Denis Komarov");
                }})
                .toString();

        System.out.println(message);
	}

	@Bean
	public SecurityProperties securityProperties() {
		SecurityProperties security = new SecurityProperties();
		security.getBasic().setPath(""); // empty so home page is unsecured
		return security;
	}

}

