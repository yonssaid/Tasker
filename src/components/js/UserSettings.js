import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ConfirmationModal from './ConfirmationModal';
import '../css/UserSettings.css';

function UserSettings() {
    const [userInfo, setUserInfo] = useState({
        username: '',
        email: '',
        age: '',
        firstName: '',
        lastName: '',
    });

    const [isEditing, setIsEditing] = useState({
        username: false,
        email: false,
        age: false,
        firstName: false,
        lastName: false,
    });

    const [passwords, setPasswords] = useState({
        currentPassword: '',
        newPassword: '',
        confirmPassword: '',
    });

    const [passwordVisibility, setPasswordVisibility] = useState({
        currentPassword: false,
        newPassword: false,
        confirmPassword: false,
    });

    const [message, setMessage] = useState('');
    const [usernameError, setUsernameError] = useState('');
    const [passwordsMatch, setPasswordsMatch] = useState(true);
    const [isModalOpen, setModalOpen] = useState(false);
    const [action, setAction] = useState('');

    useEffect(() => {
        async function fetchUserInfo() {
            try {
                const response = await axios.get('/api/users/info');
                setUserInfo(response.data);
            } catch (error) {
                setMessage('Error fetching user information.');
            }
        }

        fetchUserInfo();
    }, []);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUserInfo((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handlePasswordChange = (e) => {
        const { name, value } = e.target;
        setPasswords((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handlePasswordVisibilityToggle = (field) => {
        setPasswordVisibility((prevState) => ({
            ...prevState,
            [field]: !prevState[field],
        }));
    };

    useEffect(() => {
        setPasswordsMatch(passwords.newPassword === passwords.confirmPassword);
    }, [passwords.newPassword, passwords.confirmPassword]);

    const toggleEdit = (field) => {
        setIsEditing((prevState) => ({
            ...prevState,
            [field]: !prevState[field],
        }));
    };

    const updateUserInfo = async () => {
        try {
            setUsernameError('');
            setMessage('');

            await axios.put('/api/users/update/info', userInfo);

            setMessage('User information updated successfully.');
        } catch (error) {
            if (error.response && error.response.data && error.response.data.message === 'Username already exists') {
                setUsernameError('Username is already taken.');
            } else {
                setMessage('Error updating user information.');
            }
        }
    };

    const updatePassword = async () => {
        if (!passwordsMatch) {
            setMessage('New password and confirm password do not match.');
            return;
        }
        try {
            await axios.put('/api/users/update/password', null, {
                params: {
                    currentPassword: passwords.currentPassword,
                    newPassword: passwords.newPassword,
                },
            });
            setMessage('Password updated successfully.');
            setPasswordVisibility({
                currentPassword: false,
                newPassword: false,
                confirmPassword: false,
            });
        } catch (error) {
            setMessage('Error updating password.');
        }
    };

    const handleUpdateClick = (type) => {
        setAction(type);
        setModalOpen(true);
    };

    const handleConfirmEdit = async (confirm) => {
        setModalOpen(false);
        if (confirm) {
            if (action === 'info') {
                await updateUserInfo();
            } else if (action === 'password') {
                await updatePassword();
            }
        }
    };

    const fieldsOrder = ['username', 'firstName', 'lastName', 'email', 'age'];

    return (
        <div className="container">
            <h1 className="title">User Settings</h1>
            <p>Click the edit icon next to any field to edit your information.</p>

            <div className="infoSection">
                {fieldsOrder.map((field) => (
                    <div key={field} className="infoItem">
                        <label>{field.charAt(0).toUpperCase() + field.slice(1).replace(/([A-Z])/g, ' $1')}:</label>
                        {isEditing[field] ? (
                            <input
                                type={field === 'age' ? 'number' : 'text'}
                                name={field}
                                value={userInfo[field]}
                                onChange={handleInputChange}
                                onBlur={() => toggleEdit(field)}
                                autoFocus
                                className="input"
                            />
                        ) : (
                            <>
                                <span className="infoValue">{userInfo[field]}</span>
                                <button
                                    type="button"
                                    onClick={() => toggleEdit(field)}
                                    className="editIcon"
                                >
                                    <i className="fas fa-edit"></i>
                                </button>
                            </>
                        )}
                    </div>
                ))}
            </div>

            {usernameError && <p className="usernameError">{usernameError}</p>} {/* Display username error */}

            <h2 className="sectionTitle">Update Password</h2>
            <div className="infoSection">
                <div className="passwordItem">
                    <input
                        type={passwordVisibility.currentPassword ? 'text' : 'password'}
                        name="currentPassword"
                        placeholder="Current Password"
                        value={passwords.currentPassword}
                        onChange={handlePasswordChange}
                        className="input"
                    />
                    <button
                        type="button"
                        onClick={() => handlePasswordVisibilityToggle('currentPassword')}
                        className="toggleButton"
                    >
                        {passwordVisibility.currentPassword ? 'Hide' : 'Show'}
                    </button>
                </div>
                <div className="passwordItem">
                    <input
                        type={passwordVisibility.newPassword ? 'text' : 'password'}
                        name="newPassword"
                        placeholder="New Password"
                        value={passwords.newPassword}
                        onChange={handlePasswordChange}
                        className="input"
                    />
                    <button
                        type="button"
                        onClick={() => handlePasswordVisibilityToggle('newPassword')}
                        className="toggleButton"
                    >
                        {passwordVisibility.newPassword ? 'Hide' : 'Show'}
                    </button>
                </div>
                <div className="passwordItem">
                    <input
                        type={passwordVisibility.confirmPassword ? 'text' : 'password'}
                        name="confirmPassword"
                        placeholder="Confirm Password"
                        value={passwords.confirmPassword}
                        onChange={handlePasswordChange}
                        className="input"
                    />
                    <button
                        type="button"
                        onClick={() => handlePasswordVisibilityToggle('confirmPassword')}
                        className="toggleButton"
                    >
                        {passwordVisibility.confirmPassword ? 'Hide' : 'Show'}
                    </button>
                </div>
                {!passwordsMatch && (
                    <p className="passwordMismatch">Passwords do not match!</p>
                )}
                <button className="button" onClick={() => handleUpdateClick('password')}>
                    Update Password
                </button>
            </div>

            <button className="button" onClick={() => handleUpdateClick('info')}>Save Changes</button>

            {message && <p className={message.includes('successfully') ? 'successMessage' : 'message'}>{message}</p>} {/* Apply success message styling */}

            <ConfirmationModal
                isOpen={isModalOpen}
                message="Are you sure you want to apply these changes?"
                onConfirm={handleConfirmEdit}
            />
        </div>
    );
}

export default UserSettings;
