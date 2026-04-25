package bg.inventory.auth.client;


import bg.inventory.auth.auth.RegisterRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {

    @Value("${internal.api.key}")
    private String apiKey;

    private final RestClient restClient;


    public UserClient(RestClient.Builder builder){
        this.restClient = builder
                .baseUrl("http://localhost:8082")
                .build();
    }

    public void createUser(RegisterRequest request){
        restClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .header("X-Internal-Key", apiKey)
                .retrieve()
                .toBodilessEntity();
    }

    public UserClientResponse getUserByUsername(String username){
        return restClient.get()
                .uri("/users/{username}", username)
                .header("X-Internal-Key", apiKey)
                .retrieve()
                .body(UserClientResponse.class);
    }
}
