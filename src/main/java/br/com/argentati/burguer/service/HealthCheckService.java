package br.com.argentati.burguer.service;

import br.com.argentati.burguer.config.PomVersionReader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class HealthCheckService {

    private static final Logger logger = Logger.getLogger(HealthCheckService.class.getName());

    private final JdbcTemplate jdbcTemplate;

    public HealthCheckService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> checkHealth() {
        logger.info("Inicio do health check");
        Map<String, Object> healthDetails = new HashMap<>();

        healthDetails.put("status", "OK");
        healthDetails.put("version", PomVersionReader.getVersion());

        healthDetails.put("timestamp", Instant.now().toString());

        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        healthDetails.put("uptime", formatUptime(uptimeMillis));

        boolean databaseHealth = checkDatabaseHealth();
        healthDetails.put("database", databaseHealth ? "OK" : "DOWN");

        return healthDetails;
    }

    private boolean checkDatabaseHealth() {
        logger.info("Verificando a saúde do banco de dados");
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.info("Banco de dados está saudável");
            return true;
        } catch (Exception e) {
            logger.severe("Erro ao verificar a saúde do banco de dados: " + e.getMessage());
            return false;
        }
    }

    private String formatUptime(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return String.format("%dh %dm %ds", hours, minutes, seconds);
    }

}
