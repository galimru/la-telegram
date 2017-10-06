--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('5ba38588-c161-4e8d-a7d1-a3755451f7db', now(), null, 'Начало', 'org.lizaalert.commands.StartCommand');

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', now(), null, 'Выбор категории', 'org.lizaalert.commands.ChooseCategoryCommand');

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('98250cf3-36f3-4346-96f6-e70d987e126e', now(), null, 'Выбор форума', 'org.lizaalert.commands.ChooseForumCommand');

INSERT INTO la_state (id, created_at, updated_at, name, class_name)
VALUES ('3c38eff9-9289-4842-a272-dad5ed1e240d', now(), null, 'Оформление подписки', 'org.lizaalert.commands.SubscribeCommand');