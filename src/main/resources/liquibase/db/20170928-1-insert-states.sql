--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name)
VALUES ('e29fc8bd-8905-42a3-830f-2066cc54d7b4', now(), null, null, 'Главное меню', 'org.lizaalert.commands.StartCommand');

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name)
VALUES ('e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', now(), null, '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'Хочу получать уведомления', 'org.lizaalert.commands.GetRegionsCommand');

INSERT INTO la_state (id, created_at, updated_at, parent_id, command, class_name)
VALUES ('98250cf3-36f3-4346-96f6-e70d987e126e', now(), null, 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', null, 'org.lizaalert.commands.GetDistrictsCommand');