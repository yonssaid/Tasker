import React, { useState } from 'react';
import '../css/Sidebar.css';
import { FaHome, FaCalendarAlt, FaCog, FaSignOutAlt } from 'react-icons/fa';
import ProfilePic from '../../assets/profile-pic.jpg';

function Sidebar({ logout }) {
    const [profileMenuOpen, setProfileMenuOpen] = useState(false);

    const toggleProfileMenu = () => {
        setProfileMenuOpen(!profileMenuOpen);
    };

    return (
        <div className="sidebar">
            <div className="logo">
                <img src="/images/Tasker_Logo.png" alt="Tasker Logo" />
            </div>
            <div className="nav-item">
                <FaHome className="nav-icon" />
            </div>
            <div className="nav-item">
                <FaCalendarAlt className="nav-icon" />
            </div>
            <div className="profile-section" onClick={toggleProfileMenu}>
                <img src={ProfilePic} alt="Profile" className="profile-pic" />
                {profileMenuOpen && (
                    <div className="profile-menu">
                        <div className="profile-menu-item settings">
                            <FaCog className="nav-icon gear" />
                            <span>Settings</span>
                        </div>
                        <div className="profile-menu-item logout" onClick={logout}>
                            <FaSignOutAlt className="nav-icon" />
                            <span>Logout</span>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Sidebar;
