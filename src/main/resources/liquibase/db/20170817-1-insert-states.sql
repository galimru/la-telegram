--changeset galimru:insert-states

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('0f19c6f1-1741-4dde-b176-b1d75660617a', now(), null, 'Начало', 'org.lizaalert.commands.StartCommand', null, null);

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('26c5ff79-4c16-47c6-98cb-4876eb9c8c09', now(), null, 'Сейчас ищут', 'org.lizaalert.commands.SearchNowCommand', null, null);

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('2e596ecc-7de5-4daf-b69c-d861a2e67d4b', now(), null, 'Выбор округа', 'org.lizaalert.commands.SelectCategoryCommand', null, null);

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('ce9d485b-4aac-4a21-8ce4-609aee65a25d', now(), null, 'Выбор региона', 'org.lizaalert.commands.SelectForumCommand', null, null);

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('3b2339d9-6a5f-46a9-a978-f1d1871dc85c', now(), null, 'Сохранение региона', 'org.lizaalert.commands.SaveRegionCommand', true, null);

INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition, one_time)
VALUES ('27de5711-4997-47ba-84f7-8c7290798280', now(), null, 'Еще результаты', 'org.lizaalert.commands.MoreResultsCommand', null, true);

--INSERT INTO la_state (id, created_at, updated_at, name, class_name, transition)
--VALUES ('4b01e482-abca-4f79-9bc8-b815686dccbe', now(), null, 'Сохранение региона', 'org.lizaalert.commands.SaveRegionCommand', null);

--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', now(), null, 'Выбор категории', 'org.lizaalert.commands.ChooseCategoryCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('98250cf3-36f3-4346-96f6-e70d987e126e', now(), null, 'Выбор форума', 'org.lizaalert.commands.ChooseForumCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('3c38eff9-9289-4842-a272-dad5ed1e240d', now(), null, 'Оформление подписки', 'org.lizaalert.commands.SubscribeCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('3d1567b0-6909-4ea6-a847-6cf7b814ba40', now(), null, 'Выбор подписки', 'org.lizaalert.commands.ChooseSubscribeCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('6a945a31-ecbe-4d20-b237-0808715c1e79', now(), null, 'Отмена подписки', 'org.lizaalert.commands.UnsubscribeCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('bd1758d6-5060-4dc4-93d2-ade17e952c79', now(), null, 'Сейчас ищут', 'org.lizaalert.commands.SearchNowCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('c340dbd2-4021-4aaa-8117-6d125c17c4cf', now(), null, 'Выбор категории', 'org.lizaalert.commands.ChooseCategoryCommand');
--
--INSERT INTO la_state (id, created_at, updated_at, name, class_name)
--VALUES ('a3deaf08-54b9-44cd-ac32-8ae96f56cf5c', now(), null, 'Выбор форума', 'org.lizaalert.commands.ChooseForumCommand');