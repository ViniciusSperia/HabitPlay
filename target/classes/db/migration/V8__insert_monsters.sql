INSERT INTO monsters (
    id,
    name,
    image_url,
    difficulty_id,
    max_health,
    active,
    created_at,
    updated_at
) VALUES
-- EASY
(gen_random_uuid(), 'Slime Preguiçoso', 'https://img.freepik.com/vetores-premium/o-gesto-preguicoso-do-personagem-de-desenho-animado-splat_152558-85824.jpg', '00000000-0000-0000-0000-000000000101', 500, true, NOW(), NOW()),
(gen_random_uuid(), 'Pó da Desorganização', 'https://media.istockphoto.com/id/1405704982/pt/vetorial/doodle-icon-mental-disorder-finding-answers-confusion-concept-hand-drawn-vector.jpg?s=612x612&w=0&k=20&c=sGyNHZ40asL2bLO9nMGt5iFGo-oZPaWKuMnqGkcEmwE=', '00000000-0000-0000-0000-000000000101', 600, true, NOW(), NOW()),
(gen_random_uuid(), 'Fadiga Fofa', 'https://img.freepik.com/vetores-premium/o-desenho-animado-de-fadiga-do-fantasma_152558-28159.jpg', '00000000-0000-0000-0000-000000000101', 700, true, NOW(), NOW()),
(gen_random_uuid(), 'Monstrinho da Procrastinação', 'https://png.pngtree.com/png-vector/20230801/ourlarge/pngtree-cartoon-little-monster-sticker-illustration-vector-png-image_6828711.png', '00000000-0000-0000-0000-000000000101', 800, true, NOW(), NOW()),

-- MEDIUM
(gen_random_uuid(), 'Troll das Notificações', 'https://media.gettyimages.com/id/1205458055/pt/vetorial/internet-troll-posting-mean-comments.jpg?s=612x612&w=gi&k=20&c=knVK0PCqg1Qnf3YeidMR9MU0aE8k67vJvj4rfmFOvsI=', '00000000-0000-0000-0000-000000000102', 1500, true, NOW(), NOW()),
(gen_random_uuid(), 'Fera da Ansiedade', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoBh-RvVGK6dU_WSFlH0FILquYIEAsIT3fbw&s', '00000000-0000-0000-0000-000000000102', 1800, true, NOW(), NOW()),
(gen_random_uuid(), 'Golem do Caos Doméstico', 'https://thumbs.dreamstime.com/b/o-monstro-da-geladeira-ataca-uma-cozinha-cheia-de-caos-criada-com-tecnologia-ai-generativa-repleta-conceito-medo-e-destrui%C3%A7%C3%A3o-277054393.jpg', '00000000-0000-0000-0000-000000000102', 2000, true, NOW(), NOW()),
(gen_random_uuid(), 'Guardião das Desculpas', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlz1MxeiXiAgPKa5hOSeuX0UzP0uIPKNjAUA&s', '00000000-0000-0000-0000-000000000102', 2200, true, NOW(), NOW()),

-- HARD
(gen_random_uuid(), 'Dragão da Autossabotagem', 'https://previews.123rf.com/images/canbedone/canbedone1710/canbedone171000015/87815876-drag%C3%A3o-vermelho-dos-desenhos-animados-que-exala-o-fogo-isolado.jpg', '00000000-0000-0000-0000-000000000103', 7500, true, NOW(), NOW()),
(gen_random_uuid(), 'Titã do Cansaço', 'https://i.pinimg.com/736x/a7/ee/bc/a7eebcf1b950285c0719b1638371753a.jpg', '00000000-0000-0000-0000-000000000103', 4000, true, NOW(), NOW()),
(gen_random_uuid(), 'Demônio da Indisciplina', 'https://img.freepik.com/fotos-premium/profundidade-de-estilo-de-desenho-de-demonio-intenso-com-influencia-de-arte-peluda_899449-22596.jpg', '00000000-0000-0000-0000-000000000103', 4500, true, NOW(), NOW()),
(gen_random_uuid(), 'Ancião da Zona de Conforto', 'https://st3.depositphotos.com/3294573/13858/v/450/depositphotos_138581208-stock-illustration-businessman-standing-in-comfort-zone.jpg', '00000000-0000-0000-0000-000000000103', 5000, true, NOW(), NOW());
