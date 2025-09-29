import mysql.connector

# 1. Conexión inicial 
conexion = mysql.connector.connect(
    host="localhost",
    user="root",   
    password=""   
)
cursor = conexion.cursor()

# 2. Crear base de datos si no existe
cursor.execute("CREATE DATABASE IF NOT EXISTS gestor_tareas")
cursor.execute("USE gestor_tareas")

# 3. Crear tabla si no existe
cursor.execute("""
CREATE TABLE IF NOT EXISTS tareas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
)
""")

# -------- FUNCIONES -------- #
def agregar_tarea(nombre, descripcion):
    cursor.execute(
        "INSERT INTO tareas (nombre, descripcion) VALUES (%s, %s)", 
        (nombre, descripcion)
    )
    conexion.commit()

def mostrar_tareas():
    cursor.execute("SELECT * FROM tareas")
    return cursor.fetchall()

def eliminar_tarea(id_tarea):
    cursor.execute("DELETE FROM tareas WHERE id = %s", (id_tarea,))
    conexion.commit()

# -------- PRUEBA -------- #
# Agregar
agregar_tarea("estudio", "Estudiar para el parcial")
agregar_tarea("entrenar", "Hacer ejercicio")
agregar_tarea("lectura", "Leer un libro")
agregar_tarea("proyecto", "Realizar proyecto de programación")

# Mostrar
print("Lista de tareas:")
for tarea in mostrar_tareas():
    print(f"ID: {tarea[0]} | Nombre: {tarea[1]} | Descripción: {tarea[2]}")

# Eliminar la tarea con id=2
eliminar_tarea(2)

print("\nLista de tareas después de eliminar:")
for tarea in mostrar_tareas():
    print(f"ID: {tarea[0]} | Nombre: {tarea[1]} | Descripción: {tarea[2]}")

# Cerrar conexión
conexion.close()

