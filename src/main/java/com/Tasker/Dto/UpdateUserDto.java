package com.Tasker.Dto;

/**
 * Data Transfer Object for updating user information.
 * Contains the details needed to update an existing user's information.
 *
 * @author Yons Said
 */
public class UpdateUserDto {
    private String username;
    private String email;
    private int age;
    private String firstName;
    private String lastName;
    private String roleName;

    /**
     * Gets the username of the user.
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age of the user.
     *
     * @return the age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the user.
     *
     * @param age the age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the role name assigned to the user.
     *
     * @return the role name assigned to the user.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role name assigned to the user.
     *
     * @param roleName the role name to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
