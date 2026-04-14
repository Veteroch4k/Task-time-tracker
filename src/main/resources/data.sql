-- наполнение было нагенерено посредством Gemini --

-- 1. Добавляем 50 случайных задач
INSERT INTO tasks (name, description, status)
SELECT
    'Задача #' || i || ': ' || (ARRAY['Разработка API', 'Исправление бага', 'Написание тестов', 'Обновление документации', 'Рефакторинг кода', 'Дизайн интерфейса'])[floor(random() * 6 + 1)],
    'Подробное описание для задачи номер ' || i || '. Необходимо выполнить в установленные сроки.',
    (ARRAY['NEW', 'IN_PROGRESS', 'DONE']::task_status[])[floor(random() * 3 + 1)]
FROM generate_series(1, 50) s(i);

-- 2. Добавляем записи времени (от 1 до 3 записей на каждую существующую задачу)
WITH generated_times AS (
    SELECT
        t.id AS task_id,
        floor(random() * 5 + 1)::bigint AS employee_id, -- Симуляция 5 разных сотрудников (ID от 1 до 5)
            now() - (random() * interval '30 days') AS start_time, -- Начало в любой момент за последние 30 дней
        (random() * interval '8 hours' + interval '15 minutes') AS duration, -- Длительность от 15 минут до 8 часов
        random() > 0.2 AS is_completed, -- 80% записей имеют end_time, 20% еще в процессе (NULL)
        (ARRAY['Анализ требований', 'Написание кода', 'Код-ревью', 'Тестирование', 'Синхронизация с командой'])[floor(random() * 5 + 1)] AS description
FROM tasks t
    -- Генерируем от 1 до 3 записей времени для каждой созданной задачи
    CROSS JOIN generate_series(1, floor(random() * 3 + 1)::int)
    )
INSERT INTO time_records (employee_id, task_id, start_time, end_time, description)
SELECT
    employee_id,
    task_id,
    start_time,
    -- Если запись завершена, прибавляем длительность к началу. Иначе оставляем NULL
    CASE WHEN is_completed THEN start_time + duration ELSE NULL END AS end_time,
    description
FROM generated_times;