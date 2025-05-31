📘 Regras de Negócio Aprovadas
🧠 Módulo Habit:
Criação de Hábitos:

Qualquer usuário pode criar hábitos.

Em sessões do tipo SOLO, o jogador cria seus próprios hábitos livremente.

Em sessões TEAM, somente o ADMIN pode criar hábitos (os hábitos ficam compartilhados entre membros da equipe).

Progresso de Hábitos:

Usuário pode usar /increment para aumentar o progresso (currentProgress++).

Quando currentProgress >= target, o hábito é marcado como completed = true e completionDate = now.

Soft Delete de Hábitos:

Hábitos podem ser desativados com active = false.

Redução da Vida do Monstro:

Ao completar um hábito, a vida do monstro da sessão (GameSession) deve ser reduzida (lógica ainda será implementada no módulo Habit).

🕹️ Módulo GameSession:
Tipos de Sessão:

SOLO: Um jogador único, com seus próprios hábitos.

TEAM: Múltiplos usuários, com hábitos compartilhados definidos por um ADMIN.

Datas e Duração:

startDate nunca pode ser no passado.

durationDays deve ser escolhido a partir de um Enum: 7, 14, 21 ou 28.

endDate é calculada automaticamente: startDate + durationDays.

Usuários e Sessões:

Um mesmo usuário pode participar de várias sessões simultâneas (inclusive SOLO e TEAM ao mesmo tempo).

createdBy identifica quem criou a sessão.

Hábitos nas Sessões:

Hábitos associados à sessão são instâncias da entidade Habit.

Pode ser necessário futuramente usar uma tabela auxiliar SessionHabit para controlar hábitos padronizados e comportamento individual por sessão (ex: tracking de progresso diferente por sessão mesmo com o mesmo hábito).

🗂️ Relacionamentos (exibidos no gráfico acima):
User ↔ Habit: Um usuário pode ter vários hábitos (M:1).

GameSession ↔ User: Muitos para muitos (M:M) — controlado por game_session_users.

GameSession ↔ Habit: Muitos para muitos (M:M) — controlado por game_session_habits.

SessionHabit (futura) pode controlar customizações por sessão.