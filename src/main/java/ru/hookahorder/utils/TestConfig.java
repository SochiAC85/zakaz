package ru.hookahorder.utils;

public final class TestConfig {

    public static final String ADMIN_URL = getRequiredProperty("admin.url");
    public static final String ADMIN_LOGIN = getRequiredProperty("admin.login");
    public static final String ADMIN_PASSWORD = getRequiredProperty("admin.password");

    private TestConfig() {
    }

    private static String getRequiredProperty(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    String.format("Ошибка конфигурации: Требуется системный параметр -D%s. %n" +
                            "Пример запуска: mvn test -Dadmin.url=http://... -Dadmin.login=... -Dadmin.password=...", key)
            );
        }
        return value;
    }
}
