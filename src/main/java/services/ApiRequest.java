package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiRequest {
	public String consumo(String address){
		HttpClient client = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.ALWAYS)
				.build();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(address))
				.build();
		HttpResponse<String> response;
		try{
			response = client
					.send(request, HttpResponse.BodyHandlers.ofString());
		}catch (IOException|InterruptedException e){
			throw new RuntimeException(e);
		}

		return response.body();
	}
}