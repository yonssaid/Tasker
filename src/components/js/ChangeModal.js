import React, { useState, useEffect } from 'react';
import '../css/ChangeModal.css';
import axios from 'axios';

const ChangeModal = ({ isOpen, onClose, task, onEdit, onDelete }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editTask, setEditTask] = useState(task);
    const [categories, setCategories] = useState([]);
    const [taskCategoryName, setTaskCategoryName] = useState('No category');

    useEffect(() => {
        setEditTask(task);
        setIsEditing(false);
    }, [task]);

    useEffect(() => {
        if (isOpen) {
            fetchCategories();
            fetchCategoryForTask();
        }
    }, [isOpen]);

    const fetchCategories = async () => {
        try {
            const response = await axios.get('/api/categories/getAll');
            const data = response.data.map(category => {
                try {
                    const parsedName = JSON.parse(category.name);
                    return { id: category.id, name: parsedName.name || category.name };
                } catch {
                    return { id: category.id, name: category.name };
                }
            });
            setCategories(data);

            const matchingCategory = data.find(cat => cat.name === taskCategoryName);
            if (matchingCategory) {
                setEditTask(prevTask => ({
                    ...prevTask,
                    category: matchingCategory.id.toString()
                }));
            }

        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };
    const fetchCategoryForTask = async () => {
        if (!task || !task.id) return;

        try {
            const response = await axios.get(`/api/taskcategories/${task.id}`);
            const categoryData = response.data;
            const category = typeof categoryData.name === 'string'
                ? JSON.parse(categoryData.name)
                : categoryData;

            setTaskCategoryName(category ? category.name : 'No category');
        } catch (error) {
            console.error('Error fetching category for task:', error);
            setTaskCategoryName('No category');
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
        return task.title && task.category && task.status && task.description;
    };

    const handleSave = () => {
        if (!validateTask(editTask)) {
            alert('Please fill in all required fields.');
            return;
        }
        onEdit(editTask);
        onClose();
    };

    const handleDelete = () => {
        onDelete(task.id);
        onClose();
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
                                Description:
                                <br/>
                                <textarea
                                name="description" value={editTask.description || ''}
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
                            <p><strong>Description:</strong> {task.description || 'No description'}</p>
                            <p><strong>Category:</strong> {taskCategoryName}</p>
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
                        <>
                            <button onClick={() => setIsEditing(true)} className="edit-button">Edit</button>
                            <button onClick={handleDelete} className="delete-button">
                                <i className="fas fa-trash"></i>
                            </button>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ChangeModal;
