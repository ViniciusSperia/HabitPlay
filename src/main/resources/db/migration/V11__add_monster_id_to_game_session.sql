-- Adiciona a coluna como NULL
ALTER TABLE game_sessions
ADD COLUMN monster_id UUID;

-- Define um valor padrão para sessões antigas
UPDATE game_sessions
SET monster_id = 'b068b64e-0989-4322-af11-7dad6bb85b8f'
WHERE monster_id IS NULL;

-- Altera para NOT NULL e adiciona a constraint
ALTER TABLE game_sessions
ALTER COLUMN monster_id SET NOT NULL,
ADD CONSTRAINT fk_game_session_monster
    FOREIGN KEY (monster_id)
    REFERENCES monsters(id);