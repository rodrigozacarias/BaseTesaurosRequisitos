package com.requirementsthesauri.service;

import com.franz.agraph.repository.AGCatalog;
import com.franz.agraph.repository.AGRepository;
import com.franz.agraph.repository.AGRepositoryConnection;
import com.franz.agraph.repository.AGServer;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.riot.web.HttpOp;

import java.util.ArrayList;
import java.util.List;

public class Authentication {

    String SERVER_URL = "http://localhost:10035";
    String CATALOG_ID = "system";
    String REPOSITORY_ID = "requirements";
    String USERNAME = "admin";
    String PASSWORD = "admin";
    AGRepositoryConnection conn;

    public void getConnectionDataBase() {

        // Tests getting the repository up.
        println("\nStarting connection");
        AGServer server = new AGServer(SERVER_URL, USERNAME, PASSWORD);
        println("Available catalogs: " + server.listCatalogs());
        AGCatalog catalog = server.getCatalog(CATALOG_ID);
        println("Available repositories in catalog "
                + (catalog.getCatalogName()) + ": "
                + catalog.listRepositories());
        if (catalog.listRepositories().isEmpty()) {
            conn = createRepository(catalog).getConnection();
        }else{
            AGRepository myRepository = server.getCatalog(CATALOG_ID).openRepository(REPOSITORY_ID);
            conn = myRepository.getConnection();
        }
    }


    public AGRepository createRepository(AGCatalog catalog){

        AGRepository myRepository = catalog.createRepository(REPOSITORY_ID);
        println("Got a repository.");
        myRepository.initialize();
        println("Initialized repository.");
        AGRepositoryConnection conn = myRepository.getConnection();
        println("Got a connection.");
        println("Repository " + (myRepository.getRepositoryID())
                + " is up! It contains " + (conn.size()) + " statements.");
        return myRepository;
    }


    public static void println(Object x) {
        System.out.println(x);
    }
    protected static void closeAll() {
        while (toClose.isEmpty() == false) {
            AGRepositoryConnection conn = toClose.get(0);
            close(conn);
            while (toClose.remove(conn)) {
            }
        }
    }

    private static List<AGRepositoryConnection> toClose = new ArrayList<>();

    static void close(AGRepositoryConnection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.err.println("Error closing repository connection: " + e);
            e.printStackTrace();
        }
    }


    public Authentication() {}

    public void getAuthentication() {
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        Credentials credentials = new UsernamePasswordCredentials(USERNAME,PASSWORD);
        credsProvider.setCredentials(AuthScope.ANY, credentials);
        HttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        HttpOp.setDefaultHttpClient(httpclient);

    }

}
