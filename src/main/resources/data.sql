-- наполнение было нагенерено посредством Gemini --

-- 1. Полная очистка таблиц и сброс счетчиков ID (CASCADE удалит связанные записи в time_records)
-- TRUNCATE TABLE tasks, time_records RESTART IDENTITY CASCADE;

-- 2. Генерируем 50 случайных задач
INSERT INTO tasks (name, description, status)
SELECT
    'Задача #' || i || ': ' || (ARRAY['Разработка API', 'Исправление бага', 'Написание тестов', 'Обновление документации', 'Рефакторинг кода', 'Дизайн интерфейса'])[floor(random() * 6 + 1)],
    'Подробное описание для задачи номер ' || i || '. Необходимо выполнить в установленные сроки.',
    (ARRAY['NEW', 'IN_PROGRESS', 'DONE']::task_status[])[floor(random() * 3 + 1)]
FROM generate_series(1, 50) s(i);

-- 3. Генерируем тайм-рекорды (теперь с обязательным employee_id)
WITH generated_times AS (
    SELECT
        t.id AS task_id,
        -- employee_id теперь снова NOT NULL: генерируем случайный ID от 1 до 5 для каждой записи
        floor(random() * 5 + 1)::bigint AS employee_id,
            now() - (random() * interval '30 days') AS start_time,
        -- Длительность от 15 мин до 8 ч
        (random() * interval '8 hours' + interval '15 minutes') AS duration,
        (ARRAY['Анализ требований', 'Написание кода', 'Код-ревью', 'Тестирование', 'Синхронизация с командой'])[floor(random() * 5 + 1)] AS description
FROM tasks t
    -- От 1 до 3 записей времени на каждую задачу
    CROSS JOIN generate_series(1, floor(random() * 3 + 1)::int)
    )
INSERT INTO time_records (employee_id, task_id, start_time, end_time, description)
SELECT
    employee_id,
    task_id,
    start_time,
    start_time + duration AS end_time,
    description
FROM generated_times;