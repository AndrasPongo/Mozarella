package com.hybridtheory.mozarella.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.pet.cubefish.CubeFish;
import com.hybridtheory.mozarella.serialization.AuthorityDeserializer;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;

@Entity
public class Student implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private Integer facebookId;

	@Column(unique = true)
	private Integer googleId;

	private String name = "";
	private String password = "";
	private String salt = "abcd";

	private String role;

	private String email;
	
	private Boolean isActivated=false;
	
	private String activationCode = SaltGenerator.getSaltString(16);

	@JsonIgnore
	@ManyToMany
	private Set<LearnItemList> learnItemLists = new HashSet<>();

	public Set<LearnItemList> getLearnItemLists() {
		return learnItemLists;
	}

	@JsonIgnore
	@Transient
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, optional = false)
	private Pet pet;

	@ElementCollection
	@JsonDeserialize(using = AuthorityDeserializer.class)
	private List<GrantedAuthority> authorities;

	public Student() {
		if (pet == null) {
			pet = new CubeFish();
		}
		role = "user";
	}

	public Student(Integer id2, String username, String token, List<GrantedAuthority> authorityList) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	protected void initializePet(String petName) {
		pet = new CubeFish(petName);
	}

	public Pet getPet() {
		return pet;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student with name " + name + "id: " + id;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT"));
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getRole() {
		return role;
	}

	public String getSalt() {
		return salt;
	}

	public void setUsername(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void associateWithLearnItemsList(LearnItemList learnItemsList) {
		if (learnItemLists.add(learnItemsList)) {
			learnItemsList.addStudent(this);
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean isActivated() {
		return isActivated;
	}

	public void setActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	@JsonIgnore
	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Student && this.id.equals(((Student) (other)).id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public void disAssociateWithLearnItemsList(LearnItemList list) {
		if (learnItemLists.remove(list)) {
			list.removeStudent(this);
		}
	}
}