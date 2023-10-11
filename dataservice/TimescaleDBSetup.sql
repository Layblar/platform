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
  uptime TIME NOT NULL
);

SELECT create_hypertable('smart_meter_data','time');
SELECT add_dimension('smart_meter_data', 'sensorId');