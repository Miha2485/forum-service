package telran.java53.ForumService;

import org.springframework.stereotype.Service;
import telran.java53.Forum.Dto.DatePeriodDto;
import telran.java53.Forum.Dto.NewCommentDto;
import telran.java53.Forum.Dto.NewPostDto;
import telran.java53.Forum.Dto.PostDto;
import telran.java53.dao.PostRepository;
import telran.java53.model.Comment;
import telran.java53.model.Post;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ModelMapper modelMapper;

	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto addNewPost(String author, NewPostDto newPostDto) {
		Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author, newPostDto.getTags());
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto findPostById(String id) {
		Optional<Post> post = postRepository.findById(id);
		return post.map(value -> modelMapper.map(value, PostDto.class)).orElse(null);
	}

	@Override
	public PostDto removePost(String id) {
		Optional<Post> post = postRepository.findById(id);
		post.ifPresent(postRepository::delete);
		return post.map(value -> modelMapper.map(value, PostDto.class)).orElse(null);
	}

	@Override
	public PostDto updatePost(String id, NewPostDto newPostDto) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.setTitle(newPostDto.getTitle());
			post.setContent(newPostDto.getContent());
			postRepository.save(post);
			return modelMapper.map(post, PostDto.class);
		}
		return null;
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Optional<Post> optionalPost = postRepository.findById(id);
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			post.addComment(new Comment(author, newCommentDto.getMessage()));
			postRepository.save(post);
			return modelMapper.map(post, PostDto.class);
		}
		return null;
	}

	@Override
	public void addLike(String id) {
		Optional<Post> optionalPost = postRepository.findById(id);
		optionalPost.ifPresent(post -> {
			post.addLike();
			postRepository.save(post);
		});
	}

	@Override
	public Iterable<PostDto> findPostByAuthor(String author) {
		List<Post> posts = postRepository.findByAuthor(author);
		return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
		List<Post> posts = postRepository.findByTagsIn(tags);
		return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
		List<Post> posts = postRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom().atStartOfDay(),
				datePeriodDto.getDateTo().atTime(23, 59, 59));
		return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}
}
