import React, { useState, useEffect } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import '../css/CreationModal.css';
import PropTypes from 'prop-types';
import axios from 'axios';

const CreationModal = ({ show, closeModal, createTask, categories = [], fetchCategories }) => {
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [deadline, setDeadline] = useState(new Date());
    const [priority, setPriority] = useState('Low');
    const [category, setCategory] = useState('');
    const [showCategoryModal, setShowCategoryModal] = useState(false);
    const [newCategory, setNewCategory] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await createTask({ title, description, deadline, priority }, category);
            closeModal();
        } catch (error) {
            console.error('Error creating task:', error);
        }
    };

    const handleAddCategory = async (e) => {
        e.preventDefault();
        try {
            await axios.post('/api/categories/create', { name: newCategory });
            setNewCategory('');
            setShowCategoryModal(false);
            fetchCategories();
        } catch (error) {
            console.error('Error adding category:', error);
        }
    };

    useEffect(() => {
        if (categories.length > 0) {
            setCategory(categories[0].id.toString());
        }
    }, [categories]);

    return (
        <div className={`modal ${show ? 'show' : ''}`}>
            <div className="modal-content">
                <div className="modal-header">
                    <h2>Create New Task</h2>
                    <span className="close" onClick={closeModal}>&times;</span>
                </div>
                <div className="modal-body">
                    <form onSubmit={handleSubmit}>
                        <div className="input-container">
                            <input
                                type="text"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                placeholder="Title"
                                required
                            />
                            <input
                                type="text"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                placeholder="Description"
                            />
                            <DatePicker
                                selected={deadline}
                                onChange={(date) => setDeadline(date)}
                                dateFormat="yyyy/MM/dd"
                                placeholderText="Deadline"
                                className="react-datepicker-wrapper"
                                required
                            />
                            <select
                                value={priority}
                                onChange={(e) => setPriority(e.target.value)}
                            >
                                <option value="Low">Low</option>
                                <option value="Medium">Medium</option>
                                <option value="High">High</option>
                                <option value="Critical">Critical</option>
                            </select>
                            <select
                                value={category}
                                onChange={(e) => setCategory(e.target.value)}
                            >
                                <option value="">No Category</option>
                                {categories.map((cat) => (
                                    <option key={cat.id} value={cat.id.toString()}>
                                        {cat.name}
                                    </option>
                                ))}
                            </select>
                            <button
                                type="button"
                                className="add-category-button"
                                onClick={() => setShowCategoryModal(true)}
                            >
                                Add New Category
                            </button>
                        </div>
                        <div className="modal-footer">
                            <button type="submit">Create</button>
                        </div>
                    </form>
                </div>
            </div>

            {showCategoryModal && (
                <div className="mini-modal">
                    <div className="mini-modal-content">
                        <div className="mini-modal-header">
                            <h2>New Category</h2>
                            <span className="close" onClick={() => setShowCategoryModal(false)}>&times;</span>
                        </div>
                        <div className="mini-modal-body">
                            <form onSubmit={handleAddCategory}>
                                <input
                                    type="text"
                                    value={newCategory}
                                    onChange={(e) => setNewCategory(e.target.value)}
                                    placeholder="Category Name"
                                    required
                                />
                                <button type="submit">Add Category</button>
                            </form>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

CreationModal.propTypes = {
    show: PropTypes.bool.isRequired,
    closeModal: PropTypes.func.isRequired,
    createTask: PropTypes.func.isRequired,
    categories: PropTypes.array.isRequired,
    fetchCategories: PropTypes.func.isRequired,
};

export default CreationModal;
