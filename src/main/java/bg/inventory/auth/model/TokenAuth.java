package bg.inventory.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tokenauth")
public class TokenAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "token")
    private String token;
    @Column(name = "tipo_token")
    private String tipoToken;
    @NotNull
    @Column(name = "fecha_exp")
    private LocalDateTime fechaExp;
    @NotNull
    @Column(name = "id_usuario")
    private Long idUsuario;
}
