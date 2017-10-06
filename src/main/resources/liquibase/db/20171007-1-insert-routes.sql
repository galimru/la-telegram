--changeset galimru:insert-routes

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('d3ed0593-a4ef-4479-9e7a-7144c86e0fc0', now(), null, '/start', null, '5ba38588-c161-4e8d-a7d1-a3755451f7db');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('79f49c7b-c19e-4e98-8270-8bbe33a4831d', now(), null, 'В начало', null, '5ba38588-c161-4e8d-a7d1-a3755451f7db');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('4c06857d-e525-4ba9-bb81-8e9f591870e0', now(), null, 'Хочу получать уведомления', '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('c27d9f65-b72a-4cac-a23b-c0ece49ffb18', now(), null, null, 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', '98250cf3-36f3-4346-96f6-e70d987e126e');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('89e3f7d2-4d80-4e8a-8587-1d28bed26ca7', now(), null, null, '98250cf3-36f3-4346-96f6-e70d987e126e', '3c38eff9-9289-4842-a272-dad5ed1e240d');