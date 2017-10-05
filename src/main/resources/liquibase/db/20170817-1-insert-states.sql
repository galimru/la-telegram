--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name, transition_to)
VALUES ('5ba38588-c161-4e8d-a7d1-a3755451f7db', now(), null, null, '/start', 'org.lizaalert.commands.StartCommand', null);

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name, transition_to)
VALUES ('e29fc8bd-8905-42a3-830f-2066cc54d7b4', now(), null, null, 'В начало 🏠', 'org.lizaalert.commands.StartCommand', '5ba38588-c161-4e8d-a7d1-a3755451f7db');

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name, transition_to)
VALUES ('e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', now(), null, '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'Получать уведомления 💬', 'org.lizaalert.commands.GetCategoryListCommand', null);

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name, transition_to)
VALUES ('98250cf3-36f3-4346-96f6-e70d987e126e', now(), null, 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', null, 'org.lizaalert.commands.GetForumListCommand', null);

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name, transition_to)
VALUES ('3c38eff9-9289-4842-a272-dad5ed1e240d', now(), null, '98250cf3-36f3-4346-96f6-e70d987e126e', null, 'org.lizaalert.commands.SubscribeCommand', null);