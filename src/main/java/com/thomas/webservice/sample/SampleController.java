package com.thomas.webservice.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping(path = "/getMessage", produces  = "application/hypocritus-v1+json")
	public String getMessageV1() {
		return "Shaolin Sky";
	}
	
	@GetMapping(path = "/getMessage", produces = "application/hypocritus-v2+json")
	public String getMessageV2() {
		return "Hall of Fame";
	}
	
	@GetMapping(path = "/getMessageBean/{message}")
	public SampleBean getMessageBean(@PathVariable String message) {
		return new SampleBean("Oh My My "+message);
	}
	
	@GetMapping(path = "/checkLanguages")
	public String checkLanguages() {
		return messageSource.getMessage("a.foot.in.the.door", null, LocaleContextHolder.getLocale());
	}
}
