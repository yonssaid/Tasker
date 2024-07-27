import React, { useState, useEffect } from 'react';
import '../css/ChangeModal.css';
import ConfirmationModal from './ConfirmationModal';
import axios from 'axios';

const ChangeModal = ({ isOpen, onClose, task, onEdit, onDelete }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editTask, setEditTask] = useState(task);
    const [categories, setCategories] = useState([]);
    const [showConfirmation, setShowConfirmation] = useState(false);

    useEffect(() => {
        setEditTask(task);
        setIsEditing(false);
    }, [task]);

    useEffect(() => {
        if (isOpen) {
            fetchCategories();
        }
    }, [isOpen]);

    const fetchCategories = async () => {
        try {
            const response = await axios.get('/api/categories/getAll');
            const data = response.data.map(category => {
                try {
                    const parsedName = JSON.parse(category.name);
                    return { ...category, name: parsedName.name || category.name };
                } catch {
                    return category;
                }
            });
            setCategories(data);
            if (task && task.category) {
                setEditTask((prevTask) => ({
                    ...prevTask,
                    category: task.category
                }));
            }
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };

    if (!isOpen) return null;

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditTask((prevTask) => ({
            ...prevTask,
            [name]: value,
        }));
    };

    const validateTask = (task) => {
        return task.title && task.category && task.status;
    };

    const handleSave = () => {
        if (!validateTask(editTask)) {
            alert('Please fill in all required fields.');
            return;
        }
        setShowConfirmation(true);
    };

    const handleConfirmSave = async (confirmed) => {
        setShowConfirmation(false);
        if (confirmed) {
            try {
                await onEdit(editTask);
                setIsEditing(false);
                onClose();
            } catch (error) {
                console.error('Failed to save changes:', error);
            }
        }
    };

    const handleDelete = async () => {
        if (window.confirm('Are you sure you want to delete this task?')) {
            try {
                await onDelete(task.id);
                onClose();
            } catch (error) {
                console.error('Failed to delete task:', error);
            }
        }
    };

    return (
        <div className={`modal-overlay ${isOpen ? 'show' : ''}`}>
            <div className="modal-content">
                <div className="modal-header">
                    <h2>{isEditing ? 'Edit Task' : `Details for ${task.title || 'No title'}`}</h2>
                    <button className="modal-close" onClick={onClose}>
                        ×
                    </button>
                </div>
                <div className="modal-body">
                    {isEditing ? (
                        <div className="input-container">
                            <label>
                                Title:
                                <input
                                    type="text"
                                    name="title"
                                    value={editTask.title || ''}
                                    onChange={handleChange}
                                />
                            </label>
                            <label>
                                Category:
                                <select
                                    name="category"
                                    value={editTask.category || ''}
                                    onChange={handleChange}
                                >
                                    <option value="">No Category</option>
                                    {categories.map((cat) => (
                                        <option key={cat.id} value={cat.id.toString()}>
                                            {cat.name}
                                        </option>
                                    ))}
                                </select>
                            </label>
                            <label>
                                Status:
                                <select
                                    name="status"
                                    value={editTask.status || ''}
                                    onChange={handleChange}
                                >
                                    <option value="To-Do">To-Do</option>
                                    <option value="Completed">Completed</option>
                                </select>
                            </label>
                            <label>
                                Priority:
                                <select
                                    name="priority"
                                    value={editTask.priority || ''}
                                    onChange={handleChange}
                                >
                                    <option value="Low">Low</option>
                                    <option value="Medium">Medium</option>
                                    <option value="High">High</option>
                                    <option value="Critical">Critical</option>
                                </select>
                            </label>
                            <label>
                                Deadline:
                                <input
                                    type="date"
                                    name="deadline"
                                    value={editTask.deadline || ''}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    ) : (
                        <div>
                            <p><strong>Title:</strong> {task.title || 'No title'}</p>
                            <p><strong>Category:</strong> {task.category || 'No category'}</p>
                            <p><strong>Status:</strong> {task.status || 'No status'}</p>
                            <p><strong>Priority:</strong> {task.priority || 'No priority'}</p>
                            <p><strong>Deadline:</strong> {task.deadline || 'No deadline'}</p>
                        </div>
                    )}
                </div>
                <div className="modal-actions">
                    {isEditing ? (
                        <button onClick={handleSave} className="save-button">Save</button>
                    ) : (
                        <button onClick={() => setIsEditing(true)} className="edit-button">Edit</button>
                    )}
                    <button onClick={handleDelete} className="delete-button">Delete</button>
                </div>
            </div>
            {showConfirmation && (
                <ConfirmationModal
                    isOpen={showConfirmation}
                    message="Are you sure you want to save these changes?"
                    onConfirm={handleConfirmSave}
                />
            )}
        </div>
    );
};

export default ChangeModal;
