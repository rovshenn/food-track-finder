package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * "addr_date_modified": "2011-11-14T16:22:25.000",
 * "applicant": "May Catering",
 * "block": "4603A",
 * "cnn": "5329000",
 * "coldtruck": "Y",
 * "dayofweekstr": "Thursday",
 * "dayorder": "4",
 * "end24": "11:00",
 * "endtime": "11AM",
 * "location": "1059 EVANS AVE",
 * "location_2": {
 * "type": "Point",
 * "coordinates": [-122.378155718149, 37.737071230506]
 * },
 * "locationdesc": "Set-up at 25 Middle Point: 10:04am-10:05am",
 * "locationid": "934630",
 * "longitude": "-122.37815571814879",
 * "lot": "005",
 * "optionaltext": "Cold Truck: Sandwiches, fruit, snacks, candy, hot and cold drinks",
 * "permit": "17MFF-0110",
 * "start24": "10:00",
 * "starttime": "10AM",
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodTruckInfo {

    private String applicant;
    private String block;
    private String location;
    @JsonProperty("dayorder")
    private int dayOrder;
    @JsonProperty("end24")
    private String endTime;
    @JsonProperty("start24")
    private String startTime;
    @JsonProperty("coldtruck")
    private String coldTruck;
    @JsonProperty("optionaltext")
    private String optionalText;

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(int dayOrder) {
        this.dayOrder = dayOrder;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getColdTruck() {
        return coldTruck;
    }

    public void setColdTruck(String coldTruck) {
        this.coldTruck = coldTruck;
    }

    public String getOptionalText() {
        return optionalText;
    }

    public void setOptionalText(String optionalText) {
        this.optionalText = optionalText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodTruckInfo that = (FoodTruckInfo) o;
        return applicant.equals(that.applicant) &&
                location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicant, location);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FoodTruckInfo{");
        sb.append("applicant='").append(applicant).append('\'');
        sb.append(", block='").append(block).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", dayOrder=").append(dayOrder);
        sb.append(", endTime='").append(endTime).append('\'');
        sb.append(", startTime='").append(startTime).append('\'');
        sb.append(", coldTruck='").append(coldTruck).append('\'');
        sb.append(", optionalText='").append(optionalText).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
