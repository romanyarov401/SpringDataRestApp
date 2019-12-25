//Репозиторий данных

package com.test.dog.firsttry;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends PagingAndSortingRepository<Request, Long> {

}
