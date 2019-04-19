package gargoyle.l0x.services.app;

import gargoyle.l0x.entities.app.*;
import gargoyle.l0x.repositories.app.*;
import gargoyle.l0x.repositories.user.UserRepository;
import gargoyle.l0x.services.away.Away;
import gargoyle.l0x.services.md.MD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class App {
    private final MD md;
    private final Away away;
    private final CommentRepository commentRepository;
    private final CreationRepository creationRepository;
    private final NewsRepository newsRepository;
    private final QuoteRepository quoteRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;

    public List<Comment> getComments(Creation creation) {
        return commentRepository.findAllByCreationOrderByDateDesc(creation);
    }

    public List<News> getFirstNews(int count) {
        Page<News> page = newsRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "date")));
        return page.hasContent() ? page.getContent() : new ArrayList<>();
    }

    public Quote getRandomQuote() {
        Page<Quote> page = quoteRepository.findAll(PageRequest.of((int) (Math.random() * quoteRepository.count()), 1));
        return page.hasContent() ? page.getContent().get(0) : new Quote();
    }

    public Optional<Creation> findCreation(String alias) {
        return creationRepository.findById(alias);
    }

    public List<Creation> findCreations(Sort sort) {
        return creationRepository.findAll(sort);
    }

    public Creation save(Creation creation) {
        return creationRepository.saveAndFlush(creation);
    }

    private Sort getCreationsSort() {
        return Sort.by(Sort.Direction.ASC, "alias");
    }

    public List<Creation> findCreationsByHeadOrSource(String query) {
        return creationRepository.findAllByHeadContainsOrSourceContains(query, query);
    }

    public Page<Creation> findCreationsByHeadOrSource(String query, int page, int size) {
        return creationRepository.findAllByHeadContainsOrSourceContains(query, query, PageRequest.of(page, size, getCreationsSort()));
    }

    public void vote(String creationAlias, String username, int rate) {
        Vote vote = Vote.builder().creationAlias(creationAlias).userUsername(username).build();
        vote = voteRepository.findOne(Example.of(vote)).orElse(vote);
        vote.setRate(rate);
        voteRepository.saveAndFlush(vote);
    }

    public int getVote(String creationAlias) {
        return (int) Math.round(voteRepository.findAllByCreationAlias(creationAlias).stream()
                .map(Vote::getRate).mapToInt(Integer::intValue).summaryStatistics().getAverage());
    }

    public Comment comment(String alias, String username, String head, String source) {
        return commentRepository.saveAndFlush(Comment.builder()
                .author(userRepository.getOne(username))
                .date(LocalDateTime.now())
                .source(source)
                .body(away.rewriteHtml(md.toHtml(source)))
                .creation(creationRepository.getOne(alias))
                .flags(new char[0])
                .build());
    }
}
