package m7.graduatework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Класс сущности комментария
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата создания комментария
     */
    @NotNull
    private LocalDateTime createdAt;

    /**
     * Текст комментария
     */
    @NotEmpty
    private String text;

    /**
     * Автор комментария
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User author;

    /**
     * Объявление, к которому написан комментарий
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Ad ad;

}
