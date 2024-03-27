package data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;

import java.util.Locale;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static void sendRequest(RegistrationDto user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(user)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {
            RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    public static final class RegistrationDto {
        private final String login;
        private final String password;
        private final String status;

        public RegistrationDto(String login, String password, String status) {
            this.login = login;
            this.password = password;
            this.status = status;
        }

        public String getLogin() {
            return this.login;
        }

        public String getPassword() {
            return this.password;
        }

        public String getStatus() {
            return this.status;
        }

        public boolean equals(final Object o) {
            if (o == this) return true;
            if (!(o instanceof RegistrationDto)) return false;
            final RegistrationDto other = (RegistrationDto) o;
            final Object this$login = this.getLogin();
            final Object other$login = other.getLogin();
            if (!Objects.equals(this$login, other$login)) return false;
            final Object this$password = this.getPassword();
            final Object other$password = other.getPassword();
            if (!Objects.equals(this$password, other$password)) return false;
            final Object this$status = this.getStatus();
            final Object other$status = other.getStatus();
            if (!Objects.equals(this$status, other$status)) return false;
            return true;
        }

        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final Object $login = this.getLogin();
            result = result * PRIME + ($login == null ? 43 : $login.hashCode());
            final Object $password = this.getPassword();
            result = result * PRIME + ($password == null ? 43 : $password.hashCode());
            final Object $status = this.getStatus();
            result = result * PRIME + ($status == null ? 43 : $status.hashCode());
            return result;
        }

        public String toString() {
            return "DataGenerator.RegistrationDto(login=" + this.getLogin() + ", password=" + this.getPassword() + ", status=" + this.getStatus() + ")";
        }
    }
}