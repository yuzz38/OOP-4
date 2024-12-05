import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Team implements Serializable {
    private List<Participant> participants;

    public Team() {
        participants = new ArrayList<>();
    }

    // Добавление участника в команду
    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    // Удаление участника по имени
    public boolean removeParticipant(String name) {
        for (Participant participant : participants) {
            if (participant.getName().equalsIgnoreCase(name)) {
                participants.remove(participant);
                return true;
            }
        }
        return false;
    }

    // Показать всех участников
    public void displayParticipants() {
        if (participants.isEmpty()) {
            System.out.println("Команда пуста.");
        } else {
            for (Participant participant : participants) {
                System.out.println(participant);
            }
        }
    }
    public void displayAthlete() {
        if (participants.isEmpty()) {
            System.out.println("Команда атлетов пуста.");
        } else {
            for (Participant participant : participants) {
                if (participant instanceof Athlete) {
                    System.out.println(participant);

                }
            }
        }
    }
    // Выполнить действия для всех участников
    public void performActionForAll() {
        for (Participant participant : participants) {
            participant.action();
        }
    }

    // Найти участника по имени
    public Participant findParticipant(String name) {
        for (Participant participant : participants) {
            if (participant.getName().equalsIgnoreCase(name)) {
                return participant;
            }
        }
        return null;
    }

    // Проверка на наличие тренера
    public boolean hasTrainer() {
        for (Participant participant : participants) {
            if (participant instanceof Coach) {
                return true;
            }
        }
        return false;
    }
}