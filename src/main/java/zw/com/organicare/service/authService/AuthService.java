/**
 * @author : tadiewa
 * date: 9/10/2025
 */

package zw.com.organicare.service.authService;

import zw.com.organicare.dto.user.AuthResponse;
import zw.com.organicare.dto.user.LoginRequest;
import zw.com.organicare.dto.user.UserDto;
import zw.com.organicare.model.User;

public interface AuthService {

    UserDto register(UserDto request);
    AuthResponse login(LoginRequest request);
    UserDto update(UserDto request);

    User getAuthenticatedUser();
}
