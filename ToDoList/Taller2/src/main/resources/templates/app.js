document.addEventListener('DOMContentLoaded', () => {

    const apiUrl = 'http://localhost:8083/tareas';
    const taskList = document.getElementById('lista-tareas');
    const taskForm = document.getElementById('form-nueva-tarea');

    // --- Elementos del Modal ---
    const editModal = document.getElementById('edit-modal');
    const editForm = document.getElementById('edit-form');
    const cancelEditBtn = document.getElementById('cancel-edit');

    // Función para obtener y mostrar todas las tareas
    async function fetchTasks() {
        try {
            const response = await fetch(apiUrl);
            if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

            const tasks = await response.json();
            taskList.innerHTML = '';

            tasks.forEach(task => {
                const taskElement = document.createElement('div');
                taskElement.className = 'task-item';
                taskElement.innerHTML = `
                    <div class="content">
                        <h3>${task.nombre}</h3>
                        <p>${task.descripcion}</p>
                    </div>
                    <div class="actions">
                        <button class="edit-btn" data-id="${task.id}" data-name="${task.nombre}" data-description="${task.descripcion}">✏️</button>
                        <button class="delete-btn" data-id="${task.id}">🗑️</button>
                    </div>
                `;
                taskList.appendChild(taskElement);
            });
        } catch (error) {
            console.error('Error al obtener las tareas:', error);
            taskList.innerHTML = '<p>No se pudieron cargar las tareas.</p>';
        }
    }

    // Función para CREAR una nueva tarea
    async function createTask(event) {
        event.preventDefault();
        const nombreInput = document.getElementById('nombre-tarea');
        const descripcionInput = document.getElementById('descripcion-tarea');

        const nuevaTarea = { nombre: nombreInput.value, descripcion: descripcionInput.value };

        try {
            const response = await fetch(`${apiUrl}/crear`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(nuevaTarea)
            });

            if (response.ok) {
                nombreInput.value = '';
                descripcionInput.value = '';
                fetchTasks();
            } else {
                console.error('Error al crear la tarea');
            }
        } catch (error) {
            console.error('Error de red:', error);
        }
    }

    // Función para ACTUALIZAR una tarea
    async function updateTask(event) {
        event.preventDefault();
        const taskId = document.getElementById('edit-task-id').value;
        const taskName = document.getElementById('edit-task-name').value;
        const taskDescription = document.getElementById('edit-task-description').value;

        const updatedTask = { nombre: taskName, descripcion: taskDescription };

        try {
            const response = await fetch(`${apiUrl}/${taskId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedTask)
            });

            if(response.ok) {
                closeEditModal();
                fetchTasks();
            } else {
                console.error('Error al actualizar la tarea');
            }
        } catch(error) {
            console.error('Error de red:', error);
        }
    }


    // Función para ELIMINAR una tarea
    async function deleteTask(taskId) {
        if (confirm('¿Estás seguro de que quieres eliminar esta tarea?')) {
            try {
                const response = await fetch(`${apiUrl}/${taskId}`, { method: 'DELETE' });
                if (response.ok) {
                    fetchTasks();
                } else {
                    console.error('Error al eliminar la tarea');
                }
            } catch (error) {
                console.error('Error de red:', error);
            }
        }
    }

    // --- Funciones del Modal ---
    function openEditModal(task) {
        document.getElementById('edit-task-id').value = task.id;
        document.getElementById('edit-task-name').value = task.name;
        document.getElementById('edit-task-description').value = task.description;
        editModal.style.display = 'flex';
    }

    function closeEditModal() {
        editModal.style.display = 'none';
    }

    // --- Manejador de Clicks en la lista (para editar y eliminar) ---
    function handleListClick(event) {
        const target = event.target;

        // Si se hizo clic en el botón de editar
        if (target.classList.contains('edit-btn')) {
            const taskData = {
                id: target.dataset.id,
                name: target.dataset.name,
                description: target.dataset.description
            };
            openEditModal(taskData);
        }

        // Si se hizo clic en el botón de eliminar
        if (target.classList.contains('delete-btn')) {
            const taskId = target.dataset.id;
            deleteTask(taskId);
        }
    }

    // --- Event Listeners ---
    taskForm.addEventListener('submit', createTask);
    editForm.addEventListener('submit', updateTask);
    cancelEditBtn.addEventListener('click', closeEditModal);
    taskList.addEventListener('click', handleListClick);

    // Cargar las tareas al iniciar
    fetchTasks();
});