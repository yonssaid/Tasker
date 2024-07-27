import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';
import Sidebar from './components/js/Sidebar';
import CreationModal from './components/js/CreationModal';
import TaskBoard from './components/js/TaskBoard';
import TaskTable from './components/js/TaskTable';
import CalendarView from './components/js/CalendarView';
import axios from 'axios';

function App() {
  const [tasks, setTasks] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);
  const [categories, setCategories] = useState([]);

  const logout = async () => {
    await fetch('/api/auth/logout', { method: 'POST', credentials: 'include' });
    window.location.href = '/login';
  };

  const openModal = () => setModalVisible(true);
  const closeModal = () => setModalVisible(false);

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

      setTasks(prevTasks => [...prevTasks, { ...taskData, id: taskId }]);
    } catch (error) {
      console.error('Error creating task or task category:', error);
    }
  };

  const toggleStatus = (taskId) => {
    setTasks(prevTasks =>
        prevTasks.map(task =>
            task.id === taskId
                ? { ...task, status: task.status === 'to-do' ? 'completed' : 'to-do' }
                : task
        )
    );
  };

  const fetchCategories = async () => {
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
  };

  useEffect(() => {
    fetchCategories();
  }, []);

  return (
      <Router>
        <div className="App">
          <Sidebar logout={logout} />
          <main className="main-content">
            <Routes>
              <Route path="/" element={
                <>
                  <TaskBoard openModal={openModal} />
                  <TaskTable tasks={tasks} toggleStatus={toggleStatus} />
                  <CreationModal
                      show={modalVisible}
                      closeModal={closeModal}
                      createTask={createTask}
                      categories={categories}
                      fetchCategories={fetchCategories}
                  />
                </>
              } />
              <Route path="/calendar" element={<CalendarView />} />
            </Routes>
          </main>
        </div>
      </Router>
  );
}

export default App;
