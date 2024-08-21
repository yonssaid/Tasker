import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import axios from 'axios';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import '../css/CalendarView.css';

const CustomToolbar = ({ label, onNavigate, onView }) => (
    <div className="custom-toolbar">
        <button onClick={() => onNavigate('PREV')}>Previous</button>
        <span>{label}</span>
        <div>
            <button onClick={() => onView('month')}>Back to Month View</button>
            <button onClick={() => onNavigate('NEXT')}>Next</button>
        </div>
    </div>
);

const CustomEvent = ({ event }) => (
    <div className={`custom-event ${event.overdue ? 'overdue' : ''}`}>
        <span>{event.title}</span>
    </div>
);

const localizer = momentLocalizer(moment);

const CalendarView = () => {
    const [events, setEvents] = useState([]);

    useEffect(() => {
        const fetchTasks = async () => {
            try {
                const response = await axios.get('/api/tasks/getAll');
                const tasks = response.data;

                const today = new Date();
                const events = tasks.map(task => ({
                    title: task.title,
                    start: new Date(task.deadline),
                    end: new Date(task.deadline),
                    allDay: true,
                    overdue: new Date(task.deadline) < today,
                }));

                setEvents(events);
            } catch (error) {
                console.error('Error fetching tasks:', error);
            }
        };

        fetchTasks();
    }, []);

    return (
        <div className="calendar-view">
            <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                components={{
                    toolbar: CustomToolbar,
                    event: CustomEvent,
                }}
                style={{ height: 500 }}
            />
        </div>
    );
};

export default CalendarView;
