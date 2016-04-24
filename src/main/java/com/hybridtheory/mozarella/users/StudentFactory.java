package com.hybridtheory.mozarella.users;

public class StudentFactory {
	public static Student createStudent(String credentialType, String value){
		
		Student newStudent = new Student();
		
		if(credentialType.equals(CredentialType.USERNAMEPASSWORD.toString())){
			newStudent.setName(value.split(",")[0]);
			giveHashedPasswordForStudent(newStudent,value);
		} else if(credentialType.equals(CredentialType.FACEBOOK.toString())){
			//TODO set facebookid with value
			//TODO: come up with a way to generate a name for the user
		} else if(credentialType.equals(CredentialType.GOOGLE.toString())){
			//TODO set googleid with value
			//TODO: come up with a way to generate a name for the user
		}
		
		return newStudent;
	}
	
	private static Student giveHashedPasswordForStudent(Student Student, String password){
		//TODO: create a hash, a salt, and give them to the user
		return Student;
	}
}
