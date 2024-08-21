import React, { useState, useEffect, useCallback } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Sidebar from './components/js/Sidebar';
import CreationModal from './components/js/CreationModal';
import TaskBoard from './components/js/TaskBoard';
import TaskTable from './components/js/TaskTable';
import CalendarView from './components/js/CalendarView';
import HomePage from './components/js/HomePage';
import UserSettings from './components/js/UserSettings'; // Import the Settings component
import axios from 'axios';

function App() {
  const [tasks, setTasks] = useState([]);
  const [sortOrder, setSortOrder] = useState({
    title: 'asc',
    priority: 'asc',
    deadline: 'asc'
  });
  const [modalVisible, setModalVisible] = useState(false);
  const [categories, setCategories] = useState([]);
  const [userFirstName, setUserFirstName] = useState('');
  const [userLastName, setUserLastName] = useState('');

  // Fetch user data
  const fetchUserData = async () => {
    try {
      const userResponse = await axios.get('/api/users/info');
      setUserFirstName(userResponse.data.firstName);
      setUserLastName(userResponse.data.lastName);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  // Fetch tasks
  const fetchTasks = useCallback(async () => {
    try {
      const response = await axios.get('/api/tasks/getAll');
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  }, []);

  // Fetch categories
  const fetchCategories = useCallback(async () => {
    try {
      const response = await axios.get('/api/categories/getAll');
      const data = response.data.map(category => {
        try {
          const parsedName = JSON.parse(category.name);
          return { ...category, name: parsedName.name || category.name };
        } catch {
          return category;
        }
      });
      setCategories(data);
    } catch (error) {
      console.error('Error fetching categories:', error);
    }
  }, []);

  useEffect(() => {
    fetchUserData();
    fetchTasks();
    fetchCategories();
  }, [fetchTasks, fetchCategories]);

  // Logout function
  const logout = async () => {
    await fetch('/api/auth/logout', { method: 'POST', credentials: 'include' });
    window.location.href = '/login';
  };

  // Modal visibility
  const openModal = () => setModalVisible(true);
  const closeModal = () => setModalVisible(false);

  // Create task
  const createTask = async (taskData, categoryId) => {
    try {
      const taskResponse = await axios.post('/api/tasks/create', taskData);
      const taskId = taskResponse.data.id;

      if (categoryId) {
        await axios.post('/api/taskcategories/create', {
          taskId,
          categoryId
        });
      }

      await fetchTasks();

    } catch (error) {
      console.error('Error creating task or task category:', error);
    }
  };

  // Update tasks
  const updateTasks = async () => {
    try {
      const response = await axios.get('/api/tasks/getAll');
      setTasks(response.data);
    } catch (error) {
      console.error('Error fetching tasks:', error);
    }
  };
  // Toggle sorting
  const toggleSortOrder = (key) => {
    const newOrder = {
      ...sortOrder,
      [key]: sortOrder[key] === 'asc' ? 'desc' : 'asc'
    };
    setSortOrder(newOrder);
    return newOrder;
  };

  // Sort by title
  const handleSortByTitle = () => {
    const newOrder = toggleSortOrder('title');
    setTasks(prevTasks => [...prevTasks].sort((a, b) => {
      return newOrder.title === 'asc'
          ? a.title.localeCompare(b.title)
          : b.title.localeCompare(a.title);
    }));
  };

  // Sort by deadline
  const handleSortByDeadline = () => {
    const newOrder = toggleSortOrder('deadline');
    setTasks(prevTasks => [...prevTasks].sort((a, b) => {
      return newOrder.deadline === 'asc'
          ? new Date(a.deadline) - new Date(b.deadline)
          : new Date(b.deadline) - new Date(a.deadline);
    }));
  };

  // Sort by priority
  const handleSortByPriority = () => {
    const newOrder = toggleSortOrder('priority');
    const priorityOrder = { 'Critical': 4, 'High': 3, 'Medium': 2, 'Low': 1 };
    setTasks(prevTasks => [...prevTasks].sort((a, b) => {
      return newOrder.priority === 'asc'
          ? priorityOrder[a.priority] - priorityOrder[b.priority]
          : priorityOrder[b.priority] - priorityOrder[a.priority];
    }));
  };

  return (
      <Router>
        <div className="App">
          <Sidebar logout={logout} />
          <main className="main-content">
            <Routes>
              <Route
                  path="/user/home"
                  element={
                    <>
                      <HomePage
                          userFirstName={userFirstName}
                          userLastName={userLastName}
                          tasks={tasks}
                          openModal={openModal}
                      />
                      <CreationModal
                          show={modalVisible}
                          closeModal={closeModal}
                          createTask={createTask}
                          categories={categories}
                          fetchCategories={fetchCategories} // Needed for refreshing category list
                      />
                    </>
                  }
              />
              <Route
                  path="/user/home/table"
                  element={
                    <>
                      <TaskBoard openModal={openModal} />
                      <TaskTable
                          tasks={tasks}
                          updateTasks={updateTasks}
                          handleSortByTitle={handleSortByTitle}
                          handleSortByDeadline={handleSortByDeadline}
                          handleSortByPriority={handleSortByPriority}
                          sortOrder={sortOrder}
                      />
                      <CreationModal
                          show={modalVisible}
                          closeModal={closeModal}
                          createTask={createTask}
                          categories={categories}
                          fetchCategories={fetchCategories} // Needed for refreshing category list
                      />
                    </>
                  }
              />
              <Route path="/user/home/calendar" element={<CalendarView />} />
              <Route path="/user/settings" element={<UserSettings />} />
            </Routes>
          </main>
        </div>
      </Router>
  );
}

export default App;
