--changeset galimru:insert-routes

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('51b307c8-8859-466b-b208-0def68d9f4b6', now(), null, 'Список подписок', '5ba38588-c161-4e8d-a7d1-a3755451f7db', '3d1567b0-6909-4ea6-a847-6cf7b814ba40');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('924f63ea-7b89-4162-a7c3-c39f18638b4b', now(), null, null, '3d1567b0-6909-4ea6-a847-6cf7b814ba40', '6a945a31-ecbe-4d20-b237-0808715c1e79');
