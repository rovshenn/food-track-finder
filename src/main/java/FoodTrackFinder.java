import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.FoodTruckInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class FoodTrackFinder {

    private static final String HH24_MM = "HH:mm";
    private static final String FOODTRUCK_DATA_URL = "http://data.sfgov.org/resource/bbb8-hzi6.json";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DISPLAY_FORMAT = "%-30s%s";
    private static final Logger logger = Logger.getLogger(FoodTruckInfo.class.getName());
    public static final int PAGE_SIZE = 10;

    public static void main(String[] args) {
        FoodTrackFinder foodTrackFinder = new FoodTrackFinder();
        try {
            List<FoodTruckInfo> foodTruckInfos = foodTrackFinder.loadTruckData();

            LocalDateTime localDate = LocalDateTime.now(TimeZone.getDefault().toZoneId());
            List<FoodTruckInfo> foodTruckInfoOpen = foodTruckInfos.stream().filter(foodTruckInfo -> {
                LocalDateTime startLocalDateTime = convertTimeStrToLocalDateTime(localDate, foodTruckInfo.getStartTime());
                LocalDateTime endLocalDateTime = convertTimeStrToLocalDateTime(localDate, foodTruckInfo.getEndTime());
                return (localDate.isEqual(startLocalDateTime) || localDate.isAfter(startLocalDateTime)) &&
                        localDate.isBefore(endLocalDateTime);
            }).distinct().collect(Collectors.toList());
            foodTrackFinder.showOpenFoodTrucks(foodTruckInfoOpen);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not retrieve food truck data, please try again later");
            logger.log(Level.SEVERE, e.getLocalizedMessage());
        }
    }

    private static LocalDateTime convertTimeStrToLocalDateTime(LocalDateTime currentLocalDateTime, String hourMin) {
        LocalTime localTime = LocalTime.parse(hourMin, DateTimeFormatter.ofPattern(HH24_MM));
        return LocalDateTime.of(currentLocalDateTime.getYear(), currentLocalDateTime.getMonth(), currentLocalDateTime.getDayOfMonth(),
                localTime.getHour(), localTime.getMinute(), 0);
    }

    private List<FoodTruckInfo> loadTruckData() throws Exception {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet(FOODTRUCK_DATA_URL);
            CloseableHttpResponse response = client.execute(request);
            if (response == null || response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Could not get data for food trucks");
            }
            List<FoodTruckInfo> foodTruckInfos = new ArrayList<>();
            String foodTruckDataStr = EntityUtils.toString(response.getEntity());
            if (StringUtils.isEmpty(foodTruckDataStr)) {
                return foodTruckInfos;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode tree = objectMapper.readTree(foodTruckDataStr);
            Iterator<JsonNode> elements = tree.elements();
            while (elements.hasNext()) {
                foodTruckInfos.add(objectMapper.convertValue(elements.next(), FoodTruckInfo.class));
            }
            return foodTruckInfos;
        }
    }

    private void showOpenFoodTrucks(List<FoodTruckInfo> foodTruckInfos) throws IOException {
        int idx = 0;
        List<FoodTruckInfo> foodTruckInfosCurrent;
        if (CollectionUtils.isEmpty(foodTruckInfos)) {
            logger.log(Level.FINE, "Sorry, no food trucks at the moment.");
            return;
        }
        System.out.println(String.format(DISPLAY_FORMAT, "NAME", "ADDRESS"));
        while (foodTruckInfos.size() > idx) {
            foodTruckInfosCurrent = getPage(foodTruckInfos, idx);
            idx += PAGE_SIZE;
            displayFoodTruckInfo(foodTruckInfosCurrent);
            if (foodTruckInfos.size() > idx) {
                waitToDisplayMore();
            } else {
                System.out.println("We hope you found a nice place!");
            }
        }
    }

    private List<FoodTruckInfo> getPage(List<FoodTruckInfo> foodTruckInfos, int idx) {
        int remainingItems = foodTruckInfos.size() - idx;
        int idxEndExclusive = idx;
        if (remainingItems < PAGE_SIZE) {
            idxEndExclusive += remainingItems;
        } else {
            idxEndExclusive += PAGE_SIZE;
        }
        return foodTruckInfos.subList(idx, idxEndExclusive);
    }

    private void waitToDisplayMore() throws IOException {
        System.out.print("Press enter key to see more...");
        System.in.read();
        System.out.println();
    }

    private void displayFoodTruckInfo(List<FoodTruckInfo> foodTruckInfos) {
        StringBuilder sb = new StringBuilder();
        for (FoodTruckInfo foodTruckInfo : foodTruckInfos) {
            sb.append(String.format(DISPLAY_FORMAT, foodTruckInfo.getApplicant(), foodTruckInfo.getLocation()))
                    .append(LINE_SEPARATOR);
        }
        System.out.println(sb);
    }
}
