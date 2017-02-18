package com.hybridtheory.mozarella.users;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemManager;

@Entity
public class Student implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true)
	private Integer facebookId;
	
	@Column(unique=true)
	private Integer googleId;
	
	private String name = "";
	private String password = "";
	private String salt = "abcd";
	
	private String role;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	private LearnItemManager learnItemManager;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	private Pet pet;

	@ElementCollection
	@JsonDeserialize(using = AuthorityDeserializer.class)
	private List<GrantedAuthority> authorities;
	
	public Student(){
		if(learnItemManager == null){
			learnItemManager = new LearnItemManager();
		}
		if(pet == null){
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

	public void initialize(String studentName) {
		boolean validName = false;

		this.name = studentName;
		this.password = "password";
		learnItemManager = new LearnItemManager(this);
	}

	protected void initializePet(String petName) {
		pet = new CubeFish(petName);
	}
	
	@Transient
	public Pet getPet() {
		return pet;
	}


	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	public List<LearnItemList> getLearnItemLists() {
		return learnItemManager.getLearnItemsLists();
	}

	protected LearnItemList getLearnItemListByName(String nameOfList) {
		return learnItemManager.getLearnItemsList(nameOfList);
	}

	protected LearnItemList addNewLearnItemsList(String name) {
		return learnItemManager.createLearnItemsList(name);
	}

	protected LearnItemList addNewLearnItemsList(LearnItemList learnItemsList) {
		return learnItemManager.addNewLearnItemsList(learnItemsList);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setLearnItemManager(LearnItemManager learnItemManager) {
		this.learnItemManager = learnItemManager;
	}

	protected void addNewLearnItemToExistingList(LearnItemList learnItemsList, LearnItem learnItem) {
		learnItemManager.addNewLearnItemToLearnItemsList(learnItemsList, learnItem);
	}

	public void associateWithLearnItemsList(LearnItemList learnItemsList){
		learnItemManager.addNewLearnItemsList(learnItemsList);
	}


	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	public LearnItemManager getLearnItemManager(){
		return this.learnItemManager;
	}
	
	@Override
	public String toString(){
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
	
	public String getRole(){
		return role;
	}
	
	public String getSalt(){
		return salt;
	}

	public void setUsername(String name) {
		this.name = name;
	}
	
	public void setId(Integer id){
		this.id = id;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
}