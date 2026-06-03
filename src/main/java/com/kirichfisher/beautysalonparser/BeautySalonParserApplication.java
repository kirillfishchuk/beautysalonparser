package com.kirichfisher.beautysalonparser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;

@SpringBootApplication
public class BeautySalonParserApplication {

    @Value("${server.port:8080}")
    private String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(BeautySalonParserApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("\n========================================");
        System.out.println("Application started successfully");
        System.out.println("Local URL: http://localhost:" + serverPort);

        printAddresses(serverPort);

        System.out.println("========================================\n");
    }

    private void printAddresses(String port) {
        try {
            System.out.println("\nAvailable URLs:");

            for (NetworkInterface networkInterface :
                    Collections.list(NetworkInterface.getNetworkInterfaces())) {

                for (InetAddress address :
                        Collections.list(networkInterface.getInetAddresses())) {

                    String hostAddress = address.getHostAddress();

                    // Skip loopback and IPv6
                    if (address.isLoopbackAddress() || hostAddress.contains(":")) {
                        continue;
                    }

                    System.out.println("http://" + hostAddress + ":" + port);
                }
            }
        } catch (Exception e) {
            System.err.println("Could not enumerate network interfaces: "
                    + e.getMessage());
        }
    }
}