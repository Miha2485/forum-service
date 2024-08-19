package telran.java53.ForumService;

import java.util.List;

import telran.java53.Forum.Dto.DatePeriodDto;
import telran.java53.Forum.Dto.NewCommentDto;
import telran.java53.Forum.Dto.NewPostDto;
import telran.java53.Forum.Dto.PostDto;

public interface PostService {
	PostDto addNewPost(String author, NewPostDto newPostDto);

	PostDto findPostById(String id);

	PostDto removePost(String id);

	PostDto updatePost(String id, NewPostDto newPostDto);

	PostDto addComment(String id, String author, NewCommentDto newCommentDto);

	void addLike(String id);

	Iterable<PostDto> findPostByAuthor(String author);

	Iterable<PostDto> findPostsByTags(List<String> tags);

	Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto);

}
