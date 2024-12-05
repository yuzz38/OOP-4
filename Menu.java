import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {


    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        Team team = new Team();
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.println("\nМеню:");
                System.out.println("1. Работа с данными");
                System.out.println("2. Множественное наследование");
                System.out.println("3. Показать всех членов команды");
                System.out.println("4. Показать всех атлетов");

                System.out.println("6. Сохранить команду в файл");
                System.out.println("7. Загрузить команду из файла");
                System.out.println("0. Выход");

                int choice = getValidIntInput(scanner, "Выберите пункт меню: ");

                switch (choice) {
                    case 1:
                        showDataMenu(scanner, team);
                        break;
                    case 2:
                        showInheritanceMenu(scanner, team);
                        break;
                    case 3:
                        team.displayParticipants();
                        break;
                    case 4:
                        team.displayAthlete();
                        break;

                    case 6:
                        saveTeamToFile(team);
                        break;
                    case 7:
                        team = loadTeamFromFile();
                        break;
                    case 0:
                        System.out.println("Выход из программы.");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }

    private static void showDataMenu(Scanner scanner, Team team) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nРабота с данными:");
            System.out.println("1. Добавить спортсмена");
            System.out.println("2. Добавить тренера");
            System.out.println("3. Удалить участника");
            System.out.println("4. Найти члена команды по имени");
            System.out.println("0. Назад");

            int choice = getValidIntInput(scanner, "Выберите пункт меню: ");

            switch (choice) {
                case 1:
                    String athleteName = getValidStringInput(scanner, "Введите имя спортсмена: ");
                    int athleteAge = getValidAgeInput(scanner, "Введите возраст спортсмена: ");
                    team.addParticipant(new Athlete(athleteName, athleteAge));
                    break;
                case 2:
                    if (team.hasTrainer()) {
                        System.out.println("Тренер уже есть в команде, для добавления нового, удалите старого.");
                        break;
                    }
                    String coachName = getValidStringInput(scanner, "Введите имя тренера: ");
                    int coachAge = getValidAgeInput(scanner, "Введите возраст тренера: ");
                    int coachExperience = getValidExperienceInput(scanner, "Введите стаж работы тренера (в годах): ");
                    team.addParticipant(new Coach(coachName, coachAge, coachExperience));
                    break;
                case 3:
                    String nameToRemove = getValidStringInput(scanner, "Введите имя участника для удаления: ");
                    if (team.removeParticipant(nameToRemove)) {
                        System.out.println("Участник " + nameToRemove + " удален");
                    } else {
                        System.out.println("Участник с именем " + nameToRemove + " не найден.");
                    }
                    break;
                case 4:
                    String nameToFind = getValidStringInput(scanner, "Введите имя участника для поиска: ");
                    Participant foundParticipant = team.findParticipant(nameToFind);
                    if (foundParticipant != null) {
                        System.out.println("Найден участник: " + foundParticipant);
                    } else {
                        System.out.println("Участник не найден.");
                    }
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void showInheritanceMenu(Scanner scanner, Team team) {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\nМножественное наследование:");
            System.out.println("1. Выполнить действие для всех членов команды");
            System.out.println("0. Назад");

            int choice = getValidIntInput(scanner, "Выберите пункт меню: ");

            switch (choice) {
                case 1:
                    team.performActionForAll();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    // Метод для сохранения команды в файл
    private static void saveTeamToFile(Team team) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("team.ser"))) {
            out.writeObject(team);
            System.out.println("Команда успешно сохранена в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении команды: " + e.getMessage());
        }
    }

    // Метод для загрузки команды из файла
    private static Team loadTeamFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("team.ser"))) {
            Team team = (Team) in.readObject();
            System.out.println("Команда успешно загружена из файла.");
            return team;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке команды: " + e.getMessage());
            return new Team(); // Возвращаем новый объект команды, если ошибка
        }
    }

    // Получение корректного целочисленного ввода
    private static int getValidIntInput(Scanner scanner, String prompt) {
        int input = -1;
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print(prompt);
                input = scanner.nextInt();
                scanner.nextLine();
                if (input < 0) {
                    throw new IllegalArgumentException("Число не может быть отрицательным.");
                }
                isRunning = false;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    // Получение корректного ввода возраста (положительное число)
    private static int getValidAgeInput(Scanner scanner, String prompt) {
        int age = -1;
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print(prompt);
                age = scanner.nextInt();
                scanner.nextLine();
                if (age <= 0) {
                    throw new IllegalArgumentException("Возраст должен быть положительным числом.");
                }
                isRunning = false;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число для возраста.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return age;
    }

    // Получение корректного ввода стажа (положительное число)
    private static int getValidExperienceInput(Scanner scanner, String prompt) {
        int experience = -1;
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print(prompt);
                experience = scanner.nextInt();
                scanner.nextLine();
                if (experience < 0) {
                    throw new IllegalArgumentException("Стаж не может быть отрицательным числом.");
                }
                isRunning = false;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число для стажа.");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return experience;
    }

    // Получение корректного строкового ввода
    private static String getValidStringInput(Scanner scanner, String prompt) {
        String input = "";
        boolean isRunning = true;
        while (isRunning) {
            try {
                System.out.print(prompt);
                input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    throw new IllegalArgumentException("Имя не может быть пустым.");
                }
                if (hasNumber(input)) {
                    throw new IllegalArgumentException("Имя не может содержать цифры.");
                }
                isRunning = false;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }

    private static boolean hasNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char number = input.charAt(i);
            if (number >= '0' && number <= '9') {
                return true;
            }
        }
        return false;
    }

}
