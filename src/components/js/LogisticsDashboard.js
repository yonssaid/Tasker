import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import { PieChart, Pie, Cell, BarChart, Bar, XAxis, YAxis, Tooltip, Legend } from 'recharts';
import '../css/LogisticsDashboard.css';

const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042'];

const LogisticsDashboard = ({ logisticsData }) => {
    const navigate = useNavigate();


    if (!logisticsData) return <p>No logistics data available</p>;


    const taskData = [
        { name: 'Completed', value: logisticsData.taskStatistics.completedTasks },
        { name: 'To-do', value: logisticsData.taskStatistics.ongoingTasks },
        { name: 'Total', value: logisticsData.taskStatistics.totalTasks },
    ];


    const ageGroupData = Object.entries(logisticsData.userAgeGroups).map(([ageGroup, count]) => ({
        name: ageGroup,
        value: count,
    }));

    return (
        <div className="logistics-dashboard-container">
            <div className="back-button-container">
                <button className="back-button" onClick={() => navigate('/admin/tools')}>
                    <FaArrowLeft />
                </button>
            </div>

            <h1>Task Statistics</h1>
            <BarChart width={600} height={300} data={taskData}>
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />
                <Bar dataKey="value" fill="#8884d8" />
            </BarChart>

            <h2>Task Completion Rate</h2>
            <p>{logisticsData.taskCompletionRate.toFixed(2)}%</p>

            <h2>User Demographics by Age Group</h2>
            <PieChart width={400} height={400}>
                <Pie
                    data={ageGroupData}
                    cx={200}
                    cy={200}
                    labelLine={false}
                    label={({ name, percent }) => `${name} (${(percent * 100).toFixed(0)}%)`}
                    outerRadius={150}
                    fill="#8884d8"
                    dataKey="value"
                >
                    {ageGroupData.map((entry, index) => (
                        <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                    ))}
                </Pie>
            </PieChart>
        </div>
    );
};

export default LogisticsDashboard;
