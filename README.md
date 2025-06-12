# jc01_lesson27_hw
IT-Academy, Lesson 27.HW
Исправления:
1. В контроллере в catch убрал e.getMessage(), оставил просто сообщение. 
2. В классах Student, Teacher, Administrator убрал System.out.println. Теперь возвращает просто String.
3. Поработал с сетодом findCourseByName убрав Exception если пользователь введет неверное название курса. Использовал класс Optional Optional.empty() внеся соответствующие изменения в репозиторий, сервисы и контроллер
4. Добавил в репозитории CourseRepositoryRuntimeException
