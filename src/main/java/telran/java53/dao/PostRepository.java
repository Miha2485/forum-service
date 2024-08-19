package telran.java53.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java53.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthor(String author);
    List<Post> findByTagsIn(List<String> tags);
    List<Post> findByDateCreatedBetween(LocalDateTime from, LocalDateTime to);
}
