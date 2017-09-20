--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, parent_id, command)
VALUES ('5ba38588-c161-4e8d-a7d1-a3755451f7db', now(), null, null, '/start');

INSERT INTO la_state_template_ref (order_index, state_id, template_id)
VALUES (1, now(), '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'cd5a8d1d-daf8-43a8-b478-7b58901885c2');