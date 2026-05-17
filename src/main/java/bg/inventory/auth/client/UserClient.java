package bg.inventory.auth.client;


import bg.inventory.auth.dto.request.RegisterRequest;
import bg.inventory.auth.dto.response.UserClientResponse;
import org.springframework.beans.factory.annotation.Value;
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
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .header("X-Internal-Key", apiKey)
                .retrieve()
                .toBodilessEntity();
    }

    public UserClientResponse getUserByEmail(String email){
        return restClient.get()
                .uri("/api/v1/users/internal/email/{email}", email)
                .header("X-Internal-Key", apiKey)
                .retrieve()
                .body(UserClientResponse.class);
    }
}
