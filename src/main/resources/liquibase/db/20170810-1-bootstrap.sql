--changeset galimru:bootstrap

CREATE TABLE la_category
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  name character varying(255),
  CONSTRAINT la_category_pkey PRIMARY KEY (id)
);

CREATE TABLE la_forum
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  forum_id integer,
  name character varying(255),
  category_id uuid,
  CONSTRAINT la_forum_pkey PRIMARY KEY (id),
  CONSTRAINT fk1cqv4gatvvpp59gjvbqvtvart FOREIGN KEY (category_id)
      REFERENCES la_category (id) MATCH SIMPLE
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
  CONSTRAINT fkombsxe8ky2qh338nvfui682ty FOREIGN KEY (forum_id)
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
  name character varying(255),
  CONSTRAINT la_state_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_state_class_name UNIQUE (class_name)
);

CREATE TABLE la_route
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  command character varying(255),
  next_state_id uuid,
  prev_state_id uuid,
  CONSTRAINT la_route_pkey PRIMARY KEY (id),
  CONSTRAINT fk5s80m6a93gampqhygkfh8151h FOREIGN KEY (prev_state_id)
      REFERENCES la_state (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrcs9d922whif9chxg8iowmx18 FOREIGN KEY (next_state_id)
      REFERENCES la_state (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE la_user
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  first_name character varying(255),
  last_name character varying(255),
  user_id integer,
  username character varying(255),
  CONSTRAINT la_user_pkey PRIMARY KEY (id),
  CONSTRAINT fk_la_user_user_id UNIQUE (user_id)
);

CREATE TABLE la_subscribe
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  forum_id uuid,
  user_id uuid,
  CONSTRAINT la_subscribe_pkey PRIMARY KEY (id),
  CONSTRAINT fk5tspufcpf8lff779r3ojgtt4o FOREIGN KEY (forum_id)
      REFERENCES la_forum (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrs1iysx2jrayrlgkhahs6g7nn FOREIGN KEY (user_id)
      REFERENCES la_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_subscribe_user_id_forum_id UNIQUE (user_id, forum_id)
);

CREATE TABLE la_session
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  state_id uuid,
  user_id uuid,
  CONSTRAINT la_session_pkey PRIMARY KEY (id),
  CONSTRAINT fk7m4bfi1w4axr44qgw7hm3gh15 FOREIGN KEY (user_id)
      REFERENCES la_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkiqi7opje6b4howntqpu40yuxg FOREIGN KEY (state_id)
      REFERENCES la_state (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_session_user_id UNIQUE (user_id)
);

CREATE TABLE la_session_param
(
  id uuid NOT NULL,
  created_at timestamp without time zone,
  updated_at timestamp without time zone,
  key character varying(255),
  value character varying(512),
  session_id uuid,
  CONSTRAINT la_session_param_pkey PRIMARY KEY (id),
  CONSTRAINT fks1b4eyjt8rqtpyr46aouqv4hs FOREIGN KEY (session_id)
      REFERENCES la_session (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_la_session_param_session_id_key UNIQUE (session_id, key)
);