const tabs = document.querySelectorAll('.tab');
const tabContents = document.querySelectorAll('.tab-content');
const registeredWorkoutsList = document.getElementById('registeredWorkouts');
let registeredWorkouts = [];


document.addEventListener('DOMContentLoaded', function () {
    fetchRegisteredWorkouts();
});

async function fetchRegisteredWorkouts() {
    try {
        const response = await fetch('/records/api/recordedLessons');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); // Преобразуем ответ в JSON

        // Очищаем массив перед заполнением новыми данными
        registeredWorkouts = [];

        // Заполняем массив данными с бэкенда
        data.forEach(workout => {
            registeredWorkouts.push(workout);
        });

        renderRegisteredWorkouts();

    } catch (error) {
        console.error('Failed to fetch registered workouts:', error);
        // Здесь можно обработать ошибку (например, показать сообщение пользователю)
    }
}


tabs.forEach(tab => {
    tab.addEventListener('click', () => {
        const tabId = tab.dataset.tab + '-tab';
        tabs.forEach(t => t.classList.remove('active'));
        tabContents.forEach(content => content.style.display = 'none');
        tab.classList.add('active');
        document.getElementById(tabId).style.display = 'block';
    });
});

function register(lessonName, lessonDate, lessonTime, trainerName, lessonPlace, id) {
    const confirmation = confirm(`Уверены ли вы в записи на: ${lessonName}?`);
    if (confirmation) {
        const existingIndex = registeredWorkouts.findIndex(item => item.id === id);
        if (existingIndex === -1) {
            registeredWorkouts.push({ lessonName, lessonDate, lessonTime, trainerName, lessonPlace, id });
            sendData(id, lessonName, lessonDate);
        } else {
            alert(`Вы уже записаны на ${lessonName}`);
        }
    }
}

function sendData(id, lessonName, lessonDate) {


    fetch('/records/api/receiveString', { // Corrected URL
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain' // Указываем тип контента как plain text
        },
        body: id // Отправляем строку в теле запроса
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // Получаем текст ответа
        })
        .then(data => {
            renderRegisteredWorkouts();
            alert(`Вы записались на: ${lessonName}, ${lessonDate}`);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function isDateMoreThanOneDayInFuture(dateString) {
    const inputDate = new Date(dateString);
    const currentDate = new Date();
    const timeDifference = inputDate.getTime() - currentDate.getTime();
    const dayDifference = timeDifference / (24 * 60 * 60 * 1000);
    return dayDifference > 1;
}


function cancelWorkout(workoutName, id, lessonDate) {
    const confirmation = confirm(`Вы уверены, что хотите отменить запись на ${workoutName}?`);
    if (confirmation) {
        if (isDateMoreThanOneDayInFuture(lessonDate)) {
            fetch(`/records/api/deleteRecord/${id}`, {
                method: 'DELETE',
            })
                .then(response => {
                    if (response.ok) {
                        let index = registeredWorkouts.findIndex(item => item.id === id);
                        if (index !== -1) {
                            registeredWorkouts.splice(index, 1); // Удаляем запись из массива
                        }
                        renderRegisteredWorkouts();
                        alert(`Запись на ${workoutName} отменена.`);
                    } else {
                        // Обработка ошибок
                        console.error('Ошибка при отмене записи:', response.statusText);
                        alert('Ошибка при отмене записи.');
                    }
                })
                .catch(error => {
                    console.error('Ошибка сети:', error);
                    alert('Ошибка сети при отмене записи.');
                });
        } else {
            alert('Невозможно отменить тренировку за сутки до назначенной даты');
        }


    }
}


function renderRegisteredWorkouts() {
    registeredWorkoutsList.innerHTML = '';
    registeredWorkouts.forEach(workout => {
        const li = document.createElement('li');
        li.innerHTML = `
                    <div data-lesson-id="${workout.id}">
                        <strong>${workout.lessonName}</strong><br>
                        Дата: ${workout.lessonDate}<br>
                        Время: ${workout.lessonTime}<br>
                        Тренер: ${workout.trainerName}<br>
                        Место: ${workout.lessonPlace}
                    </div>
                    <button onclick="cancelWorkout('${workout.lessonName}','${workout.id}', '${workout.lessonDate}')">Отменить</button>
                `;
        registeredWorkoutsList.appendChild(li);
    });
}


document.addEventListener('DOMContentLoaded', function () {
    displayWorkouts("workoutsContainer"); // Вызываем функцию после загрузки DOM
});

// Функция для получения данных с сервера и отображения на фронте HTML-кода
function displayWorkouts(containerId) {
    const container = document.getElementById(containerId);
    if (!container) {
        console.error(`Контейнер с id "${containerId}" не найден.`);
        return;
    }

    fetch('/student/lessons') // Замените на ваш URL для получения данных с сервера
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.status);
            }
            return response.json(); // Предполагаем, что сервер возвращает JSON
        })
        .then(workouts => {
            // Обрабатываем полученные данные (массив объектов workout)
            container.innerHTML = ''; // Очищаем контейнер перед добавлением новых данных
            workouts.forEach(lesson => {

                const workoutHTML = `
                    <div class="workout" id="${lesson.id}">
                        <div class="workout-header">
                            <h2>${lesson.lessonName}</h2>
                        </div>
                        <p>Дата: ${lesson.lessonDate}</p>
                        <p>Время: ${lesson.lessonTime}</p>
                        <p>Тренер: ${lesson.trainerName}</p>
                        <p>Место: ${lesson.lessonPlace}</p>
                        <button onclick="register('${lesson.lessonName}', '${lesson.lessonDate}', '${lesson.lessonTime}',  '${lesson.trainerName}', '${lesson.lessonPlace}', '${lesson.id}')">Записаться</button>
                    </div>
                    `;

                container.innerHTML += workoutHTML;
            });
        })
        .catch(error => {
            console.error('Ошибка при получении данных:', error);
            container.innerHTML = '<p>Ошибка при загрузке тренировок.</p>'; // Отображаем сообщение об ошибке
        });
}


