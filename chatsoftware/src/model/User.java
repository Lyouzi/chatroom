package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.User;


public class User implements Serializable {
	private String username;
	private String password;
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	private String sex;
	private int age;
	private String nickname;
	private String signatrue;
	private String imagePath;
	private Map<String,HashSet<User>>  friends;//has-a
	//private Map<String, Set<User>> myGroups;
	//private Map<String,Set<User>>  myGroups=new  HashMap<>();//存储用户有哪些群的属性
	
	/**
	 * @return the myGroups
	 */
	/*public Map<String, Set<User>> getMyGroups() {
		return myGroups;
	}*/
	/**
	 * @param myGroups the myGroups to set
	 */
	/*public void setMyGroups(Map<String, Set<User>> myGroups) {
		this.myGroups = myGroups;
	}
*/
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSignatrue() {
		return signatrue;
	}

	public void setSignatrue(String signatrue) {
		this.signatrue = signatrue;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Map<String, HashSet<User>> getFriends() {
		return friends;
	}

	public void setFriends(Map<String, HashSet<User>> friends) {
		this.friends = friends;
	}

	public User(String username, String password, String sex, int age, String nickname, String signatrue,
			String imagePath) {
		super();
		this.username = username;
		this.password = password;
		this.sex = sex;
		this.age = age;
		this.nickname = nickname;
		this.signatrue = signatrue;
		this.imagePath = imagePath;
	}
	public User() {
		super();
	}
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", sex=" + sex + ", age=" + age + ", nickname="
				+ nickname + ", signatrue=" + signatrue + ", imagePath=" + imagePath + "]";
	}
}
