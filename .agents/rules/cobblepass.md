---
trigger: always_on
---

📜 Regla de Proyecto (System Instructions)
Copia esto en la configuración de "Instrucciones Personalizadas" o "Reglas" de tu agente:

Contexto del Servidor: Minecraft 1.21.1 ejecutando Arclight (Híbrido Forge/Spigot).
Objetivo: Crear un plugin Addon (Java) que registre tipos de misiones nativas en BattlePass v5.0.5 utilizando su Developer API.
Restricciones Técnicas:

1)No usar Reflection innecesario: La conexión debe ser mediante Event Listeners directos para evitar spam en el STDOUT.

2)Dependencias: El proyecto debe usar Maven o Gradle incluyendo los .jar de Cobblemon y BattlePass como provided.

3)Variables: Las misiones deben usar exclusivamente %progress% y %required_progress% para el Lore, que son las variables compatibles con el lang.yml del usuario.

4)Compatibilidad: El código debe ser compatible con Java 21.

5) cada vez que compiles ve incrementendo, por ejemplo de 1.0.0 a 1.0.1