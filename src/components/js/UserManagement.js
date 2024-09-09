import React, { useState } from 'react';
import { FaArrowLeft } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import '../css/UserManagement.css';
import axios from 'axios';

const UserManagement = ({ adminUsers, fetchAdminUsers }) => {
    const [editingUser, setEditingUser] = useState(null);
    const [newUser, setNewUser] = useState({
        username: '',
        email: '',
        age: '',
        firstName: '',
        lastName: '',
        role: 'USER'
    });
    const [showNewUserForm, setShowNewUserForm] = useState(false);
    const navigate = useNavigate();

    // Create user method
    const createUser = async () => {
        const userDto = {
            username: newUser.username,
            email: newUser.email,
            age: parseInt(newUser.age, 10),
            firstName: newUser.firstName,
            lastName: newUser.lastName,
            roleName: newUser.role
        };

        try {
            await axios.post('/api/admin/users/create', userDto);
            fetchAdminUsers();
            setNewUser({
                username: '',
                email: '',
                age: '',
                firstName: '',
                lastName: '',
                role: 'USER'
            });
            setShowNewUserForm(false);
        } catch (error) {
            console.error('Error creating user:', error);
        }
    };

    // Edit user method
    const editUser = async (userId) => {
        const userDto = {
            username: editingUser.username,
            email: editingUser.email,
            age: parseInt(editingUser.age, 10),
            firstName: editingUser.firstName,
            lastName: editingUser.lastName,
            roleName: editingUser.role
        };

        try {
            await axios.put(`/api/admin/users/${userId}`, userDto);
            fetchAdminUsers();
            setEditingUser(null);
        } catch (error) {
            console.error('Error editing user:', error);
        }
    };

    // Delete user method with confirmation
    const deleteUser = async (userId) => {
        const confirmed = window.confirm('Are you sure you want to delete this user?');
        if (confirmed) {
            try {
                await axios.delete(`/api/admin/users/${userId}`);
                fetchAdminUsers();
            } catch (error) {
                console.error('Error deleting user:', error);
            }
        }
    };

    const handleEditClick = (user) => {
        setEditingUser({ ...user, role: user.role.name });
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditingUser({ ...editingUser, [name]: value });
    };

    const handleNewUserChange = (e) => {
        const { name, value } = e.target;
        setNewUser({ ...newUser, [name]: value });
    };

    return (
        <div className="user-management-container">
            <div className="back-button-container">
                <button className="back-button" onClick={() => navigate('/admin/tools')}>
                    <FaArrowLeft />
                </button>
            </div>
            <h1>User Management</h1>

            <div>
                <button onClick={() => setShowNewUserForm(!showNewUserForm)}>
                    {showNewUserForm ? 'Cancel' : 'Create New User'}
                </button>
            </div>

            {showNewUserForm && (
                <div className="new-user-form">
                    <h3>Create New User</h3>
                    <div>
                        <input
                            type="text"
                            name="username"
                            value={newUser.username}
                            placeholder="Username"
                            onChange={handleNewUserChange}
                        />
                    </div>
                    <div>
                        <input
                            type="email"
                            name="email"
                            value={newUser.email}
                            placeholder="Email"
                            onChange={handleNewUserChange}
                        />
                    </div>
                    <div>
                        <input
                            type="number"
                            name="age"
                            value={newUser.age}
                            placeholder="Age"
                            onChange={handleNewUserChange}
                        />
                    </div>
                    <div>
                        <input
                            type="text"
                            name="firstName"
                            value={newUser.firstName}
                            placeholder="First Name"
                            onChange={handleNewUserChange}
                        />
                    </div>
                    <div>
                        <input
                            type="text"
                            name="lastName"
                            value={newUser.lastName}
                            placeholder="Last Name"
                            onChange={handleNewUserChange}
                        />
                    </div>
                    <div>
                        <label>Role</label>
                        <select name="role" value={newUser.role} onChange={handleNewUserChange}>
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                        </select>
                    </div>
                    <div>
                        <button onClick={createUser}>CREATE</button>
                    </div>
                </div>
            )}

            <table className="admin-users-table">
                <thead>
                <tr>
                    <th>User ID</th>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Age</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {adminUsers.map((user) => (
                    <tr key={user.userId}>
                        {editingUser && editingUser.userId === user.userId ? (
                            <>
                                <td>{user.userId}</td>
                                <td>
                                    <input
                                        type="text"
                                        name="username"
                                        value={editingUser.username}
                                        onChange={handleInputChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="email"
                                        name="email"
                                        value={editingUser.email}
                                        onChange={handleInputChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="number"
                                        name="age"
                                        value={editingUser.age}
                                        onChange={handleInputChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="text"
                                        name="firstName"
                                        value={editingUser.firstName}
                                        onChange={handleInputChange}
                                    />
                                </td>
                                <td>
                                    <input
                                        type="text"
                                        name="lastName"
                                        value={editingUser.lastName}
                                        onChange={handleInputChange}
                                    />
                                </td>
                                <td>
                                    <select name="role" value={editingUser.role} onChange={handleInputChange}>
                                        <option value="USER">USER</option>
                                        <option value="ADMIN">ADMIN</option>
                                    </select>
                                </td>
                                <td>
                                    <button className="save-button" onClick={() => editUser(user.userId)}>Save</button>
                                    <button className="cancel-button" onClick={() => setEditingUser(null)}>Cancel</button>
                                </td>
                            </>
                        ) : (
                            <>
                                <td>{user.userId}</td>
                                <td>{user.username}</td>
                                <td>{user.email}</td>
                                <td>{user.age}</td>
                                <td>{user.firstName}</td>
                                <td>{user.lastName}</td>
                                <td>{user.role.name}</td>
                                <td>
                                    <button className="edit-button" onClick={() => handleEditClick(user)}>Edit</button>
                                    <button className="delete-button" onClick={() => deleteUser(user.userId)}>Delete</button>
                                </td>
                            </>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default UserManagement;