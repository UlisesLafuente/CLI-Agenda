Los servicios dependen de implementaciones concretas y no de interfaces

En el README se indica que se aplica Dependency Inversion y que los servicios dependen de interfaces de repositorio. Sin embargo, en el código real no siempre ocurre así. Por ejemplo, EventService depende directamente de EventRepository, TaskService depende de TaskRepository y NotesService depende de NotesRepository.

Si ya has creado interfaces como IEventRepository, ITaskRepository e INotesRepository, lo ideal es que los servicios dependan de esas interfaces. Así podrías cambiar la implementación del repositorio sin modificar el servicio, y también sería más fácil hacer tests unitarios con Mockito.

Los modelos de dominio no deberían capturar sus propias excepciones

En clases como Task, Note y Event, algunos métodos como changeBody, changeTitle, setEvent_fk o setTask_fk hacen validaciones dentro de un try/catch y luego imprimen el error con System.err.println.

Este enfoque es peligroso porque el error queda oculto. Por ejemplo, si se intenta cambiar el título de un evento por uno inválido, el método imprime el mensaje, pero el flujo continúa como si nada. El servicio o la CLI no tienen una forma clara de saber que la operación ha fallado.

Es mejor que el dominio lance una excepción y que sea la capa superior, en este caso la CLI, quien decida cómo mostrar el error al usuario. El modelo no debería encargarse de imprimir mensajes por consola.

Hay mezcla entre lógica de dominio, validación y presentación

Los servicios como TaskService, NotesService y EventService no solo gestionan casos de uso, sino que también tienen métodos como formatTaskList, formatTaskDetail, formatNoteList o formatEventDetail. Esto mezcla lógica de aplicación con lógica de presentación.

El servicio debería centrarse en operaciones como crear, buscar, actualizar o eliminar. La forma de mostrar una tarea o un evento por consola debería estar en la capa CLI o en una clase específica de formateo, como TaskFormatter, EventFormatter o ConsolePresenter.

La generación manual de IDs es un problema importante

En EventService, TaskService y NotesService se genera el siguiente ID buscando todos los registros, calculando el máximo y sumando 1. Esto funciona en un proyecto pequeño, pero no es una buena práctica.

El problema es que puede fallar si dos operaciones crean registros al mismo tiempo. También obliga a cargar todos los datos solo para crear uno nuevo. Además, el esquema de test usa AUTO_INCREMENT, pero el esquema principal no siempre lo usa de forma consistente.

El tratamiento de claves foráneas con 0 no es correcto

En varias partes del modelo se usa 0 para representar que una tarea no tiene evento asociado o que una nota no tiene tarea asociada. Por ejemplo, event_fk = 0 o task_fk = 0.

Esto mezcla un valor artificial con una ausencia real de relación. En base de datos, la ausencia de relación debería representarse con NULL,

Los repositorios hacen búsquedas de forma poco eficiente

En TaskRepository.findByEventId, TaskRepository.findByCompleted y NotesRepository.findByTaskId, primero se hace findAll() y luego se filtra en memoria con streams. Deberías usar queries SQL para esto.

La base de datos debería tener más restricciones

Las tablas permiten muchos campos nulos que probablemente no deberían serlo. Por ejemplo, events.title, tasks.body, notes.body, notes.task_fk o events.eventDate deberían tener restricciones NOT NULL si son obligatorios para el negocio.

También sería recomendable definir mejor qué ocurre al borrar una tarea con notas asociadas o un evento con tareas asociadas. Ahora puede haber errores por clave foránea o comportamientos poco claros. El modelo de datos debería expresar mejor las reglas del dominio.