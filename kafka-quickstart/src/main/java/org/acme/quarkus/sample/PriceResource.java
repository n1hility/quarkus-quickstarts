package org.acme.quarkus.sample;

import io.smallrye.reactive.messaging.annotations.Stream;

import org.eclipse.microprofile.reactive.streams.operators.ReactiveStreams;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * A simple resource retrieving the "in-memory" "my-data-stream" and sending the items to a server sent event.
 */
@Path("/prices")
public class PriceResource {

    @Inject
    @Stream("my-data-stream") Publisher<Double> prices;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }


    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<Double> stream() {
        ///return ReactiveStreams.of(10.4, 10.5).buildRs();
       return ReactiveStreams.fromPublisher(prices).map(i -> (i + 300.0)).buildRs();
    }
}