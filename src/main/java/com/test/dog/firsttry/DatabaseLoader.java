// Для загрузки записей в БД

package com.test.dog.firsttry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final RequestRepository repository;

    @Autowired
    public DatabaseLoader(RequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {

        //для тестирования с локальной БД
        /* this.repository.save(new Request("Иван", "Иванов", "прорвало трубу в туалете",
                "г. Нижний Новгород, ул. Пушкина, д. 18", 2, 361.78, false,
                LocalDateTime.now())); */

        //для работы с Microsoft SQL БД
        List<Request> requestData = DatabaseMSSQL.getData();
        for (Request request: requestData) {
            this.repository.save(request);
        }

    }
}