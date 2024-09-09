import React, { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import '../css/Sidebar.css';
import { FaTable, FaCalendarAlt, FaCog, FaSignOutAlt, FaHome, FaTools } from 'react-icons/fa'; // Import FaTools icon

function Sidebar({ logout, isAdmin }) {
    const [profileMenuOpen, setProfileMenuOpen] = useState(false);
    const navigate = useNavigate();
    const profileMenuRef = useRef(null);

    const toggleProfileMenu = () => {
        setProfileMenuOpen(!profileMenuOpen);
    };

    const navigateTo = (path) => {
        navigate(path);
    };

    const handleClickOutside = (event) => {
        if (profileMenuRef.current && !profileMenuRef.current.contains(event.target)) {
            setProfileMenuOpen(false);
        }
    };

    useEffect(() => {
        document.addEventListener('mousedown', handleClickOutside);
        return () => {
            document.removeEventListener('mousedown', handleClickOutside);
        };
    }, []);

    return (
        <div className="sidebar" role="navigation" aria-label="Sidebar Navigation">
            <div className="logo-container" onClick={() => navigateTo('/user/home')} role="button" aria-label="Home">
                <div className="logo">
                    <img src="/images/Tasker_Logo.png" alt="Tasker Logo"/>
                </div>
                <div className="logo-icon">
                    <FaHome aria-hidden="true"/>
                </div>
            </div>

            <div className="nav-container">
                <div className="nav-item" onClick={() => navigateTo('/user/home/table')} role="button"
                     aria-label="Table">
                    <FaTable className="nav-icon" aria-hidden="true"/>
                </div>
                <div className="nav-item" onClick={() => navigateTo('/user/home/calendar')} role="button"
                     aria-label="Calendar">
                    <FaCalendarAlt className="nav-icon" aria-hidden="true"/>
                </div>

                {isAdmin && (
                    <div className="nav-item" onClick={() => navigateTo('/admin/tools')} role="button"
                         aria-label="Admin Tools">
                        <FaTools className="nav-icon" aria-hidden="true"/>
                    </div>
                )}
            </div>

            <div
                className="profile-section"
                onClick={toggleProfileMenu}
                role="button"
                aria-label="Profile Menu"
                style={isAdmin ? {marginTop: '60vh'} : {}}
            >

                <FaCog className="nav-icon gear1" aria-hidden="true"/>
                {profileMenuOpen && (
                    <div className="profile-menu" ref={profileMenuRef}>
                        <div className="profile-menu-item settings" onClick={() => navigateTo('/user/settings')}>
                            <FaCog className="nav-icon gear2" aria-hidden="true"/>
                            <span>All Settings</span>
                        </div>
                        <div className="profile-menu-item logout" onClick={logout} role="button" aria-label="Logout">
                            <FaSignOutAlt className="nav-icon" aria-hidden="true"/>
                            <span>Logout</span>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

export default Sidebar;
