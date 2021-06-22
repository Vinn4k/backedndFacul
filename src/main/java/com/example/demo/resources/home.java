package com.example.demo.resources;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.NewsDb;
import com.example.demo.repository.newsRepository;


@RestController
@CrossOrigin
public class home {
	
	@Autowired
	newsRepository newsRepo;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	  public ResponseEntity<?> helo(){
		  return ResponseEntity.status(HttpStatus.OK).body("Tudo ok");
		}
	@ResponseBody
	@RequestMapping(value="/news", method=RequestMethod.POST)
	  public ResponseEntity<?> novoAluno(@RequestBody NewsDb news){
		newsRepo.save(news);
		 return ResponseEntity.status(HttpStatus.CREATED).body(news);
		  }
	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> allNoticias(@PathVariable Integer id) {
		Optional<NewsDb> optNoticia = newsRepo.findById(id);
		if (optNoticia.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optNoticia.get());
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> newsDeletID(@PathVariable Integer id) {
		Optional<NewsDb> optNews = newsRepo.findById(id);
		if (!optNews.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		newsRepo.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body(optNews.get());
	}

	@ResponseBody
	@RequestMapping(value = "/news/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeNews(@PathVariable Integer id, @RequestBody NewsDb news) {
		Optional<NewsDb> optNews = newsRepo.findById(id);
		if (!optNews.isPresent())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");

		NewsDb currentNews = optNews.get();

	
		return ResponseEntity.status(HttpStatus.OK).body(currentNews);
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public ResponseEntity<?> getNews() {
		List<NewsDb> news = newsRepo.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(news);
	}

}
