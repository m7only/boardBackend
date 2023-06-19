package m7.graduatework.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.Set;

/**
 * Класс сущности объявления
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Относительный путь к объявлению для фронта
     */
    private String image;

    /**
     * Заголовок объявления
     */
    @NotEmpty
    private String title;

    /**
     * Цена товара
     */
    @PositiveOrZero
    private Integer price;

    /**
     * Описание товара
     */
    @NotEmpty
    private String description;

    /**
     * Автор объявления
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User author;

    /**
     * Комментарии к объявлению
     */
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Comment> comments;
}
