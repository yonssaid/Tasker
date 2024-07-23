import React from 'react';
import '../css/TaskTable.css';

const TaskTable = ({ tasks, toggleStatus }) => {
    return (
        <table className="task-table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Category</th>
                <th>Status</th>
                <th>Priority</th>
                <th>Due</th>
            </tr>
            </thead>
            <tbody>
            {tasks.map(task => (
                <tr key={task.id}>
                    <td>{task.name}</td>
                    <td>{task.taskcategory}</td>
                    <td>{task.priority}</td>
                    <td>{task.endDate}</td>
                    <td>
                        <button
                            className={task.status}
                            onClick={() => toggleStatus(task.id)}
                        >
                            {task.status}
                        </button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
};

export default TaskTable;
