package com.app.journalapp.service;

import com.app.journalapp.cache.AppCache;
import com.app.journalapp.constants.IPlaceHolderConstants;
import com.app.journalapp.rest.dto.WeatherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String API_KEY;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;


    public WeatherDto getWeather(String city) {

        WeatherDto weatherDto = redisService.getRedisCacheEntry("weather#" + city, WeatherDto.class);
        if (weatherDto != null) {
            return weatherDto;
        }
        else {
            String url = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(IPlaceHolderConstants.PARAM_CITY, city).replace(IPlaceHolderConstants.PARAM_API_KEY, API_KEY).replace("city", city);

            // for post call
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Accept", "application/json");
//        headers.add("key","value");
//        HttpEntity<String> http = new HttpEntity<>("parameters", headers);
//        restTemplate.exchange(url, HttpMethod.POST, http, String.class);


            ResponseEntity<WeatherDto> response = restTemplate.exchange(url, HttpMethod.GET, null, WeatherDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                WeatherDto responseWeatherDto = response.getBody();
                if (responseWeatherDto != null) {
                    redisService.saveRedisCacheEntry("weather#" + city, response.getBody(), 3000L);
                }
            }
            return null;
        }
    }
}
