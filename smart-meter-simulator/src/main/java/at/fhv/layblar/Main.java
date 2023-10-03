package at.fhv.layblar;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class Main {
    public static void main(String... args) {
        Quarkus.run(Simulator.class, args);
    }

    public static class Simulator implements QuarkusApplication {

        @Inject
        Generator generator;

        @Override
        public int run(String... args) throws Exception {
            generator.sendMeterData();
            Quarkus.waitForExit();
            return 0;
        }
    }
}
