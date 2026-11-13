# Git is context, not a backup of all bytes

[Git worktree](https://git-scm.com/docs/git-worktree) provides separate working trees while some repository state is shared. Inspect worktree list/status and branch ownership before choosing an isolated task location. Never prune, reset, clean or remove a worktree as routine recovery without checking ownership and outstanding changes.

For concurrent writers use an explicit base version: writer A and writer B read H0; A commits H1; B must detect H1 before writing H2. A timestamp check alone can miss same-tick edits. Preserve B's proposal and H1 for a three-way merge against H0; accept the merge only after checking semantics.

Sem Git, identifique conteúdos por manifesto; snapshots de recuperação dependem da política do consumidor. Se cópias forem proibidas, preserve o trabalho existente no lugar e declare o limite de recuperação. Com Git, considere também arquivos alterados, ignorados e não rastreados pertinentes à operação. A clean commit ID does not identify the current filesystem if files changed afterward. Separate Git coordination from filesystem locking and acknowledge noncooperating external writers.
