import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/TaskTable.css';
import ChangeModal from './ChangeModal';
import ConfirmationModal from './ConfirmationModal';
import { FaSortAlphaDown, FaSortAlphaUp } from 'react-icons/fa';
import { MdArrowDownward, MdArrowUpward } from 'react-icons/md';
import { IoCalendarOutline, IoCalendarSharp } from 'react-icons/io5';

const TaskTable = ({ tasks, updateTasks, handleSortByTitle, handleSortByDeadline, handleSortByPriority, sortOrder }) => {
    const [categories, setCategories] = useState({});
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);
    const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
    const [confirmMessage, setConfirmMessage] = useState('');

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const categoryPromises = tasks.map(task =>
                    axios.get(`/api/taskcategories/${task.id}`)
                        .then(categoryResponse => {
                            const categoryData = categoryResponse.data;
                            const category = typeof categoryData.name === 'string'
                                ? JSON.parse(categoryData.name)
                                : categoryData;
                            return {
                                taskId: task.id,
                                category: category || { name: 'No category' }
                            };
                        })
                        .catch(() => ({ taskId: task.id, category: { name: 'No category' } }))
                );

                const categoriesData = await Promise.all(categoryPromises);
                const categoriesMap = categoriesData.reduce((acc, categoryData) => {
                    acc[categoryData.taskId] = categoryData.category;
                    return acc;
                }, {});

                setCategories(categoriesMap);
            } catch (error) {
                console.error('Error fetching categories:', error);
            }
        };

        fetchCategories();
    }, [tasks]);

    const openConfirmModal = (message) => {
        setConfirmMessage(message);
        setIsConfirmModalOpen(true);
    };

    const handleConfirmEdit = async (updatedTask) => {
        try {
            const taskDto = {
                title: updatedTask.title,
                description: updatedTask.description,
                category: updatedTask.category,
                status: updatedTask.status,
                priority: updatedTask.priority,
                deadline: updatedTask.deadline
            };

            await axios.put(`/api/tasks/${updatedTask.id}`, taskDto);
            updateTasks();
            setIsModalOpen(false);
        } catch (error) {
            console.error('Error updating task:', error);
        }
        setIsConfirmModalOpen(false);
    };

    const handleConfirmDelete = async (taskId) => {
        try {
            await axios.delete(`/api/taskcategories/${taskId}`);
            await axios.delete(`/api/tasks/${taskId}`);
            updateTasks();
            setIsModalOpen(false);
        } catch (error) {
            console.error('Error deleting task:', error);
        }
        setIsConfirmModalOpen(false);
    };

    const handleEdit = (updatedTask) => {
        setSelectedTask(updatedTask);
        openConfirmModal('Are you sure you want to save these changes?');
    };

    const handleDelete = (taskId) => {
        setSelectedTask({ id: taskId });
        openConfirmModal('Are you sure you want to delete this task?');
    };

    const handleConfirm = async () => {
        if (confirmMessage.includes('save')) {
            await handleConfirmEdit(selectedTask);
        } else if (confirmMessage.includes('delete')) {
            await handleConfirmDelete(selectedTask.id);
        }
    };

    const handleOpenModal = (task) => {
        setSelectedTask(task);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const isOverdue = (task) => {
        if (task.status === 'Completed') {
            return false;
        }
        const today = new Date();
        const taskDeadline = new Date(task.deadline);
        return taskDeadline < today;
    };

    const toggleTaskStatus = async (task) => {
        const updatedTask = {
            ...task,
            status: task.status === 'To-Do' ? 'Completed' : 'To-Do',
            priority: 'Low'
        };
        try {
            await axios.put(`/api/tasks/${updatedTask.id}`, updatedTask);
            updateTasks();
        } catch (error) {
            console.error('Error updating task status:', error);
        }
    };

    return (
        <div className="task-container">
            <div className="task-grid">
                <div className="task-grid-header">
                    <div>Name</div>
                    <div>Category</div>
                    <div>Status</div>
                    <div>Priority</div>
                    <div>Due</div>
                </div>
                {tasks.map(task => (
                    <div
                        className={`task-grid-row ${isOverdue(task) ? 'overdue' : ''} ${task.status === 'Completed' ? 'completed-row' : ''}`}
                        key={task.id}
                    >
                        <div className="task-title" onClick={() => handleOpenModal(task)}>
                            {task.title || 'No title'}
                        </div>
                        <div>{categories[task.id]?.name || 'No category'}</div>
                        <div className={`task-status ${task.status === 'Completed' ? 'completed' : 'todo'}`}
                             onClick={() => toggleTaskStatus(task)}>
                            {task.status || 'No status'}
                        </div>
                        <div>{task.priority || 'No priority'}</div>
                        <div>{task.deadline || 'No deadline'}</div>
                    </div>
                ))}
            </div>
            <div className="task-sort-icons">
                {sortOrder.title === 'asc' ? (
                    <FaSortAlphaDown onClick={handleSortByTitle} title="Sort A-Z"/>
                ) : (
                    <FaSortAlphaUp onClick={handleSortByTitle} title="Sort Z-A"/>
                )}
                {sortOrder.priority === 'asc' ? (
                    <MdArrowDownward onClick={handleSortByPriority} title="Sort by Priority Low-High"/>
                ) : (
                    <MdArrowUpward onClick={handleSortByPriority} title="Sort by Priority High-Low"/>
                )}
                {sortOrder.deadline === 'asc' ? (
                    <IoCalendarOutline onClick={handleSortByDeadline} title="Sort by Date Ascending"/>
                ) : (
                    <IoCalendarSharp onClick={handleSortByDeadline} title="Sort by Date Descending"/>
                )}
            </div>
            <ChangeModal
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                task={selectedTask}
                onEdit={handleEdit}
                onDelete={handleDelete}
            />
            <ConfirmationModal
                isOpen={isConfirmModalOpen}
                onClose={() => setIsConfirmModalOpen(false)}
                onConfirm={handleConfirm}
                message={confirmMessage}
            />
        </div>
    );
};

export default TaskTable;