//Скрипты для фильтрации

const workoutsContainer = document.getElementById('workoutsContainer');
const searchInput = document.getElementById('searchInput');
const dateFilter = document.getElementById('dateFilter');
const nameSelect = document.getElementById('nameSelect');
let sortAsc = true;

// Загружаем все уникальные названия и добавляем в select
function populateNameSelect() {
    const workouts = document.querySelectorAll('.workout');
    const names = new Set();
    workouts.forEach(workout => {
        names.add(workout.getAttribute('data-name'));
    });

    nameSelect.innerHTML = '<option value="">Все названия</option>';
    names.forEach(name => {
        const option = document.createElement('option');
        option.value = name;
        option.textContent = name;
        nameSelect.appendChild(option);
    });
}

// Фильтрация
function filterWorkouts() {
    const searchTerm = searchInput.value.toLowerCase();
    const selectedDate = dateFilter.value;
    const selectedName = nameSelect.value;

    const workouts = document.querySelectorAll('.workout');

    workouts.forEach(workout => {
        const name = workout.getAttribute('data-name').toLowerCase();
        const date = workout.getAttribute('data-date');

        let show = true;

        if (searchTerm && !name.includes(searchTerm)) show = false;
        if (selectedDate && date !== selectedName) show = false;
        if (selectedName && name !== selectedName.toLowerCase()) show = false;

        workout.style.display = show ? "block" : "none";
    });
}

// Сортировка
function sortWorkouts() {
    const workouts = Array.from(document.querySelectorAll('.workout'));
    const sorted = workouts.sort((a, b) => {
        const dateA = new Date(a.getAttribute('data-date'));
        const dateB = new Date(b.getAttribute('data-date'));
        return sortAsc ? dateA - dateB : dateB - dateA;
    });

    sortAsc = !sortAsc;

    // Очистить контейнер и вставить отсортированные элементы
    workoutsContainer.innerHTML = '';
    sorted.forEach(workout => workoutsContainer.appendChild(workout));
}

// Инициализация
window.onload = () => {
    populateNameSelect();
};

