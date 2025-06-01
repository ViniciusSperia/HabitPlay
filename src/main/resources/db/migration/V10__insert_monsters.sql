-- V11__insert_monsters.sql

INSERT INTO monsters (
    id,
    name,
    description,
    image_url,
    difficulty,
    max_health,
    damage_per_habit,
    active,
    created_at,
    updated_at
) VALUES
-- EASY
(gen_random_uuid(), 'Slime Preguiçoso', 'Um pequeno monstro gelatinoso que atrasa seus hábitos.', 'https://img.freepik.com/vetores-premium/o-gesto-preguicoso-do-personagem-de-desenho-animado-splat_152558-85824.jpg', 'EASY', 500, 10, true, NOW(), NOW()),
(gen_random_uuid(), 'Pó da Desorganização', 'Espalha bagunça e confusão pela sua rotina.', 'https://media.istockphoto.com/id/1405704982/pt/vetorial/doodle-icon-mental-disorder-finding-answers-confusion-concept-hand-drawn-vector.jpg?s=612x612&w=0&k=20&c=sGyNHZ40asL2bLO9nMGt5iFGo-oZPaWKuMnqGkcEmwE=', 'EASY', 600, 15, true, NOW(), NOW()),
(gen_random_uuid(), 'Fadiga Fofa', 'Parece inofensivo, mas sabota sua energia matinal.', 'https://img.freepik.com/vetores-premium/o-desenho-animado-de-fadiga-do-fantasma_152558-28159.jpg', 'EASY', 700, 20, true, NOW(), NOW()),
(gen_random_uuid(), 'Monstrinho da Procrastinação', 'Te convence a deixar tudo para depois.', 'https://png.pngtree.com/png-vector/20230801/ourlarge/pngtree-cartoon-little-monster-sticker-illustration-vector-png-image_6828711.png', 'EASY', 800, 20, true, NOW(), NOW()),

-- MEDIUM
(gen_random_uuid(), 'Troll das Notificações', 'Distrações constantes que aparecem a cada minuto.', 'https://media.gettyimages.com/id/1205458055/pt/vetorial/internet-troll-posting-mean-comments.jpg?s=612x612&w=gi&k=20&c=knVK0PCqg1Qnf3YeidMR9MU0aE8k67vJvj4rfmFOvsI=', 'MEDIUM', 1500, 35, true, NOW(), NOW()),
(gen_random_uuid(), 'Fera da Ansiedade', 'Drena seu foco e paz de espírito.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoBh-RvVGK6dU_WSFlH0FILquYIEAsIT3fbw&s', 'MEDIUM', 1800, 40, true, NOW(), NOW()),
(gen_random_uuid(), 'Golem do Caos Doméstico', 'Torna toda tarefa doméstica um desafio.', 'https://thumbs.dreamstime.com/b/o-monstro-da-geladeira-ataca-uma-cozinha-cheia-de-caos-criada-com-tecnologia-ai-generativa-repleta-conceito-medo-e-destrui%C3%A7%C3%A3o-277054393.jpg', 'MEDIUM', 2000, 45, true, NOW(), NOW()),
(gen_random_uuid(), 'Guardião das Desculpas', 'Especialista em te convencer que hoje não é o dia.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlz1MxeiXiAgPKa5hOSeuX0UzP0uIPKNjAUA&s', 'MEDIUM', 2200, 50, true, NOW(), NOW()),

-- HARD
(gen_random_uuid(), 'Dragão da Autossabotagem', 'Sopra fogo em toda tentativa de progresso.', 'https://previews.123rf.com/images/canbedone/canbedone1710/canbedone171000015/87815876-drag%C3%A3o-vermelho-dos-desenhos-animados-que-exala-o-fogo-isolado.jpg', 'HARD', 3500, 75, true, NOW(), NOW()),
(gen_random_uuid(), 'Titã do Cansaço', 'Incomoda até o mais disciplinado dos heróis.', 'https://i.pinimg.com/736x/a7/ee/bc/a7eebcf1b950285c0719b1638371753a.jpg', 'HARD', 4000, 80, true, NOW(), NOW()),
(gen_random_uuid(), 'Demônio da Indisciplina', 'Testa sua vontade como nenhum outro.', 'https://img.freepik.com/fotos-premium/profundidade-de-estilo-de-desenho-de-demonio-intenso-com-influencia-de-arte-peluda_899449-22596.jpg', 'HARD', 4500, 90, true, NOW(), NOW()),
(gen_random_uuid(), 'Ancião da Zona de Conforto', 'Faz você esquecer dos seus objetivos.', 'https://st3.depositphotos.com/3294573/13858/v/450/depositphotos_138581208-stock-illustration-businessman-standing-in-comfort-zone.jpg', 'HARD', 5000, 100, true, NOW(), NOW());
