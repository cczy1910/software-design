package ru.akirakozov.sd.refactoring.config;

public class AppConfig {
    private final String dbUrl;
    private final int serverPort;

    public AppConfig(String dbUrl, int serverPort) {
        this.dbUrl = dbUrl;
        this.serverPort = serverPort;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public int getServerPort() {
        return serverPort;
    }
}
