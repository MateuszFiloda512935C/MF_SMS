# Student Management System (SMS)

## Instrukcje dotyczące kompilacji i uruchamiania aplikacji

1. **Ustawienie pliku startowego**: Ustaw `Main.java` jako plik startowy w swoim IDE (np. IntelliJ IDEA).
2. **Dodanie sterownika SQLite**: Dodaj plik `sqlite-jdbc-3.47.1.0.jar` do modułów projektu, aby umożliwić aplikacji komunikację z bazą danych SQLite.
3. **Kompilacja i uruchomienie**:
   - Skompiluj projekt w IDE.
   - Uruchom aplikację bezpośrednio z poziomu `Main.java`.
4. **Rozwiązywanie problemów po kompilacji**: Jeśli baza danych nie działa po skompilowaniu aplikacji do pliku `.jar`, sprawdź w pliku `MANIFEST.MF`, czy jest zawarta linia:
   ```
   Class-Path: sqlite-jdbc-3.47.1.0.jar
   ```

## Przegląd funkcjonalności

Aplikacja desktopowa oferuje następujące funkcjonalności:

1. **Dodawanie studentów**: Możesz dodać nowego studenta, podając jego ID, imię, wiek oraz ocenę.
2. **Usuwanie studentów**: Usuwanie istniejących rekordów studentów na podstawie ich ID.
3. **Aktualizowanie danych studentów**: Edytowanie danych istniejących studentów.
4. **Wyświetlanie listy studentów**: Przeglądanie wszystkich studentów zapisanych w bazie danych.
5. **Obliczanie średniej ocen**: Automatyczne obliczanie średniej ocen wszystkich studentów.

Wszystkie dane są zapisywane w bazie danych SQLite.

## Instrukcje dotyczące konfiguracji bazy danych

1. **Automatyczna konfiguracja bazy danych**:

   - Program automatycznie wygeneruje bazę danych `students.db`, jeśli nie znajdzie jej w swojej ścieżce.
   - Aplikacja automatycznie stworzy odpowiednią tabelę w bazie danych, jeśli jej nie znajdzie.

2. **Dodanie własnej bazy danych**:

   - Jeśli chcesz użyć własnej bazy danych, upewnij się, że:
     - Plik bazy danych nazywa się `students.db`.
     - Jest typu SQLite.
     - Znajduje się w tym samym folderze co skompilowany plik `.jar` aplikacji.
   - Baza danych musi zawierać następującą tabelę:

   ```sql
   CREATE TABLE students (
       name TEXT,
       age INTEGER,
       grade REAL,
       studentID TEXT PRIMARY KEY
   );
   ```

