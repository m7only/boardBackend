package m7.graduatework.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Enum ролей пользователей для авторизации
 */
public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
