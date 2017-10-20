--changeset galimru:insert-routes

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('d4c05845-85ff-4b0e-af6e-aa93d85069cc', now(), null, '/start', null, '0f19c6f1-1741-4dde-b176-b1d75660617a');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('79f49c7b-c19e-4e98-8270-8bbe33a4831d', now(), null, 'В начало', null, '0f19c6f1-1741-4dde-b176-b1d75660617a');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('8b5271b2-ece9-44c5-889b-dee30e8ae322', now(), null, 'Сейчас ищут', '0f19c6f1-1741-4dde-b176-b1d75660617a', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('bd8f68dd-e4f1-43bd-b203-827ad7aec54c', now(), null, 'Выбрать регион', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09', '2e596ecc-7de5-4daf-b69c-d861a2e67d4b');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('de21fd9a-4366-4584-92c3-8bc4c5c944ea', now(), null, 'Сменить регион', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09', '2e596ecc-7de5-4daf-b69c-d861a2e67d4b');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('2ca3fc2d-3ed7-42d9-8915-f6127823a10e', now(), null, null, '2e596ecc-7de5-4daf-b69c-d861a2e67d4b', 'ce9d485b-4aac-4a21-8ce4-609aee65a25d');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('d99bbfa2-e1b6-4d22-9603-0cfd112f3b3a', now(), null, null, 'ce9d485b-4aac-4a21-8ce4-609aee65a25d', '3b2339d9-6a5f-46a9-a978-f1d1871dc85c');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('d8a875ce-5aac-4f31-a673-8ae1305620c8', now(), null, null, '3b2339d9-6a5f-46a9-a978-f1d1871dc85c', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09');

INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
VALUES ('95451c4f-fca3-49a2-880b-e2f0ea767542', now(), null, 'Еще', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09', '27de5711-4997-47ba-84f7-8c7290798280');

--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('7dc4a395-a046-44de-8fd0-7701b331a686', now(), null, null, '27de5711-4997-47ba-84f7-8c7290798280', '26c5ff79-4c16-47c6-98cb-4876eb9c8c09');

--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('4c06857d-e525-4ba9-bb81-8e9f591870e0', now(), null, 'Хочу получать уведомления', '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('c27d9f65-b72a-4cac-a23b-c0ece49ffb18', now(), null, null, 'e346f406-4cd1-45cd-ad24-eb5dfeccf6f9', '98250cf3-36f3-4346-96f6-e70d987e126e');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('89e3f7d2-4d80-4e8a-8587-1d28bed26ca7', now(), null, null, '98250cf3-36f3-4346-96f6-e70d987e126e', '3c38eff9-9289-4842-a272-dad5ed1e240d');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('51b307c8-8859-466b-b208-0def68d9f4b6', now(), null, 'Список подписок', '5ba38588-c161-4e8d-a7d1-a3755451f7db', '3d1567b0-6909-4ea6-a847-6cf7b814ba40');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('924f63ea-7b89-4162-a7c3-c39f18638b4b', now(), null, null, '3d1567b0-6909-4ea6-a847-6cf7b814ba40', '6a945a31-ecbe-4d20-b237-0808715c1e79');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('653be718-9b5c-4bbe-b9d2-4c11a16099bf', now(), null, 'Сейчас ищут', '5ba38588-c161-4e8d-a7d1-a3755451f7db', 'bd1758d6-5060-4dc4-93d2-ade17e952c79');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('7365667c-ff40-426b-935b-633a715ce8b4', now(), null, null, 'bd1758d6-5060-4dc4-93d2-ade17e952c79', 'c340dbd2-4021-4aaa-8117-6d125c17c4cf');
--
--INSERT INTO la_route (id, created_at, updated_at, command, prev_state_id, next_state_id)
--VALUES ('bd80d503-903e-4e3c-a037-bfbf6894807a', now(), null, null, 'c340dbd2-4021-4aaa-8117-6d125c17c4cf', '653be718-9b5c-4bbe-b9d2-4c11a16099bf');
