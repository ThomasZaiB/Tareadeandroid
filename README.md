# Tareadeandroid – Base de datos SQLite en Android Studio

App de ejemplo en **Kotlin** que implementa una base de datos SQLite paso a paso
(CRUD de usuarios con nombre y email).

## Cómo abrirlo
1. Android Studio → **File > Open** → selecciona esta carpeta.
2. Espera la sincronización de Gradle.
3. Ejecuta en un emulador o dispositivo (botón ▶).

## Qué hay en cada archivo (según los pasos de la guía)
| Paso | Archivo |
|------|---------|
| 2. Clase de ayuda (`SQLiteOpenHelper`) | `app/src/main/java/com/example/tareasqlite/DatabaseHelper.kt` |
| 4. Modelo (clase de entidad) | `app/src/main/java/com/example/tareasqlite/Usuario.kt` |
| 5. Operaciones CRUD | `DatabaseHelper.kt` → `insertarUsuario`, `obtenerUsuarios`, `actualizarUsuario`, `eliminarUsuario` |
| 6. Usar la BD en la Activity | `app/src/main/java/com/example/tareasqlite/MainActivity.kt` |
| 7. Permisos | `app/src/main/AndroidManifest.xml` (no se necesita ninguno) |
| 8. Interfaz | `app/src/main/res/layout/activity_main.xml` + `RecyclerView` (`UsuarioAdapter.kt`) |
| 9. Verificar | Logcat con el filtro `DB` (`Usuario insertado: ...`) |
| 10. Consejos | `try-catch` en la Activity y `dbHelper.close()` en `onDestroy` |

## Uso de la app
- Escribe nombre y email y pulsa **Guardar** (insertar).
- Toca un usuario de la lista para editarlo y pulsa **Actualizar**.
- Pulsa **Eliminar** en un usuario para borrarlo.

## Dónde está la base de datos
`/data/data/com.example.tareasqlite/databases/miBD.db`
(se puede ver con **View > Tool Windows > App Inspection > Database Inspector** en Android Studio).
