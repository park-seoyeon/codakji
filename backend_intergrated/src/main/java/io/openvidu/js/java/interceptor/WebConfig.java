package io.openvidu.js.java.interceptor;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String[] EXCLUDE_PATHS = { "/user/**/**","/user/**","/error/**", "/problem/**","/codeAPI/**", "/sessions/**","/**" };

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/**")
				.excludePathPatterns(EXCLUDE_PATHS);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD");
	}

}
