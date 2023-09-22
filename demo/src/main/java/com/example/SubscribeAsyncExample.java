package com.example;

import com.example.model.Pedido;
import com.example.service.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SubscribeAsyncExample {
    private static PedidoService pedidoService = new PedidoService();  // Assume que você tem uma instância válida

    public static void main(String... args) throws Exception {
        // TODO(developer): Replace these variables before running the sample.
        String projectId = "serjava-demo";
        String subscriptionId = "eco-franca-sub";

        subscribeAsyncExample(projectId, subscriptionId);
    }

    public static void subscribeAsyncExample(String projectId, String subscriptionId) {
        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);

        // Instantiate an asynchronous message receiver.
        MessageReceiver receiver = (PubsubMessage message, AckReplyConsumer consumer) -> {
            try {
                // Handle incoming message.
                String messageData = message.getData().toStringUtf8();

                // Converte a mensagem em um objeto Pedido (ou apropriado para seus dados)
                ObjectMapper objectMapper = new ObjectMapper();
                Pedido pedido = objectMapper.readValue(messageData, Pedido.class);

                // Salva o pedido no banco de dados usando um serviço de serviço de pedido
                pedidoService.salvarPedido(pedido);

                // Acknowledge the received message.
                consumer.ack();
            } catch (Exception e) {
                // Handle any errors.
                e.printStackTrace();
                consumer.nack();
            }
        };

        Subscriber subscriber = null;
        try {
            subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
            // Start the subscriber.
            subscriber.startAsync().awaitRunning();
            System.out.printf("Listening for messages on %s:\n", subscriptionName.toString());
            // Allow the subscriber to run for 30s unless an unrecoverable error occurs.
            subscriber.awaitTerminated(30, TimeUnit.SECONDS);
        } catch (TimeoutException timeoutException) {
            // Shut down the subscriber after 30s. Stop receiving messages.
            subscriber.stopAsync();
        }
    }
}
