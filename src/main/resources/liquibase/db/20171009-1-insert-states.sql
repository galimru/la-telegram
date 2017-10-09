--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('3d1567b0-6909-4ea6-a847-6cf7b814ba40', now(), null, 'Выбор подписки', 'org.lizaalert.commands.ChooseSubscribeCommand');

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('6a945a31-ecbe-4d20-b237-0808715c1e79', now(), null, 'Отмена подписки', 'org.lizaalert.commands.UnsubscribeCommand');