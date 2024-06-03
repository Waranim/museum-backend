package org.example.museumbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.museumbackend.adapter.repository.NewsRepository;
import org.example.museumbackend.adapter.web.DTO.request.NewsReqDTO;
import org.example.museumbackend.adapter.web.DTO.response.NewsResDTO;
import org.example.museumbackend.adapter.web.DTO.response.SiteResDTO;
import org.example.museumbackend.domain.NewsEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class NewsService {

    NewsRepository newsRepository;

    ImageService imageService;

    public NewsResDTO createNews(NewsReqDTO newsDTO, MultipartFile[] images) throws Exception {
        var news = new NewsEntity();

        var date = new Timestamp(System.currentTimeMillis());
        var imageList = imageService.createListImages(news, images);

        news.setTitle(newsDTO.title());
        news.setSummary(newsDTO.summary());
        news.setContent(newsDTO.content());
        news.setPublishDate(date);
        news.setImages(imageList);

        newsRepository.save(news);

        return new NewsResDTO(
                news.getId(),
                news.getTitle(),
                news.getSummary(),
                news.getContent(),
                news.getPublishDate(),
                imageService.createListImageLinks(news.getImages()));
    }

    public NewsResDTO getNews(Long id) {
        var newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            var news = newsOptional.get();

            return new NewsResDTO(
                    news.getId(),
                    news.getTitle(),
                    news.getSummary(),
                    news.getContent(),
                    news.getPublishDate(),
                    imageService.createListImageLinks(news.getImages()));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public List<NewsResDTO> getAllNews() {
        var news = newsRepository.findAll();
        return news
                .stream()
                .map(newsEntity -> new NewsResDTO(
                        newsEntity.getId(),
                        newsEntity.getTitle(),
                        newsEntity.getSummary(),
                        newsEntity.getContent(),
                        newsEntity.getPublishDate(),
                        imageService.createListImageLinks(newsEntity.getImages())))
                .toList();
    }

    public NewsResDTO updateNews(Long id, NewsReqDTO newsDTO) {
        var newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            var news = newsOptional.get();
            news.setTitle(newsDTO.title());
            news.setSummary(newsDTO.summary());
            news.setContent(newsDTO.content());

            newsRepository.save(news);
            return new NewsResDTO(
                    news.getId(),
                    news.getTitle(),
                    news.getSummary(),
                    news.getContent(),
                    news.getPublishDate(),
                    imageService.createListImageLinks(news.getImages()));
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    public void deleteNews(Long id) {
        var newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            var news = newsOptional.get();
            newsRepository.delete(news);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
