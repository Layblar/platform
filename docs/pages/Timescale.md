# TimescaleDB
#### [Jakob Feistenauer](https://github.com/yescob)
Short guide to setup, start and use the TimescaleDB for the Layblar-Platform.

## Starting Timescale

To start the database navigate to the docker folder of the platform repository.
In the docker-compose.yaml the following Timescale container is defined:

```
  timescaledb:
    image: timescale/timescaledb-ha:pg14-latest
    ports:
      - 5432:5432
    environment:
      POSTGRES_USER: layblar
      POSTGRES_PASSWORD: password
      POSTGRES_DB: meterdata
    volumes:
      - ./TimeScaleDBSetup.sql:/docker-entrypoint-initdb.d/TimeScaleDBSetup.sql
    networks:
      - layblar-platform
```

To start the database execute:

```
docker compose up timescaledb
```

## Creating the necessary tables

Hypertables are created in the TimeScaleDBSetup.sql file in the docker folder. This file will be automatically executed when the container is started.
First create a Table with a TIMESTAMPTZ field and any other necessary fields.

```SQL
CREATE TABLE smart_meter_data (
  time TIMESTAMPTZ NOT NULL,
  sensorId VARCHAR(255) NOT NULL,
  v1_7_0 NUMERIC NULL,
  v1_8_0 NUMERIC NULL,
  v2_7_0 NUMERIC NULL,
  v2_8_0 NUMERIC NULL,
  v3_8_0 NUMERIC NULL,
  v4_8_0 NUMERIC NULL,
  v16_7_0 NUMERIC NULL,
  v31_7_0 NUMERIC NULL,
  v32_7_0 NUMERIC NULL,
  v51_7_0 NUMERIC NULL,
  v52_7_0 NUMERIC NULL,
  v71_7_0 NUMERIC NULL,
  v72_7_0 NUMERIC NULL,
  uptime VARCHAR(13) NULL
);
```

Then create a Hypertable from the newly created Table.
```SQL
SELECT create_hypertable('smart_meter_data','time');
```
[Timescale Documentation](https://docs.timescale.com/)

For Production Use all tables, even normal PostgreSQL tables should be created in this file. 
During development you can let the Panache Framework create the necessary standart tables.

## Accessing the data with Quarkus

### Quarkus Panache

You can use Quarkus Panache to access the data from a Hypertable. Create an Entity and use the @Table Annotation to bind it to the correct Databasetable.

```Java
@Entity
@Table(name = "smart_meter_data")
public class MeterDataReading extends PanacheEntityBase  {

    @Id
    public LocalDateTime time;

    ...
}
```
[Quarkus Documentation on Panache](https://quarkus.io/guides/hibernate-orm-panache)

### Native Query

You can also write native queries to access the data. Some features like TIME_BUCKET can only be used with a Native Query.

```Java
@Inject
EntityManager entityManager;

public List<String> nativeQuery() {
    List<Object[]> list = entityManager.createNativeQuery("SELECT time_bucket('5 minutes', time) AS bucket, avg(v1_7_0) FROM smart_meter_data GROUP BY bucket ORDER BY bucket DESC").getResultList();

    List<String> result = new LinkedList<>();
    for(Object[] row : list) {
        result.add("{\"Bucket\": \"" + row[0] + "\",\"Average\": \"" + row[1] + "\"}");
    }
    return result;

}
```

