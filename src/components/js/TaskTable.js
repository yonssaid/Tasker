import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/TaskTable.css';
import ChangeModal from './ChangeModal';
import ConfirmationModal from './ConfirmationModal';
import { FaSortAlphaDown, FaSortAlphaUp } from 'react-icons/fa';
import { MdArrowDownward, MdArrowUpward } from 'react-icons/md';
import { IoCalendarOutline, IoCalendarSharp } from 'react-icons/io5';

const TaskTable = () => {
    const [tasks, setTasks] = useState([]);
    const [categories, setCategories] = useState({});
    const [sortOrder, setSortOrder] = useState({
        title: 'asc',
        priority: 'asc',
        deadline: 'asc'
    });
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedTask, setSelectedTask] = useState(null);
    const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
    const [confirmAction, setConfirmAction] = useState(null);
    const [confirmMessage, setConfirmMessage] = useState('');

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await axios.get('/api/tasks/getAll');
                const tasksData = response.data;
                setTasks(tasksData);

                const categoryPromises = tasksData.map(task =>
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
                console.error('Error fetching tasks:', error);
            }
        };

        fetchTasks();
    }, []);

    const toggleSortOrder = (key) => {
        setSortOrder(prevOrder => ({
            ...prevOrder,
            [key]: prevOrder[key] === 'asc' ? 'desc' : 'asc'
        }));
    };

    const handleSortByTitle = () => {
        toggleSortOrder('title');
        const sortedTasks = [...tasks].sort((a, b) => {
            return sortOrder.title === 'asc'
                ? a.title.localeCompare(b.title)
                : b.title.localeCompare(a.title);
        });
        setTasks(sortedTasks);
    };

    const handleSortByDeadline = () => {
        toggleSortOrder('deadline');
        const sortedTasks = [...tasks].sort((a, b) => {
            return sortOrder.deadline === 'asc'
                ? new Date(a.deadline) - new Date(b.deadline)
                : new Date(b.deadline) - new Date(a.deadline);
        });
        setTasks(sortedTasks);
    };

    const handleSortByPriority = () => {
        toggleSortOrder('priority');
        const priorityOrder = { 'Critical': 4, 'High': 3, 'Medium': 2, 'Low': 1 };
        const sortedTasks = [...tasks].sort((a, b) => {
            return sortOrder.priority === 'asc'
                ? priorityOrder[a.priority] - priorityOrder[b.priority]
                : priorityOrder[b.priority] - priorityOrder[a.priority];
        });
        setTasks(sortedTasks);
    };

    const openConfirmModal = (action, message) => {
        setConfirmAction(() => action);
        setConfirmMessage(message);
        setIsConfirmModalOpen(true);
    };

    const handleConfirm = async () => {
        if (confirmAction) {
            await confirmAction();
        }
        setIsConfirmModalOpen(false);
    };

    const handleEdit = (updatedTask) => {
        openConfirmModal(async () => {
            try {
                const taskDto = {
                    title: updatedTask.title,
                    category: updatedTask.category,
                    status: updatedTask.status,
                    priority: updatedTask.priority,
                    deadline: updatedTask.deadline
                };

                await axios.put(`/api/tasks/${updatedTask.id}`, taskDto);
                const response = await axios.get('/api/tasks/getAll');
                setTasks(response.data);
                setIsModalOpen(false);
            } catch (error) {
                console.error('Error updating task:', error);
            }
        }, 'Are you sure you want to save these changes?');
    };

    const handleDelete = (taskId) => {
        openConfirmModal(async () => {
            try {
                await axios.delete(`/api/taskcategories/${taskId}`);
                await axios.delete(`/api/tasks/${taskId}`);
                const response = await axios.get('/api/tasks/getAll');
                setTasks(response.data);
                setIsModalOpen(false);
            } catch (error) {
                console.error('Error deleting task:', error);
            }
        }, 'Are you sure you want to delete this task?');
    };

    const handleOpenModal = (task) => {
        setSelectedTask(task);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
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
                        className="task-grid-row"
                        key={task.id}
                    >
                        <div
                            className="task-title"
                            onClick={() => handleOpenModal(task)}
                        >
                            {task.title || 'No title'}
                        </div>
                        <div>{categories[task.id]?.name || 'No category'}</div>
                        <div>{task.status || 'No status'}</div>
                        <div>{task.priority || 'No priority'}</div>
                        <div>{task.deadline || 'No deadline'}</div>
                    </div>
                ))}
            </div>
            <div className="task-sort-icons">
                {sortOrder.title === 'asc' ? (
                    <FaSortAlphaDown onClick={handleSortByTitle} title="Sort A-Z" />
                ) : (
                    <FaSortAlphaUp onClick={handleSortByTitle} title="Sort Z-A" />
                )}
                {sortOrder.priority === 'asc' ? (
                    <MdArrowDownward onClick={handleSortByPriority} title="Sort by Priority Low-High" />
                ) : (
                    <MdArrowUpward onClick={handleSortByPriority} title="Sort by Priority High-Low" />
                )}
                {sortOrder.deadline === 'asc' ? (
                    <IoCalendarOutline onClick={handleSortByDeadline} title="Sort by Date Ascending" />
                ) : (
                    <IoCalendarSharp onClick={handleSortByDeadline} title="Sort by Date Descending" />
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
