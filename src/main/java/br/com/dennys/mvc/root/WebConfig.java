package br.com.dennys.mvc.root;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.ComponentScan;

import br.com.dennys.mvc.root.interceptor.InterceptorHandleAcessos;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private InterceptorHandleAcessos interceptorHandleAcessos;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorHandleAcessos).addPathPatterns("/**");
	}
	
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/usuario/static/**", "/static/**")
                        .addResourceLocations("/public", "classpath:/static/")
                        .setCachePeriod(31556926);
        }

}





//@ComponentScan(basePackages = "br.com.dennys.mvc.root")
//@Configuration
//@EnableWebMvc
//public class WebConfig extends WebMvcConfigurationSupport {
//
//	@Autowired
//	private InterceptorHandleAcessos interceptorHandleAcessos;
//	
//	@Override
//	protected void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(interceptorHandleAcessos).addPathPatterns("/**");		
//	}
//	
//	@Override
//	protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//			configurer.enable();
//	}
//	
//	@Override
//	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/static/*").addResourceLocations("/resources/static/");
//	}
//
//}
