package bg.inventory.auth.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    Integer id;
    String username;
    String firstName;
    String lastName;
    String country;
    Role role;
}
