package br.com.dennys.mvc.root.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import br.com.dennys.mvc.root.model.Acessos;
import br.com.dennys.mvc.root.model.UserJson;

@Controller
@RequestMapping("rest")
public class WebServiceClient {

	@GetMapping("/service")
	@ResponseBody
	public String teste() throws IOException {

		int HTTP_COD_SUCESSO = 200;
		URL url;

		try {
			url = new URL("http://localhost:8082/acessos");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			String retornoJson;
			StringBuilder builder = new StringBuilder();
			while ((retornoJson = bufferedReader.readLine()) != null)
				builder.append(retornoJson);

			

			//System.out.println(builder.toString());
			
			String json = builder.toString();
			//json = json.substring(0, json.length() -1);
			//json = json.substring(1, json.length());
			
			System.out.println(json);
			
//			acesso = gson.fromJson(json, Acesso.class);
					
			Gson gson = new Gson();
			
			String userJson = "[{'name': 'Alex','id': 1}, "
	                + "{'name': 'Brian','id':2}, "
	                + "{'name': 'Charles','id': 3}]";
			
			UserJson[] users = gson.fromJson(userJson, UserJson[].class);
			
			for (UserJson user : users) {
				System.out.println("----------------Usuario : " + user);
			}
			
			//bufferedReader.close();
			return userJson;
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		
		
		 
		return null;
	}

}
