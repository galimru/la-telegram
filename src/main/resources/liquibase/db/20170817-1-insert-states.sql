--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name)
VALUES ('5ba38588-c161-4e8d-a7d1-a3755451f7db', now(), null, null, '/start', 'org.lizaalert.commands.StartCommand');