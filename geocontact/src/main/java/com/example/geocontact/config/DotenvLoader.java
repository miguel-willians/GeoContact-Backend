package com.example.geocontact.config;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvLoader {
    public static void load() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JDBC_DATABASE_URL", dotenv.get("JDBC_DATABASE_URL"));
    }
}
