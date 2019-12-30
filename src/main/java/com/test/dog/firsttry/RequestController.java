package com.test.dog.firsttry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RequestController {
    @Autowired
    private RequestRepository requestRepository;

    @GetMapping("/requests")
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable(value = "id") Long requestId)
            throws ResourceNotFoundException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Заявки не найдено с ID = " + requestId));
        return ResponseEntity.ok().body(request);
    }

    @PostMapping("/requests")
    public Request createRequest(@Valid @RequestBody Request request) {
        return requestRepository.save(request);
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable(value = "id") Long requestId,
                                                   @Valid @RequestBody Request requestDetails) throws ResourceNotFoundException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Заявки не найдено с ID = " + requestId));

        request.setFirstName(requestDetails.getFirstName());
        request.setLastName(requestDetails.getLastName());
        request.setDescription(requestDetails.getDescription());
        request.setAddress(requestDetails.getAddress());
        request.setDaysToComplete(requestDetails.getDaysToComplete());
        request.setWorkCost(requestDetails.getWorkCost());
        request.setPrePayment(requestDetails.isPrePayment());
        final Request updatedRequest = requestRepository.save(request);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/requests/{id}")
    public Map<String, Boolean> deleteRequest(@PathVariable(value = "id") Long requestId)
            throws ResourceNotFoundException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Заявки не найдено с ID = " + requestId));

        requestRepository.delete(request);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
