package bg.inventory.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserClientResponse {
    private Long id;
    private String nombreUsuario;
    private String apellido;
    private String username;
    private String email;
    private String passwordHash;
    private String rolUsuario;
}
