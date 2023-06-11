package m7.graduatework.repository;

import m7.graduatework.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndAd_Id(Long id, Long adId);

    Long removeById(Long id);
}
