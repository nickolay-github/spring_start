package com.example.demo.repos;

import com.example.demo.domain.Message;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    Page<Message> findByTag(String tag, Pageable pageable);
    Page<Message> findAll(Pageable pageable);
    Page<Message> findAllByAuthor(User author, Pageable pageable);
}
