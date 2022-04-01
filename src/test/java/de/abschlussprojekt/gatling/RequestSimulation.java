package de.abschlussprojekt.gatling;

import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

class RequestSimulation extends Simulation {

    void start() {
        GatlingPropertiesBuilder props = new GatlingPropertiesBuilder();
        props.simulationClass("RequestSimulation.java");
        Gatling.fromMap(props.build());
    }

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json")
            .doNotTrackHeader("1");

    ScenarioBuilder scn = scenario("Reiseloesung-Anfrage Simulation")
            .exec(http("hinfahrt")
                    .get("/reiseloesung?abfahrtLocation=frankfurt&ankunftLocation=erfurt&hinfahrtDate=2022-08-01")) // 9
            .pause(1);

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}

//import io.gatling.core.scenario.Simulation;
//import io.gatling.javaapi.core.ScenarioBuilder;
//import io.gatling.javaapi.http.HttpProtocolBuilder;
//
//import java.util.Collections;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.UUID;
//import java.util.function.Supplier;
//import java.util.stream.Stream;
//
//import static io.gatling.javaapi.core.CoreDsl.scenario;
//import static io.gatling.javaapi.http.HttpDsl.http;
//import static io.gatling.javaapi.http.HttpDsl.status;
//
//public class RequestSimulation extends Simulation {
//
//    private final String baseUrl = "http://localhost:8080";
//    private final String uri = "http://localhost:8080/?abfahrtLocation=frankfurt&ankunftLocation=erfurt&hinfahrtDate=2022-08-01";
//    private final String contentType = "application/json";
//    private final String endpoint = "/api/1.0/arrival/all";
//    private final String authUser= "blaze";
//    private final int requestCount = 1000;
//
//    HttpProtocolBuilder httpProtocol = http
//            .baseUrl(baseUrl)
//            .inferHtmlResources()
//            .acceptHeader("*/*")
//            .acceptHeader("application/json")
//            .userAgentHeader("Gatling/Performance Test");
//
//    Iterator<Map<String, Object>> feeder =
//            Stream.generate((Supplier<Map<String, Object>>) ()
//                    -> Collections.singletonMap("username", UUID.randomUUID().toString())
//            ).iterator();
//
//    ScenarioBuilder scn = scenario("Scenario 1")
//            .exec(http("bla").get("/reiseloesung?abfahrtLocation=frankfurt&ankunftLocation=erfurt&hinfahrtDate=2022-08-01")
//                    .check(status().is(200)));
//
//
//
//
//
//    ChainBuilder chainBuilder = exec(http("bla").get("/reiseloesung?abfahrtLocation=frankfurt&ankunftLocation=erfurt&hinfahrtDate=2022-08-01"));
//
//
//
//    ScenarioBuilder scn = CoreDsl.scenario("Load Test Creating Customers")
//            .feed(feeder)
//            .exec(http("create-customer-request")
//                    .get("/api/customers")
//                    .header("Content-Type", "application/json")
//                    .body(StringBody("{ \"username\": \"${username}\" }"))
//                    .check(status().is(201))
//                    .check(header("Location").saveAs("location"))
//            )
//            .exec(http("get-customer-request")
//                    .get(session -> session.getString("location"))
//                    .check(status().is(200))
//            );
//
//    public void CustomerRequestSimulation() {
//        this.setUp(scn.injectOpen(constantUsersPerSec(50).during(Duration.ofSeconds(15))))
//                .protocols(httpProtocol);
//    }
//}
