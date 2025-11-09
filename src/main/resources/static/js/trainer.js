// Получаем элементы
const modal = document.getElementById("myModal");
const btn = document.getElementById("add-workout-button");
const span = document.getElementsByClassName("close")[0];
const confirmBtn = document.getElementById("confirm-add-workout-button");
const workout = {};
const workoutNameField = document.getElementById("name");
const workoutDateField = document.getElementById("date");
const workoutTimeField = document.getElementById("time");
var workoutPlaceField;


// Открываем модальное окно при клике на кнопку
btn.addEventListener('click', () => {
  modal.style.display = "block";
});

// Закрываем модальное окно при клике на крестик
span.addEventListener('click', () => {
  modal.style.display = "none";
});

// Закрываем модальное окно при клике вне его области
window.addEventListener('click', (event) => {
  if (event.target === modal) {
    modal.style.display = "none";
  }
});

// Находим кнопку по id
const confirmButton = document.getElementById('confirm-add-workout-button');

// Устанавливаем обработчик события click
confirmButton.addEventListener('click', function () {
  workout.name = workoutNameField.value;
  workout.date = workoutDateField.value;
  workout.time = workoutTimeField.value;
  workout.place = workoutPlaceField;


  // Здесь можно выполнить действия с введенным значением (например, отправить на сервер)
  addWorkout('workouts-container');
  addDataToServer();

  modal.style.display = "none"; // Закрываем окно
  workoutNameField.value = "";
  workoutDateField.value = "";
  workoutTimeField.value = "";

});

//Функция изменения радиокнопки при выборе места тренировки
document.getElementById('workout-place-group').addEventListener('change', function (event) {
  if (event.target.type === 'radio') {
    workoutPlaceField = event.target.value;
  }
});

// Функция добавления тренировки в расписание тренера
function addWorkout(containerId) {
  const container = document.getElementById(containerId);
  if (!container) {
    console.error(`Контейнер с id "${containerId}" не найден.`);
    return;
  }

  // Генерируем уникальный ID для div.students и кнопки сохранения посещаемости
  const uniqueId = generateUniqueId();

  const workoutHTML = `
        <div class="workout">
            <div class="workout-header">
                <h2>${workout.name}</h2>
                <div class="remove-workout" onclick="removeWorkout(this.parentNode.parentNode)">&#10006;</div>
            </div>
            <p>Дата: ${workout.date}</p>
            <p>Время: ${workout.time}</p>
            <p>Место: ${workout.place}</p>
            <button class="toggle-button" onclick="toggleStudents('${uniqueId}')">Посмотреть студентов</button>
            <div class="students" id="${uniqueId}">
                <ul>
                    <li><label><input type="checkbox" data-student="Андреев Андрей"> Андреев Андрей</label></li>
                    <li><label><input type="checkbox" data-student="Федоров Федор"> Федоров Федор</label></li>
                    <li><label><input type="checkbox" data-student="Сергеев Сергей"> Сергей Сергей</label></li>
                </ul>
                <button class="save-button button-style" onclick="saveAttendance('${uniqueId}', '${workout.name}')">Сохранить посещаемость</button>
            </div>
        </div>
        `;

  container.innerHTML += workoutHTML;
}

// Функция для генерации уникального ID
function generateUniqueId() {
  return 'students-' + Math.random().toString(36).substring(2, 15);
}

// Функция для отображения/скрытия списка студентов
function toggleStudents(studentsId) {
  const studentsDiv = document.getElementById(studentsId);
  if (studentsDiv) {
    studentsDiv.classList.toggle('show'); // Добавляем/удаляем класс "show"
  } else {
    console.error(`Список студентов с id "${studentsId}" не найден.`);
  }
}

// Функция для сохранения посещаемости
function saveAttendance(studentsId, workoutName) {
  const studentsDiv = document.getElementById(studentsId);
  if (!studentsDiv) {
    console.error(`Список студентов с id "${studentsId}" не найден.`);
    return;
  }

  const checkboxes = studentsDiv.querySelectorAll('input[type="checkbox"]');
  const attendance = [];

  checkboxes.forEach(checkbox => {
    attendance.push({
      student: checkbox.dataset.student,
      attended: checkbox.checked
    });
  });

  console.log(`Посещаемость для "${workoutName}":`, attendance);
  // Здесь вы можете отправить данные посещаемости на сервер
}

// Функция для удаления workout
function removeWorkout(workoutElement) {
  removeWorkoutData(workoutElement.id);
  // workoutElement.remove();
}

