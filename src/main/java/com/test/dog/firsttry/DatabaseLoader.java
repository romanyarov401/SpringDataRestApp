package com.test.dog.firsttry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final RequestRepository repository;

    @Autowired
    public DatabaseLoader(RequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new Request("Иван", "Иванов", "прорвало трубу в туалете",
                "г. Нижний Новгород, ул. Пушкина, д. 18", 2, 361.78, false,
                LocalDateTime.now()));
    }
}