package bg.inventory.auth.repository;

import bg.inventory.auth.model.TokenAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITokenRepository extends JpaRepository<TokenAuth, Long> {
    List<TokenAuth> findByIdUsuario(Long idUsuario);
    void deleteByIdUsuario(Long idUsuario);
}