//Отправка данных о добавленном занятии контроллеру
function addDataToServer() {

  const lessonData = {
    lessonName: workoutNameField.value,
    lessonDate: workoutDateField.value,
    lessonTime: workoutTimeField.value,
    lessonPlace: workoutPlaceField
  };
  fetch('/lessons/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(lessonData)
  })
    .then(response => {
      if (!response.ok) {
        return response.json().then(errors => {
          displayErrors(errors); // Display errors on the form
          throw new Error("Validation failed");
        });
      }
      displayWorkouts('workouts-container');
      return response.json();
    })
    .then(data => {
      document.getElementById('message').innerText = data.message;
      document.getElementById('errors').innerText = '';  // Clear errors
      clearForm(); // Optional: Clear the form
    })
    .catch(error => {
      document.getElementById('message').innerText = 'An error occurred: ' + error;
    });
}

function displayErrors(errors) {  // Helper function to display errors
  let errorText = '';
  for (const field in errors) {
    errorText += `${field}: ${errors[field]}\n`;
  }
  document.getElementById('errors').innerText = errorText;
}


document.addEventListener('DOMContentLoaded', function () {
  displayWorkouts("workouts-container"); // Вызываем функцию после загрузки DOM
});

// Функция для получения данных с сервера и отображения на фронте HTML-кода
function displayWorkouts(containerId) {
  const container = document.getElementById(containerId);
  if (!container) {
    console.error(`Контейнер с id "${containerId}" не найден.`);
    return;
  }

  fetch('/trainer/lessons') 
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
        const uniqueId = generateUniqueId(); // Функция для генерации уникального ID

        const workoutHTML = `
             <div class="workout" id="${lesson.id}">
               <div class="workout-header">
                 <h2>${lesson.lessonName}</h2>
                 <div class="remove-workout" onclick="removeWorkout(this.parentNode.parentNode)">&#10006;</div>
               </div>
               <p>Дата: ${lesson.lessonDate}</p>
               <p>Время: ${lesson.lessonTime}</p>
               <p>Место: ${lesson.lessonPlace}</p>
               <button class="toggle-button" onclick="toggleStudents('${uniqueId}')">Посмотреть студентов</button>
                <div class="students" id="${uniqueId}">
                 <ul>
                 </ul>
                 <button class="save-button button-style" onclick="saveAttendance('${uniqueId}', '${workout.name}')">Сохранить посещаемость</button>
               </div>
             </div>
           `;

        container.innerHTML += workoutHTML;

        const studentsContainer = document.getElementById(uniqueId); // Находим контейнер для студентов
        if (studentsContainer) {
            generateStudentList(lesson.id) // Call generateStudentList with lesson.id
            .then(studentListHTML => { // After generateStudentList completes
              studentsContainer.querySelector("ul").innerHTML = studentListHTML;
            });
        }

      });
    })
    .catch(error => {
      console.error('Ошибка при получении данных:', error);
      container.innerHTML = '<p>Ошибка при загрузке тренировок.</p>'; // Отображаем сообщение об ошибке
    });
}


// Вспомогательная функция для генерации списка студентов
async function generateStudentList(lessonId) {
  try {
    const response = await fetch(`/records/api/recordedStudents/${lessonId}`); // Замените на ваш URL
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const students = await response.json(); // Преобразуем ответ в JSON
    console.log(students);

    if (!students || students.length === 0) {
      return '<li>Нет студентов для этой тренировки.</li>';
    }

    return students.map(student => `
         <li>
           <label>
             <input type="checkbox" data-student="${student.studentName}"> ${student.studentName}
           </label>
         </li>
       `).join('');

  } catch (error) {
    console.error('Failed to fetch students:', error);
    return '<li>Ошибка при загрузке списка студентов.</li>'; // Обработка ошибки
  }
}

// Вспомогательная функция для генерации уникального ID (пример)
function generateUniqueId() {
  return Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
}


function removeWorkoutData(workoutId) {
  if (confirm('Вы уверены, что хотите удалить эту тренировку?')) {
    fetch(`/lessons/${workoutId}`, { // Замените на ваш URL
      method: 'DELETE',
    })
      .then(response => {
        if (response.ok) {
          // Успешное удаление
          alert('Тренировка успешно удалена.');
          // Обновите список тренировок на странице
          displayWorkouts('workouts-container'); // Предполагается, что у вас есть такая функция
        } else {
          // Обработка ошибок
          console.error('Ошибка при удалении тренировки:', response.statusText);
          alert('Ошибка при удалении тренировки.');
        }
      })
      .catch(error => {
        console.error('Ошибка сети:', error);
        alert('Ошибка сети при удалении тренировки.');
      });
  }
}

// Пример использования (в HTML):
// Предположим, у вас есть кнопка с ID "deleteWorkoutButton"
// <button id="deleteWorkoutButton" onclick="deleteWorkout(123)">Удалить</button>
// Замените 123 на ID конкретной тренировки
