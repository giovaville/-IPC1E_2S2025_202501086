# -IPC1E_2S2025_202501086
# BiblioSystem - Proyecto 1

## Descripción
BiblioSystem es una aplicación desarrollada en Java para la gestión de una biblioteca universitaria. El sistema permite administrar libros, usuarios y préstamos, aplicando reglas de negocio definidas en el proyecto.

## Funcionalidades
- Inicio de sesión por rol
- Gestión de libros
- Gestión de usuarios
- Registro de préstamos y devoluciones
- Consulta de historial de préstamos
- Generación de reportes en HTML
- Registro de operaciones en bitácora

## Roles del sistema
- Administrador
- Operador
- Estudiante

## Requisitos
- Java JDK 8 o superior
- NetBeans (opcional, recomendado)
- Sistema operativo Windows

## Cómo ejecutar el proyecto
1. Descargar o clonar el repositorio.
2. Abrir la carpeta `PROYECTO1_IPC1`.
3. Ejecutar el proyecto desde NetBeans o cualquier IDE compatible con Java.
4. También se puede ejecutar mediante el archivo `.jar` ubicado en la carpeta correspondiente del proyecto.

## Archivos incluidos
- Código fuente del proyecto
- Manual técnico
- Manual de usuario
- Informe de desarrollo
- Diagramas
- Archivo ejecutable `.jar`

## Estructura del repositorio
- `PROYECTO1_IPC1`: contiene el código fuente del sistema
- Documentos PDF del proyecto: manual técnico, manual de usuario, informe y diagramas

## Problemas encontrados
- Manejo de arreglos estáticos sin uso de ArrayList
- Validaciones de préstamos activos y vencidos
- Manejo de archivos de texto para persistencia
- Actualización de tablas en la interfaz

## Soluciones adoptadas
- Uso de recorridos manuales con ciclos
- Validaciones centralizadas en el controlador
- Lectura y escritura controlada en archivos `.txt`
- Refresco de tablas después de operaciones importantes

## Mejoras futuras
- Uso de base de datos
- Mejorar la interfaz gráfica
- Optimizar búsquedas
- Implementar notificaciones de vencimiento
