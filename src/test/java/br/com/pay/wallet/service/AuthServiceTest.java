
package br.com.pay.wallet.service;

import br.com.pay.wallet.repository.AuthRepository;
import br.com.pay.wallet.repository.ClientsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private ClientsRepository clientsRepository;
    @Mock
    private AuthRepository authRepository;

    @Test
    public void shouldThrowErrorOnInvalidCredentials() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> authService.authenticate("12345678900", "1q2w3e4r"));
    }

}
