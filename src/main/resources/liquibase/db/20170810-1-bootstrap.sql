--changeset galimru:bootstrap

CREATE TABLE la_forum
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  enabled boolean,
  forum_id integer,
  name character varying(255),
  parent_id uuid,
  CONSTRAINT la_forum_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_forum_parent_id FOREIGN KEY (parent_id)
      REFERENCES la_forum (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_forum_forum_id UNIQUE (forum_id)
);

CREATE TABLE la_topic
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  active boolean,
  author character varying(255),
  image_url character varying(2048),
  message character varying(4096),
  title character varying(255),
  topic_id integer,
  forum_id uuid,
  CONSTRAINT la_topic_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_topic_forum_id FOREIGN KEY (forum_id)
      REFERENCES la_forum (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_topic_topic_id UNIQUE (topic_id)
);

CREATE TABLE la_state
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  class_name character varying(255),
  command character varying(255),
  parent_id uuid,
  CONSTRAINT la_state_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_state_parent_id FOREIGN KEY (parent_id)
      REFERENCES la_state (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_state_parent_id_command UNIQUE (parent_id, command)
);

CREATE TABLE la_user
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  chat_id character varying(255),
  first_name character varying(255),
  last_name character varying(255),
  user_id integer,
  username character varying(255),
  sate_id uuid,
  CONSTRAINT la_user_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_user_state_id FOREIGN KEY (sate_id)
      REFERENCES la_state (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_user_user_id UNIQUE (user_id)
);

CREATE TABLE la_user_forum_ref
(
  user_id uuid NOT NULL,
  forum_id uuid NOT NULL,
  CONSTRAINT fk_la_user_forum_ref_forum_id FOREIGN KEY (forum_id)
      REFERENCES la_forum (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_user_forum_ref_user_id FOREIGN KEY (user_id)
      REFERENCES la_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);