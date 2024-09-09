import React from 'react';
import { Link } from 'react-router-dom';
import '../css/AdminTools.css';

const AdminTools = () => {
    return (
        <div>
            <h1>Admin Tools</h1>
            <div className="tab-navigation">
                <Link to="/admin/tools/users">
                    <button>User Management</button>
                </Link>
                <Link to="/admin/tools/tasks">
                    <button>Task Management</button>
                </Link>
                <Link to="/admin/tools/logistics">
                    <button>Logistics Data</button>
                </Link>
            </div>
        </div>
    );
};

export default AdminTools;