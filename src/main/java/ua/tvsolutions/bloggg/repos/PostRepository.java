package ua.tvsolutions.bloggg.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.tvsolutions.bloggg.models.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Iterable<Post> findByTag(String filter);

    @Query("select p from Post p where p.text like %?1%")
    List<Post> findByTextContains(String filter);
}
