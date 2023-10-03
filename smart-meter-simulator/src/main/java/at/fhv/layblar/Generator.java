package at.fhv.layblar;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Generator {

    @Channel("meter-data")
    Emitter<MeterDataReading> emitter;

    public void sendMeterData() throws InterruptedException {

        while (true) {

            try (InputStream inputStream = this.getClass().getResourceAsStream("/data/tmp_data.json")) {
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(inputStream);
    
                while (parser.nextToken() != null) {
                    if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                        emitter.send(getMeterDataFromJson(parser));
                        Thread.sleep(5000);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private MeterDataReading getMeterDataFromJson(JsonParser parser) throws JsonProcessingException, IllegalArgumentException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.treeToValue(processJsonObject(parser), MeterDataReading.class);
    }

    private static ObjectNode processJsonObject(JsonParser parser) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonObject = objectMapper.createObjectNode();

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = parser.getCurrentName();
            parser.nextToken(); // Move to the field value

            if (parser.getCurrentToken() == JsonToken.START_OBJECT) {
                // If a nested object is encountered, recursively process it
                ObjectNode nestedObject = processJsonObject(parser);
                jsonObject.set(fieldName, nestedObject);
            } else {
                // Otherwise, add the field and value to the JsonObject
                String fieldValue = parser.getValueAsString();
                jsonObject.put(fieldName, fieldValue);
            }
        }

        return jsonObject;
    }
}
