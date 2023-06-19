package m7.graduatework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

/**
 * Класс сущности пользователя
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ник пользователя (email)
     */
    @NotNull
    private String username;

    /**
     * Пароль пользователя
     */
    @NotNull
    private String password;

    /**
     * Имя пользователя
     */
    private String firstName;

    /**
     * Фамилия пользователя
     */
    private String lastName;

    /**
     * Телефон пользователя
     */
    private String phone;

    /**
     * Относительный путь к аватарке для фронта
     */
    private String image;

    /**
     * Роль пользователя для авторизации
     */
    @Enumerated(value = EnumType.STRING)
    private Role role;

    /**
     * Список комментариев пользователя
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Comment> comments;

    /**
     * Список объявлений пользователя
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Ad> ads;
}
