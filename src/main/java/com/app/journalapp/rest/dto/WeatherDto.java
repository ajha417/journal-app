package com.app.journalapp.rest.dto;

// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDto{
    public Current current;

    @Getter
    @Setter
    public class Current{
        public int temperature;
        @JsonProperty("wind_speed")
        public int windSpeed;
        @JsonProperty("wind_dir")
        public String windDir;
        @JsonProperty("feelslike")
        public int feelsLike;
    }
}

