#!/bin/bash

# Nombre del servicio de la BD y puerto
DB_HOST="mysql_db"
DB_PORT="3306"

echo "Iniciando script de entrada. Esperando que $DB_HOST:$DB_PORT esté disponible..."

# Usamos 'nc -z' para verificar si el puerto está abierto.
# Si el comando falla (el puerto no responde), el bucle continúa.
while ! nc -z $DB_HOST $DB_PORT; do
  sleep 1 # Espera 1 segundo antes de volver a intentar
done

echo "Base de datos disponible. Iniciando la aplicación Spring Boot..."

# Ejecuta el comando original de la aplicación (sustituye al 'command' original)
exec java -jar app.jar