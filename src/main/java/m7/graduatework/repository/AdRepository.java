package m7.graduatework.repository;

import m7.graduatework.entity.Ad;
import m7.graduatework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByAuthor(User user);

    void removeById(Long id);
}
