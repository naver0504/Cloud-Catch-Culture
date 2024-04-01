package com.example.eventservice.register;

import com.example.eventservice.entity.event.CulturalEvent;
import com.example.eventservice.repository.CulturalEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("/register")
public class RegistrationController {

    private final CulturalEventRepository culturalEventRepository;

    @Value("${openapi.key}")
    private String openApiKey;

//    @GetMapping("/0-999")
    public void registration0to1000() {
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final RestTemplate restTemplate = new RestTemplate();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 0, 999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

//    @GetMapping("/1000-1999")
    public void registration1000to1999() {
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final RestTemplate restTemplate = new RestTemplate();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 1000, 1999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

//    @GetMapping("/2000-2999")
    public void registration2000to2999() {
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final RestTemplate restTemplate = new RestTemplate();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 2000, 2999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }

//    @GetMapping("/3000-3999")
    public void registration3000to3999() {
        final List<CulturalEvent> culturalEventList = new ArrayList<>();
        final RestTemplate restTemplate = new RestTemplate();
        final HashMap<String, Object> result = restTemplate.getForObject(RegistrationUtils.getOpenApiUrl(openApiKey, 3000, 3999), HashMap.class);
        addCulturalEventToList(culturalEventList, result);
        culturalEventRepository.saveAll(culturalEventList);
    }
    private void addCulturalEventToList(final List<CulturalEvent> culturalEventList, final HashMap<String, Object> result) {
        if(RegistrationUtils.isResultSuccess(result)) {
            RegistrationUtils.getEventInfo(result).forEach(event -> {

                        log.info("Event = {}",event);
                        culturalEventList.add(RegistrationUtils.createCulturalEvent(event));
                    }
            );
        }
    }




}
