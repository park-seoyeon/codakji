package io.openvidu.js.java.model;

public class MemberDto {

	private int user_number;
	private boolean admin;
	private boolean teach;
	private String email;
	private String name;
	private String password;
	private String created_at;
	private boolean certification;
	private String oauth;
	private String token;
	public boolean profile;
	
	public MemberDto() {
		super();
	}
	
	public MemberDto(String email, String name, String password, String oauth) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.oauth = oauth;
	}
	

	public MemberDto(String email, String name, String password, String oauth, String token) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.oauth = oauth;
		this.token = token;
	}

	
	public MemberDto(int user_number, boolean admin, boolean teach, String email, String name, String password,
			String created_at, boolean certification, String oauth, String token, boolean profile) {
		super();
		this.user_number = user_number;
		this.admin = admin;
		this.teach = teach;
		this.email = email;
		this.name = name;
		this.password = password;
		this.created_at = created_at;
		this.certification = certification;
		this.oauth = oauth;
		this.token = token;
		this.profile = profile;
	}

	public int getUser_number() {
		return user_number;
	}
	public void setUser_number(int user_number) {
		this.user_number = user_number;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isTeach() {
		return teach;
	}
	public void setTeach(boolean teach) {
		this.teach = teach;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public boolean isCertification() {
		return certification;
	}
	public void setCertification(boolean certification) {
		this.certification = certification;
	}
	
	public String getOauth() {
		return oauth;
	}
	public void setOauth(String oauth) {
		this.oauth = oauth;
	}
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isProfile() {
		return profile;
	}

	public void setProfile(boolean profile) {
		this.profile = profile;
	}
	
	@Override
	public String toString() {
		return "MemberDto [user_number=" + user_number + ", admin=" + admin + ", teach=" + teach + ", email=" + email
				+ ", name=" + name + ", password=" + password + ", created_at=" + created_at + ", certification="
				+ certification + ", oauth=" + oauth + ", token=" + token + ", profile=" + profile + "]";
	}

	
}
