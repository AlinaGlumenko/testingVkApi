package models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    String id;
    String username;
    String login;
    String password;
    String token;
}
