import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import axios from 'axios';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import '../css/CalendarView.css';

const CustomToolbar = ({ label, onNavigate}) => (
    <div className="custom-toolbar">
        <button onClick={() => onNavigate('PREV')}>Previous</button>
        <span>{label}</span>
        <button onClick={() => onNavigate('NEXT')}>Next</button>
    </div>
);

const CustomEvent = ({ event }) => (
    <div className="custom-event">
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

                const events = tasks.map(task => ({
                    title: task.title,
                    start: new Date(task.deadline),
                    end: new Date(task.deadline),
                    allDay: true,
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
