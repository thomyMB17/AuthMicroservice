package bg.inventory.auth.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    @GetMapping("/info")

    public ResponseEntity<AccountResponse> getInfo(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(new AccountResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getCountry(),
                user.getRole()
        ));
    }
}
