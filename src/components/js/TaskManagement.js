import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import '../css/TaskManagement.css';

const TaskManagement = ({ adminTasks, fetchAdminTasks }) => {
    const navigate = useNavigate();
    const [selectedTask, setSelectedTask] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    const [editTaskData, setEditTaskData] = useState({
        title: '',
        description: '',
        status: '',
        priority: '',
        deadline: ''
    });

    useEffect(() => {
        if (selectedTask) {
            setEditTaskData({
                title: selectedTask.title,
                description: selectedTask.description,
                status: selectedTask.status,
                priority: selectedTask.priority,
                deadline: selectedTask.deadline
            });
        }
    }, [selectedTask]);

    const handleEditClick = (task) => {
        setSelectedTask(task);
        setIsEditing(true);
    };

    const handleDeleteClick = async (taskId) => {
        try {
            await axios.delete(`/api/tasks/${taskId}`);
            fetchAdminTasks();
        } catch (error) {
            console.error('Error deleting task:', error);
        }
    };

    const handleSave = async () => {
        try {
            const updatedTask = { ...selectedTask, ...editTaskData };
            await axios.put(`/api/tasks/${selectedTask.id}`, updatedTask);
            fetchAdminTasks();
            setIsEditing(false);
            setSelectedTask(null);
        } catch (error) {
            console.error('Error saving task:', error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setEditTaskData((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    return (
        <div className="task-management-container">
            <div className="back-button-container">
                <button className="back-button" onClick={() => navigate('/admin/tools')}>
                    <FaArrowLeft />
                </button>
            </div>
            <h1>Task Management</h1>
            {(isEditing) && (
                <div className="edit-task-form">
                    <h3>Edit Task</h3>
                    <label>
                        Title:
                        <input
                            type="text"
                            name="title"
                            value={editTaskData.title}
                            onChange={handleInputChange}
                        />
                    </label>
                    <label>
                        Description:
                        <textarea
                            name="description"
                            value={editTaskData.description}
                            onChange={handleInputChange}
                        ></textarea>
                    </label>
                    <label>
                        Status:
                        <input
                            type="text"
                            name="status"
                            value={editTaskData.status}
                            onChange={handleInputChange}
                        />
                    </label>
                    <label>
                        Priority:
                        <input
                            type="text"
                            name="priority"
                            value={editTaskData.priority}
                            onChange={handleInputChange}
                        />
                    </label>
                    <label>
                        Deadline:
                        <input
                            type="date"
                            name="deadline"
                            value={editTaskData.deadline}
                            onChange={handleInputChange}
                        />
                    </label>
                    <button onClick={handleSave}>Save</button>
                    <button onClick={() => { setIsEditing(false); setSelectedTask(null); }}>
                        Cancel
                    </button>
                </div>
            )}
            <table className="admin-tasks-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Deadline</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                {adminTasks.map((task) => (
                    <tr key={task.id}>
                        <td>{task.id}</td>
                        <td>{task.title}</td>
                        <td>{task.description}</td>
                        <td>{task.status}</td>
                        <td>{task.priority}</td>
                        <td>{task.deadline}</td>
                        <td>
                            <button className="edit-button" onClick={() => handleEditClick(task)}>Edit</button>
                            <button className="delete-button" onClick={() => handleDeleteClick(task.id)}>Delete</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default TaskManagement;
