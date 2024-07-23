import React from 'react';
import '../css/TaskBoard.css';

const TaskBoard = ({ openModal }) => {
    return (
        <div className="task-board">
            <h2>Dashboard</h2>
            <button onClick={openModal}>Create New</button>
        </div>
    );
};

export default TaskBoard;
