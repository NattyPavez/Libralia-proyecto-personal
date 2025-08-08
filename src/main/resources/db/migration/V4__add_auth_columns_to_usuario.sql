ALTER TABLE public.usuario ADD COLUMN login    VARCHAR(50)  NOT NULL;
ALTER TABLE public.usuario ADD COLUMN password VARCHAR(225) NOT NULL;
ALTER TABLE public.usuario ADD COLUMN rol      VARCHAR(20)  NOT NULL DEFAULT 'USER';
ALTER TABLE public.usuario ADD COLUMN activo  BOOLEAN      NOT NULL DEFAULT true;

CREATE UNIQUE INDEX IF NOT EXISTS uk_usuario_login  ON public.usuario(login);
CREATE UNIQUE INDEX IF NOT EXISTS uk_usuario_correo ON public.usuario(correo);
