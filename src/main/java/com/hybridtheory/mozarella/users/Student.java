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
import com.hybridtheory.mozarella.pet.Pet;
import com.hybridtheory.mozarella.pet.cubefish.CubeFish;
import com.hybridtheory.mozarella.wordteacher.InputSanitizer;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemList;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItemsManager;

@Entity
public class Student implements UserDetails {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(unique=true)
	private Integer facebookId;
	
	@Column(unique=true)
	private Integer googleId;
	
	private String name = "";
	private String password = "";
	private String salt = "abcd";
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	private LearnItemsManager learnItemManager;
	
	@JsonIgnore
	@OneToOne(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, optional = false)
	private Pet pet;
	
	@Transient
	private InputSanitizer inputSanitizer = new InputSanitizer();

	@ElementCollection
	private List<GrantedAuthority> authorities;
	
	public Student(){
		learnItemManager = new LearnItemsManager(this);
		pet = new CubeFish(name+"s pet");
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void initialize(String studentName) {
		boolean validName = false;
		validName = inputSanitizer.checkStudentNameIsValid(studentName);
		if (!validName) {
			throw new IllegalArgumentException("Invalid name for Student");
		}
		this.name = studentName;
		this.password = "password";
		learnItemManager = new LearnItemsManager(this);
	}
	
	protected void initializePet(String petName) {
		pet = new CubeFish(petName);
	}
	
	@Transient
	public Pet getPet() {
		return pet;
	}

	@JsonIgnore
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

	public InputSanitizer getInputSanitizer() {
		return inputSanitizer;
	}

	public void setInputSanitizer(InputSanitizer inputSanitizer) {
		this.inputSanitizer = inputSanitizer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLearnItemManager(LearnItemsManager learnItemManager) {
		this.learnItemManager = learnItemManager;
	}
	
	protected void addNewLearnItemToExistingList(LearnItemList learnItemsList, LearnItem learnItem) {
		learnItemManager.addNewLearnItemToLearnItemsList(learnItemsList, learnItem);
	}
	
	public void associateWithLearnItemsList(LearnItemList learnItemsList){
		learnItemManager.addNewLearnItemsList(learnItemsList);
	}

	public LearnItemsManager getLearnItemManager(){
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
	
	public String getSalt(){
		return salt;
	}
	
}