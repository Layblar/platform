package at.fhv.layblar.infrastructure;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import at.fhv.layblar.domain.Household;
import at.fhv.layblar.domain.MeterDataReading;
import io.quarkiverse.rabbitmqclient.RabbitMQClient;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.rabbitmq.client.*;

@ApplicationScoped
public class MeterDataProcessor {

    @Inject
    ObjectMapper mapper;

    @Inject
    @org.eclipse.microprofile.reactive.messaging.Channel("rabbit")
    Emitter<Record<String,MeterDataReading>> emitter;

    @Inject
    RabbitMQClient client;

    private Channel channel;
    private Connection connection;

    public void onApplicationStart(@Observes StartupEvent event) {
        connection = client.connect();
        List<Household> households =Household.listAll();
        for (Household household : households) {
            for (String smartMeterId : household.smartMeterId) {
                createChannelAsync(household.householdId, smartMeterId);
            }
        }
    }

    public CompletableFuture<Void> createChannelAsync(String householdId, String smartMeterId) {
        return CompletableFuture.runAsync(() -> createChannel(householdId, smartMeterId));
    }

    public void deleteQueue(String smartMeterId) {
        try {
            channel.queueDelete(smartMeterId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChannel(String householdId, String smartMeterId) {
        try {
            channel = connection.createChannel();
            channel.queueDeclare(smartMeterId, true, false, false, null);
            channel.queueBind(smartMeterId, "amq.topic", smartMeterId);
            channel.basicConsume(smartMeterId, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    emitter.send(Record.of(householdId, createMeterDataReading(householdId, smartMeterId, JsonObject.mapFrom(mapper.readTree(new String(body, StandardCharsets.UTF_8))))));
                    //saveToDatabase(householdId, smartMeterId, JsonObject.mapFrom(mapper.readTree(new String(body, StandardCharsets.UTF_8))));
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    @Incoming("data")
    @Blocking
    @Transactional
    public void saveToDatabase(Record<String,MeterDataReading> record){
        record.value().persist();
    }   

    public MeterDataReading createMeterDataReading(String householdId, String smartMeterId, JsonObject data) throws JsonMappingException, JsonProcessingException {
        MeterDataReading mdr = mapper.readValue(data.encode(), MeterDataReading.class);
        mdr.time = LocalDateTime.parse(data.getString("timestamp"));
        mdr.sensorId = smartMeterId;
        mdr.householdId = householdId;
        return mdr;
    }

    // @Transactional
    // public void saveToDatabase(String householdId, String smartMeterId, JsonObject data) throws JsonMappingException, JsonProcessingException {
    //     MeterDataReading mdr = mapper.readValue(data.encode(), MeterDataReading.class);
    //     mdr.time = LocalDateTime.parse(data.getString("timestamp"));
    //     mdr.sensorId = smartMeterId;
    //     mdr.householdId = householdId;
    //     MeterDataReading.persist(mdr);
    // }

}
