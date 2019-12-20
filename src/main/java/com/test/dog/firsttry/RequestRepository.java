//Репозиторий данных

package com.test.dog.firsttry;

import org.springframework.data.repository.CrudRepository;

public interface RequestRepository extends CrudRepository<Request, Long> {
}
