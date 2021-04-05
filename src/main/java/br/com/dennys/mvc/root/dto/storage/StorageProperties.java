package br.com.dennys.mvc.root.dto.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {
	/**
	 * Folder location for storing files
	 */
	//private String location = "/home/dennys/Documentos";
	private String location = "src/main/resources/templates/images/";
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
