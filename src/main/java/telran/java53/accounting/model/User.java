package telran.java53.accounting.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	@Id
    String login;
	@Setter
    String password;
	@Setter
    String firstName;
	@Setter
    String lastName;
	
    @Builder.Default
    Set<String> roles = new HashSet<>();

public User(String login, String password, String firstName, String lastName) {
	this();
	this.login = login;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
}
public boolean addRole(String role) {
	return roles.add(role);
}

public boolean removeRole(String role) {
	return roles.remove(role);
}

}
