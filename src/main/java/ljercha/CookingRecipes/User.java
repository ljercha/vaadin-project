package ljercha.CookingRecipes;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable, Cloneable {
	
	private static int userCounter = 0;
	
	private Long id;
	private String login;
	private String email;
	private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof User && obj.getClass().equals(getClass())) {
			return this.id.equals(((User) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}
	

	@Override
	public String toString() {
		return login;
	}

}
