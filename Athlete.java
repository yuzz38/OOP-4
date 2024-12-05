class Athlete extends Participant {

    public Athlete(String name, int age) {
        super(name, age);
    }

    @Override
    public void action() {
        System.out.println(getName() + " выполняет упражнение.");
    }

    @Override
    public String toString() {
        return "Спортсмен: " + getName() + ", возраст: " + getAge();
    }
}