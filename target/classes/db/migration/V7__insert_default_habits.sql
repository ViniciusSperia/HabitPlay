INSERT INTO default_habits (
    id,
    name,
    description,
    difficulty,
    target,
    damage,
    active,
    created_at,
    updated_at
) VALUES
-- EASY
(gen_random_uuid(), 'Beber água', 'Tomar 8 copos de água durante o dia', 'EASY', 8, 10, true, NOW(), NOW()),
(gen_random_uuid(), 'Fazer a cama', 'Organizar a cama ao acordar', 'EASY', 1, 8, true, NOW(), NOW()),
(gen_random_uuid(), 'Escovar os dentes', 'Escovar os dentes após cada refeição', 'EASY', 3, 7, true, NOW(), NOW()),
(gen_random_uuid(), 'Alongar-se', 'Fazer alongamento por 5 minutos', 'EASY', 1, 9, true, NOW(), NOW()),
(gen_random_uuid(), 'Ler por 10 minutos', 'Ler qualquer coisa por pelo menos 10 minutos', 'EASY', 1, 8, true, NOW(), NOW()),
(gen_random_uuid(), 'Evitar açúcar', 'Evitar doces por um dia', 'EASY', 1, 12, true, NOW(), NOW()),
(gen_random_uuid(), 'Dar um elogio', 'Fazer alguém sorrir com um elogio', 'EASY', 1, 9, true, NOW(), NOW()),
(gen_random_uuid(), 'Ficar 1h sem celular', 'Desconectar por uma hora', 'EASY', 1, 11, true, NOW(), NOW()),
(gen_random_uuid(), 'Caminhar 1000 passos', 'Dar uma caminhada leve durante o dia', 'EASY', 1, 10, true, NOW(), NOW()),
(gen_random_uuid(), 'Organizar algo', 'Organizar uma gaveta, pasta ou área da casa', 'EASY', 1, 9, true, NOW(), NOW()),

-- MEDIUM
(gen_random_uuid(), 'Exercício físico', 'Fazer atividade física por pelo menos 30 minutos', 'MEDIUM', 1, 17, true, NOW(), NOW()),
(gen_random_uuid(), 'Estudar', 'Dedicar 1h ao estudo', 'MEDIUM', 1, 18, true, NOW(), NOW()),
(gen_random_uuid(), 'Meditação', 'Meditar por 15 minutos', 'MEDIUM', 1, 16, true, NOW(), NOW()),
(gen_random_uuid(), 'Preparar refeição saudável', 'Cozinhar uma refeição balanceada', 'MEDIUM', 1, 19, true, NOW(), NOW()),
(gen_random_uuid(), 'Dormir cedo', 'Ir dormir antes das 22h', 'MEDIUM', 1, 16, true, NOW(), NOW()),
(gen_random_uuid(), 'Journaling', 'Escrever um diário ou reflexão pessoal', 'MEDIUM', 1, 15, true, NOW(), NOW()),
(gen_random_uuid(), 'Aprender algo novo', 'Assistir a uma aula ou vídeo educativo', 'MEDIUM', 1, 20, true, NOW(), NOW()),
(gen_random_uuid(), 'Evitar cafeína à noite', 'Não tomar café após as 18h', 'MEDIUM', 1, 17, true, NOW(), NOW()),
(gen_random_uuid(), 'Tarefas domésticas', 'Fazer faxina leve ou lavar roupas', 'MEDIUM', 1, 19, true, NOW(), NOW()),
(gen_random_uuid(), 'Agradecer por 3 coisas', 'Escrever 3 motivos de gratidão no dia', 'MEDIUM', 1, 16, true, NOW(), NOW()),

-- HARD
(gen_random_uuid(), 'Ficar offline 1 dia', 'Evitar redes sociais durante o dia todo', 'HARD', 1, 27, true, NOW(), NOW()),
(gen_random_uuid(), 'Treino intenso', 'Fazer um treino avançado por 45 minutos', 'HARD', 1, 30, true, NOW(), NOW()),
(gen_random_uuid(), 'Estudo prolongado', 'Estudar por 3 horas seguidas', 'HARD', 1, 25, true, NOW(), NOW()),
(gen_random_uuid(), 'Ler um capítulo inteiro', 'Ler um capítulo completo de um livro técnico ou complexo', 'HARD', 1, 23, true, NOW(), NOW()),
(gen_random_uuid(), 'Fazer algo fora da zona de conforto', 'Enfrentar um medo ou desafio pessoal', 'HARD', 1, 28, true, NOW(), NOW()),
(gen_random_uuid(), 'Jejum intermitente', 'Ficar 16h sem comer (com supervisão)', 'HARD', 1, 24, true, NOW(), NOW()),
(gen_random_uuid(), 'Escrever um artigo/post técnico', 'Criar conteúdo educativo original', 'HARD', 1, 22, true, NOW(), NOW()),
(gen_random_uuid(), 'Estudo técnico avançado', 'Resolver 3 problemas de código complexos', 'HARD', 1, 26, true, NOW(), NOW()),
(gen_random_uuid(), 'Organizar finanças', 'Revisar orçamento e planejamento financeiro', 'HARD', 1, 25, true, NOW(), NOW()),
(gen_random_uuid(), 'Mentoria/ensinar alguém', 'Dedicar tempo a ajudar alguém a evoluir', 'HARD', 1, 29, true, NOW(), NOW());
