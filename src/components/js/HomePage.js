import { useNavigate } from 'react-router-dom';
import '../css/HomePage.css';

const HomePage = ({ userFirstName, userLastName, tasks, openModal}) => {
    const navigate = useNavigate();

    const parseDate = (dateString) => {
        const date = new Date(dateString);
        return isNaN(date.getTime()) ? 'Invalid Date' : date.toLocaleDateString();
    };

    const getMostRecentTasks = () => {
        if (!tasks || tasks.length === 0) return [];

        return tasks
            .filter(task => task.deadline)
            .sort((a, b) => new Date(b.deadline) - new Date(a.deadline))
            .slice(0, 3);
    };

    const recentTasks = getMostRecentTasks();

    return (
        <div>
            <div className="home-page">
                <div className="welcome-container">
                    <h1>
                        Welcome back, <span className="user-firstname">{userFirstName}</span> <span className="user-lastname">{userLastName}</span>
                    </h1>
                </div>
                <div className="task-container">
                    <section>
                        <h2>Upcoming Tasks</h2>
                        <ul>
                            {recentTasks.map(task => (
                                <li key={task.id}>
                                    <div>Name: {task.title}</div>
                                    <div>Priority: {task.priority}</div>
                                    <div>Deadline: {parseDate(task.deadline)}</div>
                                </li>
                            ))}
                        </ul>
                    </section>
                </div>
            </div>
            <div className="buttons-container">
                <div className="buttons">
                    <button onClick={() => navigate('/user/home/table')}>Table View</button>
                    <button onClick={() => navigate('/user/home/calendar')}>Calendar View</button>
                    <button className="create-new" onClick={openModal}>Create New</button>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
