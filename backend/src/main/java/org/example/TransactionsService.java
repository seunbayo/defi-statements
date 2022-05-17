package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Transaction;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static org.example.model.Transaction.Builder.transaction;

public class TransactionsService {

    private final static String TEMPLATE_URI_STRING = "https://api.covalenthq.com/v1/%s/address/%s/transactions_v2/?quote-currency=USD&format=JSON&block-signed-at-asc=false&no-logs=false&page-size=10000&key=%s";
    private final static ObjectMapper MAPPER = new ObjectMapper();

    public static List<Transaction> getResponse(int chainId, String address, String apiKey) throws URISyntaxException, IOException, InterruptedException {
        final var request = HttpRequest.newBuilder()
            .uri(new URI(format(TEMPLATE_URI_STRING, chainId, address, apiKey)))
            .GET()
            .build();

        final var response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        final var jsonNode = MAPPER.readTree(response.body());
        return StreamSupport.stream(jsonNode.path("data").path("items").spliterator(), false)
            .map(node -> transaction()
                .hash(node.path("tx_hash").asText())
                .from(node.path("from_address").asText())
                .to(node.path("to_address").asText())
                .value(node.path("value").asText())
                .valueQuote(node.path("value_quote").decimalValue())
                .feesPaid(node.path("fees_paid").asText())
                .build())
            .toList();
    }

}
