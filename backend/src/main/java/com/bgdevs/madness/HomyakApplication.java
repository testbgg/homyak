package com.bgdevs.madness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class HomyakApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomyakApplication.class, args);
	}


    @Configuration
    @Controller
    class SpaRedirectConfiguration extends WebMvcConfigurerAdapter {


        @GetMapping(value = "/invoices/**", produces = MediaType.TEXT_HTML_VALUE)
        public ModelAndView returnSpaBundle(HttpServletRequest request) {
            return new ModelAndView("index");
        }
    }
}