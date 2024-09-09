package com.Tasker.Dto;

/**
 * Data Transfer Object for registering an admin-created user.
 * Contains the necessary details for creating a new admin-created user.
 *
 * @author Yons Said
 */
public class RegisterAdminDto {
    private String username;
    private String password;
    private String email;
    private int age;
    private String firstName;
    private String lastName;
    private String roleName;

    /**
     * Gets the username of the admin-created user.
     *
     * @return the username of the admin-created user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the admin-created user.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the admin-created user.
     *
     * @return the password of the admin-created user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the admin-created user.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address of the admin-created user.
     *
     * @return the email address of the admin-created user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the admin-created user.
     *
     * @param email the email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the age of the admin-created user.
     *
     * @return the age of the admin-created user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of the admin-created user.
     *
     * @param age the age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the first name of the admin-created user.
     *
     * @return the first name of the admin-created user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the admin-created user.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the admin-created user.
     *
     * @return the last name of the admin-created user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the admin-created user.
     *
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the role name assigned to the admin-created user.
     *
     * @return the role name assigned to the admin-created user.
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets the role name assigned to the admin-created user.
     *
     * @param roleName the role name to set.
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
